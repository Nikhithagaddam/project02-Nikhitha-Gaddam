package strategypattern;

public class MovingRight implements ElementMovement{
	
	@Override
	public void moveElement(ElementLocation eLocation) {
		eLocation.setCurrentLocation(ElementLocation.MOVING_RIGHT);
		
	}
	

}
