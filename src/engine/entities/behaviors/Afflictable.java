package engine.entities.behaviors;

import engine.entities.Entity;

/**
 * Represents an Entity that is afflictable, that is
 * to be able to be harmed, damages, or affected by
 * status ailments.
 */
public interface Afflictable extends Entity {
    /**
     * Receives damage.
     */
    void receiveDamage();
}
