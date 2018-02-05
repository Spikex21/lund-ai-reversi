package game;

import decision.Decision;

public class Board {
	public static final int X_LENGTH = 8;	//must be even
	public static final int Y_LENGTH = 8;	//must be even
	
	public static final char DEFAULT_BLACK_TILE = 'X';
	public static final char DEFAULT_WHITE_TILE = 'O';
	
	public static char BLACK_TILE = DEFAULT_BLACK_TILE;
	public static char WHITE_TILE = DEFAULT_WHITE_TILE;
	
	public char[][] board;
	
	public Board() {
		board = new char[Y_LENGTH][X_LENGTH];
		
		for(int row = Y_LENGTH - 1; row >= 0; row--) {
			for(int col = 0; col < X_LENGTH; col++) {
				board[col][row] = ' ';
			}
		}
		
		board[Y_LENGTH/2][X_LENGTH/2] = BLACK_TILE;
		board[Y_LENGTH/2-1][X_LENGTH/2-1] = BLACK_TILE;
		
		board[Y_LENGTH/2-1][X_LENGTH/2] = WHITE_TILE;
		board[Y_LENGTH/2][X_LENGTH/2-1] = WHITE_TILE;
	}
	
	public Board(Board b) {
		board = new char[Y_LENGTH][X_LENGTH];
		
		for(int row = Y_LENGTH - 1; row >= 0; row--) {
			for(int col = 0; col < X_LENGTH; col++) {
				board[col][row] = b.board[col][row];
			}
		}
	}
	
	public boolean isTileEmpty(int x, int y) {
		if(board[getActualX(x)][getActualY(y)] != ' ') {
			return false;
		}
		return true;
	}
	
	public boolean isInsideBoard(int x, int y) {
		if(x < 1 || x > Board.X_LENGTH || y < 1 || y > Board.Y_LENGTH) {
			return false;
		}	
		return true;
	}
	
	public boolean areValidMoves(char teamPiece) {
		boolean foundMove = false;
		for(int x = 1; x <= X_LENGTH && !foundMove; x++) {
			for(int y = 1; y <= Y_LENGTH && !foundMove; y++) {
				if(isValidMove(new Decision(x, y, teamPiece))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isValidMove(Decision decision) {
		if(!isTileEmpty(decision.x, decision.y))
			return false;
		if(!isInsideBoard(decision.x, decision.y))
			return false;

		Board testBoard = new Board(this);
		testBoard.playDecision(decision);
		if(testBoard.scoreBoard(decision.teamPiece) <= scoreBoard(decision.teamPiece)+1) {//+1 because the get to add one piece be default		
			return false;
		}
		return true;
	}
	
	public void playDecision(Decision decision) {
		
		board[getActualX(decision.x)][getActualY(decision.y)] = decision.teamPiece;
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				if(x == 0 && y == 0)
					continue;
				flipInDirection(x, y, decision.x, decision.y);
			}
		}
	
	}
	
	private void flipInDirection(int netX, int netY, int startX, int startY) {
		char homePiece = board[getActualX(startX)][getActualY(startY)];
		boolean foundAwayPiece = false;
		boolean foundHomePiece = false;
		
		int x = startX;
		int y = startY;
		while(!foundAwayPiece || !foundHomePiece) {
			x+=netX;
			y+=netY;
			if(x > X_LENGTH || x < 1 || y > Y_LENGTH || y < 1)
				return;
			char pieceAtLocation = board[getActualX(x)][getActualY(y)];
			if(pieceAtLocation == ' ')
				return;
			else if(pieceAtLocation == homePiece) {
				if(foundAwayPiece) {
					foundHomePiece = true;
					break;
				} else {
					return;
				}
					
			} else if(pieceAtLocation != homePiece)
				foundAwayPiece = true;
			else
				throw new IllegalArgumentException("Unrecognized Piece at position " + x +", " + y);			
		}
		
		x -= netX;
		y -= netY;
		
		while(!(x == startX && y == startY)) {
			board[getActualX(x)][getActualY(y)] = homePiece;
			x -= netX;
			y -= netY;
		}
		
	}
	
	public int scoreBoard(char teamPiece) {
		int score = 0;
		for(int row = Y_LENGTH - 1; row >= 0; row--) {
			for(int col = 0; col < X_LENGTH; col++) {
				if(board[col][row] == teamPiece)
				score++;
			}
		}
		return score;
	}
	
	private int getActualY(int y) {
		return y-1;
	}
	private int getActualX(int x) {
		return x-1;
	}
		
	public void drawBoard() {
		System.out.println("\n\n\n");
		System.out.println("  +---+---+---+---+---+---+---+---+");	//TODO make this fit with X_LENGTH/Y_LENGTH
		for(int row = Y_LENGTH - 1; row >= 0; row--) {
			StringBuilder rowBuilder = new StringBuilder((row+1) +" |");
				for(int col = 0; col < X_LENGTH; col++) {
					rowBuilder.append(' ');
					rowBuilder.append(board[col][row]);
					rowBuilder.append(' ');
					rowBuilder.append('|');
				}

			System.out.println(rowBuilder.toString());
			System.out.println("  +---+---+---+---+---+---+---+---+");	//TODO make this fit with X_LENGTH/Y_LENGTH
		}
		System.out.println("    a   b   c   d   e   f   g   h  ");
	}
	
//	public static void main(String[] args) {
//		Board b = new Board();
//		b.drawBoard();
//	}
}
