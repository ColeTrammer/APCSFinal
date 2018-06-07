package engine.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import engine.entities.behaviors.Afflictable;
import engine.entities.behaviors.Afflicter;
import engine.entities.components.MovementComponent;
import engine.entities.components.Rectangle;
import engine.entities.components.Stationary;
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

    @SuppressWarnings("unused")
    public Laser(Rectangle rect, Direction expandDirection) {
        this(rect, new Stationary(), expandDirection);
    }

    /**
     * Creates a laser with specified bounds and velocity,
     * that starts with 0 length in the expansion direction,
     * and increases that dimension by the velocity in the
     * specified direction until it reaches the length
     * specified. The expansion direction must have a
     * corresponding non-zero velocity. Does not expand if
     * passed in NONE.
     *
     * @param expandDirection the direction to expand in. If NONE, no expansion occurs.
     */
    @SuppressWarnings("unused")
    public Laser(Rectangle rect, MovementComponent movementComponent, Direction expandDirection) {
        super(rect, movementComponent);
        this.expandDirection = expandDirection;
        if (expandDirection.isHorizontal() && getVelocity().x == 0 || expandDirection.isVertical() && getVelocity().y == 0) {
            throw new IllegalArgumentException(String.format("A laser cannot expand in the %s direction and have 0 %s velocity.", expandDirection.name(), expandDirection.name()));
        }
        if (expandDirection.isHorizontal()) {
            targetLength = rect.getWidth();
            setWidth(0);
        } else if (expandDirection.isVertical()) {
            targetLength = rect.getHeight();
            setHeight(0);
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
        } else {
            setDeltaTime(delta);
        }
    }

    /**
     * Expands the laser in the direction of expansion
     * in accordance with the velocity in that direction. Once
     * expansion is no longer needed, updates the direction to
     * NONE.
     *
     * @param delta time step from when this was last called.
     */
    private void expand(float delta) {
        if (expandDirection.isHorizontal()) {
            if (getWidth() < targetLength) {
                if (expandDirection == Direction.RIGHT) {
                    addWidth(Math.abs(getVelocity().x) * delta);
                } else {
                    addWidth(Math.abs(getVelocity().x) * delta);
                    subX(Math.abs(getVelocity().x) * delta);
                }
            } else {
                expandDirection = Direction.NONE;
            }
        } else if (expandDirection.isVertical()) {
            if (getHeight() < targetLength) {
                if (expandDirection == Direction.UP) {
                    addHeight(Math.abs(getVelocity().y) * delta);
                } else {
                    addHeight(Math.abs(getVelocity().y) * delta);
                    subY(Math.abs(getVelocity().y) * delta);
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
        if (getVelocity().x > 0) {
            subWidth(Math.abs(displacement.x));
        } else if (getVelocity().x < 0) {
            subWidth(Math.abs(displacement.x));
            super.moveOutOf(displacement);
        }
        if (getVelocity().y > 0) {
            subHeight(Math.abs(displacement.y));
        } else if (getVelocity().y < 0) {
            subHeight(Math.abs(displacement.y));
            super.moveOutOf(displacement);
        }
    }

    @Override
    public void giveDamage(Afflictable afflictable) {
        afflictable.receiveDamage();
    }
}
