package builderpattern;

import model.ShooterElement;

public abstract class ShooterElementBuilder {
	
	protected ShooterElement element;
    
    public ShooterElement getShooterElement() {
		return element;
	}
    
    public void createShooterElement(ShooterElement e) {
    	element = e;
    }
    
    public abstract void buildElement();

}
