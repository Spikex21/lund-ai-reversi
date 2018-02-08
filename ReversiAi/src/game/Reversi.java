package game;

import java.util.Scanner;

import player.Ai;
import player.Human;
import player.Player;

public class Reversi {

	public Player black;
	public Player white;
	public Player current;
	
	public Board board;
	
	private long moveTime = 0;
	
	public Reversi(Player black, Player white, Player first) {
		this.black = black;
		this.white = white;
		
		if(black.teamPiece == white.teamPiece) {
			System.out.println("Team pieces cannot be the same. Reverting back to default O and X");
			black.teamPiece = Board.DEFAULT_BLACK_TILE;
			white.teamPiece = Board.DEFAULT_WHITE_TILE;
		} else {
			Board.BLACK_TILE = black.teamPiece;
			Board.WHITE_TILE = white.teamPiece;
		}

		current = first;
		
		if(first != black && first != white) {
			throw new IllegalArgumentException("Player to move first is not in the game");
		}
		
		board = new Board();
	}
	
	private static Player createPlayer() {
		boolean isHuman = false;
		String name;
		char teamPiece;
		
		Scanner playerInput = new Scanner(System.in);
		
		System.out.print("Should this player be human or AI?    ");
		String playerType = "";
		
		do {
			playerType = playerInput.nextLine();
			if(playerType.equalsIgnoreCase("human")) {
				isHuman = true;
				break;
			} else if (playerType.equalsIgnoreCase("ai")) {
				isHuman = false;
				break;
			}
			System.out.println("Please type 'Human' or 'AI'");
		} while(true);
		
		System.out.print("What is this " + ((isHuman)? "Human's" : "AI's") + " name?    ");
		do {
			name = playerInput.nextLine();
		} while(name.length() == 0);
		
		System.out.print("What should their token character be? (for example: O or X)    ");
		do {
			teamPiece = playerInput.nextLine().charAt(0);
		} while(name.length() == 0);
		
		if(isHuman) {
			return new Human(name, teamPiece);
		} else {
			return new Ai(name, teamPiece);
		} 
		
	}
	
	private static Player whosFirst(Player p1, Player p2) {
		Scanner firstScan = new Scanner(System.in);
		
		System.out.println("Who is going first?");
		System.out.println("Type 1 for " + p1.name);
		System.out.println("Type 2 for " + p2.name);
		
		int first = firstScan.nextInt();
		while(first != 1 && first != 2) {
			System.out.println("Please input either 1 or 2");
			first = firstScan.nextInt();
		}
		firstScan.nextLine();
		return ((first == 1)? p1 : p2);
		
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
	
	private void announceNoValidMoves(Player player) {
		System.out.println();
		System.out.println("I'm sorry, but " + player.name + " has no valid moves.");
		System.out.println("Your turn has been skipped");
		System.out.println();
	}
	
	private void finish() {
		System.out.println("\n");
		System.out.println("-------Game Over-------");
		System.out.println();
		System.out.println("Time taken for all moves: " + moveTime + " ms");
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
		System.out.println("Please create the first player");
		Player black = createPlayer();
		System.out.println("Please create the second player");
		Player white = createPlayer();
		
		Reversi game = new Reversi(black, white, whosFirst(black, white));
		
		game.board.drawBoard();
		while(true) {
			if(!game.board.areValidMoves(game.current.teamPiece)) {
				Player skipped = game.current;
				game.switchTurns();
				if(!game.board.areValidMoves(game.current.teamPiece)) {
					game.finish();
					break;
				} else {
					game.announceNoValidMoves(skipped);
				}
			}
			
			game.announcePlayer(game.current.name);
			long beforeMoveTime = System.currentTimeMillis();
			game.makeDecide(game.current);
			long afterMoveTime = System.currentTimeMillis();
			game.moveTime += (afterMoveTime - beforeMoveTime);
			game.board.drawBoard();
			System.out.println();
			System.out.println("Time taken for move: " + (afterMoveTime - beforeMoveTime) + " ms");
			
			game.switchTurns();
		}
	}
}
