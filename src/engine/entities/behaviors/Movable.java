package engine.entities.behaviors;

import com.badlogic.gdx.math.Vector2;
import engine.entities.Entity;

/**
 * Represents an entity that is able to move
 * and necessarily collide with other entities.
 */
public interface Movable extends Entity {
    /**
     * Tells the entity how to move out
     * of a collision by giving it a displacement.
     * @param displacement Vector2 telling the entity how to move.
     */
    void moveOutOf(Vector2 displacement);

    /**
     * Gets the entity's velocity.
     * @return Vector2 representing velocity.
     */
    Vector2 getVelocity();
}
