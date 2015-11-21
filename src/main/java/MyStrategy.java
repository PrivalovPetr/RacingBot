import model.Car;
import model.Game;
import model.Move;
import model.World;


public class MyStrategy implements Strategy {

	@Override
	public void move(Car self, World world, Game game, Move move) {
		// TODO Auto-generated method stub
		MySecondStrategy str = new MySecondStrategy();
		
		
		str.move(self, world, game, move);
	}

}
