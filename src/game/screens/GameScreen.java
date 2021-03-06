package game.screens;

import com.badlogic.gdx.Input;
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
import game.Constants;
import game.Level;
import game.TheGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    private final BitmapFont font;

    private final List<String> levels;
    private int levelIndex;

    private boolean slowmode;

    public GameScreen(TheGame game) {
        this.game = game;
        this.levels = new ArrayList<>();
        try {
            BufferedReader reader = Gdx.files.internal("assets/levels/level_map.txt").reader(8192);
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("!")) {
                    int numLevels = Integer.parseInt(line.substring(1));
                    for (int i = 0; i < numLevels; i++) {
                        levels.add(null);
                    }
                } else {
                    String[] args = line.split("\\s+");
                    if (Boolean.parseBoolean(args[2])) {
                        int i = Integer.parseInt(args[0]) - 1;
                        levels.set(i, String.format("assets/levels/%s", args[1]));
                    }
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            Gdx.app.error("File map not found", System.getProperty("user.dir") + "\\assets\\levels\\level_map.txt", e);
        } catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
            Gdx.app.error("Invalid or inaccessible file map", System.getProperty("user.dir") + "\\assets\\levels\\level_map.txt", e);
        }

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        while (levelIndex < levels.size() && levels.get(levelIndex) == null) levelIndex++;
        this.level = new Level(levels.get(levelIndex), font);
    }

    public void reset() {
        this.level = new Level(levels.get(levelIndex), font);
    }

    @Override
    public void show() {
        gameViewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        hudViewport = new ScreenViewport();
        hudBatch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        if (slowmode) {
            delta /= 10;
        }
        /*
        Delta becoming to large is currently very problematic,
        as it causes the collision detection algorithm to break down.
        Also, it currently only happens when the user drags the screen
        around. Which should pause the game, but does not. Therefore,
        ignoring render calls when the delta is too large is advisable.
        */
        if (delta > Constants.MAX_DELTA) {
            int divisor = 2;
            while (delta / divisor > Constants.MAX_DELTA) {
                divisor++;
            }
            delta /= divisor;
            for (int j = 0; j < divisor - 1; j++) {
                level.update(delta);
            }
        }
        /* UPDATE */
        level.update(delta);
        if (level.getLevelState() == Level.LevelState.LOST) {
            game.showEndScreen();
            return;
        } else if (level.getLevelState() == Level.LevelState.WON) {
            levelIndex++;
            while (levelIndex < levels.size() && levels.get(levelIndex) == null) levelIndex++;
            if (levelIndex < levels.size()) {
                level = new Level(levels.get(levelIndex), font);
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

        if (levelIndex >= levels.size()) {
            font.draw(hudBatch, "You Won!", 20, Constants.WORLD_HEIGHT - 20);
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
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.Y) {
            slowmode = !slowmode;
        }
        level.keyDown(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        level.keyUp(keycode);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        level.keyTyped(character);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.showMenuScreen();
        return true;
    }
}
