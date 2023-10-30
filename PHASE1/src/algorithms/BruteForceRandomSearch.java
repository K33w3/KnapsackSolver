package algorithms;

import model.PentominoDatabase;
import model.Search;

import java.util.Arrays;
import java.util.Random;

/**
 * Implements a brute-force random search algorithm to solve the Pentomino puzzle.
 * This algorithm attempts to randomly place pentomino pieces on the board until a valid configuration
 * (solution) is found.
 * <p>
 * The strategy:
 * 1. Randomly choose a piece and a mutation (rotation/flipping).
 * 2. Randomly choose a position on the board to place it.
 * 3. Check if the piece can be placed at that position without overlap or going out of the board's bounds.
 * 4. If possible, place the piece on the board.
 * 5. Repeat steps 1-4 until all pieces are placed or the board is full.
 * 6. Check if the board is completely filled. If yes, a solution is found.
 * 7. If not, clear the board and start over.
 * <p>
 * This is a non-deterministic approach; therefore, the time to find a solution can vary.
 */
public class BruteForceRandomSearch {

	public static Search search = new Search();

	/**
	 * Initiates the brute force random search to find a valid solution for the Pentomino puzzle.
	 * <p>
	 * The algorithm will continue indefinitely until a solution is found.
	 * Whenever a piece is placed on the board, the UI is updated to display the current state of the board.
	 *
	 * @param field The 2D grid representing the current state of the field, where -1 indicates an empty space
	 *              and any other number indicates a placed pentomino piece.
	 */
	public static void bruteForceRandom(final int[][] field) {
		final Random random = new Random();
		boolean solutionFound = false;

		while (true) {
			//Empty board again to find a solution
			for (final int[] ints : field)
				Arrays.fill(ints, -1);

			//Put all pentominoes with random rotation/flipping on a random position on the board
			for (int i = 0; i < Search.INPUT.length; i++) {

				//Choose a pentomino and randomly rotate/flip it
				final int pentID = Search.characterToID(Search.INPUT[i]);
				final int mutation = random.nextInt(PentominoDatabase.data[pentID].length);
				final int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];

				//Randomly generate a position to put the pentomino on the board
				final int x;
				final int y;

				if (Search.HORIZONTAL_GRID_SIZE < pieceToPlace.length) {
					//this particular rotation of the piece is too long for the field
					x = -1;
				} else if (Search.HORIZONTAL_GRID_SIZE == pieceToPlace.length) {
					//this particular rotation of the piece fits perfectly into the width of the field
					x = 0;
				} else {
					//there are multiple possibilities where to place the piece without leaving the field
					x = random.nextInt(Search.HORIZONTAL_GRID_SIZE - pieceToPlace.length + 1);
				}

				if (Search.VERTICAL_GRID_SIZE < pieceToPlace[0].length) {
					//this particular rotation of the piece is too high for the field
					y = -1;
				} else if (Search.VERTICAL_GRID_SIZE == pieceToPlace[0].length) {
					//this particular rotation of the piece fits perfectly into the height of the field
					y = 0;
				} else {
					//there are multiple possibilities where to place the piece without leaving the field
					y = random.nextInt(Search.VERTICAL_GRID_SIZE - pieceToPlace[0].length + 1);
				}

				//If there is a possibility to place the piece on the field, do it
				if (x >= 0 && y >= 0) {
					if (Search.canPlace(pieceToPlace, x, y, field)) {
						Search.addPiece(field, pieceToPlace, pentID, x, y);
						try {
							Thread.sleep(1);
						} catch (final Exception ex) {
							System.out.println(ex);
						}
						Search.ui.setState(field);
					}
				}

			}

			if (Search.filledMatrix(field))
				solutionFound = true;

			if (solutionFound) {
				Search.ui.setState(field);
				System.out.println("Solution found");
				break;
			}
		}
	}
}
