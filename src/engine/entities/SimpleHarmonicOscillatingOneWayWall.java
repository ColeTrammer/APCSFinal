package engine.entities;

import engine.entities.behaviors.Movable;
import engine.utils.Direction;

@SuppressWarnings("unused")
public class SimpleHarmonicOscillatingOneWayWall extends SimpleHarmonicOscillatingWall {
    private Direction allow;

    public SimpleHarmonicOscillatingOneWayWall(float x, float y, float width, float height, float centerX, float centerY, float amplitudeX, float amplitudeY, float k, Direction allow) {
        super(x, y, width, height, centerX, centerY, amplitudeX, amplitudeY, k);
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
