package player;

import java.util.Random;

import decision.Decision;
import game.Board;

public class Ai extends Player{

	public Ai(String name, char teamPiece) {
		super(name, teamPiece);
	}

	@Override
	public Decision getDecision(Board board) {
		//TODO temporary random choice AI
		Decision d = null;
		Random rand = new Random();
		do {
			int x = rand.nextInt(8)+1;
			int y = rand.nextInt(8)+1;
			d = new Decision(x, y, teamPiece);
		}while(!board.isValidMove(d));
		return d;
	}

}
