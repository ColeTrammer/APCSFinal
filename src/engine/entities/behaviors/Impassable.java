package engine.entities.behaviors;

import engine.entities.Entity;

/**
 * Represents an entity that other entities cannot move though.
 * Accomplishes this through the expel method.
 */
public interface Impassable extends Entity {
    /**
     * Moves the overlapping entity out of
     * the impassable one.
     * @param movable The entity to expel.
     */
    void expel(Movable movable);
}
