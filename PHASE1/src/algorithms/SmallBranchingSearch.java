package algorithms;

import model.PentominoDatabase;
import model.Search;

/**
 * Implements a reduced branching search algorithm to solve the Pentomino puzzle.
 * <p>
 * This strategy is optimized to reduce the number of possibilities it explores
 * by checking for potential issues ahead of time, before placing a piece.
 * </p>
 */
public class SmallBranchingSearch {
	public int width;
	public int height;
	public int unfilledCellsCount; // if multithreading or parallel processing, ensure that access to the unfilledCellsCount variable is thread-safe.

	public static int count = 0;

	/**
	 * Initializes a new search with a given board width and height.
	 *
	 * @param width  The width of the field.
	 * @param height The height of the field.
	 */
	public SmallBranchingSearch(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.unfilledCellsCount = width * height;
	}

	/**
	 * Attempts to solve the puzzle using a recursive reduced branching approach.
	 *
	 * @param field        The 2D grid representing the current state of the board.
	 * @param index        The index of the current Pentomino piece being placed.
	 * @param sortedPieces The ordered list of pieces to be placed.
	 * @return true if a solution is found, false otherwise.
	 */
	public boolean basicSearch(final int[][] field, final int index, final char[] sortedPieces) {
		count++;

		if (index == Search.INPUT.length)
			return true;

		if (!canFillRemaining(Search.INPUT.length - index))
			return false;

		final int id = Search.characterToID(sortedPieces[index]);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				for (int k = 0; k < PentominoDatabase.data[id].length; k++) {
					final int[][] piece = PentominoDatabase.data[id][k];

					if (pieceHasTrappedBlankSpaceWithBounds(piece, x, y))
						continue;

					if (canPlace(piece, x, y, field, width, height)) {
						addPiece(field, piece, id, x, y);
						Search.updateUIWithField(field);

						if (basicSearch(field, index + 1, sortedPieces))
							return true;

						removePiece(field, piece, x, y);
					}
				}
			}
		}

		return false;
	}

	/**
	 * Checks if a pentomino piece can be placed on the field.
	 *
	 * @param piece  The pentomino piece.
	 * @param col    The column to start placing the piece from.
	 * @param row    The row to start placing the piece from.
	 * @param field  The 2D grid representing the current state of the board.
	 * @param width  The width of the field.
	 * @param height The height of the field.
	 * @return true if the piece can be placed, false otherwise.
	 */
	public boolean canPlace(final int[][] piece, final int col, final int row, final int[][] field, final int width, final int height) {
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
	 * Determines if the remaining pieces can fill the board.
	 *
	 * @param remainingPieces Number of pieces remaining.
	 * @return true if the remaining pieces can fill the board, false otherwise.
	 */
	private boolean canFillRemaining(final int remainingPieces) {
		final int maxCellsThatCanBeFilled = remainingPieces * 5;  // each piece has 5 cells

		return maxCellsThatCanBeFilled >= unfilledCellsCount;
	}

	/**
	 * Checks if placing a given pentomino piece results in a trapped blank space.
	 *
	 * @param piece Pentomino piece.
	 * @param row   Row to start placing the piece from.
	 * @param col   Column to start placing the piece from.
	 * @return true if the piece creates a trapped blank space, false otherwise.
	 */
	private boolean pieceHasTrappedBlankSpaceWithBounds(final int[][] piece, final int row, final int col) {
		final int ySize = piece.length;
		final int xSize = piece[0].length;

		if (row == 0) {
			// Top left corner.
			if (col == 0 && piece[0][0] == 0)
				return true;

			// Top right corner
			if (col == width - xSize && piece[0][xSize - 1] == 0)
				return true;
		}

		if (row == height - ySize) {
			// Bottom left corner
			if (col == 0 && piece[ySize - 1][0] == 0)
				return true;

			// Bottom right corner
			return col == width - xSize && piece[ySize - 1][xSize - 1] == 0;
		}

		return false;
	}

	/**
	 * Checks for trapped blank spaces within the board after placing a piece.
	 *
	 * @param field Current state of the board.
	 * @param piece Pentomino piece.
	 * @param row   Row to start placing the piece from.
	 * @param col   Column to start placing the piece from.
	 * @param x     Horizontal position relative to the piece.
	 * @param y     Vertical position relative to the piece.
	 * @return true if there's a trapped blank space, false otherwise.
	 */
	private boolean pieceHasTrappedBlankSpace(final int[][] field, final int[][] piece, final int row, final int col, final int x, final int y) {
		final int ySize = piece.length;
		final int xSize = piece[0].length;

		if (piece[y][x] != 0)
			return false;

		final int fieldX = col + x;
		final int fieldY = row + y;

		final boolean isBlockLeft = fieldX - 1 < 0 || field[fieldX - 1][fieldY] != -1 || (x - 1 >= 0 && piece[y][x - 1] != 0);
		final boolean isBlockRight = fieldX + 1 >= width || field[fieldX + 1][fieldY] != -1 || (x + 1 < xSize && piece[y][x + 1] != 0);
		final boolean isBlockAbove = fieldY - 1 < 0 || field[fieldX][fieldY - 1] != -1 || (y - 1 >= 0 && piece[y - 1][x] != 0);
		final boolean isBlockBelow = fieldY + 1 >= height || field[fieldX][fieldY + 1] != -1 || (y + 1 < ySize && piece[y + 1][x] != 0);

		return isBlockLeft && isBlockRight && isBlockAbove && isBlockBelow;
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
	public void addPiece(final int[][] field, final int[][] piece, final int pieceID, final int col, final int row) {
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
	 * Removes a pentomino piece from the board.
	 *
	 * @param field Current state of the board.
	 * @param piece Pentomino piece to be removed.
	 * @param col   Column where the top-left cell of the piece was placed.
	 * @param row   Row where the top-left cell of the piece was placed.
	 */
	public void removePiece(final int[][] field, final int[][] piece, final int col, final int row) {
		for (int y = 0; y < piece.length; y++) {
			for (int x = 0; x < piece[0].length; x++) {
				if (piece[y][x] != 0) {
					field[col + x][row + y] = -1;
				}
			}
		}

		unfilledCellsCount += 5;
	}
}
