package entities;

public interface Entity {
    void update(float delta);
    void render(Object renderer_);
    boolean rendersWithShapeRenderer();
    boolean overlapsWith(Entity other);
    boolean expired();
}
