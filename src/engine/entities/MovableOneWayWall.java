package engine.entities;

import engine.entities.behaviors.Movable;
import engine.utils.Direction;

/**
 * Wall that only allows motion in a singular direction.
 */
@SuppressWarnings("unused")
public class MovableOneWayWall extends MovableWall {
    private final Direction allow;

    public MovableOneWayWall(float x, float y, float width, float height, float velX, float velY, float boundX, float boundY, float boundWidth, float boundHeight, Direction allow) {
        super(x, y, width, height, velX, velY, boundX, boundY, boundWidth, boundHeight);
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
