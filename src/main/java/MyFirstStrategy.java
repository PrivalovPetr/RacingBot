import model.Car;
import model.Game;
import model.Move;
import model.World;

import static java.lang.StrictMath.*;


public final class MyFirstStrategy implements Strategy {
    
	private final double offset = 0.25D;
    private final double preOffset = 1.25D;
    
    @Override
    public void move(Car self, World world, Game game, Move move) {
    	double nextWaypointX = (self.getNextWaypointX() + 0.5D) 
    			* game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) 
        		* game.getTrackTileSize();
        
        switch (world.getTilesXY()[self.getNextWaypointX()][self.getNextWaypointY()]) {
            case LEFT_TOP_CORNER:
            	if(abs(self.getX() - nextWaypointX) < (0.5D*game.getTrackTileSize()))
            		if(abs(self.getY() - nextWaypointY) < (preOffset*game.getTrackTileSize()))
            			slowTurnRightFromBot(self, world, game, move);
            		else
            			accelerateToPreTurnRightFromBot(self, world, game, move);
            	else
            		if(abs(self.getX() - nextWaypointX) < (preOffset*game.getTrackTileSize()))
            			slowTurnBotFromRight(self, world, game, move);
            		else
            			accelerateToPreTurnBotFromRight(self, world, game, move);
                break;
            case RIGHT_TOP_CORNER:
            	if(abs(self.getX() - nextWaypointX) < (0.5D*game.getTrackTileSize()))
            		if(abs(self.getY() - nextWaypointY) < (preOffset*game.getTrackTileSize()))
            			slowTurnLeftFromBot(self, world, game, move);
            		else
            			accelerateToPreTurnLeftFromBot(self, world, game, move);
            	else
            		if(abs(self.getX() - nextWaypointX) < (preOffset*game.getTrackTileSize()))
            			slowTurnBotFromLeft(self, world, game, move);
            		else
            			accelerateToPreTurnBotFromLeft(self, world, game, move);
                break;
            case LEFT_BOTTOM_CORNER:
            	if(abs(self.getX() - nextWaypointX) < (0.5D*game.getTrackTileSize()))
            		if(abs(self.getY() - nextWaypointY) < (preOffset*game.getTrackTileSize()))
            			slowTurnRightFromTop(self, world, game, move);
            		else
            			accelerateToPreTurnRightFromTop(self, world, game, move);
            	else
            		if(abs(self.getX() - nextWaypointX) < (preOffset*game.getTrackTileSize()))
            			slowTurnTopFromRight(self, world, game, move);
            		else
            			accelerateToPreTurnTopFromRight(self, world, game, move);
                break;
            case RIGHT_BOTTOM_CORNER:
            	if(abs(self.getX() - nextWaypointX) < (0.5D*game.getTrackTileSize()))
            		if(abs(self.getY() - nextWaypointY) < (preOffset*game.getTrackTileSize()))
            			slowTurnLeftFromTop(self, world, game, move);
            		else
            			accelerateToPreTurnLeftFromTop(self, world, game, move);
            	else
            		if(abs(self.getX() - nextWaypointX) < (preOffset*game.getTrackTileSize()))
            			slowTurnTopFromLeft(self, world, game, move);
            		else
            			accelerateToPreTurnTopFromLeft(self, world, game, move);
                break;
            default:
        }
        
        
    }
    
   

	private void accelerateToPreTurnTopFromLeft(Car self, World world, 
			Game game, Move move) {
		
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX -= preOffset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY += offset * game.getTrackTileSize();
        
        double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);       

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(1.0D);
	}



	private void slowTurnTopFromLeft(Car self, World world, Game game, Move move) {
		
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX -= offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY -= offset * game.getTrackTileSize();
		
		double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);
        double speedModule = hypot(self.getSpeedX(), self.getSpeedY());

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(0.75D);

        if (speedModule * speedModule * abs(angleToWaypoint) > 2.5D * 2.5D * PI) {
            move.setBrake(true);//TODO
        }
		
	}



	private void accelerateToPreTurnLeftFromTop(Car self, World world, 
			Game game, Move move) {
		
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX += offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY -= preOffset * game.getTrackTileSize();
        
        double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);       

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(1.0D);
		
	}



	private void slowTurnLeftFromTop(Car self, World world, Game game, Move move) {
		
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX -= offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY -= offset * game.getTrackTileSize();
		
		double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);
        double speedModule = hypot(self.getSpeedX(), self.getSpeedY());

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(0.75D);

        if (speedModule * speedModule * abs(angleToWaypoint) > 2.5D * 2.5D * PI) {
            move.setBrake(true);//TODO
        }
		
	}



	private void accelerateToPreTurnTopFromRight(Car self, World world, 
			Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX += preOffset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY += offset * game.getTrackTileSize();
        
        double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);       

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(1.0D);
		
	}



	private void slowTurnTopFromRight(Car self, World world, Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX += offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY -= offset * game.getTrackTileSize();
		
		double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);
        double speedModule = hypot(self.getSpeedX(), self.getSpeedY());

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(0.75D);

        if (speedModule * speedModule * abs(angleToWaypoint) > 2.5D * 2.5D * PI) {
            move.setBrake(true);//TODO
        }
		
	}



	private void accelerateToPreTurnRightFromTop(Car self, World world, 
			Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX -= offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY -= preOffset * game.getTrackTileSize();
        
        double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);       

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(1.0D);
		
	}



	private void slowTurnRightFromTop(Car self, World world, Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX += offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY -= offset * game.getTrackTileSize();
		
		double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);
        double speedModule = hypot(self.getSpeedX(), self.getSpeedY());

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(0.75D);

        if (speedModule * speedModule * abs(angleToWaypoint) > 2.5D * 2.5D * PI) {
            move.setBrake(true);//TODO
        }
		
	}



	private void accelerateToPreTurnBotFromLeft(Car self, World world, 
			Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX -= preOffset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY -= offset * game.getTrackTileSize();
        
        double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);       

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(1.0D);
		
	}



	private void slowTurnBotFromLeft(Car self, World world, Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX -= offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY += offset * game.getTrackTileSize();
		
		double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);
        double speedModule = hypot(self.getSpeedX(), self.getSpeedY());

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(0.75D);

        if (speedModule * speedModule * abs(angleToWaypoint) > 2.5D * 2.5D * PI) {
            move.setBrake(true);//TODO
        }
		
	}



	private void accelerateToPreTurnLeftFromBot(Car self, World world, 
			Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX += offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY += preOffset * game.getTrackTileSize();
        
        double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);       

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(1.0D);
		
	}



	private void slowTurnLeftFromBot(Car self, World world, Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX -= offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY += offset * game.getTrackTileSize();
		
		double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);
        double speedModule = hypot(self.getSpeedX(), self.getSpeedY());

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(0.75D);

        if (speedModule * speedModule * abs(angleToWaypoint) > 2.5D * 2.5D * PI) {
            move.setBrake(true);//TODO
        }
		
	}



	private void accelerateToPreTurnBotFromRight(Car self, World world, 
			Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX += preOffset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY -= offset * game.getTrackTileSize();
        
        double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);       

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(1.0D);
		
	}



	private void slowTurnBotFromRight(Car self, World world, Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX += offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY += offset * game.getTrackTileSize();
		
		double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);
        double speedModule = hypot(self.getSpeedX(), self.getSpeedY());

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(0.75D);

        if (speedModule * speedModule * abs(angleToWaypoint) > 2.5D * 2.5D * PI) {
            move.setBrake(true);//TODO
        }
		
	}



	private void accelerateToPreTurnRightFromBot(Car self, World world, 
			Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX -= offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY += preOffset * game.getTrackTileSize();
        
        double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);       

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(1.0D);
		
	}

	private void slowTurnRightFromBot(Car self, World world, Game game, Move move) {
		double nextWaypointX = (self.getNextWaypointX() + 0.5D) * game.getTrackTileSize();
		nextWaypointX += offset * game.getTrackTileSize();
        double nextWaypointY = (self.getNextWaypointY() + 0.5D) * game.getTrackTileSize();
        nextWaypointY += offset * game.getTrackTileSize();
		
		double angleToWaypoint = self.getAngleTo(nextWaypointX, nextWaypointY);
        double speedModule = hypot(self.getSpeedX(), self.getSpeedY());

        move.setWheelTurn(angleToWaypoint * 32.0D / PI);
        move.setEnginePower(0.75D);

        if (speedModule * speedModule * abs(angleToWaypoint) > 2.5D * 2.5D * PI) {
            move.setBrake(true);//TODO
        }		
	}    
}
