package algorithms;

import model.PentominoDatabase;
import model.Search;

/**
 * Contains an advanced algorithms for searching pentomino configurations on a grid.
 */
public class BranchingSearch {

	/**
	 * Executes a recursive search algorithm to find a suitable configuration for placing pentominoes on the given grid.
	 * It tries to fit each pentomino on the grid and recursively checks the next pentomino until all are placed or no configuration is found.
	 *
	 * @param field The 2D grid representing the current state of the field, where -1 indicates an empty space and any other number indicates a placed pentomino piece.
	 * @param index The current index in the input character array, which guides which pentomino is currently being attempted to place on the grid.
	 * @param input An array of characters, where each character represents a unique type of pentomino piece to be placed on the grid.
	 * @return true if a solution is found where all pentominoes from the input are placed on the grid without overlap, false otherwise.
	 */
	public static boolean basicSearch(final int[][] field, final int index, final char[] input) {
		Search.count++;

		if (index == Search.INPUT.length)
			return true;

		final int id = Search.characterToID(input[index]);
		for (int y = 0; y < Search.fieldHeight; y++) {
			for (int x = 0; x < Search.fieldWidth; x++) {
				for (int k = 0; k < PentominoDatabase.data[id].length; k++) {
					final int[][] piece = PentominoDatabase.data[id][k];

					if (!Search.consecutiveTrappedBlocks(field)) {
						continue;
					}

					if (canPlace(piece, x, y, field)) {
						Search.addPiece(field, piece, id, x, y);
						if (doesFieldHaveTraps(field)) {
							Search.removePiece(field, piece, x, y);
							continue;
						}

						if (basicSearch(field, index + 1, input))
							return true;

						Search.removePiece(field, piece, x, y);
					}
				}
			}
		}

		return false;
	}

	/**
	 * Determines if a given piece has a blank space that's surrounded by other pieces/blocks.
	 *
	 * @param field The current state of the field.
	 * @param piece The pentomino piece.
	 * @param row   Starting row position.
	 * @param col   Starting column position.
	 * @param x     X-coordinate of the space being checked in the piece.
	 * @param y     Y-coordinate of the space being checked in the piece.
	 * @return true if there's a trapped blank space, false otherwise.
	 */
	private static boolean pieceHasTrappedBlankSpace(final int[][] field, final int[][] piece, final int row, final int col, final int x, final int y) {
		final int ySize = piece.length;
		final int xSize = piece[0].length;
		final int height = Search.fieldHeight;
		final int width = Search.fieldWidth;

		if (piece[y][x] != 0) {
			return false;
		}

		final int fieldX = col + x;
		final int fieldY = row + y;

		final boolean isBlockLeft = fieldX - 1 < 0 || field[fieldX - 1][fieldY] != -1 || (x - 1 >= 0 && piece[y][x - 1] != 0);
		final boolean isBlockRight = fieldX + 1 >= width || field[fieldX + 1][fieldY] != -1 || (x + 1 < xSize && piece[y][x + 1] != 0);
		final boolean isBlockAbove = fieldY - 1 < 0 || field[fieldX][fieldY - 1] != -1 || (y - 1 >= 0 && piece[y - 1][x] != 0);
		final boolean isBlockBelow = fieldY + 1 >= height || field[fieldX][fieldY + 1] != -1 || (y + 1 < ySize && piece[y + 1][x] != 0);

		return isBlockLeft && isBlockRight && isBlockAbove && isBlockBelow;
	}

	/**
	 * Checks if the current field/grid contains any trapped configurations which cannot be filled by any piece.
	 *
	 * @param field The current state of the field/grid.
	 * @return true if traps are found, false otherwise.
	 */
	private static boolean doesFieldHaveTraps(final int[][] field) {
		if (matchPatternInField(new int[][]{
				{1, 1, 1},
				{1, 0, 1},
				{1, 1, 1},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 1, 1, 2},
				{1, 0, 0, 1},
				{2, 1, 1, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 1, 2},
				{1, 0, 1},
				{1, 0, 1},
				{2, 1, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 1, 1, 1, 2},
				{1, 0, 0, 0, 1},
				{2, 1, 1, 1, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 1, 1, 1, 1, 2},
				{1, 0, 0, 0, 0, 1},
				{2, 1, 1, 1, 1, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 1, 2},
				{1, 0, 1},
				{1, 0, 1},
				{1, 0, 1},
				{2, 1, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 1, 2},
				{1, 0, 1},
				{1, 0, 1},
				{1, 0, 1},
				{2, 0, 2},
				{2, 1, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 1, 1, 2},
				{1, 0, 1, 2},
				{1, 0, 1, 2},
				{1, 0, 0, 1},
				{2, 1, 1, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 2, 2, 1, 2},
				{1, 1, 1, 0, 1},
				{1, 0, 0, 1, 2},
				{2, 1, 1, 2, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 2, 2, 1, 2},
				{1, 0, 1, 0, 1},
				{1, 0, 0, 1, 2},
				{2, 1, 1, 2, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 1, 2, 1, 2},
				{1, 0, 1, 0, 1},
				{1, 1, 0, 0, 1},
				{2, 2, 1, 1, 2},
		}, field, -1, -1)) {
			return true;
		}

