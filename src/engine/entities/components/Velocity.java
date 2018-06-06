package engine.entities.components;

import com.badlogic.gdx.math.Vector2;

public class Velocity extends AbstractMovementComponent {
    private Vector2 velocity;

    public Velocity(PositionComponent positionComponent, float vx, float vy) {
        super(positionComponent);
        this.velocity = new Vector2(vx, vy);
    }

    @Override
    public void move(float delta) {
        getPositionComponent().setX(getPositionComponent().getX() + velocity.x * delta);
        getPositionComponent().setY(getPositionComponent().getY() + velocity.y * delta);
    }

    public Vector2 getVelocity() { return velocity; }
}
