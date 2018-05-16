package engine.entities;

import engine.entities.behaviors.Impassable;
import engine.entities.behaviors.Movable;
import engine.entities.templates.MovableRectangleEntity;
import engine.entities.templates.RectangleEntity;
import engine.utils.Collisions;

/**
 * An entity that other entities cannot move through. It
 * is completely static and unchanging, only interacting
 * with other entities when in a collision.
 */
public class Wall extends RectangleEntity implements Impassable {
    /**
     * Basic Constructor
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity.
     * @param height height of the entity.
     */
    public Wall(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void expel(Movable movable) {
        if (movable instanceof MovableRectangleEntity) {
            movable.moveOutOf(Collisions.expelDistance((MovableRectangleEntity) movable, this));
        }
    }
}
