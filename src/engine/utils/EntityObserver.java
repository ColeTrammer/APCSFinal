package engine.utils;

import engine.entities.Entity;

/**
 * An interface that describes the behavior necessary
 * to react to an entity's change in state.
 */
public interface EntityObserver {

    /**
     * This method will be called by the entity
     * to notify the observer that the entity
     * has expired.
     *
     * @param entity The entity to expire.
     */
    void expire(Entity entity);

    /**
     * This method will be called by the entity
     * to notify the observer that the entity
     * has spawned another entity.
     *
     * @param entity The entity to be spawned.
     */
    void spawn(Entity entity);
}
