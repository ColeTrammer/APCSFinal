package components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import engine.entities.Laser;
import engine.entities.Player;
import engine.entities.Wall;
import engine.utils.ArrayEntityManager;
import engine.utils.EntityManager;

import java.util.Random;

/**
 * Represents a Level...
 */
public class Level {
    private static final float LASER_WIDTH = 50f;
    private static final float LASER_HEIGHT = 5;
    private static final float FRACTION_OPEN = 1 / 3f;
    private static final float SPAWN_DELAY = 0.6f;

    private final EntityManager manager;
    private final Random random;
    private float elapsedTime;

    /**
     * Will be changed soon...
     */
    public Level() {
        manager = new ArrayEntityManager();
        random = new Random();
        elapsedTime = SPAWN_DELAY;

        // add Player
        manager.add(new Player(Constants.WORLD_CENTER.x, Constants.WORLD_HEIGHT * FRACTION_OPEN, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, Constants.PLAYER_SPEED, Constants.PLAYER_JUMP_HEIGHT, Constants.GRAVITY));

        // adds Walls around the screen so entities are bounded by the screen.
        manager.add(new Wall(-Constants.BORDER_WALL_THICKNESS, -Constants.BORDER_WALL_THICKNESS, Constants.BORDER_WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.BORDER_WALL_THICKNESS));
        manager.add(new Wall(Constants.BORDER_WALL_THICKNESS, -Constants.BORDER_WALL_THICKNESS, Constants.WORLD_WIDTH, Constants.BORDER_WALL_THICKNESS));
        manager.add(new Wall(Constants.BORDER_WALL_THICKNESS, Constants.WORLD_HEIGHT, Constants.WORLD_WIDTH, Constants.BORDER_WALL_THICKNESS));
        manager.add(new Wall(Constants.WORLD_WIDTH, -Constants.BORDER_WALL_THICKNESS, Constants.BORDER_WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.BORDER_WALL_THICKNESS));

        manager.add(new Wall(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT * FRACTION_OPEN));
        manager.add(new Wall(0, Constants.WORLD_HEIGHT * (1 - FRACTION_OPEN), Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT * FRACTION_OPEN));
    }

    /**
     * Method to update the level every frame. Should be called before render.
     * @param delta time step from when this was last called.
     */
    public void update(float delta) {
        elapsedTime += delta;
        if (elapsedTime > SPAWN_DELAY) {
            manager.add(new Laser(0, Constants.WORLD_HEIGHT * (random.nextFloat() * ((Constants.WORLD_HEIGHT * FRACTION_OPEN - LASER_HEIGHT) / Constants.WORLD_HEIGHT) + FRACTION_OPEN), LASER_WIDTH, LASER_HEIGHT, Constants.PLAYER_SPEED, 0));
            //manager.add(new Pulse(0, Constants.WORLD_HEIGHT * FRACTION_OPEN + (Constants.PLAYER_HEIGHT / 2), Constants.WORLD_WIDTH, LASER_HEIGHT * 2, 0.2f, 0.2f, Direction.RIGHT));
            elapsedTime = 0;
        }
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
