package model;
import java.awt.Graphics2D;
import java.awt.Color;

public class ShooterElement extends GameElement{


    public static final int SIZE=20;
    public static final String ELEMENTNAME = "Shooter element";
    public static final int HIT = 1;
    public static final int NORMAL = 0;
    public static final int BOMBED = 2;
    
    public int delay = 0;
    public int type = NORMAL;

    public ShooterElement(int x,int y,Color color,boolean filled){
        super(x,y,color,filled,SIZE,SIZE);
    }
    @Override
    public void render(Graphics2D g2){
        g2.setColor(color);
        if(super.filled){
            g2.fillRect(x,y,width,height);

        }else{
            g2.drawRect(x, y, width, height);
        }

    }
    
    @Override
    public void animate(){

    }
	@Override
	public void notifyListeners() {
		for(var o: observers) {
			o.actionPerformed(ELEMENTNAME);
		}
		
	}
}
