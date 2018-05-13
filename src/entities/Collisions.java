package entities;

import com.badlogic.gdx.math.Vector2;

public class Collisions {
    public static void collided(Entity o1, Entity o2) {
        /* For MovingEntity collisions, assume that it was caused by
         * MovingEntity movement dictated by its velocity
         * (no instant translation is accounted for) */
        if (o1 instanceof MovingEntity && o2 instanceof Wall ||
                o2 instanceof MovingEntity && o1 instanceof Wall) {
            MovingEntity movingEntity = (MovingEntity) (o1 instanceof MovingEntity ? o1 : o2);
            Wall wall = (Wall) (o1 instanceof MovingEntity ? o2 : o1);
            Vector2 velocity  = movingEntity.getVelocity();
            float distanceY = Float.MAX_VALUE;
            float distanceX = Float.MAX_VALUE;

            if (velocity.x < 0 && movingEntity.getX() < wall.getX() + wall.getWidth()) {
                distanceX = movingEntity.getX() - (wall.getX() + wall.getWidth());
            } else if (velocity.x > 0 && movingEntity.getX() + movingEntity.getWidth() > wall.getX()) {
                distanceX = movingEntity.getX() - (wall.getX() - movingEntity.getWidth());
            }

            if (velocity.y < 0 && movingEntity.getY() < wall.getY() + wall.getHeight()) {
                distanceY = movingEntity.getY() - (wall.getY() + wall.getHeight());
            } else if (velocity.y > 0 && movingEntity.getY() + movingEntity.getHeight() > wall.getY()) {
                distanceY = movingEntity.getY() - (wall.getY() - movingEntity.getHeight());
            }

            if (Math.abs(distanceX) <= Math.abs(distanceY)) {
                movingEntity.subX(distanceX);
            } else {
                movingEntity.subY(distanceY);
            }
        }
        if (o1 instanceof Player && o2 instanceof Enemy ||
                o2 instanceof Player && o1 instanceof Enemy) {
            Player player = (Player) (o1 instanceof  Player ? o1 : o2);
            player.damage();
        }
    }
}