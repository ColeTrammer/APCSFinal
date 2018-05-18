package engine.entities;

import engine.entities.behaviors.Movable;
import engine.utils.Direction;

/**
 * Wall that only allows motion in a singular direction.
 */
public class OneWayWall extends Wall {
    private Direction allow;

    /**
     * Constructs a wall that allows motion in one direction.
     * @param x      x-coordinate of the entity's position.
     * @param y      y-coordinate of the entity's position.
     * @param width  width of the entity.
     * @param height height of the entity.
     * @param allow the direction that should be allowed.
     */
    public OneWayWall(float x, float y, float width, float height, Direction allow) {
        super(x, y, width, height);
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
