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
     * @param rendererTool Object with which the entity will use to render itself.
     */
    void render(Object rendererTool);

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
     * Method's purpose is to allow an EntityObserver
     * to get notified when the entity expires.
     * @param observer EntityObserver to add to the list of observers
     *          to notify when the entity expires.
     */
    void addObserver(EntityObserver observer);

    /**
     * Method's purpose is to allow an EntityObserver
     * to cleanse itself from it's responsibilities to
     * the entity class by ignoring the Entity's expiration.
     * @param observer EntityObserver to remove from the list of observers
     *          to notify when the entity expires.
     */
    void removeObserver(EntityObserver observer);

    /**
     * Method's purpose is to allow the Entity
     * to tell all of it's observers that it has
     * expired, and thus all references of the
     * Entity should be removed, and the other
     * methods of this interface should not
     * be called.
     */
    void expire();

    /**
     * Method's purpose is to allow the Entity
     * to tell all of it's observers that it
     * has spawned a new Entity and to add
     * this new Entity to it's list of entities.
     * @param entity The entity to be spawned.
     */
    void spawn(Entity entity);
}
