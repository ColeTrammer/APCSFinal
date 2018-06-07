package engine.entities.components;

public class Rectangle implements PositionComponent {
    private float x;
    private float y;
    private float width;
    private float height;

    public Rectangle(float x, float y, float width, float height) {
        this.x = x + width / 2;
        this.y = y + height / 2;
        this.width = width;
        this.height = height;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public boolean overlapsWith(PositionComponent other) {
        if (other instanceof Rectangle) {
            return overlapsWithInternal((Rectangle) other);
        } else {
            return other.overlapsWith(this);
        }
    }

    private boolean overlapsWithInternal(Rectangle other) {
        return new com.badlogic.gdx.math.Rectangle(this.x - this.width / 2, this.y - this.height / 2, this.width, this.height).overlaps(
                new com.badlogic.gdx.math.Rectangle(other.x - other.width / 2, other.y - other.height / 2, other.width, other.height));
    }
}
