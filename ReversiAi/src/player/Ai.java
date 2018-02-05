package player;

import java.util.Random;

import decision.Decision;
import game.Board;

public class Ai extends Player{
	
	public static final int TREE_DEPTH = 3;
	private char opponentPiece;

	public Ai(String name, char teamPiece) {
		super(name, teamPiece);
	}

	@Override
	public Decision getDecision(Board board) {
		opponentPiece = (Board.BLACK_TILE == teamPiece)? Board.WHITE_TILE : Board.BLACK_TILE; 
		Decision bestDecision = null;
		int bestChoice = Integer.MIN_VALUE;
		
		//TODO 	for (all spots on the board...)
		//			if(it is not a valid move)
		//				continue;
		
		//			Decision tempDecision = whatever the valid move was;
		//			Board tempBoard = new Board(board);
		//			tempBoard.makeDecision(tempDecision);
		//			int value = minValue(tempBoard, bestChoice, Integer.Max_VALUE, TREE_DEPTH);
		//			if(value > bestChoice)
		//				bestDecision = tempDecision;
		
		
		return null;
	}
	
	private int maxValue(Board board, int alpha, int beta, int depth) {
		depth--;
		if(depth == 0)
			return board.scoreBoard(teamPiece);
		/*
		 * from video stuff
		 */
		return 0;
	}
	
	private int minValue(Board board, int alpha, int beta, int depth) {
		/*
		 * from video stuff
		 */
		return 0;
	}

}
