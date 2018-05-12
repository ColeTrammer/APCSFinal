package components;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import logic.Enemy;
import logic.ObjectManager;
import logic.Wall;
import utils.Constants;

public class GameScreen extends InputAdapter implements Screen {
    //public static final String TAG = GameScreen.class.getName();
    
    private TheGame game;
    private ObjectManager manager;
    
    private ExtendViewport gameViewport;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    
    private ScreenViewport hudViewport;
    private SpriteBatch hudBatch;
    private BitmapFont font;
    
    public GameScreen(TheGame game, ObjectManager manager) {
        this.game = game;
        this.manager = manager;
        manager.add(new Wall(-Constants.WALL_THICKNESS, -Constants.WALL_THICKNESS, Constants.WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.WALL_THICKNESS));
        manager.add(new Wall(Constants.WALL_THICKNESS, -Constants.WALL_THICKNESS, Constants.WORLD_WIDTH, Constants.WALL_THICKNESS));
        manager.add(new Wall(Constants.WALL_THICKNESS, Constants.WORLD_HEIGHT, Constants.WORLD_WIDTH, Constants.WALL_THICKNESS));
        manager.add(new Wall(Constants.WORLD_WIDTH, -Constants.WALL_THICKNESS, Constants.WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.WALL_THICKNESS));
        manager.add(new Enemy(50, 50, 50, 50));
    }
    
    @Override
    public void show() {
        gameViewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        
        hudViewport = new ScreenViewport();
        hudBatch = new SpriteBatch();
        
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        Gdx.input.setInputProcessor(this);
    }
    
    @Override
    public void render(float delta) {
        manager.update(delta);
        
        Gdx.gl.glClearColor(Constants.GAME_BACKGROUND_COLOR.r, Constants.GAME_BACKGROUND_COLOR.g, Constants.GAME_BACKGROUND_COLOR.b, Constants.GAME_BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        gameViewport.apply(true);
        
        renderer.setProjectionMatrix(gameViewport.getCamera().combined);
        batch.setProjectionMatrix(gameViewport.getCamera().combined);
        
        manager.render(renderer, batch);
        
        hudViewport.apply(true);
        
        hudBatch.setProjectionMatrix(hudViewport.getCamera().combined);
        hudBatch.begin();
        
        hudBatch.end();
    }
    
    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
        hudViewport.update(width, height, true);
        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
    }
    
    @Override
    public void pause() {
        
    }
    
    @Override
    public void resume() {

    }
    
    @Override
    public void hide() {
        renderer.dispose();
        batch.dispose();
        hudBatch.dispose();
        font.dispose();
    }
    
    @Override
    public void dispose() {

    }
    
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.showMenuScreen();
        return true;
    }
}
