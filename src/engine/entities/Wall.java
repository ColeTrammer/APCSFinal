package engine.entities;

import com.badlogic.gdx.math.Vector2;
import engine.entities.behaviors.Impassable;
import engine.entities.behaviors.Movable;
import engine.entities.components.MovementComponent;
import engine.entities.components.Rectangle;
import engine.entities.components.Stationary;
import engine.entities.templates.MovableRectangleEntity;
import engine.entities.templates.RectangleEntity;
import engine.utils.Collisions;

/**
 * An entity that other entities cannot move through. It
 * is completely static and unchanging, only interacting
 * with other entities when in a collision.
 */
public class Wall extends MovableRectangleEntity implements Impassable {
    @SuppressWarnings("unused")
    public Wall(Rectangle rect) {
        this(rect, new Stationary());
    }

    /**
     * Basic Constructor
     *
     */
    @SuppressWarnings("WeakerAccess")
    public Wall(Rectangle rect, MovementComponent movementComponent) {
        super(rect, movementComponent);
    }

    @Override
    protected String getTag() {
        return "wall";
    }

    @Override
    public void expel(Movable movable) {
        if (movable instanceof RectangleEntity) {
            Vector2 displacement = Collisions.expelDistance((RectangleEntity) movable, this);
            if (displacement.x == 0 && getVelocity().x != 0) {
                displacement.x = getVelocity().x * getDeltaTime();
            }
            movable.moveOutOf(displacement);
        }
    }
}
