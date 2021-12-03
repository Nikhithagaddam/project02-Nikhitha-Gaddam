package builderpattern;

import java.awt.Color;

import model.ShooterElement;

public class HitShooterElement extends ShooterElementBuilder{

	@Override
	public void buildElement() {
		element.color = Color.red;
		element.delay = 3;
		element.type = ShooterElement.HIT;
	}

}
