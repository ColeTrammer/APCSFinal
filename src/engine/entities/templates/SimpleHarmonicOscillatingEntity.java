package engine.entities.templates;

import com.badlogic.gdx.math.Vector2;

public abstract class SimpleHarmonicOscillatingEntity extends AcceleratingRectangleEntity {
    private Vector2 center;
    private float k;

    public SimpleHarmonicOscillatingEntity(float x, float y, float width, float height, float centerX, float centerY, float amplitudeX, float amplitudeY, float k) {
        // 0.5 * k * A^2 = 0.5 * m * v^2 + 0.5 * k * x^2
        // m = 1 b/c we can
        // v = sqrt(k * A^2 - k * x^2)
        super(x, y, width, height, (float) Math.sqrt(k * Math.max(0, Math.pow(amplitudeX, 2) - Math.pow(centerX - (x + width / 2), 2))), (float) Math.sqrt(k * Math.max(0, Math.pow(amplitudeY, 2) - Math.pow(centerY - (y + height / 2), 2))), 0, 0);
        this.center = new Vector2(centerX, centerY);
        this.k = k;
    }

    @Override
    public void update(float delta) {
        // F = -kx = ma
        // m = 1 b/c we can
        // a = -kx
        setAcceleration(k * (center.x - (getX() + getWidth() / 2)), k * (center.y - (getY() + getHeight() / 2)));
        super.update(delta);
    }
}
