package engine.entities;

import com.badlogic.gdx.math.Vector2;
import engine.utils.Direction;

@SuppressWarnings("unused")
public class InvincibleLaser extends Laser {

    public InvincibleLaser(float x, float y, float width, float height, float velX, float velY, Direction expandDirection) {
        super(x, y, width, height, velX, velY, expandDirection);
    }

    @Override
    public void moveOutOf(Vector2 displacement) {}
}
