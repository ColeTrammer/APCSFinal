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
import components.Level;
import components.TheGame;

/**
 * Screen that displays the Game.
 */
public class GameScreen extends InputAdapter implements Screen {
    //public static final String TAG = GameScreen.class.getName();
    
    private final TheGame game;
    private Level level;

    private ExtendViewport gameViewport;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    
    private ScreenViewport hudViewport;
    private SpriteBatch hudBatch;
    private BitmapFont font;

    private int levelIndex;

    public GameScreen(TheGame game) {
        this.game = game;
        this.level = new Level(String.format("levels/l%d.js", ++levelIndex));
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
        level.update(delta);
        if (level.getLevelState() == Level.LevelState.LOST) {
            game.showEndScreen();
            return;
        } else if (level.getLevelState() == Level.LevelState.WON) {
            levelIndex++;
            if (levelIndex <= Constants.NUM_LEVELS) {
                level = new Level(String.format("levels/l%d.js", levelIndex));
            }
        }

        /* RENDER */
        Gdx.gl.glClearColor(Constants.GAME_BACKGROUND_COLOR.r, Constants.GAME_BACKGROUND_COLOR.g, Constants.GAME_BACKGROUND_COLOR.b, Constants.GAME_BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameViewport.apply(true);

        renderer.setProjectionMatrix(gameViewport.getCamera().combined);
        batch.setProjectionMatrix(gameViewport.getCamera().combined);

        level.render(renderer, batch);

        hudViewport.apply(true);

        hudBatch.setProjectionMatrix(hudViewport.getCamera().combined);
        hudBatch.begin();

        if (levelIndex > Constants.NUM_LEVELS) {
            font.draw(hudBatch, "You Won!", 20, Constants.WORLD_HEIGHT - 30);
        }

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
