package entities;

/**
 * An entity that other entities cannot move through. It
 * is completely static and unchanging, only interacting
 * with other entities when in a collision.
 */
public class Wall extends RectangleEntity {
    /**
     * Basic Constructor
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity.
     * @param height height of the entity.
     */
    public Wall(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
