package engine.entities.components;

import com.badlogic.gdx.math.Vector2;

@SuppressWarnings("unused")
public class SimpleHarmonicOscillation extends Acceleration {
    private final Vector2 center;
    private final float k;

    public SimpleHarmonicOscillation(PositionComponent positionComponent, float centerX, float centerY, float amplitudeX, float amplitudeY, float k) {
        super(positionComponent, (float) Math.sqrt(k * Math.max(0, Math.pow(amplitudeX, 2) - Math.pow(centerX - positionComponent.getX(), 2))), (float) Math.sqrt(k * Math.max(0, Math.pow(amplitudeY, 2) - Math.pow(centerY - positionComponent.getY(), 2))), 0, 0);
        this.center = new Vector2(centerX, centerY);
        this.k = k;
    }

    @Override
    public void move(float delta) {
        getAcceleration().set(k * (center.x - getPositionComponent().getX()), k * (center.y - getPositionComponent().getY()));
        super.move(delta);
    }
}
