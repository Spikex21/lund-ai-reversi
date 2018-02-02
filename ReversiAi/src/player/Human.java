package player;

import java.util.Scanner;

import decision.Decision;
import game.Board;

public class Human extends Player{

	public Human(String name, char teamPiece) {
		super(name, teamPiece);
	}
	
	@Override
	public Decision getDecision(Board board) {
		Scanner scan = new Scanner(System.in);
		Decision decision = null;
		
		System.out.print("Your piece is '" + teamPiece + "', where would you like to move?   ");	//TODO "move?"
		
		while(true) {
			String location = scan.nextLine();
			if(location.length() != 2) {
				System.out.println("Please use form XY where X is lowercase 'a-h' and Y is '1-8'");
				System.out.print("Try again:   ");
				continue;
			}
			
			int x = location.charAt(0) - 'a' + 1;
			int y = location.charAt(1) - '1' + 1;
			
			if(!board.isInsideBoard(x, y)) {
				System.out.println("That location is not inside the board");
				System.out.println("X should be lowercase 'a-h' and Y should be '1-8'");
				System.out.print("Try again:   ");
				continue;
			}
			
			if(!board.isTileEmpty(x, y)) {
				System.out.println("That tile is already occupied");
				System.out.print("Try again:   ");
				continue;
			}
			
			decision = new Decision(x, y, teamPiece);
			if(!board.isValidMove(decision)) {
				System.out.println("You will not gain any points from that position");
				System.out.print("Try again:   ");
				continue;
			}
			break;
			
		}
		return decision;
	}

}
