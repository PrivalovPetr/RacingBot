import static java.lang.StrictMath.PI;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.hypot;

import java.util.LinkedList;
import java.util.List;

import model.Car;
import model.Game;
import model.Move;
import model.TileType;
import model.World;


public class MySecondStrategy implements Strategy {

	List<MyTile> way = null;
	int turnType;
	static int currentDirection = 0;
	
	final int TOP = 1;
	final int RIGHT = 2;
	final int BOT = 3;
	final int LEFT = 4;
	
	final int RIGHT_FROM_BOT = 1;
	final int BOT_FROM_RIGHT = 2;
	final int LEFT_FROM_BOT = 3;
	final int BOT_FROM_LEFT = 4;
	final int RIGHT_FROM_TOP = 5;
	final int TOP_FROM_RIGHT = 6;
	final int LEFT_FROM_TOP = 7;
	final int TOP_FROM_LEFT = 8;
	
	private final double offset = 0.25D;
    private final double preOffset = 1.25D;
	@Override
	public void move(Car self, World world, Game game, Move move) {
		
		world.getWaypoints(); //TODO
		MyTile nextCheckPoint = new MyTile(self.getNextWaypointX(),self.getNextWaypointY());
		
		findWay(self, world, game, move);
		if (currentDirection == 0){
			double angle = self.getAngle();
			currentDirection = TOP;
			if (angle == 0) currentDirection = RIGHT;
			if (angle == PI) currentDirection = LEFT;
			if (angle == PI/2) currentDirection = BOT;
			if (angle == -PI/2) currentDirection = TOP;
		}
		
		int turn = getNextTurn();
		double turnX = (way.get(turn).x +0.5D)*game.getTrackTileSize();
		double turnY = (way.get(turn).y +0.5D)*game.getTrackTileSize();
		switch (turnType) {
        case RIGHT_FROM_BOT:
        	if(abs(self.getY() - turnY) < (preOffset*game.getTrackTileSize())){
        		turnX += offset * game.getTrackTileSize();
        		turnY += offset * game.getTrackTileSize();
        		slowTurn(self, world, game, move, new TurnPoint(turnX, turnY));
        	}
        	else{
        		turnY += preOffset * game.getTrackTileSize();    		
        		accelerate(self, world, game, move, new Point(turnX, turnY));
        	}
        	break;
        case BOT_FROM_RIGHT:
        	if(abs(self.getX() - turnX) < (preOffset*game.getTrackTileSize())){
        		turnX += offset * game.getTrackTileSize();
        		turnY += offset * game.getTrackTileSize();
        		slowTurn(self, world, game, move, new TurnPoint(turnX, turnY));
        	}else{
        		turnX += preOffset * game.getTrackTileSize();    		
        		accelerate(self, world, game, move, new Point(turnX, turnY));
        	}
            break;
        case LEFT_FROM_BOT:
        	if(abs(self.getY() - turnY) < (preOffset*game.getTrackTileSize())){
        		turnX -= offset * game.getTrackTileSize();
        		turnY += offset * game.getTrackTileSize();
        		slowTurn(self, world, game, move, new TurnPoint(turnX, turnY));
        	}else{
        		turnY += preOffset * game.getTrackTileSize();
        		accelerate(self, world, game, move, new Point(turnX, turnY));
        	}
        	break;
        case BOT_FROM_LEFT:
        	if(abs(self.getX() - turnX) < (preOffset*game.getTrackTileSize())){
        		turnX -= offset * game.getTrackTileSize();
        		turnY += offset * game.getTrackTileSize();
        		slowTurn(self, world, game, move, new TurnPoint(turnX, turnY));
        	}else{
        		turnX -= preOffset * game.getTrackTileSize();
        		accelerate(self, world, game, move, new Point(turnX, turnY));
        	}
            break;
        case RIGHT_FROM_TOP:
        	if(abs(self.getY() - turnY) < (preOffset*game.getTrackTileSize())){
        		turnX += offset * game.getTrackTileSize();
        		turnY -= offset * game.getTrackTileSize();
        		slowTurn(self, world, game, move, new TurnPoint(turnX, turnY));
        	}else{
        		turnY -= preOffset * game.getTrackTileSize();
        		accelerate(self, world, game, move, new Point(turnX, turnY));
        	}
        	break;
        case TOP_FROM_RIGHT:
        	if(abs(self.getX() - turnX) < (preOffset*game.getTrackTileSize())){
        		turnX += offset * game.getTrackTileSize();
        		turnY -= offset * game.getTrackTileSize();
        		slowTurn(self, world, game, move, new TurnPoint(turnX, turnY));
        	}else{
        		turnX += preOffset * game.getTrackTileSize();
        		accelerate(self, world, game, move, new Point(turnX, turnY));
        	}
            break;
        case LEFT_FROM_TOP:
        	if(abs(self.getY() - turnY) < (preOffset*game.getTrackTileSize())){
        		turnX -= offset * game.getTrackTileSize();
        		turnY -= offset * game.getTrackTileSize();
        		slowTurn(self, world, game, move, new TurnPoint(turnX, turnY));
        	}else{
        		turnY -= preOffset * game.getTrackTileSize();
        		accelerate(self, world, game, move, new Point(turnX, turnY));
        	}
        	break;
        case TOP_FROM_LEFT:
        	if(abs(self.getX() - turnX) < (preOffset*game.getTrackTileSize())){
        		turnX -= offset * game.getTrackTileSize();
        		turnY -= offset * game.getTrackTileSize();
        		slowTurn(self, world, game, move, new TurnPoint(turnX, turnY));
        	}else{
        		turnX -= preOffset * game.getTrackTileSize();
        		accelerate(self, world, game, move, new Point(turnX, turnY));
        	}
            break;
        default:
        	accelerate(self, world, game, move, new Point(turnX, turnY));
    }
		

	}
	
	

	

