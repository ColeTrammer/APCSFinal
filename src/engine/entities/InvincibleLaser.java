package engine.entities;

import com.badlogic.gdx.math.Vector2;
import engine.entities.components.MovementComponent;
import engine.entities.components.Rectangle;
import engine.utils.Direction;

@SuppressWarnings("unused")
public class InvincibleLaser extends Laser {

    public InvincibleLaser(Rectangle rect, MovementComponent movementComponent, Direction expandDirection) {
        super(rect, movementComponent, expandDirection);
    }

    @Override
    public void moveOutOf(Vector2 displacement) {
    }
}
