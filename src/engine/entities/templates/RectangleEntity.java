package engine.entities.templates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import engine.entities.components.Rectangle;
import engine.utils.Collisions;
import engine.entities.Entity;
import engine.utils.TextureLoader;

/**
 * The RectangleEntity class provides a standard implementation
 * of the Entity interface, by extending the AbstractEntity class
 * and using a single Rectangle as its
 * hit box, and rendering a white rectangle on the screen'
 * in the render method.
 */
public abstract class RectangleEntity extends AbstractEntity {
    private final Rectangle rect;
    private final TextureLoader textureLoader;

    /**
     * Basic Constructor
     */
    protected RectangleEntity(Rectangle rect) {
        if (rect.getWidth() < 0 || rect.getHeight() < 0) {
            throw new IllegalArgumentException("Width and height parameters must be positive.");
        }
        this.rect = rect;
        this.textureLoader = TextureLoader.getInstance();
    }

    @Override
    public void render(Object rendererTool) {
        SpriteBatch batch = (SpriteBatch) rendererTool;
        batch.draw(textureLoader.getTexture(getTag(), getWidth(), getHeight()), getX(), getY(), getWidth(), getHeight());
    }

    protected String getTag() {
        return null;
    }

    @Override
    public RenderTool getRenderTool() {
        return RenderTool.SPRITE_BATCH;
    }

    @Override
    public void checkCollision(Entity other) {
        /*
        if the entity is of this class
        then check if the rectangles overlap
        else its on the other class to deal with it
        For example, a hypothetical entity with multiple bounding
        rectangles or a polygon would need to implement
        the case where it collides with another one of its own
        and the other when it collides with this simple rectangle only entity
        */
        if (other instanceof RectangleEntity) {
            checkCollisionInternal((RectangleEntity) other);
        } else {
            other.checkCollision(this);
        }
    }

    /**
     * Calls Collisions.collided(this, other) if this
     * rectangle overlaps with the other's rectangle.
     *
     * @param other The entity this one is being checked against.
     */
    private void checkCollisionInternal(RectangleEntity other) {
        if (this.rect.overlapsWith(other.rect)) {
            Collisions.collided(this, other);
        }
    }

    public float getX() {
        return rect.getX() - rect.getWidth() / 2;
    }

    public float getY() {
        return rect.getY() - rect.getHeight() / 2;
    }

    public float getWidth() {
        return rect.getWidth();
    }

    public float getHeight() {
        return rect.getHeight();
    }

    protected void setWidth(float width) {
        addX((width - rect.getWidth()) / 2);
        rect.setWidth(width);
    }

    protected void setHeight(float height) {
        addY((height - rect.getHeight()) / 2);
        rect.setHeight(height);
    }

    protected void addX(float x) {
        rect.setX(rect.getX() + x);
    }

    protected void addY(float y) {
        rect.setY(rect.getY() + y);
    }

    protected void addWidth(float width) {
        setWidth(getWidth() + width);
    }

    protected void addHeight(float height) {
        setHeight(getHeight() + height);
    }

    protected void subX(float x) {
        rect.setX(rect.getX() - x);
    }

    protected void subY(float y) {
        rect.setY(rect.getY() - y);
    }

    protected void subWidth(float width) {
        setWidth(getWidth() - width);
    }

    protected void subHeight(float height) {
        setHeight(getHeight() - height);
    }
}
