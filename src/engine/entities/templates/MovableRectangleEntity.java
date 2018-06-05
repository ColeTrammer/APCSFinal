package engine.entities.templates;

import com.badlogic.gdx.math.Vector2;
import engine.entities.behaviors.Movable;

/**
 * Simple extension of the AbstractEntity class
 * that enables entity movement by keeping track of the entity's velocity,
 * and then using it to update it's position in
 * the update method.
 */
public abstract class MovableRectangleEntity extends RectangleEntity implements Movable {
    private final Vector2 velocity;

    /**
     * Basic Constructor
     *
     * @param x      x-coordinate of the entity's position.
     * @param y      y-coordinate of the entity's position.
     * @param width  width of the entity.
     * @param height height of the entity.
     * @param velX   x-component of the entity's velocity.
     * @param velY   y-component of the entity's velocity.
     */
    protected MovableRectangleEntity(float x, float y, float width, float height, float velX, float velY) {
        super(x, y, width, height);
        velocity = new Vector2(velX, velY);
    }

    @Override
    public void update(float delta) {
        addX(velocity.x * delta);
        addY(velocity.y * delta);
        super.update(delta);
    }

    @Override
    public void moveOutOf(Vector2 displacement) {
        addX(displacement.x);
        addY(displacement.y);
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    protected float getVelocityX() {
        return velocity.x;
    }

    protected float getVelocityY() {
        return velocity.y;
    }

    //public void setVelocity(Vector2 velocity) { this.velocity = velocity; }
    //public void setVelocity(float x, float y) { velocity.set(x, y); }
    protected void setVelocityX(float x) {
        velocity.set(x, velocity.y);
    }

    protected void setVelocityY(float y) {
        velocity.set(velocity.x, y);
    }

    //public void addVelocity(Vector2 vec) { velocity.set(velocity.x + vec.x, velocity.y + vec.y); }
    protected void addVelocity(float x, float y) {
        velocity.set(velocity.x + x, velocity.y + y);
    }

    protected void addVelocityX(float x) {
        velocity.set(velocity.x + x, velocity.y);
    }
    //public void addVelocityY(float y) { velocity.set(velocity.x, velocity.y + y); }

    //public void subVelocity(Vector2 velocity) { this.velocity = this.velocity.sub(velocity); }
    protected void subVelocityX(float x) {
        velocity.set(velocity.x - x, velocity.y);
    }
    //public void subVelocityY(float y) { velocity.set(velocity.x, velocity.y - y); }

    //public void invertVelocityX() { velocity.set(-velocity.x, velocity.y); }
    //public void invertVelocityY() { velocity.set(velocity.x, -velocity.y); }
    //public void invertVelocity() { velocity.set(-velocity.x, -velocity.y); }
}
