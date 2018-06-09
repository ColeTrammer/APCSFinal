package engine.entities.components;

public abstract class AbstractMovementComponent implements MovementComponent {
    private final PositionComponent positionComponent;

    @SuppressWarnings("WeakerAccess")
    public AbstractMovementComponent(PositionComponent positionComponent) {
        this.positionComponent = positionComponent;
    }

    protected PositionComponent getPositionComponent() { return positionComponent; }
}
