package components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.utils.ArrayEntityManager;
import engine.utils.EntityManager;
import engine.utils.Timer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
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
        Reader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            Gdx.app.error("File Not Found", path, e);
            System.exit(1);
        }
        ScriptEngine js = new ScriptEngineManager().getEngineByName("nashorn");
        js.put("level", this);
        js.put("manager", manager);
        js.put("timer", timer);
        try {
            js.eval(reader);
        } catch (Exception e) {
            Gdx.app.error("Invalid Level", path, e);
            System.exit(1);
        }
    }

    @SuppressWarnings("unused")
    public void setIsLevelOver(BooleanSupplier isLevelOver) {
        this.isLevelOver = isLevelOver;
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
