package engine.entities;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import engine.entities.behaviors.Afflictable;
import engine.entities.templates.AcceleratingRectangleEntity;

/**
 * Entity that represents the player of the game. As such,
 * it's motion is controlled by user input, and it has the
 * highest amount of unique interactions with other Entities.
 */
public class Player extends AcceleratingRectangleEntity implements Afflictable {
    private final float xSpeed;
    private final float ySpeed;

    /**
     * Constructs the Player with the given initial position,
     * dimensions, speed of motion, jump height, and force of
     * gravity.
     * @param x x-coordinate of the entity's position.
     * @param y y-coordinate of the entity's position.
     * @param width width of the entity.
     * @param height height of the entity.
     * @param xSpeed Speed of the player's horizontal motion.
     * @param jumpHeight Max height of the player's jump.
     * @param gravity Strength of gravity acting on the player.
     */
    public Player(float x, float y, float width, float height, float xSpeed, float jumpHeight, float gravity) {
        super(x, y, width, height, 0, 0, 0, -gravity);
        this.xSpeed = xSpeed;
        // basic kinematics equation (v^2 = v_0^2 + 2 * a * x) with v_0 = 0
        this.ySpeed = (float) Math.sqrt(2 * gravity * jumpHeight);
    }

    @Override
    public void update(float delta) {
        // resets x-velocity to zero each frame.
        setVelocityX(0);

        // changes velocity based on keyboard input.
        if (Gdx.input.isKeyPressed(Keys.LEFT)  || Gdx.input.isKeyPressed(Keys.A)) {
            subVelocityX(xSpeed);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            addVelocityX(xSpeed);
        }
        /*
        This method of determining jump-ability is flawed. The player has 0 y-velocity
        at the height of its jump. However, floating point math means that it is unlikely
        that it will actually equal zero due to acceleration. Therefore, it will stay in
        as a FEATURE.
        */
        if (Gdx.input.isKeyPressed(Keys.SPACE) && getVelocityY() == 0) {
            setVelocityY(ySpeed);
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
        if (displacement.y > 0) {
            setVelocityY(0);
        }
    }

    @Override
    public void receiveDamage() {
        expire();
    }
}
