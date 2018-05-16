package engine.entities;

import engine.entities.behaviors.Movable;
import engine.utils.Direction;

/**
 * Wall that only blocks motion in a singular direction.
 */
public class OneWayWall extends Wall {
    private Direction block;

    /**
     * Constructs a wall that blocks motion in one direction.
     * @param x      x-coordinate of the entity's position.
     * @param y      y-coordinate of the entity's position.
     * @param width  width of the entity.
     * @param height height of the entity.
     * @param block the direction that should be blocked.
     */
    public OneWayWall(float x, float y, float width, float height, Direction block) {
        super(x, y, width, height);
        this.block = block;
    }

    @Override
    public void expel(Movable movable) {
        if (movable.getVelocity().x < 0 && block == Direction.RIGHT ||
                movable.getVelocity().x > 0 && block == Direction.LEFT ||
                movable.getVelocity().y < 0 && block == Direction.UP ||
                movable.getVelocity().y > 0 && block == Direction.DOWN) {
            super.expel(movable);
        }
    }
}
