package engine.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import engine.entities.behaviors.Afflictable;
import engine.entities.behaviors.Afflicter;
import engine.entities.templates.RectangleEntity;
import engine.utils.Direction;

/**
 * Represents a pulse that has a delayed appearance
 * on the screen and the afflicts the player when active.
 */
public class Pulse extends RectangleEntity implements Afflicter {
    private final float targetDimension;
    private final float delay;
    private final float duration;
    private float elapsedTime;
    private final Direction direction;
    private boolean canGiveDamage;

    /**
     * Constructs the pulse with the given time constraints.
     * Automatically removes itself when its duration is over.
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity.
     * @param height height of the entity.
     * @param delay time before the pulse becomes active.
     * @param duration time the pulse is active.
     * @param direction direction for the pulse to expand in.
     */
    public Pulse(float x, float y, float width, float height, float delay, float duration, Direction direction) {
        super(x, y, width, height);
        this.delay = delay;
        this.duration = duration;
        this.elapsedTime = 0;
        this.direction = direction;
        this.canGiveDamage = false;
        if (direction.isVertical()) {
            setWidth(0);
            targetDimension = width;
        } else if (direction.isHorizontal()) {
            setHeight(0);
            targetDimension = height;
        } else {
            targetDimension = 0;
        }
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
        if (elapsedTime < delay) {
            float increment = (delta / delay) * (targetDimension / 2);
            if (direction.isVertical()) {
                subWidth(increment * 2);
                addX(increment);
            } else if (direction.isHorizontal()) {
                subHeight(increment * 2);
                addY(increment);
            }
        } else if (elapsedTime < delay + duration) {
            canGiveDamage = true;
        } else {
            expire();
        }
    }

    @Override
    public void render(Object renderTool) {
        ShapeRenderer renderer = (ShapeRenderer) renderTool;
        if (canGiveDamage) {
            renderer.setColor(Color.RED);
        } else {
            renderer.setColor(new Color(elapsedTime / delay, 0, 1 - (elapsedTime / delay), 1));
        }
        super.render(renderer);
    }

    @Override
    public void giveDamage(Afflictable afflictable) {
        if (canGiveDamage) {
            afflictable.receiveDamage();
        }
    }
}
