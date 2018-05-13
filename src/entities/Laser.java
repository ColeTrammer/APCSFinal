package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Laser extends MovableRectangleEntity {
    /**
     * Basic Constructor
     *
     * @param x      x-coordinate of the entity's position.
     * @param y      y-coordinate of the entity's position.
     * @param width  width of the entity
     * @param height height of the entity
     * @param velX   x-component of the entity's velocity
     * @param velY   y-component of the entity's velocity
     */
    public Laser(float x, float y, float width, float height, float velX, float velY) {
        super(x, y, width, height, velX, velY);
    }

    @Override
    public void render(Object rendererTool) {
        ShapeRenderer renderer = (ShapeRenderer) rendererTool;
        renderer.setColor(Color.RED);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }
}
