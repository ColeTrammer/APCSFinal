package engine.entities.components;

import com.badlogic.gdx.math.Vector2;

public class Acceleration extends Velocity {
    private Vector2 acceleration;

    public Acceleration(PositionComponent positionComponent, float vx, float vy, float ax, float ay) {
        super(positionComponent, vx, vy);
        this.acceleration = new Vector2(ax, ay);
    }

    @Override
    public void move(float delta) {
        getVelocity().set(getVelocity().x + acceleration.x * delta, getVelocity().y + acceleration.y * delta);
        super.move(delta);
    }

    protected Vector2 getAcceleration() { return acceleration; }
}
