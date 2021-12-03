package strategypattern;

public class FlyingElement implements ElementMovement{

	
	
	
	
	
	
	@Override
	public void moveElement(ElementLocation eLocation) {
		eLocation.setCurrentLocation(ElementLocation.FLYING);
		
	}

}
