package logic;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import logic.Collisions;

public class ObjectManager {
    private List<GameObject> objects;
    
    public ObjectManager(Player player) {
        objects = new ArrayList<>();
        objects.add(player);
    }
    
    public void update(float delta) {
        for (GameObject o : objects) {
            o.update(delta);
        }
        
        for (int i = 0; i < objects.size() - 1; i++) {
            for (int j = 1; j < objects.size(); j++) {
                if (objects.get(i).getRect().overlaps(
                    objects.get(j).getRect())) {
                        Collisions.collided(objects.get(i), objects.get(j));
                }
            }
        }
    }
    
    public void render(ShapeRenderer renderer, SpriteBatch batch) {
        boolean usesShapeRenderer = true;
        boolean switchOccurred = false;
        renderer.begin(ShapeType.Filled);
        for (GameObject o : objects) {
            if (usesShapeRenderer != o.rendersWithShapeRenderer()) {
                switchOccurred = true;
                usesShapeRenderer = !usesShapeRenderer;
                renderer.end();
                batch.begin();
            } 
            if (usesShapeRenderer) {
                o.render(renderer);
            } else {
                o.render(batch);
            }
        }
        if (switchOccurred)
            batch.end();
        else
            renderer.end();
    }
    
    public void add(GameObject o) {
        if (o.rendersWithShapeRenderer()) {
            objects.add(0, o);
        } else {
            objects.add(o);
        }
    }
}
