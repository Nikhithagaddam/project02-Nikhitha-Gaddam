package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import strategypattern.ElementLanded;
import strategypattern.ElementLocation;
import strategypattern.ElementMovement;
import strategypattern.FlyingElement;
import strategypattern.MovingLeft;
import strategypattern.MovingRight;
import view.GameBoard;

public class EnemyComposite extends GameElement implements ElementLocation{

    public static final int NROWS =2;
    public static final int NCOLS=10;
    public static final int ENEMY_SIZE=20;
    public static final int UNIT_MOVE=5;

    private ArrayList<ArrayList<GameElement>> rows;
    private ArrayList<GameElement> bombs;
    private boolean movingToRight = true;
    private Random random = new Random();
    private final GameBoard gameBoard;
    
    private String  currentLocation;
    private ElementMovement elementMovement;
    private MovingRight eMovingRight = new MovingRight();
    private MovingLeft eMovingLeft = new MovingLeft();
    private ElementLanded elementLanded = new ElementLanded();
    private int enemiesCount;

    public EnemyComposite(GameBoard gameBoard) {
       rows = new ArrayList<>();
       bombs = new ArrayList<>();
       this.gameBoard = gameBoard;
       enemiesCount = NROWS * NCOLS;
       
       currentLocation = ElementLocation.FLYING;
       elementMovement = new FlyingElement();

       for(int r=0;r <NROWS;r++){
           var oneRow = new ArrayList<GameElement>();
           rows.add(oneRow);
           for (int c=0;c<NCOLS;c++){
        	   Enemy e = new Enemy( 
                       c * ENEMY_SIZE *2 , r *ENEMY_SIZE *2, ENEMY_SIZE,Color.yellow,true);
        	   e.addListener(gameBoard);
               oneRow.add(e);

               
           }
       }
    }
    
    public int getEnemiesCount() {
		return enemiesCount;
	}


    @Override
    public void render(Graphics2D g2) {
        for(var r:rows){
            for(var e:r){
                e.render(g2);
            }
        }

        for(var b :bombs){
            b.render(g2);
        }
        
    }

    @Override
    public void animate() {
    	if(currentLocation == ElementLocation.LANDED) {
    		return;
    	}
        int dx = UNIT_MOVE;
        if(movingToRight){
            if(rightEnd() >= GameBoard.WIDTH) {
                dx = -dx;
                movingToRight = false;
                elementMovement = eMovingLeft;
            }
        }else {
            dx=-dx;
            if(leftEnd() <= 0){
                dx = -dx;
                movingToRight = true;
                elementMovement = eMovingRight;
            }
        }

        for (var row:rows) {
            for( var e : row){
                e.x +=dx;
                if(currentLocation != ElementLocation.FLYING) {
                    if(e.y >= GameBoard.HEIGHT - ENEMY_SIZE) elementMovement = elementLanded;
                    if(movingToRight && currentLocation != ElementLocation.MOVING_RIGHT) e.y += ENEMY_SIZE;
                    if(!movingToRight && currentLocation != ElementLocation.MOVING_LEFT) e.y += ENEMY_SIZE;
                }
                
            }
        }
        
        elementMovement.moveElement(this);

        for(var b :bombs){
            b.animate();
        }
              
    }
    
    
    private int rightEnd(){
        int xEnd = -100;
        for(var row:rows){
            if(row.size() == 0) continue;
            int x= row.get(row.size() -1).x + ENEMY_SIZE;
            if(x > xEnd) xEnd = x;
        }
        return xEnd;
    }

    private int leftEnd(){
        int xEnd = 9000;
        for(var row:rows){
            if(row.size() == 0) continue;
            int x= row.get(0).x;
            if(x < xEnd) xEnd = x;
        }
        return xEnd;
    }

    public void dropBombs(){
        for(var row: rows){
            for( var e:row){
                if(random.nextFloat() < 0.1F){
                	Bomb b = new Bomb(e.x,e.y);
                	b.addListener(gameBoard);
                    bombs.add(b);
                }
            }
        }

    }

    public void removeBombsOutOfBound(){
        var remove = new ArrayList<GameElement>();
        for(var b : bombs){
            if(b.y >= GameBoard.HEIGHT){
                remove.add(b);
            }
            
        }
        bombs.removeAll(remove);
    }

    public void processCollision(Shooter shooter){
        var removeBullets = new ArrayList<GameElement>();

        for(var row:rows){
            var removeEnemies = new ArrayList<GameElement>();
            for(var enemy: row){
                for(var bullet:shooter.getWeapons()) {
                    if(enemy.collideWith(bullet)){
                    	--enemiesCount;
                        removeBullets.add(bullet);
                        removeEnemies.add(enemy);
                    }
                }
            }
            row.removeAll(removeEnemies);

        }
        shooter.getWeapons().removeAll(removeBullets);


        var removeBombs = new ArrayList<GameElement>();
        removeBullets.clear();

        for(var b:bombs){
            for( var bullet:shooter.getWeapons()){
                if(b.collideWith(bullet)){
                    removeBombs.add(b);
                    removeBullets.add(bullet);
                }
            }
        }

        shooter.getWeapons().removeAll(removeBullets);
        bombs.removeAll(removeBombs);
    }
    
    public ArrayList<GameElement> getBombs() {
		return bombs;
	}
    
    public String getCurrentLocation() {
		return currentLocation;
	}


	@Override
	public void notifyListeners() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setCurrentLocation(String location) {
		currentLocation = location;
		
	}
}