	private void accelerate(Car self, World world, Game game, Move move, Point point) {
		double angleToWaypoint = self.getAngleTo(point.x, point.y);       

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(1.0D - angleToWaypoint * angleToWaypoint);
        
        //TODO nitro conditions, 
        //TODO Oil conditions
        //TODO throw conditions
	}





	private void slowTurn(Car self, World world, Game game, Move move, Point point) {
		
		double angleToWaypoint = self.getAngleTo(point.x, point.y);
        double speedModule = hypot(self.getSpeedX(), self.getSpeedY());

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(0.75D);

        if (speedModule * speedModule * abs(angleToWaypoint) > 2.5D * 2.5D * PI) {
            move.setBrake(true);//TODO
        }
      //TODO Oil conditions
      //TODO throw conditions
      
	}





	private int getNextTurn() {
		int wayDirection = currentDirection;
		
		for(int i = 0; i<way.size()-1; i++){
			int newWayDirection;
			if (way.get(i).x == way.get(i+1).x){
				newWayDirection = way.get(i).y < way.get(i+1).y ? BOT : TOP; //1 = TOP, 
			}else{														 //2 = RIGHT
				newWayDirection = way.get(i).x < way.get(i+1).x ? RIGHT : LEFT;// 3 = BOT, 
			}															// 4 = LEFT
			if (wayDirection != newWayDirection){
				currentDirection = newWayDirection;
				if(wayDirection == 1){
					if(newWayDirection == 4)
						turnType = LEFT_FROM_BOT;
					if(newWayDirection == 2)
						turnType = RIGHT_FROM_BOT;
				}
				if(wayDirection == 3){
					if(newWayDirection == 4)
						turnType = LEFT_FROM_TOP;
					if(newWayDirection == 2)
						turnType = RIGHT_FROM_TOP;
				}
				if(wayDirection == 2){
					if(newWayDirection == 1)
						turnType = TOP_FROM_LEFT;
					if(newWayDirection == 3)
						turnType = BOT_FROM_LEFT;
				}
				if(wayDirection == 4){
					if(newWayDirection == 1)
						turnType = TOP_FROM_RIGHT;
					if(newWayDirection == 3)
						turnType = BOT_FROM_RIGHT;
				}
				return i;
			}
		}
		return way.size()-1;
	}



