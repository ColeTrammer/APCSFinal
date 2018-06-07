package engine.entities;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import engine.entities.behaviors.Afflictable;
import engine.entities.components.Acceleration;
import engine.entities.components.Rectangle;
import engine.entities.templates.MovableRectangleEntity;

/**
 * Entity that represents the player of the game. As such,
 * it's motion is controlled by user input, and it has the
 * highest amount of unique interactions with other Entities.
 */
@SuppressWarnings("unused")
public class Player extends MovableRectangleEntity implements Afflictable {
    private final float xSpeed;
    private final float ySpeed;

    /**
     * Constructs the Player with the given initial position,
     * dimensions, speed of motion, jump height, and force of
     * gravity.
     *
     * @param xSpeed     Speed of the player's horizontal motion.
     * @param jumpHeight Max height of the player's jump.
     * @param gravity    Strength of gravity acting on the player.
     */
    public Player(Rectangle rect, float xSpeed, float jumpHeight, float gravity) {
        super(rect, new Acceleration(rect, 0, 0, 0, -gravity));
        this.xSpeed = xSpeed;
        // basic kinematics equation (v^2 = v_0^2 + 2 * a * x) with v_0 = 0
        this.ySpeed = (float) Math.sqrt(2 * gravity * jumpHeight);
    }

    @Override
    public void update(float delta) {
        // resets x-velocity to zero each frame.
        getVelocity().x = 0;

        // changes velocity based on keyboard input.
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            getVelocity().x -= xSpeed;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            getVelocity().x += xSpeed;
        }
        /*
        This method of determining jump-ability is flawed. The player has 0 y-velocity
        at the height of its jump. However, floating point math means that it is unlikely
        that it will actually equal zero due to acceleration. Therefore, it will stay in
        as a FEATURE.
        */
        if ((Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) && getVelocity().y == 0) {
            getVelocity().y += ySpeed;
        }

        // applies the acceleration to the velocity to the position.
        super.update(delta);
    }

    @Override
    public void render(Object rendererTool) {
        ShapeRenderer renderer = (ShapeRenderer) rendererTool;
        renderer.setColor(Color.BLUE);
        super.render(renderer);
    }

    @Override
    public void moveOutOf(Vector2 displacement) {
        super.moveOutOf(displacement);
        if (displacement.y > 0 && getVelocity().y < 0) {
            getVelocity().y = 0;
        } else if (displacement.y < 0 && getVelocity().y > 0) {
            getVelocity().y *= -1;
        }
    }

    @Override
    public void receiveDamage() {
        expire();
    }
}
