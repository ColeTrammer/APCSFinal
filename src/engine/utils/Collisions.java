package engine.utils;

import com.badlogic.gdx.math.Vector2;
import engine.entities.Entity;
import engine.entities.behaviors.Afflictable;
import engine.entities.behaviors.Afflicter;
import engine.entities.behaviors.Impassable;
import engine.entities.behaviors.Movable;
import engine.entities.templates.RectangleEntity;

/**
 * A static class with methods that processes collisions that occur
 * in the game. The public method collided is called anytime two
 * objects collide, and determines what to do using instanceof
 * to determine what type of objects collided. Because collisions
 * happen to two distinct entities, it makes little sense to make
 * the collision logic part of a single class, as it would have to
 * be duplicated in both classes, or their would be a hierarchy that
 * would determine which entity was responsible for handling the collision.
 * Instead, all collision logic is handled by this class.
 */
public final class Collisions {
    /**
     * Handles the collision between any two entities.
     * Must be called if two entities overlap.
     * The order of the parameters is irrelevant.
     *
     * @param e1 The first object in the collision.
     * @param e2 The second object in the collision.
     */
    public static void collided(Entity e1, Entity e2) {
        if (e1 instanceof Movable && e2 instanceof Impassable ||
                e2 instanceof Movable && e1 instanceof Impassable) {
            Movable movable = (Movable) (e1 instanceof Movable && e2 instanceof Impassable ? e1 : e2);
            Impassable impassable = (Impassable) (e1 instanceof Movable && e2 instanceof Impassable ? e2 : e1);
            // Gdx.app.log("Collision", String.format("Movable %s & Impassable %s", movable.toString(), impassable.toString()));
            impassable.expel(movable);
        }

        if (e1 instanceof Afflictable && e2 instanceof Afflicter ||
                e2 instanceof Afflictable && e1 instanceof Afflicter) {
            Afflictable afflictable = (Afflictable) (e1 instanceof Afflictable && e2 instanceof Afflicter ? e1 : e2);
            Afflicter afflicter = (Afflicter) (e1 instanceof Afflictable && e2 instanceof Afflicter ? e2 : e1);
            // Gdx.app.log("Collision", String.format("Afflictable %s & Afflicter %s", afflictable.toString(), afflicter.toString()));
            afflicter.giveDamage(afflictable);
        }
    }

    /**
     * Determines the distance needed for an rectangleEntity1 to no longer
     * be overlapping the rectangleEntity2. If the object had zero velocity is some direction,
     * the distance returned will be Float.MAX_VALUE to signify that motion in that direction
     * should not occur.
     *
     * @param rectangleEntity1 MovableRectangleEntity that is overlapping the rectangleEntity2.
     * @param rectangleEntity2        RectangleEntity that the rectangleEntity1 is in.
     * @return Vector2 with x and y values corresponding to the
     * distance the rectangleEntity1 must move in that direction
     * to no longer be colliding with the rectangleEntity2. Components of the
     * Vector2 will be Float.MAX_VALUE if motion should certainly not occur in that direction.
     */
    private static Vector2 distanceToMoveOutOf(RectangleEntity rectangleEntity1, RectangleEntity rectangleEntity2) {
        Vector2 res = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);

        /*
        if the entity is moving left and its x-position is to the left
        of the wall's right edge, then the distance between the those two
        becomes distanceX, and if the entity was moving right and it's right
        edge was to the right of the wall's left edge, update the distance
        all the same.
        */
        if (rectangleEntity1.getX() < rectangleEntity2.getX() + rectangleEntity2.getWidth()) {
            res.x = rectangleEntity1.getX() - (rectangleEntity2.getX() + rectangleEntity2.getWidth());
        }
        if (rectangleEntity1.getX() + rectangleEntity1.getWidth() > rectangleEntity2.getX()) {
            float displacement = rectangleEntity1.getX() - (rectangleEntity2.getX() - rectangleEntity1.getWidth());
            if (Math.abs(displacement) < Math.abs(res.x)) {
                res.x = displacement;
            }
        }
        // does the same as above except with in the y-direction.
        if (rectangleEntity1.getY() < rectangleEntity2.getY() + rectangleEntity2.getHeight()) {
            res.y = rectangleEntity1.getY() - (rectangleEntity2.getY() + rectangleEntity2.getHeight());
        }
        if (rectangleEntity1.getY() + rectangleEntity1.getHeight() > rectangleEntity2.getY()) {
            float displacement = rectangleEntity1.getY() - (rectangleEntity2.getY() - rectangleEntity1.getHeight());
            if (Math.abs(displacement) < Math.abs(res.y)) {
                res.y = displacement;
            }
        }

        return res;
    }

    /**
     * Moves the overlapping entity such that it
     * will now sit right next to the other rectangleEntity,
     * such that there is no distance between the two.
     *
     * @param overlapping MovableRectangleEntity that needs to move.
     * @param impassable  RectangleEntity that is being moved out of.
     */
    public static Vector2 expelDistance(RectangleEntity overlapping, RectangleEntity impassable) {
        Vector2 distanceToMove = distanceToMoveOutOf(overlapping, impassable);

        /*
        finds the smallest distance the entity must move to no longer overlap
        the wall, and then shifts the entity over that distance.
        */
        if (Math.abs(distanceToMove.x) <= Math.abs(distanceToMove.y)) {
            return new Vector2(-distanceToMove.x, 0);
        } else {
            return new Vector2(0, -distanceToMove.y);
        }
    }

    private Collisions() {
    }
}