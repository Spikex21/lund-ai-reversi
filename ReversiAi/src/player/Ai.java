package player;

import java.util.Random;

import decision.Decision;
import game.Board;

public class Ai extends Player{
	
	public static final int TREE_DEPTH = 9;
	private char opponentPiece;

	public Ai(String name, char teamPiece) {
		super(name, teamPiece);
	}

	@Override
	public Decision getDecision(Board board) {
		opponentPiece = (Board.BLACK_TILE == teamPiece)? Board.WHITE_TILE : Board.BLACK_TILE; 
		Decision bestDecision = null;
		int bestChoice = Integer.MIN_VALUE;
		
		for (int x = 1; x <= 8; x++) {
			for (int y = 1; y <= 8; y++) {
				Decision tempDecision = new Decision(x, y, teamPiece);
				
				if (board.isValidMove(tempDecision)) {
					Board tempBoard = new Board(board);
					
					tempBoard.board[tempDecision.x - 1][tempDecision.y - 1] = teamPiece;
					
					int value = minValue(tempBoard, bestChoice, Integer.MAX_VALUE, TREE_DEPTH);
					
					if (value > bestChoice) {
						bestChoice = value;
						bestDecision = tempDecision;
					}
				}
			}
		}
		
		
		//TODO 	for (all spots on the board...)
		//			if(it is not a valid move)
		//				continue;
		
		//			Decision tempDecision = whatever the valid move was;
		//			Board tempBoard = new Board(board);
		//			tempBoard.makeDecision(tempDecision);
		//			int value = minValue(tempBoard, bestChoice, Integer.Max_VALUE, TREE_DEPTH);
		//			if(value > bestChoice)
		//				bestDecision = tempDecision;
		
		
		return bestDecision;
	}
	
	private int maxValue(Board board, int alpha, int beta, int depth) {
		depth--;
		if (depth == 0 || !board.areValidMoves(teamPiece)) {
			return board.scoreBoard(teamPiece);
		}
		
		int maxVal = Integer.MIN_VALUE;
	
		for (int x = 1; x <= 8; x++) {
			for (int y = 1; y <= 8; y++) {
				Decision tempDecision = new Decision(x, y, teamPiece);
				
				if (board.isValidMove(tempDecision)) {
					Board tempBoard = new Board(board);
					tempBoard.board[tempDecision.x - 1][tempDecision.y - 1] = teamPiece;

					int val = minValue(tempBoard, alpha, beta, depth);
					
					if (val > maxVal) {
						maxVal = val;
					}
					
					if (val >= beta) {
						return maxVal;
					}
						
					if (val > alpha) {
						alpha = val;
					}
				}
			}
		}

		return maxVal;
	}
	
	private int minValue(Board board, int alpha, int beta, int depth) {
		depth--;
		if (depth == 0 || !board.areValidMoves(opponentPiece)) {
			return board.scoreBoard(teamPiece);
		}
		
		int minVal = Integer.MAX_VALUE;
	
		for (int x = 1; x <= 8; x++) {
			for (int y = 1; y <= 8; y++) {
				Decision tempDecision = new Decision(x, y, opponentPiece);
				
				if (board.isValidMove(tempDecision)) {
					Board tempBoard = new Board(board);
					tempBoard.board[tempDecision.x - 1][tempDecision.y - 1] = opponentPiece;
		
					int val = maxValue(tempBoard, alpha, beta, depth);
					
					if (val < minVal) {
						minVal = val;
					}
					
					if (val <= alpha) {
						return minVal;
					}
						
					if (val < beta) {
						beta = val;
					}
				}
			}
		}
		return minVal;
	}
}
