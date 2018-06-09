package engine.entities.components;

public class Rectangle implements PositionComponent {
    private float x;
    private float y;
    private float width;
    private float height;

    @SuppressWarnings("unused")
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
        return Math.abs(this.x - other.x) < (this.width + other.width) / 2 &&
                Math.abs(this.y - other.y) < (this.height + other.height) / 2;
    }
}
