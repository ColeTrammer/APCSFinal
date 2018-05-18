package components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import engine.entities.Player;
import engine.entities.Wall;
import engine.utils.ArrayEntityManager;
import engine.utils.EntityManager;

public class Level {
    private EntityManager manager;

    public Level() {
        manager = new ArrayEntityManager();

        // add Player
        manager.add(new Player(Constants.WORLD_CENTER.x, Constants.WORLD_CENTER.y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, Constants.PLAYER_SPEED, Constants.PLAYER_JUMP_HEIGHT, Constants.GRAVITY));

        // adds Walls around the screen so entities are bounded by the screen.
        manager.add(new Wall(-Constants.WALL_THICKNESS, -Constants.WALL_THICKNESS, Constants.WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.WALL_THICKNESS));
        manager.add(new Wall(Constants.WALL_THICKNESS, -Constants.WALL_THICKNESS, Constants.WORLD_WIDTH, Constants.WALL_THICKNESS));
        manager.add(new Wall(Constants.WALL_THICKNESS, Constants.WORLD_HEIGHT, Constants.WORLD_WIDTH, Constants.WALL_THICKNESS));
        manager.add(new Wall(Constants.WORLD_WIDTH, -Constants.WALL_THICKNESS, Constants.WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.WALL_THICKNESS));

        manager.add(new Wall(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT * 1 / 5));
        manager.add(new Wall(0, Constants.WORLD_HEIGHT * 4 / 5, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT * 1 / 5));
    }

    public void update(float delta) {
        manager.update(delta);
    }

    public void render(ShapeRenderer renderer, SpriteBatch batch) {
        manager.render(renderer, batch);
    }

    public boolean isPlayerExpired() {
        return manager.isPlayerExpired();
    }
}
