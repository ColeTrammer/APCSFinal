package engine.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import engine.entities.templates.AbstractEntity;

@SuppressWarnings("unused")
public class Text extends AbstractEntity {
    private String text;
    private final BitmapFont font;
    private float x;
    private float y;

    public Text(String text, BitmapFont font, float x, float y) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Object rendererTool) {
        SpriteBatch batch = (SpriteBatch) rendererTool;
        font.draw(batch, text, x, y);
    }

    @Override
    public RenderTool getRenderTool() {
        return RenderTool.SPRITE_BATCH;
    }

    @Override
    public void checkCollision(Entity other) {}

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setText(String text) { this.text = text; }
    protected String getText() { return text; }
}
