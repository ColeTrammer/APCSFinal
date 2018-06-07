package engine.entities.templates;

import com.badlogic.gdx.math.Vector2;
import engine.entities.behaviors.Movable;
import engine.entities.components.MovementComponent;
import engine.entities.components.Rectangle;

/**
 * Simple extension of the AbstractEntity class
 * that enables entity movement by keeping track of the entity's velocity,
 * and then using it to update it's position in
 * the update method.
 */
public abstract class MovableRectangleEntity extends RectangleEntity implements Movable {
    private final MovementComponent movementComponent;

    /**
     * Basic Constructor
     *
     */
    protected MovableRectangleEntity(Rectangle rect, MovementComponent movementComponent) {
        super(rect);
        this.movementComponent = movementComponent;
        movementComponent.setPositionComponent(rect);
    }

    @Override
    public void update(float delta) {
        movementComponent.move(delta);
        super.update(delta);
    }

    @Override
    public void moveOutOf(Vector2 displacement) {
        addX(displacement.x);
        addY(displacement.y);
    }

    @Override
    public Vector2 getVelocity() {
        return movementComponent.getVelocity();
    }
}
