package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class EntityManager {
    private Array<Entity> entities;
    
    public EntityManager(Player player) {
        entities = new Array<>();
        entities.add(player);
    }
    
    public void update(float delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
        
        for (int i = 0; i < entities.size - 1; i++) {
            for (int j = 1; j < entities.size; j++) {
                if (entities.get(i).overlapsWith(entities.get(j))) {
                    Collisions.collided(entities.get(i), entities.get(j));
                }
            }
        }

        for (int i = entities.size - 1; i >= 0; i--) {
            if (entities.get(i).expired()) {
                entities.removeIndex(i);
            }
        }
    }
    
    public void render(ShapeRenderer renderer, SpriteBatch batch) {
        boolean usesShapeRenderer = true;
        boolean switchOccurred = false;
        renderer.begin(ShapeType.Filled);
        for (Entity e : entities) {
            if (usesShapeRenderer != e.rendersWithShapeRenderer()) {
                switchOccurred = true;
                usesShapeRenderer = !usesShapeRenderer;
                renderer.end();
                batch.begin();
            } 
            if (usesShapeRenderer) {
                e.render(renderer);
            } else {
                e.render(batch);
            }
        }
        if (switchOccurred) {
            batch.end();
        } else {
            renderer.end();
        }
    }
    
    public void add(Entity e) {
        if (e.rendersWithShapeRenderer()) {
            entities.insert(1, e);
        } else {
            entities.add(e);
        }
    }

    public Player getPlayer() {
        for (Entity e : entities) {
            if (e instanceof Player) {
                return (Player) e;
            }
        }
        return null;
    }
}
