package model;

import algorithms.BranchingSearch;
import algorithms.BruteForceRandomSearch;
import algorithms.BruteForceSearch;
import algorithms.SmallBranchingSearch;

import java.util.*;

/**
 * The Search class provides functionality to find solutions to fit a set of pentomino pieces
 * within a specified grid using different algorithms.
 */
public class Search {
	public static final int HORIZONTAL_GRID_SIZE = 5;
	public static final int VERTICAL_GRID_SIZE = 12;

	public static Scanner scanner = new Scanner(System.in);

	// public static final char[] INPUT = {'X', 'Z', 'P', 'N', 'L', 'I', 'U', 'V'}; // 8 shapes - best for 8xn
	public static final char[] INPUT = {'X', 'Z', 'T', 'U', 'W', 'Y', 'L', 'I', 'P', 'N', 'F', 'V'}; // 12 shapes- best for 12xn
	//public static final char[] INPUT = {'X', 'Z', 'T', 'U', 'W', 'Y', 'L', 'I', 'P', 'N'}; // 10 shapes- best for 10xn

	public static boolean isTransposed = false;

	public static int fieldHeight = HORIZONTAL_GRID_SIZE;
	public static int fieldWidth = VERTICAL_GRID_SIZE;

	public static UI ui;

	public static int count = 0;

	/**
	 * The main method that prompts the user for a search algorithm and initiates the search.
	 *
	 * @param args Command-line arguments (not used).
	 */
	public static void main(final String[] args) {
		System.out.println("Choose the method you want to use: ");
		System.out.println();
		System.out.println("0. Searching using the best algorithm based on the grid size");
		System.out.println("1. Searching using deterministic brute force");
		System.out.println("2. Searching using random brute force");
		System.out.println("3. Searching using branching with flooding algorithm");
		System.out.println("4. Searching using branching for grid's with size 8x5");

		final int method = scanner.nextInt();

		PentominoBuilder.makeDatabase();
		search(method);
		System.out.print("Number of iterations: " + count);
	}

	/**
	 * Executes the selected search method and updates the UI with the solution (if found).
	 *
	 * @param method The selected search method.
	 */
	public static void search(final int method) {
		if (HORIZONTAL_GRID_SIZE > VERTICAL_GRID_SIZE) {
			fieldWidth = VERTICAL_GRID_SIZE;
			fieldHeight = HORIZONTAL_GRID_SIZE;
			isTransposed = true;
		} else {
			fieldWidth = HORIZONTAL_GRID_SIZE;
			fieldHeight = VERTICAL_GRID_SIZE;
		}

		ui = new UI(HORIZONTAL_GRID_SIZE, VERTICAL_GRID_SIZE, 50);

		final Date start = new Date();
		final int[][] field = new int[fieldWidth][fieldHeight];
		final char[] pieces = getSortedPieces();
		initializeField(field, fieldWidth, fieldHeight);

		if (method == 0) {
			if (INPUT.length == 8) {
				if (new SmallBranchingSearch(fieldWidth, fieldHeight).basicSearch(field, 0, pieces))
					System.out.println("Solution Found");
				else
					System.out.println("Has No Solution");
			} else {
				if (BranchingSearch.basicSearch(field, 0, pieces))
					System.out.println("Solution Found");
				else
					System.out.println("Has No Solution");
			}
		} else if (method == 1) {
			if (BruteForceSearch.basicSearch(field, fieldWidth, fieldHeight, 0))
				System.out.println("Solution Found");
			else
				System.out.println("Has No Solution");
		} else if (method == 2) {
			BruteForceRandomSearch.bruteForceRandom(field);
		} else if (method == 3) {
			if (BranchingSearch.basicSearch(field, 0, pieces))
				System.out.println("Solution Found");
			else
				System.out.println("Has No Solution");
		} else if (method == 4) {
			if (INPUT.length == 8) {
				if (new SmallBranchingSearch(fieldWidth, fieldHeight).basicSearch(field, 0, pieces))
					System.out.println("Solution Found");
				else
					System.out.println("Has No Solution");
			} else System.out.println("This algorithm cannot be performed for an input length different than 8");
		}

		Search.updateUIWithField(field);

		final Date end = new Date();

		final long time = end.getTime() - start.getTime();
		System.out.printf("Found result in %s ms%n", time);
	}

