package engine.utils;

import com.badlogic.gdx.math.Vector2;
import engine.entities.Entity;
import engine.entities.Laser;
import engine.entities.Player;
import engine.entities.behaviors.Impassable;
import engine.entities.behaviors.Movable;
import engine.entities.templates.MovableRectangleEntity;
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
public class Collisions {
    /**
     * Handles the collision between any two entities.
     * Must be called if two entities overlap.
     * The order of the parameters is irrelevant.
     * @param o1 The first object in the collision.
     * @param o2 The second object in the collision.
     */
    public static void collided(Entity o1, Entity o2) {
        if (o1 instanceof Movable && o2 instanceof Impassable ||
                o2 instanceof Movable && o1 instanceof Impassable) {
            Movable movable = (Movable) (o1 instanceof Movable ? o1 : o2);
            Impassable impassable = (Impassable) (o1 instanceof Impassable ? o1 : o2);
            impassable.expel(movable);
        }

        if (o1 instanceof Player && o2 instanceof Laser ||
                o2 instanceof Player && o1 instanceof Laser) {
            Player player = (Player) (o1 instanceof  Player ? o1 : o2);
            //Laser laser = (Laser) (o1 instanceof Player ? o2  : o1);
            //damagePlayer(player/*, laser*/);
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
    private static Vector2 distanceToMoveOutOf(MovableRectangleEntity movableRectangleEntity, RectangleEntity rectangleEntity) {
        Vector2 velocity  = movableRectangleEntity.getVelocity();
        Vector2 res = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);

        /*
        if the entity is moving left and its x-position is to the left
        of the wall's right edge, then the distance between the those two
        becomes distanceX, and if the entity was moving right and it's right
        edge was to the right of the wall's left edge, update the distance
        all the same.
        */
        if (velocity.x < 0 && movableRectangleEntity.getX() < rectangleEntity.getX() + rectangleEntity.getWidth()) {
            res.x = movableRectangleEntity.getX() - (rectangleEntity.getX() + rectangleEntity.getWidth());
        } else if (velocity.x > 0 && movableRectangleEntity.getX() + movableRectangleEntity.getWidth() > rectangleEntity.getX()) {
            res.x = movableRectangleEntity.getX() - (rectangleEntity.getX() - movableRectangleEntity.getWidth());
        }
        // does the same as above except with in the y-direction.
        if (velocity.y < 0 && movableRectangleEntity.getY() < rectangleEntity.getY() + rectangleEntity.getHeight()) {
            res.y = movableRectangleEntity.getY() - (rectangleEntity.getY() + rectangleEntity.getHeight());
        } else if (velocity.y > 0 && movableRectangleEntity.getY() + movableRectangleEntity.getHeight() > rectangleEntity.getY()) {
            res.y = movableRectangleEntity.getY() - (rectangleEntity.getY() - movableRectangleEntity.getHeight());
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
    public static Vector2 expelDistance(MovableRectangleEntity overlapping, RectangleEntity impassable) {
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

//    /**
//     * Reflects the movableRectangleEntity by moving it's position to
//     * be on the edge of the rectangleEntity it collides with, and inverting
//     * it's velocity appropriately such that it bounces off the rectangleEntity
//     * seamlessly.
//     * @param movableRectangleEntity the MovableRectangleEntity to reflect off of the rectangleEntity
//     * @param rectangleEntity the RectangleEntity being reflected off.
//    */
//    public static void reflect(MovableRectangleEntity movableRectangleEntity, RectangleEntity rectangleEntity) {
//        Vector2 distanceToMove = distanceToMoveOutOf(movableRectangleEntity, rectangleEntity);
//
//        /*
//        Uses information about the shortest distance to travel to
//        fix the movableRectangleEntity to adjust it's position
//        and velocity correctly.
//        */
//        if (Math.abs(distanceToMove.x) <= Math.abs(distanceToMove.y)) {
//            movableRectangleEntity.invertVelocityX();
//            movableRectangleEntity.subX(distanceToMove.x);
//        } else {
//            movableRectangleEntity.invertVelocityY();
//            movableRectangleEntity.subY(distanceToMove.y);
//        }
//    }

    /**
     * Damages the player.
     * @param player Player in collision.
     * //@param laser Laser in collision.
     */
    private static void damagePlayer(Player player/*, Laser laser*/) {
        player.damage();
    }
}