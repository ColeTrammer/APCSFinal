package components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.entities.Entity;
import engine.entities.Player;
import engine.utils.ArrayEntityManager;
import engine.utils.Direction;
import engine.utils.EntityManager;
import engine.utils.Timer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Represents a Level...
 */
public class Level {
    private final EntityManager manager;
    private final Timer timer;
    private float elapsedTime;
    private Predicate<Float> isLevelOver;

    /**
     * Will be changed soon...
     */
    public Level(String path) {
        manager = new ArrayEntityManager();
        timer = new Timer();
        elapsedTime = 0;

        readLevel(path);
    }

    private void readLevel(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            Map<String, Float> vars = initVars();
            StringBuilder file = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                file.append(line);
                line = reader.readLine();
            }
            reader.close();
            for (String statement : file.toString().replaceAll("/\\*.*?\\*/", "").split(";\\w*")) {
                statement = replaceVars(statement, vars);
                statement = replaceExpressions(statement, "{{", "}}");
                if (statement.startsWith("!")) {
                    addVar(statement, vars);
                }
                if (statement.startsWith("+")) {
                    addEntity(statement.substring(1));
                }
                if (statement.startsWith("-")) {
                    readLevel(String.format("levels/%s", statement.substring(1)));
                }
                if (statement.startsWith("~")) {
                    addTimer(statement.substring(1));
                }
                if (statement.startsWith("*")) {
                    statement = statement.substring(1);
                    if (statement.startsWith("END ")) {
                        final String command = statement.substring(5, statement.length() - 1);
                        isLevelOver = (elapsedTime) -> {
                            boolean over = false;
                            try {
                                over = Boolean.parseBoolean(evalExpression(command, "__elapsedTime", elapsedTime));
                            } catch (ScriptException | ClassCastException e) {
                                Gdx.app.error("Invalid expression", path, e);
                                System.exit(1);
                            }
                            return over;
                        };
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Gdx.app.error("File Not Found", path, e);
            System.exit(1);
        } catch (IOException e) {
            Gdx.app.error("IO Problem", path, e);
            System.exit(1);
        } catch (ScriptException | ClassCastException e) {
            Gdx.app.error("Invalid expression", path, e);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            Gdx.app.error("Invalid entity", path, e);
            System.exit(1);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            Gdx.app.error("Invalid entity parameters", path, e);
            System.exit(1);
        }
    }

    private Map<String, Float> initVars() throws IllegalAccessException {
        Map<String, Float> vars = new HashMap<>();
        Field[] constants = Constants.class.getDeclaredFields();
        for (Field f : constants) {
            if (Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()) && f.getType() == float.class) {
                vars.put(f.getName(), f.getFloat(null));
            }
        }
        return vars;
    }

    private String replaceVars(String str, Map<String, Float> vars) {
        StringBuilder varReplaced = new StringBuilder();
        while (str.contains("$")) {
            varReplaced.append(str, 0, str.indexOf("$"));
            str = str.substring(str.indexOf("$") + 1);
            int i;
            for (i = 0; i < str.length(); i++) {
                if (str.substring(i, i + 1).matches("\\W")) {
                    break;
                }
            }
            String name = str.substring(0, i);
            varReplaced.append(String.format("%f", vars.get(name)));
            str = str.substring(i);
        }
        return varReplaced.append(str).toString();
    }

    private String replaceExpressions(String str, String startDelimiter, String endDelimiter) throws ScriptException {
        StringBuilder expReplaced = new StringBuilder();
        while (str.contains(startDelimiter)) {
            expReplaced.append(str, 0, str.indexOf(startDelimiter));
            expReplaced.append(evalExpression(str.substring(str.indexOf(startDelimiter) + startDelimiter.length(), str.indexOf(endDelimiter))));
            str = str.substring(str.indexOf(endDelimiter) + endDelimiter.length());
        }
        return expReplaced.append(str).toString();
    }

    private void addVar(String line, Map<String, Float> vars) {
        line = line.replaceAll(" ", "");
        int i;
        for (i = 1; i < line.length(); i++) {
            if (line.charAt(i) == '=') {
                break;
            }
        }
        String name = line.substring(1, i);
        vars.put(name, Float.parseFloat(line.substring(i + 1)));
    }

    private void addEntity(String command) throws ScriptException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        command = replaceExpressions(command, "{", "}");
        Gdx.app.debug("Command", command);
        String[] argStrings = command.substring(command.indexOf(" ") + 1).split(", *");
        Class[] paramsTypes = new Class[argStrings.length];
        Object[] args = new Object[argStrings.length];
        for (int i = 0; i < argStrings.length; i++) {
            String str = argStrings[i];
            if (str.startsWith("[[")) {
                paramsTypes[i] = Direction.class;
                args[i] = Direction.valueOf(str.substring(2, str.length() - 2));
            } else {
                paramsTypes[i] = float.class;
                args[i] = Float.parseFloat(str);
            }
        }
        Class<? extends Entity> type = Class.forName(String.format("engine.entities.%s", command.substring(0, command.indexOf(" ")))).asSubclass(Entity.class);
        manager.add(type.getConstructor(paramsTypes).newInstance(args));
    }

    private String evalExpression(String str) throws ScriptException {
        return evalExpression(str, null, null);
    }

    private String evalExpression(String str, String varName, Float value) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine js = mgr.getEngineByName("JavaScript");
        if (varName != null && value != null) {
            js.put(varName, value);
        }
        Iterator<Entity> players = manager.getByType(Player.class);
        players.forEachRemaining((e) -> js.put("__playerY", ((Player) e).getY()));
        return js.eval(str).toString();
    }

    private void addTimer(String command) {
        String[] timerArgs = command.split(" ");
        float startTime = Float.parseFloat(timerArgs[1]);
        float endTime = Float.parseFloat(timerArgs[3]);
        float repeatDelay = Float.parseFloat(timerArgs[5]);
        timer.addAction(startTime, endTime, repeatDelay, () -> {
            try {
                addEntity(command.substring(command.indexOf(">") + 1));
            } catch (ScriptException | ClassCastException e) {
                Gdx.app.error("Invalid expression", command, e);
                System.exit(1);
            } catch (ClassNotFoundException e) {
                Gdx.app.error("Invalid entity", command, e);
                System.exit(1);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                Gdx.app.error("Invalid entity parameters", command, e);
                System.exit(1);
            }
        });
    }

    /**
     * Method to update the level every frame. Should be called before render.
     * @param delta time step from when this was last called.
     */
    public void update(float delta) {
        elapsedTime += delta;
        timer.tick(delta);
        manager.update(delta);
    }

    /**
     * Renders all components of the level.
     * @param renderer ShapeRenderer to use.
     * @param batch SpriteBatch to use.
     */
    public void render(ShapeRenderer renderer, SpriteBatch batch) {
        manager.render(renderer, batch);
    }

    /**
     * Determines whether the player is expired.
     * @return true if player is expired. False otherwise.
     */
    public LevelState getLevelState() {
        return manager.isPlayerExpired() ? LevelState.LOST :
                isLevelOver.test(elapsedTime) ? LevelState.WON :
                LevelState.ONGOING;
    }

    public enum LevelState {
        ONGOING, WON, LOST
    }
}
