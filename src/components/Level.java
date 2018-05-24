package components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.entities.Entity;
import engine.utils.ArrayEntityManager;
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
import java.util.Arrays;
import java.util.Collections;
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
//        // add Player
//        manager.add(new Player(Constants.WORLD_CENTER.x, Constants.WORLD_HEIGHT * FRACTION_OPEN, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, Constants.PLAYER_SPEED, Constants.PLAYER_JUMP_HEIGHT, Constants.GRAVITY));
//
//        // adds Walls around the screen so entities are bounded by the screen.
//        manager.add(new Wall(-Constants.BORDER_WALL_THICKNESS, -Constants.BORDER_WALL_THICKNESS, Constants.BORDER_WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.BORDER_WALL_THICKNESS));
//        manager.add(new Wall(Constants.BORDER_WALL_THICKNESS, -Constants.BORDER_WALL_THICKNESS, Constants.WORLD_WIDTH, Constants.BORDER_WALL_THICKNESS));
//        manager.add(new Wall(Constants.BORDER_WALL_THICKNESS, Constants.WORLD_HEIGHT, Constants.WORLD_WIDTH, Constants.BORDER_WALL_THICKNESS));
//        manager.add(new Wall(Constants.WORLD_WIDTH, -Constants.BORDER_WALL_THICKNESS, Constants.BORDER_WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.BORDER_WALL_THICKNESS));
//
//        manager.add(new Wall(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT * FRACTION_OPEN));
//        manager.add(new Wall(0, Constants.WORLD_HEIGHT * (1 - FRACTION_OPEN), Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT * FRACTION_OPEN));
//
//        timer.addAction(0.3f, 15f, 1f,
//            () -> manager.add(
//                new Pulse(0, Constants.WORLD_HEIGHT * FRACTION_OPEN + (Constants.PLAYER_HEIGHT / 2), Constants.WORLD_WIDTH, LASER_HEIGHT * 2, 0.5f, 0.2f, Direction.RIGHT))
//        );
//
//        timer.addAction(0f, 15f, 0.6f,
//            () -> manager.add(
//                new Laser(0, Constants.WORLD_HEIGHT * (random.nextFloat() * ((Constants.WORLD_HEIGHT * FRACTION_OPEN - LASER_HEIGHT) / Constants.WORLD_HEIGHT) + FRACTION_OPEN), LASER_WIDTH, LASER_HEIGHT, Constants.PLAYER_SPEED, 0))
//        );
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
                    line = line.substring(1);
                    String[] argStrings = line.substring(line.indexOf(" ") + 1).split(" +");
                    Float[] args = Arrays.stream(argStrings).mapToDouble(Double::parseDouble).mapToObj(d -> (float) d).toArray(Float[]::new);
                    Class[] paramsTypes = Collections.nCopies(args.length, float.class).toArray(new Class[args.length]);
                    Class<? extends Entity> type = Class.forName(String.format("engine.entities.%s", line.substring(0, line.indexOf(" ")))).asSubclass(Entity.class);
                    manager.add(type.getConstructor(paramsTypes).newInstance((Object[]) args));
                }
                if (line.startsWith("-")) {
                    readLevel(String.format("levels/%s", line.substring(1)));
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
        } catch (IllegalAccessException e) {
            Gdx.app.error("Constants Broken", path, e);
            System.exit(1);
        } catch (ScriptException | ClassCastException e) {
            Gdx.app.error("Invalid expression", path, e);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            Gdx.app.error("Invalid entity", path, e);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            Gdx.app.error("Invalid entity parameters", path, e);
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
