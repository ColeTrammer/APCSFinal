package engine.entities;

import engine.entities.behaviors.Movable;
import engine.entities.components.MovementComponent;
import engine.entities.components.Rectangle;
import engine.entities.components.Stationary;
import engine.utils.Direction;

/**
 * Wall that only allows motion in a singular direction.
 */
@SuppressWarnings("unused")
public class OneWayWall extends Wall {
    private final Direction allow;

    public OneWayWall(Rectangle rect, Direction allow) {
        this(rect, new Stationary(), allow);
    }

    /**
     * Constructs a wall that allows motion in one direction.
     *
     * @param allow  the direction that should be allowed.
     */
    @SuppressWarnings("WeakerAccess")
    public OneWayWall(Rectangle rect, MovementComponent movementComponent, Direction allow) {
        super(rect, movementComponent);
        this.allow = allow;
    }

    @Override
    public void expel(Movable movable) {
        if ((movable.getVelocity().x >= 0 || allow != Direction.RIGHT) &&
                (movable.getVelocity().x <= 0 || allow != Direction.LEFT) &&
                (movable.getVelocity().y >= 0 || allow != Direction.UP) &&
                (movable.getVelocity().y <= 0 || allow != Direction.DOWN)) {
            super.expel(movable);
        }
    }
}
