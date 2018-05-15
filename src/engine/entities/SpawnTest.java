package engine.entities;

import engine.entities.templates.AbstractEntity;

public class SpawnTest extends AbstractEntity {
    private float elapsedTime;

    public SpawnTest() {
        elapsedTime = 0;
    }


    @Override
    public void update(float delta) {
        elapsedTime += delta;
        if (elapsedTime > 1) {
            spawn(new Laser(0, 0, 20, 20, 500, 0));
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
