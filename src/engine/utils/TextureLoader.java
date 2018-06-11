package engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureLoader {
    private static final TextureLoader instance = new TextureLoader();
    public static TextureLoader getInstance() { return instance; }

    private final Map<String, Texture> textures;

    public Texture getTexture(String entityName, float width, float height) {
        String key = getFilePath(entityName, width, height);
        if (textures.get(key) == null) {
            textures.put(key, new Texture(Gdx.files.internal(key)));
        }
        return textures.get(key);
    }

    private String getFilePath(String entityName, float width, float height) {
        return String.format("assets/textures/%s_%dx%d.png", entityName, (int) width, (int) height);
    }

    private TextureLoader() {
        textures = new HashMap<>();
    }
}
