/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import java.util.Random;

/**
 * This class includes the methods to support the search of a solution.
 */
public class Search
{
	public static final int horizontalGridSize = 5;
	public static int count = 0;
	public static final int verticalGridSize = 6;

	public static final char[] input = {'W', 'Y', 'I', 'T', 'Z', 'L'};

	//	{'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F'}

	//Static UI class to display the board
	public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);

	/**
	 * Helper function which starts a basic search algorithm
	 */
	public static void search()
	{
		// Initialize an empty board
		int[][] field = new int[horizontalGridSize][verticalGridSize];

		for(int i = 0; i < field.length; i++)
		{
			for(int j = 0; j < field[i].length; j++)
			{
				// -1 in the state matrix corresponds to empty square
				// Any positive number identifies the ID of the pentomino
				field[i][j] = -1;
			}
		}
		//Start the basic search
		if (basicSearch(field, 0)) {
			System.out.println("Solution Found");
		} else {
			System.out.println("Has No Solution");
		}
		ui.setState(field);
	}

	/**
	 * Get as input the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
	private static int characterToID(char character) {
		int pentID = -1;
		if (character == 'X') {
			pentID = 0;
		} else if (character == 'I') {
			pentID = 1;
		} else if (character == 'Z') {
			pentID = 2;
		} else if (character == 'T') {
			pentID = 3;
		} else if (character == 'U') {
			pentID = 4;
		} else if (character == 'V') {
			pentID = 5;
		} else if (character == 'W') {
			pentID = 6;
		} else if (character == 'Y') {
			pentID = 7;
		} else if (character == 'L') {
			pentID = 8;
		} else if (character == 'P') {
			pentID = 9;
		} else if (character == 'N') {
			pentID = 10;
		} else if (character == 'F') {
			pentID = 11;
		}
		return pentID;
	}

	/**
	 * Basic implementation of a search algorithm. It is not a bruto force algorithms (it does not check all the posssible combinations)
	 * but randomly takes possible combinations and positions to find a possible solution.
	 * The solution is not necessarily the most efficient one
	 * This algorithm can be very time-consuming
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 */
	private static boolean basicSearch(int[][] field, int index){
		count++;
		if (index == input.length) {
			return true;
		}
		int id = characterToID(input[index]);
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				for (int k = 0; k < PentominoDatabase.data[id].length; k++) {
					int[][] piece = PentominoDatabase.data[id][k];

					if (canPlace(piece, i, j, field)) {
						addPiece(field, piece, id, i, j);
						ui.setState(field);
//						try {
//							Thread.sleep(10);
//						} catch (Exception ex) {
//							System.out.println(ex);
//						}


						if (basicSearch(field, index + 1)) {
							return true;
						}

						removePiece(field, piece, i, j);
					}
				}
			}
		}


		return false;
	}

	public static boolean canPlace(int[][] piece, int row, int col, int[][] field) {
		for (int i = 0; i < piece.length; i++) {
			for (int j = 0; j < piece[0].length; j++) {
				if (piece[i][j] != 0) {
					int newRow = row + i;
					int newCol = col + j;
					if (newRow < 0 || newRow >= field.length || newCol < 0 || newCol >= field[0].length || field[newRow][newCol] != -1) {
						return false;
					}
				}
			}
		}
		return true;

//		if (row + piece.length > field.length) {
//			return false;
//		}
//		for (int i = 0; i < piece.length; i++) {
//			if (col + piece[i].length > field[0].length)
//				return false;
//		}
//
//		for (int i = 0; i < piece.length; i++) {
//			for (int j = 0; j < piece[i].length; j++) {
//				if (piece[i][j] >= 1 && field[row + i][col + j] != -1) {
//					return false;
//				}
//			}
//		}
//
//		return true;
	}

	public static void removePiece(int[][] field, int[][] piece, int row, int col) {
		for (int i = 0; i < piece.length; i++) {
			for (int j = 0; j < piece[0].length; j++) {
				if (piece[i][j] != 0) {
					field[row + i][col + j] = -1;
				}
			}
		}
	}


	/**
	 * Adds a pentomino to the position on the field (overriding current board at that position)
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 * @param piece a matrix representing the pentomino to be placed in the board
	 * @param pieceID ID of the relevant pentomino
	 * @param x x position of the pentomino
	 * @param y y position of the pentomino
	 */
	public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y)
	{
		for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
		{
			for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
			{
				if (piece[i][j] == 1)
				{
					// Add the ID of the pentomino to the board if the pentomino occupies this square
					field[x + i][y + j] = pieceID;
				}
			}
		}
	}

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
	public static void main(String[] args)
	{
		PentominoBuilder.makeDatabase();
		search();
		System.out.print("Number of iterations: " + count);
	}
}