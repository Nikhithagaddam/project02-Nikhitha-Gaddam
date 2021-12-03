package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import model.GameElement;


public class TimeView extends GameElement {

	private String text;
    private int size;
    private int minutes;
    private int seconds;
    private int milliSeconds;
	
    public TimeView(int x , int y, Color color, int size){
        super(x,y,color,false,0,0);
        this.size=size;
        minutes = 0;
        seconds = 0;
        milliSeconds = 0;
    }
    
    
	
	@Override
	public void render(Graphics2D g2) {
		++milliSeconds;
    	if(milliSeconds >= 20){
    		milliSeconds = 0;
    		++seconds;
    		if(seconds > 59) {
    			seconds = 0;
    			++minutes;
    		}
    	}
    		
		
		String sec = seconds > 9 ? "" + seconds : "0" + seconds;
		String min = minutes > 9 ? "" + minutes : "0" + minutes;
		text = min + ":" + sec;
        g2.setColor(color);
        g2.setFont(new Font("Monospaced", Font.BOLD,size));
        g2.drawString(text,x,y);
		
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
	}
	
	public int getSeconds() {
		return seconds;
	}



	@Override
	public void notifyListeners() {
		// TODO Auto-generated method stub
		
	}


}
