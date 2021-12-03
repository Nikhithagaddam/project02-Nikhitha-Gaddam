package strategypattern;

public class ElementLanded implements ElementMovement {

	@Override
	public void moveElement(ElementLocation eLocation) {
		eLocation.setCurrentLocation(ElementLocation.LANDED);
		
	}

}
