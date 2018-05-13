package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Enemy class
 */
public class Enemy extends RectangleEntity {
    /**
     * Basic Constructor
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity
     * @param height height of the entity
     */
    public Enemy(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void render(Object renderer_) {
        ShapeRenderer renderer = (ShapeRenderer) renderer_;
        renderer.setColor(Color.RED);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }
}
