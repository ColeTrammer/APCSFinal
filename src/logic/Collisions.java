package logic;

import com.badlogic.gdx.math.Vector2;

public class Collisions {
    public static void collided(GameObject o1, GameObject o2) {
        /* PLAYER COLLISIONS FIRST */

        /* For logic.Player collisions, assume that it was caused by
         * logic.Player movement dictated by its velocity
         * (no instant movement is accounted for) */
        if (o1 instanceof Player && o2 instanceof Wall ||
                o2 instanceof Player && o1 instanceof Wall) {
            Player player = (Player) (o1 instanceof Player ? o1 : o2);
            Wall wall = (Wall) (o1 instanceof Player ? o2 : o1);
            Vector2 velocity = player.getVelocity();
            float distanceY = Float.MAX_VALUE;
            float distanceX = Float.MAX_VALUE;

            if (velocity.x < 0 && player.getX() < wall.getX() + wall.getWidth())
                distanceX = player.getX() - (wall.getX() + wall.getWidth());
            else if (velocity.x > 0 && player.getX() + player.getWidth() > wall.getX())
                distanceX = player.getX() - (wall.getX() - player.getWidth());

            if (velocity.y < 0 && player.getY() < wall.getY() + wall.getHeight())
                distanceY = player.getY() - (wall.getY() + wall.getHeight());
            else if (velocity.y > 0 && player.getY() + player.getHeight() > wall.getY())
                distanceY = player.getY() - (wall.getY() - player.getHeight());

            if (Math.abs(distanceX) <= Math.abs(distanceY))
                player.subX(distanceX);
            else
                player.subY(distanceY);
        }
        if (o1 instanceof Player && o2 instanceof Enemy ||
                o2 instanceof Player && o1 instanceof Enemy) {
            Player player = (Player) (o1 instanceof  Player ? o1 : o2);
            player.damage();
        }
    }
}