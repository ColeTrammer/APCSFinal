package entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Simple extension of the AbstractEntity class
 * that enables entity movement by keeping track of the entity's velocity,
 * and then using it to update it's position in
 * the update method.
 */
public abstract class MovableRectangleEntity extends RectangleEntity {
    private Vector2 velocity;

    //public MovableRectangleEntity() { this(0, 0, 0, 0, 0, 0); }
    /**
     * Basic Constructor (initialized entity's velocity to { 0, 0 })
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity
     * @param height height of the entity
     */
    public MovableRectangleEntity(float x, float y, float width, float height) { this(x, y, width, height, 0, 0); }

    /**
     * Basic Constructor
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity
     * @param height height of the entity
     * @param velX x-component of the entity's velocity
     * @param velY y-component of the entity's velocity
     */
    public MovableRectangleEntity(float x, float y, float width, float height, float velX, float velY) {
        super(x, y, width, height);
        velocity = new Vector2(velX, velY);
    }

    @Override
    public void update(float delta) {
        addX(velocity.x * delta);
        addY(velocity.y * delta);
    }

    public Vector2 getVelocity() { return velocity; }
    //public float getVelocityX() { return velocity.x; }
    //public float getVelocityY() { return velocity.y; }

    //public void setVelocity(Vector2 velocity) { this.velocity = velocity; }
    public void setVelocity(float x, float y) { velocity.set(x, y); }
    //public void setVelocityX(float x) { velocity.set(x, velocity.y); }
    //public void setVelocityY(float y) { velocity.set(velocity.x, y); }

    public void addVelocityX(float x) { velocity.set(velocity.x + x, velocity.y); }
    public void addVelocityY(float y) { velocity.set(velocity.x, velocity.y + y); }

    public void subVelocityX(float x) { velocity.set(velocity.x - x, velocity.y); }
    public void subVelocityY(float y) { velocity.set(velocity.x, velocity.y - y); }

    public void invertVelocityX() { velocity.set(-velocity.x, velocity.y); }
    public void invertVelocityY() { velocity.set(velocity.x, -velocity.y); }
    //public void invertVelocity() { velocity.set(-velocity.x, -velocity.y); }
}
