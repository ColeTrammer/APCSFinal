package engine.entities;

import components.Constants;
import engine.entities.templates.AbstractEntity;

/**
 * A class simply used to test the spawn functionality
 * of the entity manager.
 */
@SuppressWarnings("unused")
public class SpawnTest extends AbstractEntity {
    private float elapsedTime;

    /**
     * Basic Constructor
     */
    public SpawnTest() {
        elapsedTime = 0;
    }

    @Override
    public void update(float delta) {
        elapsedTime += delta;
        if (elapsedTime > 0.5f) {
            spawn(new Laser(0, ((float) Math.random()) * (Constants.WORLD_HEIGHT - 20), 40, 10, 500, 0));
            elapsedTime = 0;
        }
    }

    @Override
    public void render(Object renderTool) {}

    @Override
    public RenderTool getRenderTool() { return RenderTool.SPRITE_BATCH; }

    @Override
    public void checkCollision(Entity other) {}
}
