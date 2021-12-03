package builderpattern;

import model.ShooterElement;

public class ShooterElementBuilderDirector {

	private ShooterElementBuilder builder;
	
	public ShooterElement getShooterElement() {
		return builder.getShooterElement();
	}
	
	public void setBuilder(ShooterElementBuilder elementBuilder) {
		builder = elementBuilder;
	}
	
	public void buildElement(ShooterElement e) {
		builder.createShooterElement(e);
		builder.buildElement();
	}

}
