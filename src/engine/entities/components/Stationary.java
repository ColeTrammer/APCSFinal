package engine.entities.components;

import com.badlogic.gdx.math.Vector2;

@SuppressWarnings("unused")
public class Stationary extends AbstractMovementComponent {
    private static final Vector2 v = new Vector2(0,0);

    @Override
    public void move(float delta) {}

    @Override
    public Vector2 getVelocity() {
        return v;
    }
}
