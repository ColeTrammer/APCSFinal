package components;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import utils.Constants;

/**
 * Screen that displays the menu of the game.
 */
public class MenuScreen extends InputAdapter implements Screen {
    //public static final String TAG = MenuScreen.class.getName();
    
    private TheGame game;
    
    private FitViewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;

    public MenuScreen(TheGame game) {
        this.game = game;
    }
    
    @Override
    public void show() {
        batch = new SpriteBatch();
        
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        Gdx.input.setInputProcessor(this);
        
        font = new BitmapFont();
        font.getData().setScale(Constants.MENU_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }
    
    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(Constants.MENU_BACKGROUND_COLOR.r, Constants.MENU_BACKGROUND_COLOR.g, Constants.MENU_BACKGROUND_COLOR.b, Constants.MENU_BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(viewport.getCamera().combined);
        
        batch.begin();

        // Renders the String MENU_MESSAGE in the center of the screen
        final GlyphLayout layout = new GlyphLayout(font, Constants.MENU_MESSAGE);
        font.draw(batch, Constants.MENU_MESSAGE, Constants.MENU_CENTER.x, Constants.MENU_CENTER.y + layout.height / 2, 0, Align.center, false);
        
        batch.end();
    }
    
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    
    @Override
    public void pause() {
        
    }
    
    @Override
    public void resume() {

    }
    
    @Override
    public void hide() {
        
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.showGameScreen();
        return true;
    }
}
