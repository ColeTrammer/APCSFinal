package engine.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import engine.entities.Entity;

/**
 * Interface used to keep track of all the entities currently
 * part of the game. As such, it allows for all entities
 * to be rendered and updated through a singular method calls,
 * and for entities to be added explicitly to the manager's scope.
 */
public interface EntityManager extends EntityObserver {
    /**
     * Calls the update method of each and every entity,
     * and checks for collisions between any two entities.
     * @param delta time step from when this was last called.
     */
    void update(float delta);

    /**
     * Handles rendering of all entities, and takes into account
     * the possibility of a group of entities that require different
     * objects to be rendered.
     * @param renderer ShapeRenderer to be used to render entities that require it.
     * @param batch SpriteBatch to be used to render entities that require it.
     */
    void render(ShapeRenderer renderer, SpriteBatch batch);

    /**
     * Tells the manager to update and render
     * the entity every frame.
     * @param entity The entity to add to the manager's list
     */
    void add(Entity entity);

    /**
     * determines whether or not the Player is expired.
     * @return true if the player is expired, false otherwise.
     */
    boolean isPlayerExpired();

    /**
     * Resets the manager to a default state
     * where it tracks zero entities.
     */
    void reset();
}
