package player;

import decision.Decision;
import game.Board;

public abstract class Player {
	
	public String name;
	public char teamPiece;
	
	public Player(String name, char teamPiece) {
		this.name = name;
		this.teamPiece = teamPiece;
	}
	
	public abstract Decision getDecision(Board board);
}
