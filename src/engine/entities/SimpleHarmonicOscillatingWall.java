package engine.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import engine.entities.behaviors.Impassable;
import engine.entities.behaviors.Movable;
import engine.entities.templates.RectangleEntity;
import engine.entities.templates.SimpleHarmonicOscillatingEntity;
import engine.utils.Collisions;

@SuppressWarnings("unused")
public class SimpleHarmonicOscillatingWall extends SimpleHarmonicOscillatingEntity implements Impassable {
    public SimpleHarmonicOscillatingWall(float x, float y, float width, float height, float centerX, float centerY, float amplitudeX, float amplitudeY, float k) {
        super(x, y, width, height, centerX, centerY, amplitudeX, amplitudeY, k);
    }

    @Override
    public void render(Object renderTool) {
        ShapeRenderer renderer = (ShapeRenderer) renderTool;
        renderer.setColor(Color.BLACK);
        super.render(renderer);
    }

    @Override
    public void expel(Movable movable) {
        if (movable instanceof RectangleEntity) {
            movable.moveOutOf(Collisions.expelDistance((RectangleEntity) movable, this));
        }
    }
}
