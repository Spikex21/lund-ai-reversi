package game;

import player.Ai;
import player.Human;
import player.Player;

public class Reversi {

	public Player black;
	public Player white;
	public Player current;
	
	public Board board;
	
	public Reversi(Player black, Player white, Player first) {
		this.black = black;
		Board.BLACK_TILE = black.teamPiece;
		this.white = white;
		Board.WHITE_TILE = white.teamPiece;
		current = first;
		
		if(first != black && first != white) {
			throw new IllegalArgumentException("Player to move first is not in the game");
		}
		
		board = new Board();
	}
	
	private void announcePlayer(String playerName) {
		System.out.println();
		System.out.println("-------------------------");
		System.out.println(playerName +"'s move!");
		System.out.println("-------------------------");
		System.out.println();
	}
	
	private void makeDecide(Player p) {
		board.playDecision(p.getDecision(board));
	}
	
	private void switchTurns() {
		current = (black == current)? white : black;
	}
	
	private void noValidMoves() {
		System.out.println();
		System.out.println("I'm sorry, but " + current.name + " has no valid moves.");
		System.out.println();
	}
	
	private void finish() {
		System.out.println();
		System.out.println("-------------------------");
		System.out.println("Final Score");
		System.out.println("-------------------------");
		int blackScore = board.scoreBoard(black.teamPiece);
		int whiteScore = board.scoreBoard(white.teamPiece);
		System.out.println(black.name + " (" + black.teamPiece +  ")" + ": " + blackScore + "  |  " + white.name + " (" + white.teamPiece +  ")" + ": " + whiteScore);
		if(blackScore == whiteScore)
			System.out.println("It's a tie!");
		else {
			Player winner = (blackScore > whiteScore)? black : white;
			System.out.println(winner.name + " Wins!");
		}
	}
	
	public static void main(String[] args) {
		Player black = new Ai("Evan", 'X');
		Player white = new Ai("Ben", 'O');
		Reversi game = new Reversi(black, white, white);
		long moveTime = 0;
		
		game.board.drawBoard();
		while(true) {
			game.announcePlayer(game.current.name);
			long beforeMoveTime = System.currentTimeMillis();
			game.makeDecide(game.current);
			long afterMoveTime = System.currentTimeMillis();
			moveTime += (afterMoveTime - beforeMoveTime);
			game.board.drawBoard();
			System.out.println();
			System.out.println("Time taken for move: " + (afterMoveTime - beforeMoveTime) + " ms");
			
			game.switchTurns();
			if(!game.board.areValidMoves(game.current.teamPiece)) {
				char opponentPiece = (Board.BLACK_TILE == game.current.teamPiece)? Board.WHITE_TILE : Board.BLACK_TILE; 
				
				if (!game.board.areValidMoves(opponentPiece)) {
					long endTime = System.currentTimeMillis();
					System.out.println();
					System.out.println("Time taken for all moves: " + moveTime + " ms");
					game.finish();
					break;
				} else {
					game.noValidMoves();
					game.switchTurns();
				}
			}
		}
	}
}
