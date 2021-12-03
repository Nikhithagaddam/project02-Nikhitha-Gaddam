package strategypattern;

public class MovingLeft implements ElementMovement{

	@Override
	public void moveElement(ElementLocation eLocation) {
		eLocation.setCurrentLocation(ElementLocation.MOVING_LEFT);
		
	}

}
