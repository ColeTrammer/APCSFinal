package components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            HashMap<String, Float> vars = new HashMap<>();
            Field[] constants = Constants.class.getDeclaredFields();
            for (Field f : constants) {
                if (Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()) && f.getType() == float.class) {
                    vars.put(f.getName(), f.getFloat(null));
                }
            }
            String line = reader.readLine();
            while (line != null) {
                //Gdx.app.debug("Line", line);
                StringBuilder varReplaced = new StringBuilder();
                String temp = line;
                while (temp.contains("$")) {
                    varReplaced.append(temp, 0, temp.indexOf("$"));
                    temp = temp.substring(temp.indexOf("$") + 1);
                    int i;
                    for (i = 0; i < temp.length(); i++) {
                        if (temp.substring(i, i + 1).matches("\\W")) {
                            break;
                        }
                    }
                    String name = temp.substring(0, i);
                    varReplaced.append(String.format("%f", vars.get(name)));
                    temp = temp.substring(i);
                }
                varReplaced.append(line);
                line = varReplaced.toString();
                List<Float> expressions = new ArrayList<>();
                while (line.contains("{{")) {
                    Gdx.app.debug("line", line);
                    Gdx.app.debug("Exp", line.substring(line.indexOf("{{") + 2, line.indexOf("}}")));
                    expressions.add((float)((double)((Double) engine.eval(line.substring(line.indexOf("{{") + 2, line.indexOf("}}"))))));
                    line = line.substring(line.indexOf("}}") + 2);
                }
                //Gdx.app.debug("Expressions", expressions.toString());
                if (line.startsWith("!")) {
                    int i;
                    for (i = 0; i < temp.length(); i++) {
                        if (temp.substring(i, i + 1).matches("\\W")) {
                            break;
                        }
                    }
                    //String name = line.substring(1, i);
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
        }
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
