package engine.entities.components;

public abstract class AbstractMovementComponent implements MovementComponent {
    private PositionComponent positionComponent;

    @Override
    public void setPositionComponent(PositionComponent positionComponent) { this.positionComponent = positionComponent; }

    protected PositionComponent getPositionComponent() { return positionComponent; }
}
