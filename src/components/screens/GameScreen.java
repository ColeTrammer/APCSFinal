package components.screens;

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
import components.Constants;
import components.TheGame;
import engine.entities.OneWayWall;
import engine.utils.Direction;
import engine.utils.EntityManager;
import engine.entities.SpawnTest;
import engine.entities.Wall;

/**
 * Screen that displays the Game.
 */
public class GameScreen extends InputAdapter implements Screen {
    //public static final String TAG = GameScreen.class.getName();
    
    private final TheGame game;
    private final EntityManager manager;

    private ExtendViewport gameViewport;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    
    private ScreenViewport hudViewport;
    private SpriteBatch hudBatch;
    private BitmapFont font;

    public GameScreen(TheGame game, EntityManager manager) {
        this.game = game;
        this.manager = manager;

        // adds Walls around the screen so entities are bounded by the screen.
        manager.add(new Wall(-Constants.WALL_THICKNESS, -Constants.WALL_THICKNESS, Constants.WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.WALL_THICKNESS));
        manager.add(new Wall(Constants.WALL_THICKNESS, -Constants.WALL_THICKNESS, Constants.WORLD_WIDTH, Constants.WALL_THICKNESS));
        manager.add(new Wall(Constants.WALL_THICKNESS, Constants.WORLD_HEIGHT, Constants.WORLD_WIDTH, Constants.WALL_THICKNESS));
        manager.add(new Wall(Constants.WORLD_WIDTH, -Constants.WALL_THICKNESS, Constants.WALL_THICKNESS, Constants.WORLD_HEIGHT + 2 * Constants.WALL_THICKNESS));

        manager.add(new SpawnTest());
        manager.add(new OneWayWall(150, 100, 200, 20, Direction.UP));
        manager.add(new OneWayWall(250, 250, 200, 20, Direction.UP));
        manager.add(new OneWayWall(450, 400, 200, 20, Direction.UP));
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
        /*
        Delta becoming to large is currently very problematic,
        as it causes the collision detection algorithm to break down.
        Also, it currently only happens when the user drags the screen
        around. Which should pause the game, but does not. Therefore,
        ignoring render calls when the delta is too large is advisable.
        */
        if (delta > Constants.MAX_DELTA) {
            return;
        }
        /* UPDATE */
        manager.update(delta);
        if (manager.isPlayerExpired()) {
            game.showEndScreen();
            return;
        }

        /* RENDER */
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
        game.showMenuScreen();
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
