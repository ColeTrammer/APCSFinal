package engine.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import engine.entities.templates.MovableRectangleEntity;

/**
 * The laser class represents a laser that damages the player
 * upon impact, and is destroyed when it comes in contact
 * with a Wall. It moves according it's velocity, and
 * can expand it's dimensions over time to give the illusion
 * of coming from outside of the screen.
 */
public class Laser extends MovableRectangleEntity {
    private float targetLength;
    private ExpandDirection expandDirection;

    /**
     * Constructs a laser that expands in the direction that
     * requires the most expansion.
     * @param x      x-coordinate of the entity's position.
     * @param y      y-coordinate of the entity's position.
     * @param width  width of the entity.
     * @param height height of the entity.
     * @param velX   x-component of the entity's velocity. Must be non-zero if width >= height.
     * @param velY   y-component of the entity's velocity. Must be non-zero if height > width.
     */
    public Laser(float x, float y, float width, float height, float velX, float velY) {
        this(x, y, width, height, velX, velY, width >= height ? ExpandDirection.X : ExpandDirection.Y);
    }

    /**
     * Creates a laser with specified bounds and velocity,
     * that starts with 0 length in the expansion direction,
     * and increases that dimension by the velocity in the
     * specified direction until it reaches the length
     * specified. The expansion direction must have a
     * corresponding non-zero velocity. Does not expand if
     * passed in NONE.
     * @param x      x-coordinate of the entity's position.
     * @param y      y-coordinate of the entity's position.
     * @param width  width of the entity.
     * @param height height of the entity.
     * @param velX   x-component of the entity's velocity.
     * @param velY   y-component of the entity's velocity.
     * @param expandDirection the direction to expand in. If NONE, no expansion occurs.
     */
    public Laser(float x, float y, float width, float height, float velX, float velY, ExpandDirection expandDirection) {
        super(x, y, width, height, velX, velY);
        this.expandDirection = expandDirection;
        if (expandDirection == ExpandDirection.X && velX == 0 || expandDirection == ExpandDirection.Y && velY == 0) {
            throw new IllegalArgumentException(String.format("A laser cannot expand in the %s direction and have 0 %s velocity.", expandDirection.name(), expandDirection.name()));
        }
        if (expandDirection == ExpandDirection.X) {
            setWidth(0);
            targetLength = width;
        } else if (expandDirection == ExpandDirection.Y) {
            setHeight(0);
            targetLength = height;
        }
    }

    /**
     * Specifies the directions that a laser can expand in.
     * Does not expand if the direction is NONE.
     */
    public enum ExpandDirection {
        X, Y, NONE
    }

    @Override
    public void update(float delta) {
        if (expandDirection == ExpandDirection.X) {
            if (getWidth() < targetLength) {
                if (getVelocityX() > 0) {
                    addWidth(getVelocityX() * delta);
                } else {
                    addWidth(-getVelocityX() * delta);
                    addX(getVelocityX() * delta);
                }
            } else {
                expandDirection = ExpandDirection.NONE;
            }
        } else if (expandDirection == ExpandDirection.Y) {
            if (getHeight() < targetLength) {
                if (getVelocityY() > 0) {
                    addHeight(getVelocityY() * delta);
                } else {
                    addHeight(-getVelocityY() * delta);
                    addY(getVelocityY() * delta);
                }
            } else {
                expandDirection = ExpandDirection.NONE;
            }
        } else {
            super.update(delta);
        }
    }

    @Override
    public void render(Object rendererTool) {
        ShapeRenderer renderer = (ShapeRenderer) rendererTool;
        renderer.setColor(Color.RED);
        renderer.rect(getX(), getY(), getWidth(), getHeight());
    }
}
