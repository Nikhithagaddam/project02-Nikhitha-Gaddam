package model;

import java.util.ArrayList;

import builderpattern.BombedShooterElement;
import builderpattern.HitShooterElement;
import builderpattern.ShooterElementBuilder;
import builderpattern.ShooterElementBuilderDirector;

import java.awt.Color;
import java.awt.Graphics2D;

public class Shooter extends GameElement {

    public static final int UNIT_MOVE = 10;
    public static final int MAX_BULLETS = 3;
    
    public static final String ELEMENTNAME = "Shooter Element";

    private ArrayList<GameElement> components = new ArrayList<>();
    private ArrayList<GameElement> weapons = new ArrayList<>();
    private int componentSize;
    private ShooterElementBuilderDirector builderDirector;
    private ShooterElementBuilder elementBuilder;

    public Shooter(int x, int y){
          super(x,y,0,0);
          var size = ShooterElement.SIZE;
          var s1= new ShooterElement(x - size,y -  size,Color.white,false);
          var s2= new ShooterElement(x,y - size,Color.white,false);
          var s3= new ShooterElement(x - size,y,Color.white,false);
          var s4= new ShooterElement(x,y,Color.white,false);
          components.add(s1);
          components.add(s2);
          components.add(s3);
          components.add(s4);
          componentSize = components.size();
          builderDirector = new ShooterElementBuilderDirector();
    }


    public void moveRight(){
        super.x += UNIT_MOVE;
        for (var c: components){
            c.x += UNIT_MOVE;
        }
    }

    public void moveLeft(){
        super.x -= UNIT_MOVE;
        for (var c: components){
            c.x -= UNIT_MOVE;
        }
    }
    
    public void processCollision(ArrayList<GameElement> bombs) {
    	var eliminatedBombs =  new ArrayList<GameElement>();
    	var eliminatedShooters = new ArrayList<GameElement>();

    	for (var c: components) {
        	for(var b:bombs){
        		if(c.collideWith(b)) {
        			ShooterElement e = (ShooterElement) c;
        			elementBuilder = new HitShooterElement();
        			builderDirector.setBuilder(elementBuilder);
        			builderDirector.buildElement(e);
        				
          			eliminatedBombs.add(b);
        		}
        		
        	}
        	ShooterElement e = (ShooterElement) c;
        	
        	if(e.type == ShooterElement.HIT && e.delay > 0) {
				e.delay = e.delay - 1;
			} else if(e.type == ShooterElement.HIT){
				elementBuilder = new BombedShooterElement();
				builderDirector.setBuilder(elementBuilder);
				builderDirector.buildElement(e);
			}
        	
        	if(e.type == ShooterElement.BOMBED && e.delay > 0) 
        		e.delay = e.delay - 1;
        	else if(e.type == ShooterElement.BOMBED) {
        		eliminatedShooters.add(e);
        		--componentSize;
        	}
        	
        	
    	}
    	
    	
    	bombs.removeAll(eliminatedBombs);
    	components.removeAll(eliminatedShooters);
    	notifyListeners();

    }

    public boolean canFireMoreBullet(){
        return weapons.size() < MAX_BULLETS && components.size() > 0;
    }
    
    public int getComponentSize() {
    	return componentSize;
    }

    public void removeBulletsOutOfBound(){
        var remove = new ArrayList<GameElement>();
        for (var w:weapons) {
            if(w.y < 0) remove.add(w);
        }
        weapons.removeAll(remove);
    }
    public ArrayList<GameElement> getWeapons(){
        return weapons;
    }
     @Override
    public void render(Graphics2D g2){
       for(var c: components){
           c.render(g2);
       }
       for(var w: weapons){
        w.render(g2);
    }
    }
    
    @Override
    public void animate(){
        for(var w: weapons){
            w.animate();
        }

    }


	@Override
	public void notifyListeners() {
		for(var o: observers) {
			o.actionPerformed(ELEMENTNAME);
		}
		
	}
}
