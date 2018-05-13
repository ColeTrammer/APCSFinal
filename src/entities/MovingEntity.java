package entities;

import com.badlogic.gdx.math.Vector2;

public class MovingEntity extends AbstractEntity {
    private Vector2 velocity;

    //public MovingEntity() { this(0, 0, 0, 0, 0, 0); }
    public MovingEntity(float x, float y, float width, float height) { this(x, y, width, height, 0, 0); }
    public MovingEntity(float x, float y, float width, float height, float velX, float velY) {
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
}
