package entities;

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
    public boolean rendersWithShapeRenderer() { return true; }

    @Override
    public void checkCollision(Entity other) {}
}
