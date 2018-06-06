package engine.entities.components;

import com.badlogic.gdx.math.Vector2;

public interface MovementComponent {
    Vector2 getVelocity();
    void move(float delta);
}
