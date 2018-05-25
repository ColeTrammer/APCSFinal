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
import java.util.HashMap;

/**
 * Represents a Level...
 */
public class Level {
    private final EntityManager manager;
    private final Timer timer;

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
            HashMap<String, Float> vars = new HashMap<>();
            Field[] constants = Constants.class.getDeclaredFields();
            for (Field f : constants) {
                if (Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()) && f.getType() == float.class) {
                    vars.put(f.getName(), f.getFloat(null));
                }
            }
            String line = reader.readLine();
            while (line != null) {
                Gdx.app.debug("Line", line);
                line = replaceVars(line, vars);
                line = replaceExpressions(line);
                Gdx.app.debug("Processed", line);
                if (line.startsWith("!")) {
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
                if (line.startsWith("+")) {
                    String command = line.substring(1);
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
                if (line.startsWith("-")) {
                    readLevel(String.format("levels/%s", line.substring(1)));
                }
                if (line.startsWith("~")) {
                    String[] timerArgs = line.split(" ");
                    float startTime = Float.parseFloat(timerArgs[1]);
                    float endTime = Float.parseFloat(timerArgs[3]);
                    float repeatDelay = Float.parseFloat(timerArgs[5]);
                    final String command = line.substring(line.indexOf(">") + 1);
                    timer.addAction(startTime, endTime, repeatDelay, () -> {
                        try {
                            String argsString = command.substring(command.indexOf(" ") + 1);
                            String[] argStrings = argsString.split(", *");
                            Class[] paramsTypes = new Class[argStrings.length];
                            Object[] args = new Object[argStrings.length];
                            for (int i = 0; i < argStrings.length; i++) {
                                String str = argStrings[i];
                                if (str.startsWith("{")) {
                                    str = String.format("%s", evalExpression(str.substring(1, str.length() - 1)));
                                }
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
                    });
                }
                line = reader.readLine();
            }

            reader.close();
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

    private String replaceVars(String str, HashMap<String, Float> vars) {
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

    private String replaceExpressions(String str) throws ScriptException {
        StringBuilder expReplaced = new StringBuilder();
        while (str.contains("{{")) {
            expReplaced.append(str, 0, str.indexOf("{{"));
            expReplaced.append(evalExpression(str.substring(str.indexOf("{{") + 2, str.indexOf("}}"))));
            str = str.substring(str.indexOf("}}") + 2);
        }
        return expReplaced.append(str).toString();
    }

    private float evalExpression(String str) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        return Float.parseFloat(engine.eval(str).toString());
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
    public boolean isPlayerExpired() {
        return manager.isPlayerExpired();
    }
}
