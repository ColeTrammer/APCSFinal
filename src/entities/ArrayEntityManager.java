package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

/**
 * Simplest implementation of EntityManager. Utilizes
 * the data structure of an Array. Because the
 * entities must remain sorted based off how they are
 * rendered, it is possible another data type is more efficient,
 * but using arrays is a simple approach.
 */
public class ArrayEntityManager implements EntityManager {
    private Array<Entity> entities;

    /**
     * Constructs the ArrayEntityManager to be empty.
     */
    public ArrayEntityManager() {
        entities = new Array<>();
    }

    @Override
    public void update(float delta) {
        // Calls the update method of each and every entity.
        for (Entity entity : entities) {
            entity.update(delta);
        }

        // checks for collisions between any two entities.
        for (int i = 0; i < entities.size - 1; i++) {
            for (int j = 1; j < entities.size; j++) {
                entities.get(i).checkCollision(entities.get(j));
            }
        }
    }

    @Override
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
        for (Entity entity : entities) {
            /*
            if the entities no longer require the ShapeRenderer
            then update local vars and begin the batch.
            */
            if (usesShapeRenderer != entity.rendersWithShapeRenderer()) {
                switchOccurred = true;
                usesShapeRenderer = !usesShapeRenderer;
                renderer.end();
                batch.begin();
            }
            // render the entity based on whether or not it needs the ShapeRenderer
            if (usesShapeRenderer) {
                entity.render(renderer);
            } else {
                entity.render(batch);
            }
        }
        // ends whichever rendering tool is necessary.
        if (switchOccurred) {
            batch.end();
        } else {
            renderer.end();
        }
    }

    @Override
    public void add(Entity entity) {
        // subscribes to the entity to know when it expires
        entity.addObserver(this);
        /*
        keeps entities separated by rendering tool
        by adding entities that require the ShapeRenderer
        to the front, and the other entities to the back.
        */
        if (entity.rendersWithShapeRenderer()) {
            entities.insert(0, entity);
        } else {
            entities.add(entity);
        }
    }

    @Override
    public boolean isPlayerExpired() {
        /*
        Once a Player is expired, it is removed
        from the list of entities. Therefore, if
        there is no Players in the list, the Player
        was expired.
        */
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void reset() {
        /*
        iterates through the list
        and then removes itself from the
        observers and deletes it's own
        reference to every entity.
        */
        for (int i = entities.size - 1; i >= 0; i--) {
            entities.get(i).removeObserver(this);
            entities.removeIndex(i);
        }
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
