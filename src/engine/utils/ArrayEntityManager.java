package engine.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import engine.entities.Entity;
import engine.entities.Player;

/**
 * Simplest implementation of EntityManager. Utilizes
 * the data structure of an Array. Because the
 * entities must remain sorted based off how they are
 * rendered, it is possible another data type is more efficient,
 * but using arrays is a simple approach.
 */
public class ArrayEntityManager implements EntityManager {
    private final Array<Entity> entities;
    private final Array<Entity> staging;

    /**
     * Constructs the ArrayEntityManager to be empty.
     */
    public ArrayEntityManager() {
        entities = new Array<>();
        staging = new Array<>();
    }

    @Override
    public void update(float delta) {
        // adds all entities in staging to main list.
        for (Entity entity : staging) {
            add(entity);
        }
        staging.clear();
        // Gdx.app.debug("Num entities", String.format("%d", entities.size));
        /*
        Must iterate backward through the away
        because entities can be removed from the list
        after update or collision by expiring.
        */
        // Calls the update method of each and every entity.
        for (int i = entities.size - 1; i >= 0; i--) {
            entities.get(i).update(delta);
        }

        // checks for collisions between any two entities.
        for (int i = entities.size - 1; i >= 1; i--) {
            /*
            Because the entity at position i can be removed
            by checkCollision, it must be stored so that this
            can be detected.
            */
            Entity current = entities.get(i);
            for (int j = i - 1; j >= 0; j--) {
                if (i >= entities.size || entities.get(i) != current) {
                    break;
                }
                entities.get(i).checkCollision(entities.get(j));
            }
        }
    }

    @Override
    public void render(ShapeRenderer renderer, SpriteBatch batch) {
        Entity.RenderTool renderTool = Entity.RenderTool.SHAPE_RENDERER;
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
            if (renderTool != entity.getRenderTool()) {
                switchOccurred = true;
                renderTool = Entity.RenderTool.SPRITE_BATCH;
                renderer.end();
                batch.begin();
            }
            // render the entity based on whether or not it needs the ShapeRenderer
            if (renderTool == Entity.RenderTool.SHAPE_RENDERER) {
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

    private void add(Entity entity) {
        // subscribes to the entity to know when it expires
        entity.addObserver(this);
        /*
        keeps entities separated by rendering tool
        by adding entities that require the ShapeRenderer
        to the front, and the other entities to the back.
        */
        if (entity.getRenderTool() == Entity.RenderTool.SHAPE_RENDERER) {
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

    /**
     * Expires the entity that requests it
     * by first removing it's own observation of
     * the Entity, and then removing the entity
     * itself from the list of entities.
     *
     * @param entity The entity to expire.
     */
    @Override
    public void expire(Entity entity) {
        entity.removeObserver(this);
        entities.removeValue(entity, true);
    }

    /**
     * Sets the entity to be spawned by adding
     * it to staging. Needs to be a separate array
     * so that adding spawning entities cannot
     * interfere with the update loop.
     *
     * @param entity The entity to be spawned.
     */
    @Override
    public void spawn(Entity entity) {
        staging.add(entity);
    }
}
