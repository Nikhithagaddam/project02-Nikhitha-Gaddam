package view;

import java.awt.Container;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.KeyController;
import controller.TimerListener;
import model.Bomb;
import model.Enemy;
import model.EnemyComposite;
import model.Shooter;
import model.ShooterElement;
import observerpattern.GameElementCollisionObserver;

public class GameBoard implements GameElementCollisionObserver{

    public static final int WIDTH=600;
    public static final int HEIGHT=300;

    public static final int FPS =20;
    public static final int DELAY = 1000/FPS; 
    public static final int BULLETS = 400;

    private JFrame window;

    private MyCanvas canvas;
    private Shooter shooter;
    private EnemyComposite enemyComposite;
    private Timer timer;
    private TimerListener timerListener;
   
    private TimeView timeView;
    private JLabel scores;
    private JPanel northPanel;
    private int score;
    private Boolean stopped;
    private JButton controlButton;
    private JButton startButton;
    private Boolean isGameOver;
    
    
    public GameBoard(JFrame window){
            this.window=window;
    }
    
    public void init(){
    	
    	isGameOver = false;

        Container cp = window.getContentPane();
        canvas = new MyCanvas(this,WIDTH,HEIGHT);
        cp.add(BorderLayout.CENTER,canvas);
        canvas.addKeyListener(new KeyController(this));
        canvas.requestFocusInWindow();
        canvas.setFocusable(true);

        startButton = new JButton("Start");
        controlButton = new JButton("Stop");
        JButton quitButton = new JButton("Quit");
        controlButton.setFocusable(false);
        controlButton.setVisible(false);
        startButton.setFocusable(false);
        quitButton.setFocusable(false);

        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        southPanel.add(quitButton);
        southPanel.add(controlButton);
        cp.add(BorderLayout.SOUTH,southPanel);
        

        
        timeView = new TimeView(WIDTH - 40, 10, Color.darkGray, 13);
        scores = new JLabel(" ");
        northPanel = new JPanel();
        northPanel.add(scores);
        cp.add(BorderLayout.NORTH, northPanel);
 
        canvas.getGameElements().add(new TextDraw("click <start> to Play",100,100,Color.yellow,30));
        //shooter = new Shooter(GameBoard.WIDTH/2,GameBoard.HEIGHT-ShooterElement.SIZE);
        
        timerListener = new TimerListener(this);
        timer = new Timer(DELAY,timerListener);
        score = 0;

        startButton.addActionListener(event-> {
        	if(isGameOver) {
        		score = 0;
        		timeView = new TimeView(WIDTH - 40, 10, Color.darkGray, 13);
        	}
            shooter = new Shooter(GameBoard.WIDTH/2, GameBoard.HEIGHT - ShooterElement.SIZE);
            shooter.addListener(this);
            enemyComposite = new EnemyComposite(this);
            canvas.getGameElements().clear();
            canvas.getGameElements().add(shooter);
            canvas.getGameElements().add(enemyComposite);
            canvas.getGameElements().add(timeView);
            scores.setText("Scores: " + score);
            startButton.setVisible(false);
            controlButton.setVisible(true);
            timer.start();
        });
        
        
        stopped = false;
        controlButton.addActionListener(event-> {
        	if(!stopped) {
        		controlButton.setText("Continue");
        		timer.stop();
        		stopped = true;
        	} else {
        		controlButton.setText("Stop");
        		timer.start();
        		stopped = false;
        	}
        	
        });


        quitButton.addActionListener(event -> System.exit(0));
    
        
    }

    public MyCanvas getCanvas(){
        return canvas;
    }

    public Timer getTimer() {
        return timer;
    }

    public TimerListener getTimerListener() {
        return timerListener;
    }

    public Shooter getShooter(){
        return shooter;
    }

    public EnemyComposite getEnemyComposite(){
        return enemyComposite;
    }
    
    public void gameOver(String message) {
    	canvas.getGameElements().add(new TextDraw(message + ". Score" + score,100,100,Color.red,30));
    	isGameOver = true;
    	controlButton.setVisible(false);
    	startButton.setText("Restart");
    	startButton.setVisible(true);
    	timer.stop();
    }

	@Override
	public void actionPerformed(String action) {
		
		switch(action) {
			case Bomb.ELEMENTNAME:
				score += 10;
				scores.setText("Scores: " + score);
				break;
			case Enemy.ELEMENTNAME:
				score += 10;
				scores.setText("Scores: " + score);
				if(enemyComposite.getEnemiesCount() < 1) {
					gameOver("You Win");
				}
				//Process end of enemy elements
				break;
			case Shooter.ELEMENTNAME:
				if(!(shooter.getComponentSize() > 0)) {
					
					gameOver("You Lost");
				}
				break;
				
			default:
				//no action
		
		}
		
	}
}
