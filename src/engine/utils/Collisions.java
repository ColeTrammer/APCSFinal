package engine.utils;

import com.badlogic.gdx.Gdx;
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
     * @param e1 The first object in the collision.
     * @param e2 The second object in the collision.
     */
    public static void collided(Entity e1, Entity e2) {
        if (e1 instanceof Movable && e2 instanceof Impassable ||
                e2 instanceof Movable && e1 instanceof Impassable) {
            Movable movable = (Movable) (e1 instanceof Movable && e2 instanceof Impassable ? e1 : e2);
            Impassable impassable = (Impassable) (e1 instanceof Movable && e2 instanceof Impassable ? e2 : e1);
            Gdx.app.log("Collision", String.format("Movable %s & Impassable %s", movable.toString(), impassable.toString()));
            Gdx.app.log("Delta", String.format("%f", Gdx.graphics.getDeltaTime()));
            impassable.expel(movable);
        }

        if (e1 instanceof Afflictable && e2 instanceof Afflicter ||
                e2 instanceof Afflictable && e1 instanceof Afflicter) {
            Afflictable afflictable = (Afflictable) (e1 instanceof Afflictable && e2 instanceof Afflicter ? e1 : e2);
            Afflicter afflicter = (Afflicter) (e1 instanceof Afflictable && e2 instanceof Afflicter ? e2 : e1);
            afflicter.giveDamage(afflictable);
        }
    }

    /**
     * Determines the distance needed for an movableRectangleEntity to no longer
     * be overlapping the rectangleEntity. If the object had zero velocity is some direction,
     * the distance returned will be Float.MAX_VALUE to signify that motion in that direction
     * should not occur.
     * @param movableRectangleEntity MovableRectangleEntity that is overlapping the rectangleEntity.
     * @param rectangleEntity RectangleEntity that the movableRectangleEntity is in.
     * @return Vector2 with x and y values corresponding to the
     * distance the movableRectangleEntity must move in that direction
     * to no longer be colliding with the rectangleEntity. Components of the
     * Vector2 will be Float.MAX_VALUE if motion should certainly not occur in that direction.
     */
    private static Vector2 distanceToMoveOutOf(RectangleEntity movableRectangleEntity, RectangleEntity rectangleEntity) {
        Vector2 res = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);

        /*
        if the entity is moving left and its x-position is to the left
        of the wall's right edge, then the distance between the those two
        becomes distanceX, and if the entity was moving right and it's right
        edge was to the right of the wall's left edge, update the distance
        all the same.
        */
        if (movableRectangleEntity.getX() < rectangleEntity.getX() + rectangleEntity.getWidth()) {
            res.x = movableRectangleEntity.getX() - (rectangleEntity.getX() + rectangleEntity.getWidth());
        }
        if (movableRectangleEntity.getX() + movableRectangleEntity.getWidth() > rectangleEntity.getX()) {
            float displacement = movableRectangleEntity.getX() - (rectangleEntity.getX() - movableRectangleEntity.getWidth());
            if (Math.abs(displacement) < Math.abs(res.x)) {
                res.x = displacement;
            }
        }
        // does the same as above except with in the y-direction.
        if (movableRectangleEntity.getY() < rectangleEntity.getY() + rectangleEntity.getHeight()) {
            res.y = movableRectangleEntity.getY() - (rectangleEntity.getY() + rectangleEntity.getHeight());
        }
        if (movableRectangleEntity.getY() + movableRectangleEntity.getHeight() > rectangleEntity.getY()) {
            float displacement = movableRectangleEntity.getY() - (rectangleEntity.getY() - movableRectangleEntity.getHeight());
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
     * @param overlapping MovableRectangleEntity that needs to move.
     * @param impassable RectangleEntity that is being moved out of.
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

    private Collisions() {}
}