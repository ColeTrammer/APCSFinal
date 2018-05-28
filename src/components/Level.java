package components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.entities.Entity;
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
import java.util.function.BooleanSupplier;

/**
 * Represents a Level...
 */
public class Level {
    private final EntityManager manager;
    private final Timer timer;
    private BooleanSupplier isLevelOver;
    /**
     * Will be changed soon...
     */
    public Level(String path) {
        manager = new ArrayEntityManager();
        timer = new Timer();

        readLevel(path);
    }

    private void readLevel(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            final Map<String, Float> vars = initVars();
            final Map<String, Object> objects = new HashMap<>();
            objects.put("__manager", manager);
            objects.put("__timer", timer);
            StringBuilder file = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                file.append(line);
                line = reader.readLine();
            }
            reader.close();
            for (String statement : file.toString().replaceAll("/\\*.*?\\*/", "").split(";\\w*")) {
                statement = replaceVars(statement, vars);
                statement = replaceExpressions(statement, "{{", "}}", objects);
                if (statement.startsWith("!")) {
                    addVar(statement, vars);
                }
                if (statement.startsWith("+")) {
                    addEntity(statement.substring(1), objects);
                }
                if (statement.startsWith("-")) {
                    readLevel(String.format("levels/%s", statement.substring(1)));
                }
                if (statement.startsWith("~")) {
                    addTimer(statement.substring(1), objects);
                }
                if (statement.startsWith("*")) {
                    statement = statement.substring(1);
                    if (statement.startsWith("END ")) {
                        final String command = statement.substring(5, statement.length() - 1);
                        isLevelOver = () -> {
                            boolean over = false;
                            try {
                                over = Boolean.parseBoolean(evalExpression(replaceJavaCalls(command, objects)));
                            } catch (ScriptException | ClassCastException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
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

    private String replaceExpressions(String str, String startDelimiter, String endDelimiter, Map<String, Object> objects) throws ScriptException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder expReplaced = new StringBuilder();
        while (str.contains(startDelimiter)) {
            expReplaced.append(str, 0, str.indexOf(startDelimiter));
            String code = str.substring(str.indexOf(startDelimiter) + startDelimiter.length(), str.indexOf(endDelimiter));
            code = replaceJavaCalls(code, objects);
            expReplaced.append(evalExpression(code));
            str = str.substring(str.indexOf(endDelimiter) + endDelimiter.length());
        }
        return expReplaced.append(str).toString();
    }

    private String replaceJavaCalls(String str, Map<String, Object> objects) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuilder output = new StringBuilder();
        while (str.contains("@")) {
            output.append(str, 0, str.indexOf("@"));
            str = str.substring(str.indexOf("@"));
            String objName = str.substring(1, str.indexOf("."));
            String methodName = str.substring(str.indexOf(".") + 1, str.indexOf("("));
            String argString = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
            Class<?> argTypes[] = new Class<?>[0];
            Object[] args = new Object[0];
            if (!argString.isEmpty()) {
                String[] argStrings = argString.split(", *");
                argTypes = new Class<?>[argStrings.length];
                args = new Object[argStrings.length];
                for (int i = 0; i < argStrings.length; i++) {
                    String arg = argStrings[i];
                    if (arg.contains("@")) {
                        arg = replaceJavaCalls(arg, objects);
                    }
                    argTypes[i] = String.class;
                    args[i] = arg;
                }
            }
            output.append(callMethod(methodName, objects.get(objName), argTypes, args).toString());
            str = str.substring(str.indexOf(")") + 1);
        }
        return output.append(str).toString();
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

    private void addEntity(String command, Map<String, Object> objects) throws ScriptException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        command = replaceExpressions(command, "{", "}", objects);

        String[] argStrings = command.substring(command.indexOf(" ") + 1).split(", *");
        Class[] paramsTypes = new Class[argStrings.length];
        Object[] args = new Object[argStrings.length];
        String name = null;
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
        Class<? extends Entity> type;
        if (command.contains("@")) {
            name = command.substring(command.indexOf("@") + 1, command.indexOf(" "));
            type = Class.forName(String.format("engine.entities.%s", command.substring(0, command.indexOf("@")))).asSubclass(Entity.class);
        } else {
            type = Class.forName(String.format("engine.entities.%s", command.substring(0, command.indexOf(" ")))).asSubclass(Entity.class);
        }
        Entity e = type.getConstructor(paramsTypes).newInstance(args);
        manager.add(e);
        if (name != null) {
            objects.put(name, e);
        }
    }

    private String evalExpression(String str) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine js = mgr.getEngineByName("JavaScript");
        return js.eval(str).toString();
    }

    private void addTimer(String command, Map<String, Object> objects) {
        String[] timerArgs = command.split(" ");
        float startTime = Float.parseFloat(timerArgs[1]);
        float endTime = Float.parseFloat(timerArgs[3]);
        float repeatDelay = Float.parseFloat(timerArgs[5]);
        timer.addAction(startTime, endTime, repeatDelay, () -> {
            try {
                addEntity(command.substring(command.indexOf(">") + 1), objects);
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

    private Object callMethod(String name, Object obj, Class<?>[] argTypes, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return obj.getClass().getMethod(name, argTypes).invoke(obj, args);
    }

    /**
     * Method to update the level every frame. Should be called before render.
     * @param delta time step from when this was last called.
     */
    public void update(float delta) {
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
                isLevelOver.getAsBoolean() ? LevelState.WON :
                LevelState.ONGOING;
    }

    public enum LevelState {
        ONGOING, WON, LOST
    }
}
