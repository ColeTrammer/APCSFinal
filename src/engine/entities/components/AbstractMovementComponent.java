package engine.entities.components;

public abstract class AbstractMovementComponent implements MovementComponent {
    private PositionComponent positionComponent;

    public AbstractMovementComponent(PositionComponent positionComponent) {
        this.positionComponent = positionComponent;
    }

    protected PositionComponent getPositionComponent() { return positionComponent; }
}
