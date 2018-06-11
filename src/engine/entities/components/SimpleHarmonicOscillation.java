package engine.entities.components;

import com.badlogic.gdx.math.Vector2;

@SuppressWarnings("unused")
public class SimpleHarmonicOscillation extends Acceleration {
    private Vector2 center;
    private Vector2 amplitude;
    private float k;

    public SimpleHarmonicOscillation(float centerX, float centerY, float amplitudeX, float amplitudeY, float k) {
        super(0, 0, 0, 0);
        this.center = new Vector2(centerX, centerY);
        this.amplitude = new Vector2(amplitudeX, amplitudeY);
        this.k = k;
    }

    @Override
    public void setPositionComponent(PositionComponent positionComponent) {
        getVelocity().set(
                (float) Math.sqrt(k * Math.max(0, Math.pow(amplitude.x, 2) - Math.pow(center.x - positionComponent.getX(), 2))),
                (float) Math.sqrt(k * Math.max(0, Math.pow(amplitude.y, 2) - Math.pow(center.y - positionComponent.getY(), 2))));
        super.setPositionComponent(positionComponent);
    }

    @Override
    public void move(float delta) {
        getAcceleration().set(k * (center.x - getPositionComponent().getX()), k * (center.y - getPositionComponent().getY()));
        super.move(delta);
    }
}
