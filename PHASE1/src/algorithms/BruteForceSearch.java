package algorithms;

import model.PentominoDatabase;
import model.Search;

/**
 * Implements a brute-force search algorithm to solve the Pentomino puzzle.
 * <p>
 * The strategy:
 * 1. Try each Pentomino piece in each possible position on the board.
 * 2. For each piece, try every possible mutation (rotation/flipping).
 * 3. If a piece can be placed without overlap or going out of the board's bounds, place it on the board.
 * 4. Recursively try to place the next piece.
 * 5. If all pieces are placed successfully, a solution is found.
 * 6. If not, remove the last placed piece (backtrack) and try a new position/mutation.
 * <p>
 * This deterministic approach ensures every possibility is tried, but may be time-consuming.
 */
public class BruteForceSearch {

	public static int unfilledCellsCount = Search.fieldHeight * Search.fieldWidth;

	/**
	 * This method attempts to solve the puzzle using a recursive brute-force approach.
	 *
	 * @param field            The 2D grid representing the current state of the field,
	 *                         where -1 indicates an empty space and any other number indicates
	 *                         a placed pentomino piece.
	 * @param width            The width of the field.
	 * @param height           The height of the field.
	 * @param currentPentomino The index of the current Pentomino piece being placed.
	 * @return true if a solution is found, false otherwise.
	 */
	public static boolean basicSearch(final int[][] field, final int width, final int height, final int currentPentomino) {
		Search.count++;
		
		if (filledMatrix(field))
			return true;

		if (currentPentomino >= Search.INPUT.length)
			return false;

		final int remainingPieces = Search.INPUT.length - currentPentomino;

		if (!canFillRemaining(remainingPieces))
			return false;

		final int pentID = Search.characterToID(Search.INPUT[currentPentomino]);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int mutation = 0; mutation < PentominoDatabase.data[pentID].length; mutation++) {
					final int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];

					if (canPlace(pieceToPlace, x, y, field, width, height)) {
						addPiece(field, pieceToPlace, pentID, x, y);
						Search.updateUIWithField(field);

						// If recursively placing the next piece leads to a solution, return true.
						if (basicSearch(field, width, height, currentPentomino + 1)) {
							return true;
						}

						removePiece(field, pieceToPlace, x, y);  // Backtrack.
					}
				}
			}
		}

		return false;
	}

	/**
	 * Checks if the remaining pieces can fill the unfilled cells on the board.
	 *
	 * @param remainingPieces The number of pieces that are yet to be placed.
	 * @return true if it's possible to fill the remaining cells with the pieces, false otherwise.
	 */
	private static boolean canFillRemaining(final int remainingPieces) {
		final int maxCellsThatCanBeFilled = remainingPieces * 5;  // each piece has 5 cells
		return maxCellsThatCanBeFilled >= unfilledCellsCount;
	}

	/**
	 * Checks if all cells of the field are filled.
	 *
	 * @param field The 2D grid representing the current state of the board.
	 * @return true if all cells are filled, false otherwise.
	 */
	public static boolean filledMatrix(final int[][] field) {
		for (int j = 0; j < field[0].length; j++) {
			for (final int[] ints : field) {
				if (ints[j] == -1) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Adds a pentomino piece to the field.
	 *
	 * @param field   The 2D grid representing the current state of the board.
	 * @param piece   The pentomino piece to be placed.
	 * @param pieceID The ID of the pentomino piece.
	 * @param col     The column where the top-left cell of the piece will be placed.
	 * @param row     The row where the top-left cell of the piece will be placed.
	 */
	public static void addPiece(final int[][] field, final int[][] piece, final int pieceID, final int col, final int row) {
		for (int y = 0; y < piece.length; y++) {
			for (int x = 0; x < piece[0].length; x++) {
				if (piece[y][x] == 1) {
					field[col + x][row + y] = pieceID;
				}
			}
		}

		unfilledCellsCount -= 5;
	}

	/**
	 * Removes a pentomino piece from the field.
	 *
	 * @param field The 2D grid representing the current state of the board.
	 * @param piece The pentomino piece to be removed.
	 * @param col   The column where the top-left cell of the piece is placed.
	 * @param row   The row where the top-left cell of the piece is placed.
	 */
	public static void removePiece(final int[][] field, final int[][] piece, final int col, final int row) {
		for (int y = 0; y < piece.length; y++) {
			for (int x = 0; x < piece[0].length; x++) {
				if (piece[y][x] != 0) {
					field[col + x][row + y] = -1;
				}
			}
		}

		unfilledCellsCount += 5;
	}

	/**
	 * Checks if a pentomino piece can be placed on the field without overlap or going out of bounds.
	 *
	 * @param piece  The pentomino piece to be checked.
	 * @param col    The column where the top-left cell of the piece will be placed.
	 * @param row    The row where the top-left cell of the piece will be placed.
	 * @param field  The 2D grid representing the current state of the board.
	 * @param width  The width of the field.
	 * @param height The height of the field.
	 * @return true if the piece can be placed, false otherwise.
	 */
	public static boolean canPlace(final int[][] piece, final int col, final int row, final int[][] field, final int width, final int height) {
		//final long startTime = System.nanoTime();
		for (int y = 0; y < piece.length; y++) {
			for (int x = 0; x < piece[0].length; x++) {
				final int newRow = row + y;
				final int newCol = col + x;

				if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width || field[newCol][newRow] != -1) {
					// This part of piece is outside bounds.
					if (piece[y][x] != 0)
						return false;
					else
						continue;
				}

				if (pieceHasTrappedBlankSpace(field, piece, row, col, x, y))
					return false;
			}
		}
		/*final long endTime = System.nanoTime();  // end timing
		final long duration = (endTime - startTime);  // compute elapsed time in nanoseconds
		System.out.printf("CanPlace took %d nanoseconds (%.6f ms)%n", duration, duration / 1e6);*/
		return true;
	}

	/**
	 * Checks if placing the pentomino piece would create a trapped blank space on the board.
	 *
	 * @param field The 2D grid representing the current state of the board.
	 * @param piece The pentomino piece being checked.
	 * @param row   The row where the top-left cell of the piece will be placed.
	 * @param col   The column where the top-left cell of the piece will be placed.
	 * @param x     The x-coordinate of the cell being checked in the piece's matrix.
	 * @param y     The y-coordinate of the cell being checked in the piece's matrix.
	 * @return true if a trapped blank space is created, false otherwise.
	 */
	private static boolean pieceHasTrappedBlankSpace(final int[][] field, final int[][] piece, final int row, final int col, final int x, final int y) {
		final int ySize = piece.length;
		final int xSize = piece[0].length;

		if (piece[y][x] != 0)
			return false;

		final int fieldX = col + x;
		final int fieldY = row + y;

		final boolean isBlockLeft = fieldX - 1 < 0 || field[fieldX - 1][fieldY] != -1 || (x - 1 >= 0 && piece[y][x - 1] != 0);
		final boolean isBlockRight = fieldX + 1 >= Search.fieldWidth || field[fieldX + 1][fieldY] != -1 || (x + 1 < xSize && piece[y][x + 1] != 0);
		final boolean isBlockAbove = fieldY - 1 < 0 || field[fieldX][fieldY - 1] != -1 || (y - 1 >= 0 && piece[y - 1][x] != 0);
		final boolean isBlockBelow = fieldY + 1 >= Search.fieldHeight || field[fieldX][fieldY + 1] != -1 || (y + 1 < ySize && piece[y + 1][x] != 0);

		return isBlockLeft && isBlockRight && isBlockAbove && isBlockBelow;
	}
}

