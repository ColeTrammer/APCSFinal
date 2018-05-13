package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

/**
 * Class used to keep track of all the entities currently
 * part of the game. As such, it allows for all entities
 * to be rendered and updated through a single method call,
 * and for entities to be added explicitly to the list.
 */
public class EntityManager implements EntityObserver {
    private Array<Entity> entities;

    /**
     * Constructs the EntityManager to be empty.
     */
    public EntityManager() {
        entities = new Array<>();
    }

    /**
     * Calls the update method of each and every entity,
     * and checks for collisions between any two entities.
     * @param delta time step from when this was last called.
     */
    public void update(float delta) {
        // Calls the update method of each and every entity.
        for (Entity e : entities) {
            e.update(delta);
        }

        // checks for collisions between any two entities.
        for (int i = 0; i < entities.size - 1; i++) {
            for (int j = 1; j < entities.size; j++) {
                entities.get(i).checkCollision(entities.get(j));
            }
        }
    }

    /**
     * Handles rendering of all entities, and takes into account
     * the possibility of a group of entities that require different
     * objects to be rendered.
     * @param renderer ShapeRenderer to be used to render entities that require it.
     * @param batch SpriteBatch to be used to render entities that require it.
     */
    public void render(ShapeRenderer renderer, SpriteBatch batch) {
        boolean usesShapeRenderer = true;
        boolean switchOccurred = false;
        /*
        NOTE: entities are always separated such that all
        entities that require the same type of rendering tool
        always are next to each other in entities.
        */

        // begin by rendering entities that require the ShapeRenderer.
        renderer.begin(ShapeType.Filled);
        for (Entity e : entities) {
            /*
            if the entities no longer require the ShapeRenderer
            then update local vars and begin the batch.
            */
            if (usesShapeRenderer != e.rendersWithShapeRenderer()) {
                switchOccurred = true;
                usesShapeRenderer = !usesShapeRenderer;
                renderer.end();
                batch.begin();
            }
            // render the entity based on whether or not it needs the ShapeRenderer
            if (usesShapeRenderer) {
                e.render(renderer);
            } else {
                e.render(batch);
            }
        }
        // ends whichever rendering tool is necessary.
        if (switchOccurred) {
            batch.end();
        } else {
            renderer.end();
        }
    }
    
    public void add(Entity e) {
        // subscribes to the entity to know when it expires
        e.addObserver(this);
        /*
        keeps entities separated by rendering tool
        by adding entities that require the ShapeRenderer
        to the front, and the other entities to the back.
        */
        if (e.rendersWithShapeRenderer()) {
            entities.insert(0, e);
        } else {
            entities.add(e);
        }
    }

    /**
     * determines whether or not the Player is expired.
     * @return true if the player is expired, false otherwise.
     */
    public boolean isPlayerExpired() {
        /*
        Once a Player is expired, it is removed
        from the list of entities. Therefore, if
        there is no Players in the list, the Player
        was expired.
        */
        for (Entity e : entities) {
            if (e instanceof Player) {
                return false;
            }
        }
        return true;
    }

    /**
     * Expires the entity that requests it
     * by first removing it's own observation of
     * the Entity, and then removing the entity
     * itself from the list of entities.
     * @param entity The entity to expire.
     */
    @Override
    public void expire(Entity entity) {
        entity.removeObserver(this);
        entities.removeValue(entity, true);
    }
}
