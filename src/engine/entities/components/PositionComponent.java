package engine.entities.components;

public interface PositionComponent {
    float getX();
    float getY();
    void setX(float x);
    void setY(float y);
    boolean overlapsWith(PositionComponent other);
}
