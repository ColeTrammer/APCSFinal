package entities;

import com.badlogic.gdx.math.Vector2;

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
        /*
        Adjusts a MovableRectangleEntity's position to be right next to the Wall
        if it collides with one.
        */
        if (o1 instanceof MovableRectangleEntity && o2 instanceof Wall ||
                o2 instanceof MovableRectangleEntity && o1 instanceof Wall) {
            MovableRectangleEntity movingEntity = (MovableRectangleEntity) (o1 instanceof MovableRectangleEntity ? o1 : o2);
            Wall wall = (Wall) (o1 instanceof MovableRectangleEntity ? o2 : o1);
            Vector2 velocity  = movingEntity.getVelocity();
            float distanceY = Float.MAX_VALUE;
            float distanceX = Float.MAX_VALUE;

            /*
            if the entity is moving left and its x-position is to the left
            of the wall's right edge, then the distance between the those two
            becomes distanceX, and if the entity was moving right and it's right
            edge was to the right of the wall's left edge, update the distance
            all the same.
            */
            if (velocity.x < 0 && movingEntity.getX() < wall.getX() + wall.getWidth()) {
                distanceX = movingEntity.getX() - (wall.getX() + wall.getWidth());
            } else if (velocity.x > 0 && movingEntity.getX() + movingEntity.getWidth() > wall.getX()) {
                distanceX = movingEntity.getX() - (wall.getX() - movingEntity.getWidth());
            }
            // does the same as above except with in the y-direction.
            if (velocity.y < 0 && movingEntity.getY() < wall.getY() + wall.getHeight()) {
                distanceY = movingEntity.getY() - (wall.getY() + wall.getHeight());
            } else if (velocity.y > 0 && movingEntity.getY() + movingEntity.getHeight() > wall.getY()) {
                distanceY = movingEntity.getY() - (wall.getY() - movingEntity.getHeight());
            }

            /*
            finds the smallest distance the entity must move to no longer overlap
            the wall, and then shifts the entity over that distance.
            */
            if (Math.abs(distanceX) <= Math.abs(distanceY)) {
                movingEntity.subX(distanceX);
            } else {
                movingEntity.subY(distanceY);
            }
        }
        /*
        Damages the player if it collides with an Enemy.
        */
        if (o1 instanceof Player && o2 instanceof Enemy ||
                o2 instanceof Player && o1 instanceof Enemy) {
            Player player = (Player) (o1 instanceof  Player ? o1 : o2);
            player.damage();
        }
    }
}