package observerpattern;

public interface GameElementCollision {
	
	void addListener(GameElementCollisionObserver o);
	void removeListener(GameElementCollisionObserver o);
	void notifyListeners();

}