	private void findWay(Car self, World world, Game game, Move move) {
		int x = (int) (self.getX()/800);
		int y = (int) (self.getY()/800);
		MyTile start = new MyTile(x, y);
		MyTile finish = new MyTile(self.getNextWaypointX(), self.getNextWaypointY());
		int xSize = world.getTilesXY().length;
		int ySize = world.getTilesXY()[0].length;
		int mass[][] = new int[xSize][ySize];
		for(int i = 0; i<xSize; i++)
			for(int j = 0; j<ySize; j++)
				mass[i][j] = 1000;
		mass[start.x][start.y] = 0;
		LinkedList<MyTile> borderTiles = new LinkedList<>();
		borderTiles.add(start);
		while(!borderTiles.isEmpty()){
			MyTile buf = borderTiles.remove();
			if (buf.equals(finish))
				break; //Выйти если нашел финиш 
			int d = mass[buf.x][buf.y];
			try{
				if (world.getTilesXY()[buf.x+1][buf.y]!=TileType.EMPTY){
					if(mass[buf.x+1][buf.y]>d+1){
						mass[buf.x+1][buf.y] = d+1;
						borderTiles.add(new MyTile(buf.x+1, buf.y));
					}
				}
			}catch(IndexOutOfBoundsException ex){}
			try{
				if (world.getTilesXY()[buf.x-1][buf.y]!=TileType.EMPTY){
					if(mass[buf.x-1][buf.y]>d+1){
						mass[buf.x-1][buf.y] = d+1;
						borderTiles.add(new MyTile(buf.x-1, buf.y));
					}
				}
			}catch(IndexOutOfBoundsException ex){}
			try{	
				if (world.getTilesXY()[buf.x][buf.y+1]!=TileType.EMPTY){
					if(mass[buf.x][buf.y+1]>d+1){
						mass[buf.x][buf.y+1] = d+1;
						borderTiles.add(new MyTile(buf.x, buf.y+1));
					}
				}
			}catch(IndexOutOfBoundsException ex){}
			try{
				if (world.getTilesXY()[buf.x][buf.y-1]!=TileType.EMPTY){
					if(mass[buf.x][buf.y-1]>d+1){
						mass[buf.x][buf.y-1] = d+1;
						borderTiles.add(new MyTile(buf.x, buf.y-1));
					}
				}
			}catch(IndexOutOfBoundsException ex){}
		}
		LinkedList<MyTile> pass = new LinkedList<MyTile>();
		pass.addLast(finish);
		if(mass[finish.x][finish.y]<1000){//Дошли до финиша
			//Восстанавливаем путь
			while(true){
				if(start.equals(pass.get(0)))
					break;
				int cx = pass.get(0).x;
				int cy = pass.get(0).y;
				int d = mass[cx][cy];
				try{
					if(mass[cx+1][cy] == d-1){
						pass.addFirst(new MyTile(cx+1, cy));
						continue;
					}
				}catch(IndexOutOfBoundsException ex){}
				try{
					if(mass[cx-1][cy] == d-1){
						pass.addFirst(new MyTile(cx-1, cy));
						continue;
					}
				}catch(IndexOutOfBoundsException ex){}
				try{
					if(mass[cx][cy+1] == d-1){
						pass.addFirst(new MyTile(cx, cy+1));
						continue;
					}
				}catch(IndexOutOfBoundsException ex){}
				try{
					if(mass[cx][cy-1] == d-1){
						pass.addFirst(new MyTile(cx, cy-1));
						continue;
					}
				}catch(IndexOutOfBoundsException ex){}
			}
		}
		//восстановили путь
		way = pass;
	}



	private class MyTile{
		private int x;
		private int y;
		public MyTile(int x, int y){
			this.x = x;
			this.y = y;
		}
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		public boolean equals(MyTile other){
			if((this.x==other.getX())&&(this.y==other.getY())){
				return true;
			}
			return false;
		}
		public String toString(){
			return x+","+y;
		}
	}
	private class Point{
		private double x;
		private double y;
		public Point(double x, double y){
			this.x = x;
			this.y = y;
		}
		public String toString(){
			return x+","+y;
		}
	}
	
	private class TurnPoint extends Point{

		public TurnPoint(double turnX, double turnY) {
			super(turnX, turnY);
		}		
	}
}
