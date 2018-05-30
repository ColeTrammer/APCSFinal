package engine.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import engine.entities.behaviors.Afflictable;
import engine.entities.behaviors.Afflicter;
import engine.entities.templates.RectangleEntity;
import engine.utils.Direction;

/**
 * Represents a pulse that has a delayed appearance
 * on the screen and the afflicts the player when active.
 */
@SuppressWarnings("unused")
public class Pulse extends RectangleEntity implements Afflicter {
    private static final float minDimension = 2f;

    private final float targetDimension;
    private final float delay;
    private final float duration;
    private final Direction direction;
    private float elapsedTime;
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
        this.direction = direction;
        this.elapsedTime = 0;
        this.canGiveDamage = false;
        if (direction.isHorizontal()) {
            setWidth(minDimension);
            targetDimension = width;
        } else if (direction.isVertical()) {
            setHeight(minDimension);
            targetDimension = height;
        } else {
            targetDimension = 0;
        }
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
        if (elapsedTime < delay) {
            float increment = (delta / delay) * ((targetDimension - minDimension) / 2);
            if (direction.isHorizontal()) {
                addWidth(increment * 2);
                subX(increment);
            } else if (direction.isVertical()) {
                addHeight(increment * 2);
                subY(increment);
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
        float relativeTime = Math.min(elapsedTime, delay);
        renderer.setColor(relativeTime / delay, 1 - relativeTime / delay, relativeTime / delay, 1);
        super.render(renderer);
    }

    @Override
    public void giveDamage(Afflictable afflictable) {
        if (canGiveDamage) {
            afflictable.receiveDamage();
        }
    }
}