		if (matchPatternInField(new int[][]{
				{2, 1, 1, 2},
				{1, 1, 0, 1},
				{1, 1, 0, 1},
				{1, 0, 0, 1},
				{2, 1, 1, 2},
		}, field, -1, -1)) {
			return true;
		}

		return matchPatternInField(new int[][]{
				{2, 1, 1, 2},
				{1, 2, 0, 1},
				{1, 0, 2, 1},
				{2, 1, 1, 2},
		}, field, -1, -1);
	}

	/**
	 * Checks if a pattern can be found within the field/grid.
	 *
	 * @param pattern A matrix representing the pattern to find.
	 * @param field   The current state of the field/grid.
	 * @param xOffset Optional X offset for starting the search.
	 * @param yOffset Optional Y offset for starting the search.
	 * @return true if the pattern matches, false otherwise.
	 */
	public static boolean matchPatternInField(final int[][] pattern, final int[][] field, final int xOffset, final int yOffset) {
		final int fieldWidth = Search.fieldWidth;
		final int fieldHeight = Search.fieldHeight;

		if (pattern.length > fieldWidth) {
			return false;
		}

		for (int y = yOffset; y < fieldHeight - pattern.length + 1 - yOffset; y++) {
			for (int x = xOffset; x < fieldWidth - pattern[0].length + 1 - xOffset; x++) {
				boolean hasMatch = true;
				patternRowLoop:
				for (int i = 0; i < pattern.length; i++) {
					for (int j = 0; j < pattern[0].length; j++) {
						final int matcher = pattern[i][j];
						final int fieldX = x + j;
						final int fieldY = y + i;

						if (matcher == 2) {
							continue;
						}

						final boolean hasBlockAtIndex = hasBlockAt(field, fieldX, fieldY);

						final boolean matchesPattern = matcher == 0 && !hasBlockAtIndex || matcher == 1 && hasBlockAtIndex;

						if (!matchesPattern) {
							hasMatch = false;
							break patternRowLoop;
						}
					}
				}
				if (hasMatch) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if a block (non-empty cell) exists at the specified position in the field.
	 *
	 * @param field  The current state of the field.
	 * @param fieldX X-coordinate in the field.
	 * @param fieldY Y-coordinate in the field.
	 * @return true if a block exists, false otherwise.
	 */
	public static boolean hasBlockAt(final int[][] field, final int fieldX, final int fieldY) {
		if (fieldX < 0 || fieldX >= Search.fieldWidth || fieldY < 0 || fieldY >= Search.fieldHeight) {
			return true;
		}

		return field[fieldX][fieldY] != -1;
	}

	/**
	 * Determines if a given pentomino piece can be placed at the specified position on the field/grid.
	 *
	 * @param piece The pentomino piece.
	 * @param col   Column to place the piece.
	 * @param row   Row to place the piece.
	 * @param field The current state of the field/grid.
	 * @return true if the piece can be placed, false otherwise.
	 */
	public static boolean canPlace(final int[][] piece, final int col, final int row, final int[][] field) {
		for (int y = 0; y < piece.length; y++) {
			for (int x = 0; x < piece[0].length; x++) {
				final int newRow = row + y;
				final int newCol = col + x;

				if (newRow < 0 || newRow >= Search.fieldHeight || newCol < 0 || newCol >= Search.fieldWidth || field[newCol][newRow] != -1) {
					// This part of piece is outside bounds.
					if (piece[y][x] != 0) {
						return false;
					} else {
						continue;
					}
				}

				if (pieceHasTrappedBlankSpace(field, piece, row, col, x, y)) {
					return false;
				}
			}
		}

		return true;
	}
}
