package entities;

/**
 * The Entity interface is simply polymorphic mechanism
 * so that all things in the game can be dealt with
 * in the same manner.
 */
public interface Entity {
    /**
     * Method's purpose it to update any relevant state in the entity.
     * Meant to be called before every render, and allows
     * for entities to move or change over time.
     * @param delta time step from when this was last called.
     */
    void update(float delta);

    /**
     * Method's purpose is to draw the entity on the screen.
     * Meant to be called every frame, and allows
     * for entities to show be viewed by humans.
     * @param renderer_ Object with which the entity will use to render itself.
     */
    void render(Object renderer_);

    /**
     * Method's purpose it to allow entities to either be drawn with a
     * ShapeRenderer or SpriteBatch. Must be respected when calling render.
     * @return true if the Entity must be rendered with a ShapeRenderer
     * false if the Entity must be rendered with a SpriteBatch
     */
    boolean rendersWithShapeRenderer();

    /**
     * Method's purpose is to be able to tell when to entities collide,
     * as this is the most common form of interaction between to entities
     * Currently, what to do when a collision occurs is governed by the
     * Collisions class. Calls Collisions.collided(this, other) if
     * this and other overlap with each other.
     * @param other The entity this one is being checked against.
     */
    void checkCollision(Entity other);

    /**
     * Method's purpose is to an entity to determine it's own
     * existence. When it deliberated to return true, the entity
     * is requesting to be deleted. Once this is set to true,
     * methods in this interface should no longer be called.
     * (If they are, they should do nothing).
     * @return true if the entity is expired
     * false if it is not
     */
    boolean expired();
}
