package engine.utils;

/**
 * Enumeration to represent the different
 * direction possible within a two dimensional
 * world.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT, NONE;

    /**
     * Determines whether or not the direction is horizontal.
     *
     * @return true if the Direction is horizontal; false otherwise.
     */
    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }

    /**
     * Determines whether or not the direction is vertical.
     *
     * @return true if the Direction is vertical; false otherwise.
     */
    public boolean isVertical() {
        return this == DOWN || this == UP;
    }
}
