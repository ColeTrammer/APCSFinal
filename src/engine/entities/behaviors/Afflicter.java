package engine.entities.behaviors;

import engine.entities.Entity;

/**
 * Represents an Entity that can give damage, harm,
 * or status ailments to an Afflictable Entity.
 */
public interface Afflicter extends Entity {
    /**
     * Damages the entity passed in.
     * @param afflictable The Entity to damage.
     */
    void giveDamage(Afflictable afflictable);
}