	/**
	 * Initializes the field with default values.
	 *
	 * @param field  The 2D field array to be initialized.
	 * @param width  Width of the field.
	 * @param height Height of the field.
	 */
	public static void initializeField(final int[][] field, final int width, final int height) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				field[x][y] = -1;
			}
		}
	}

	/**
	 * Converts a character representing a pentomino piece to its corresponding ID.
	 *
	 * @param character The character representation of the pentomino.
	 * @return The ID of the pentomino.
	 */
	public static int characterToID(final char character) {
		return switch (character) {
			case 'X' -> 0;
			case 'I' -> 1;
			case 'Z' -> 2;
			case 'T' -> 3;
			case 'U' -> 4;
			case 'V' -> 5;
			case 'W' -> 6;
			case 'Y' -> 7;
			case 'L' -> 8;
			case 'P' -> 9;
			case 'N' -> 10;
			case 'F' -> 11;
			default -> -1;
		};
	}

	/**
	 * Checks if the entire field matrix is filled.
	 *
	 * @param field The 2D field array.
	 * @return True if the matrix is filled, false otherwise.
	 */
	public static boolean filledMatrix(final int[][] field) {
		for (int j = 0; j < field[0].length; j++)
			for (final int[] ints : field)
				if (ints[j] == -1)
					return false;

		return true;
	}

	/**
	 * Checks if a given piece can be placed at a specific position in the field.
	 *
	 * @param piece The 2D array representing the piece.
	 * @param col   The column to start placing the piece.
	 * @param row   The row to start placing the piece.
	 * @param field The 2D field array.
	 * @return True if the piece can be placed, false otherwise.
	 */
	public static boolean canPlace(final int[][] piece, final int col, final int row, final int[][] field) {
		for (int y = 0; y < piece.length; y++) {
			for (int x = 0; x < piece[0].length; x++) {
				final int newRow = row + y;
				final int newCol = col + x;

				if (newRow < 0 || newRow >= VERTICAL_GRID_SIZE || newCol < 0 || newCol >= HORIZONTAL_GRID_SIZE || field[newCol][newRow] != -1)
					// This part of piece is outside bounds.
					if (piece[y][x] != 0)
						return false;
			}
		}
		return true;
	}

	/**
	 * Checks if there are any trapped blocks that can't form a complete pentomino piece.
	 *
	 * @param field The 2D field array.
	 * @return True if there are no trapped blocks, false otherwise.
	 */
	public static boolean consecutiveTrappedBlocks(final int[][] field) {
		final int[][] visited = new int[field.length][field[0].length];
		final int trappedCount = 1;
		int temp;

		for (int i = 0; i < visited.length; i++)
			visited[i] = Arrays.copyOf(field[i], field[i].length);

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				temp = dfs(visited, i, j, trappedCount);
				if (temp % 5 != 0) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Performs a depth-first search to count trapped blocks within the field.
	 *
	 * @param visited      A 2D array to keep track of visited cells.
	 * @param row          The current row position in the DFS traversal.
	 * @param col          The current column position in the DFS traversal.
	 * @param trappedCount The current count of trapped blocks.
	 * @return The total count of consecutive trapped blocks in the connected component.
	 */
	private static int dfs(final int[][] visited, final int row, final int col, int trappedCount) {
		if (row < 0 || row >= visited.length || col < 0 || col >= visited[0].length || visited[row][col] != -1) {
			return 0;
		}

		visited[row][col] = 0;

		trappedCount = 1;
		trappedCount += dfs(visited, row - 1, col, trappedCount);
		trappedCount += dfs(visited, row + 1, col, trappedCount);
		trappedCount += dfs(visited, row, col - 1, trappedCount);
		trappedCount += dfs(visited, row, col + 1, trappedCount);

		return trappedCount;
	}

	/**
	 * Removes a given pentomino piece from the specified position in the field.
	 *
	 * @param field The 2D field array.
	 * @param piece The 2D array representing the piece.
	 * @param col   The column from where the piece starts.
	 * @param row   The row from where the piece starts.
	 */
	public static void removePiece(final int[][] field, final int[][] piece, final int col, final int row) {
		for (int y = 0; y < piece.length; y++) {
			for (int x = 0; x < piece[0].length; x++) {
				if (piece[y][x] != 0) {
					field[col + x][row + y] = -1;
				}
			}
		}
	}

	/**
	 * Adds a given pentomino piece to the specified position in the field.
	 *
	 * @param field   The 2D field array.
	 * @param piece   The 2D array representing the piece.
	 * @param pieceID The ID of the pentomino piece.
	 * @param col     The column to start placing the piece.
	 * @param row     The row to start placing the piece.
	 */
	public static void addPiece(final int[][] field, final int[][] piece, final int pieceID, final int col, final int row) {
		for (int y = 0; y < piece.length; y++) {
			for (int x = 0; x < piece[0].length; x++) {
				if (piece[y][x] == 1) {
					field[col + x][row + y] = pieceID;
				}
			}
		}
	}

	/**
	 * Retrieves the complexity score of a given pentomino piece based on the current field dimensions.
	 *
	 * @param piece The character representation of the pentomino.
	 * @return The complexity score of the piece.
	 */
	private static int getPieceComplexity(final char piece) {
		final int height = isTransposed ? HORIZONTAL_GRID_SIZE : VERTICAL_GRID_SIZE;
		final int width = isTransposed ? VERTICAL_GRID_SIZE : HORIZONTAL_GRID_SIZE;

		if (width == 5 && height == 8)
			return switch (piece) {
				case 'X' -> 1;
				case 'Z' -> 2;
				case 'P' -> 3;
				case 'N' -> 4;
				case 'L' -> 5;
				case 'I' -> 6;
				case 'U' -> 7;
				case 'V' -> 8;
				default -> 1;
			};
		else if (width == 4 && height == 15) {
			return switch (piece) {
				case 'I' -> 1;
				case 'U' -> 2;
				case 'X' -> 3;
				case 'F' -> 4;
				case 'N' -> 5;
				case 'Z' -> 6;
				case 'L' -> 7;
				case 'Y' -> 8;
				case 'W' -> 9;
				case 'T' -> 10;
				case 'V' -> 11;
				case 'P' -> 12;
				default -> 1;
			};
		} else if (width == 3 && height == 20) {
			return switch (piece) {
				case 'V' -> 1;
				case 'L' -> 2;
				case 'N' -> 3;
				case 'F' -> 4;
				case 'T' -> 5;
				case 'W' -> 6;
				case 'Y' -> 7;
				case 'Z' -> 8;
				case 'I' -> 9;
				case 'P' -> 10;
				case 'X' -> 11;
				case 'U' -> 12;
				default -> 1;
			};
		} else {
			return switch (piece) {
				case 'I' -> 1;
				case 'X' -> 2;
				case 'Z' -> 3;
				case 'T' -> 4;
				case 'L' -> 5;
				case 'P' -> 6;
				case 'N' -> 7;
				case 'V' -> 8;
				case 'U' -> 9;
				case 'W' -> 10;
				case 'Y' -> 11;
				case 'F' -> 12;
				default -> 1;
			};
		}
	}

	/**
	 * Sorts the pentomino pieces based on their complexity scores.
	 *
	 * @return A sorted array of pentomino characters.
	 */
	private static char[] getSortedPieces() {
		final Character[] charObjectArray = new Character[INPUT.length];
		for (int i = 0; i < INPUT.length; i++) {
			charObjectArray[i] = INPUT[i];
		}

		final List<Character> sortedList = Arrays.stream(charObjectArray)
				.sorted(Comparator.comparingInt(Search::getPieceComplexity)).toList();

		for (int i = 0; i < sortedList.size(); i++) {
			charObjectArray[i] = sortedList.get(i);
		}

		final char[] sortedCharArray = new char[charObjectArray.length];
		for (int i = 0; i < charObjectArray.length; i++) {
			sortedCharArray[i] = charObjectArray[i];
		}

		return sortedCharArray;
	}

	/**
	 * Updates the UI state with the provided field.
	 *
	 * @param field The 2D field array.
	 */
	public static void updateUIWithField(final int[][] field) {
		ui.setState(Search.isTransposed ? transposeField(field, VERTICAL_GRID_SIZE, HORIZONTAL_GRID_SIZE) : field);
	}

	/**
	 * Transposes the given field matrix.
	 *
	 * @param field  The 2D field array to be transposed.
	 * @param width  Width of the field.
	 * @param height Height of the field.
	 * @return The transposed 2D field array.
	 */
	private static int[][] transposeField(final int[][] field, final int width, final int height) {
		final int[][] result = new int[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				result[y][x] = field[x][y];
			}
		}

		return result;
	}
}
