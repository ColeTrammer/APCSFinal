package engine.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import engine.entities.behaviors.Afflictable;
import engine.entities.behaviors.Afflicter;
import engine.entities.templates.MovableRectangleEntity;
import engine.utils.Direction;

/**
 * The laser class represents a laser that damages the player
 * upon impact, and is destroyed when it comes in contact
 * with a Wall. It moves according it's velocity, and
 * can expand it's dimensions over time to give the illusion
 * of coming from outside of the screen.
 */
public class Laser extends MovableRectangleEntity implements Afflicter {
    private float targetLength;
    private Direction expandDirection;

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
        this(x, y, width, height, velX, velY, width >= height ?
                                                    velX > 0 ? Direction.RIGHT : Direction.LEFT :
                                                    velY > 0 ? Direction.UP    : Direction.DOWN);
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
    private Laser(float x, float y, float width, float height, float velX, float velY, Direction expandDirection) {
        super(x, y, width, height, velX, velY);
        this.expandDirection = expandDirection;
        if (expandDirection.isHorizontal() && velX == 0 || expandDirection.isVertical() && velY == 0) {
            throw new IllegalArgumentException(String.format("A laser cannot expand in the %s direction and have 0 %s velocity.", expandDirection.name(), expandDirection.name()));
        }
        if (expandDirection.isHorizontal()) {
            setWidth(0);
            targetLength = width;
        } else if (expandDirection.isVertical()) {
            setHeight(0);
            targetLength = height;
        }
    }

    @Override
    public void update(float delta) {
        if (expandDirection != Direction.NONE) {
            expand(delta);
        }
        // not else because expand can change the direction to NONE once it finishes.
        if (expandDirection == Direction.NONE) {
            if (getWidth() < 0 || getHeight() < 0) {
                expire();
            }
            super.update(delta);
        }
    }

    /**
     * Expands the laser in the direction of expansion
     * in accordance with the velocity in that direction. Once
     * expansion is no longer needed, updates the direction to
     * NONE.
     * @param delta time step from when this was last called.
     */
    private void expand(float delta) {
        if (expandDirection.isHorizontal()) {
            if (getWidth() < targetLength) {
                if (expandDirection == Direction.RIGHT) {
                    addWidth(Math.abs(getVelocityX()) * delta);
                } else {
                    addWidth(Math.abs(getVelocityX()) * delta);
                    subX(Math.abs(getVelocityX()) * delta);
            }
            } else {
                expandDirection = Direction.NONE;
            }
        } else if (expandDirection.isVertical()) {
            if (getHeight() < targetLength) {
                if (expandDirection == Direction.UP) {
                    addHeight(Math.abs(getVelocityY()) * delta);
                } else {
                    addHeight(Math.abs(getVelocityY()) * delta);
                    subY(Math.abs(getVelocityY()) * delta);
                }
            } else {
                expandDirection = Direction.NONE;
            }
        }
    }

    @Override
    public void render(Object rendererTool) {
        ShapeRenderer renderer = (ShapeRenderer) rendererTool;
        renderer.setColor(Color.RED);
        super.render(renderer);
    }

    @Override
    public void moveOutOf(Vector2 displacement) {
        if (getVelocityX() > 0) {
            subWidth(Math.abs(displacement.x));
        } else if (getVelocityX() < 0) {
            subWidth(Math.abs(displacement.x));
            super.moveOutOf(displacement);
        }
        if (getVelocityY() > 0) {
            subHeight(Math.abs(displacement.y));
        } else if (getVelocityY() < 0) {
            subHeight(Math.abs(displacement.y));
            super.moveOutOf(displacement);
        }
    }

    @Override
    public void giveDamage(Afflictable afflictable) {
        afflictable.receiveDamage();
    }
}
