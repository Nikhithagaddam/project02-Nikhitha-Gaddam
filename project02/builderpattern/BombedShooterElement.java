package builderpattern;

import java.awt.Color;

import model.ShooterElement;

public class BombedShooterElement extends ShooterElementBuilder{
	
	@Override
	public void buildElement() {
		element.color = Color.red;
		element.filled = true;
		element.delay = 6;
		element.type = ShooterElement.BOMBED;
		
	}

}
