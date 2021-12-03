package strategypattern;

public interface ElementLocation {
	
	public static final String LANDED = "Element touched down";
	public static final String MOVING_RIGHT = "Element moving right";
	public static final String MOVING_LEFT = "Element moving left";
	public static final String FLYING = "Element flying";
	
	
	void setCurrentLocation(String location);

}
