package entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents an entity with an acceleration.
 * The acceleration is by default added to the
 * velocity every frame in the update method.
 */
public abstract class AcceleratingRectangleEntity extends MovableRectangleEntity {
    private Vector2 acceleration;

    /**
     * Basic Constructor
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity.
     * @param height height of the entity.
     * @param velX x-component of the entity's velocity.
     * @param velY y-component of the entity's velocity.
     * @param accX x-component of the entity's acceleration.
     * @param accY y-component of the entity's acceleration.
     */
    public AcceleratingRectangleEntity(float x, float y, float width, float height, float velX, float velY, float accX, float accY) {
        super(x, y, width, height, velX, velY);
        acceleration = new Vector2(accX, accY);
    }

    @Override
    public void update(float delta) {
        addVelocity(acceleration.x * delta, acceleration.y * delta);
        super.update(delta);
    }

    //public Vector2 getAcceleration() { return acceleration; }
    //public float getAccelerationX() { return acceleration.x; }
    //public float getAccelerationY() { return acceleration.y; }

    //public void setAcceleration(Vector2 acceleration) { this.acceleration = acceleration; }
    //public void setAcceleration(float x, float y) { acceleration.set(x, y); }
    //public void setAccelerationX(float x) { acceleration.set(x, acceleration.y); }
    //public void setAccelerationY(float y) { acceleration.set(acceleration.x, y); }

    //public void addAcceleration(Vector2 acceleration) { this.acceleration = this.acceleration.add(acceleration); }
    //public void addAccelerationX(float x) { acceleration.set(acceleration.x + x, acceleration.y); }
    //public void addAccelerationY(float y) { acceleration.set(acceleration.x, acceleration.y + y); }

    //public void subAcceleration(Vector2 acceleration) { this.acceleration = this.acceleration.sub(acceleration); }
    //public void subAccelerationX(float x) { acceleration.set(acceleration.x - x, acceleration.y); }
    //public void subAccelerationY(float y) { acceleration.set(acceleration.x, acceleration.y - y); }

    //public void invertAccelerationX() { acceleration.set(-acceleration.x, acceleration.y); }
    //public void invertAccelerationY() { acceleration.set(acceleration.x, -acceleration.y); }
    //public void invertAcceleration() { acceleration.set(-acceleration.x, -acceleration.y); }
}
