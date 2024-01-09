package com.bcs2024.knapsack.algorithm;

import model.PentominoDatabase;

import java.io.FileWriter;
import java.util.Arrays;

/**
 * The {@code com.bcs2024.knapsack.algorithm.Bot} class represents an automated player (bot) for the Pentris game.
 * It uses a heuristic approach to determine the best position and rotation for placing a pentomino piece.
 */
public class Bot {

    private Game game;
    public double[] weights;
    public static String results;

    /**
     * Constructs a new com.bcs2024.knapsack.algorithm.Bot instance associated with a specific game.
     *
     * @param game The game instance this bot will operate on.
     */
    public Bot(final Game game) {
        this.game = game;
        this.weights = new double[]{0.4, 1, 0.1, 0.3, 1, 0.2};
        this.results = "";
    }

    /**
     * Determines the best position and rotation for the current pentomino piece.
     *
     * @param wh An array of heuristic weights.
     * @return An array containing the best column position and rotation index.
     */
    private int[] botPosRot(final double[] wh) {
        final int[] temp = getBestPos(wh);
        return new int[]{temp[0], temp[1]};
    }

    /**
     * Checks if all elements in a given array are zeros.
     *
     * @param matrix The array to check.
     * @return {@code true} if all elements are zero; {@code false} otherwise.
     */
    private boolean allOs(final double[] matrix) {
        for (final double v : matrix) {
            if (v != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the best position for placing the current pentomino piece based on the given weights.
     *
     * @param actualW The weights to use for evaluating positions.
     * @return An array containing the best column position and rotation index.
     */
    private int[] getBestPos(final double[] actualW) {
        if (actualW != null && !allOs(actualW)) {
            this.weights = actualW;
        }
        final int[] res;
        final int[][] copy = new int[game.VERTICAL_GRID_SIZE][game.HORIZONTAL_GRID_SIZE];

        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[0].length; j++) {
                copy[i][j] = -1;
            }
        }

        for (int i = 0; i < game.VERTICAL_GRID_SIZE; i++) {
            copy[i] = Arrays.copyOf(game.field[i], game.field[i].length);
        }
        game.removePiece(copy, game.activeShape, game.actualX, game.actualY);

        int rota = 0, col = 0;
        double minWeight = 100000.0;

        for (int rot = 0; rot < PentominoDatabase.data[game.actualID].length; rot++) {
            final int[][] shape = PentominoDatabase.data[game.actualID][rot];
            for (int y = 0; y < game.HORIZONTAL_GRID_SIZE - shape[0].length + 1; y++) {
                final int x = getX(copy, y, shape);

                if (game.canPlace(copy, shape, x, y)) {
                    game.addPiece(copy, shape, game.actualID, x, y);

                    final int count = countGaps(Math.min(game.VERTICAL_GRID_SIZE - game.maxHeight + 1, x), copy);
                    final int height = game.VERTICAL_GRID_SIZE - x + 1;
                    final int remainingRows = unclearedRows(copy);
                    final int heyDiff = heightDifference(copy);
                    final int untouched = untouchedBlocks(copy, shape, x, y);
                    final double bump = bumpiness(copy);
                    double weight = 0;
                    if (Game.playMode == Game.PlayMode.TrainedBot) {
                        weight = weights[0] * height + weights[1] * count + weights[2] * remainingRows + weights[3] * heyDiff + weights[4] * untouched + weights[5] * bump;
                    } else {
                        weight = height + count + remainingRows + heyDiff + untouched + bump;
                    }
                    if (weight < minWeight) {
                        minWeight = weight;
                        rota = rot;
                        col = y;
                    }
                    game.removePiece(copy, shape, x, y);
                }
            }
        }

        res = new int[]{col, rota};

        return res;
    }

    /**
     * Counts the number of gaps (empty spaces that are not reachable) in the game field.
     *
     * @param height The height from which to start counting gaps.
     * @param matrix The game field matrix.
     * @return The number of gaps in the field.
     */
    private int countGaps(final int height, final int[][] matrix) {
        int count = 0;

        final int[][] cop = new int[game.VERTICAL_GRID_SIZE - height + 1][game.HORIZONTAL_GRID_SIZE];

        if (height - 1 >= 0) {
            for (int i = height - 1; i < matrix.length; i++) {
                cop[i - height + 1] = Arrays.copyOf(matrix[i], matrix[i].length);
            }
        } else {
            for (int i = height; i < matrix.length; i++) {
                cop[i - height] = Arrays.copyOf(matrix[i], matrix[i].length);
            }
        }

        for (int i = 0; i < cop.length; i++) {
            for (int j = 0; j < cop[0].length; j++) {
                if (cop[i][j] == -1) {
                    count += trappedEmptyBlocks(cop, i, j);
                    for (int m = 0; m < cop.length; m++) {
                        for (int n = 0; n < cop[0].length; n++) {
                            if (cop[m][n] == 20) matrix[m][n] = -1;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * Counts the number of trapped empty blocks in a given field starting from a specific position.
     *
     * @param field The game field matrix.
     * @param x     The x-coordinate to start from.
     * @param y     The y-coordinate to start from.
     * @return The number of trapped empty blocks.
     */
    private static int trappedEmptyBlocks(final int[][] field, final int x, final int y) {
        if (x < 0 || x >= field.length || y < 0 || y >= field[0].length || field[x][y] != -1) {
            return 0;
        }

        field[x][y] = 20;

        int trapped = 1;

        trapped += trappedEmptyBlocks(field, x + 1, y);
        trapped += trappedEmptyBlocks(field, x - 1, y);
        trapped += trappedEmptyBlocks(field, x, y + 1);
        trapped += trappedEmptyBlocks(field, x, y - 1);

        if (x < 1) return 0;

        return trapped;
    }

    /**
     * Counts the number of rows in the field that are not completely filled.
     *
     * @param matrix The game field matrix.
     * @return The number of uncleared rows.
     */
    private int unclearedRows(final int[][] matrix) {
        int count = 0;

        for (final int[] ints : matrix) {
            boolean full = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (ints[j] == -1) {
                    full = false;
                    break;
                }
            }

            if (full) {
                count++;
            }
        }

        return matrix.length - count;
    }

    /**
     * Calculates the height difference between the tallest and shortest columns in the field.
     *
     * @param matrix The game field matrix.
     * @return The height difference.
     */
    private int heightDifference(final int[][] matrix) {
        int maxX = 0;
        int minX = matrix.length;

        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][j] != -1) {
                    if (i > maxX) {
                        maxX = i;
                    }
                    if (i < minX) {
                        minX = i;
                    }
                    break;
                }
            }
        }

        final int diff = matrix.length - minX - (matrix.length - maxX) + 1;

        return diff;
    }

    /**
     * Counts the number of blocks in the field that are adjacent to the current shape and are untouched (empty).
     *
     * @param matrix The game field matrix.
     * @param shape  The current pentomino shape.
     * @param x      The x-coordinate of the shape's position.
     * @param y      The y-coordinate of the shape's position.
     * @return The number of untouched adjacent blocks.
     */
    private int untouchedBlocks(final int[][] matrix, final int[][] shape, final int x, final int y) {
        int count = 0;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 0) {
                    if (x + i + 1 < matrix.length && matrix[x + i + 1][y + j] == -1) {
                        count++;
                    }
                    if (y + j - 1 > 0 && matrix[x + i][y + j - 1] == -1) {
                        count++;
                    }
                    if (y + j + 1 < matrix[0].length && matrix[x + i][y + j + 1] == -1) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     * Calculates the bumpiness of the field, which is the sum of the absolute differences in height between adjacent columns.
     *
     * @param matrix The game field matrix.
     * @return The bumpiness value.
     */
    private double bumpiness(final int[][] matrix) {
        int bump = 0;
        final int[] heights = new int[matrix[0].length];

        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][j] != -1) {
                    heights[j] = i;
                }
            }
        }

        for (int i = 0; i < heights.length - 1; i++) {
            bump += Math.abs(heights[i] - heights[i + 1]);
        }

        return bump;
    }

    /**
     * Determines the x-coordinate to place the current pentomino shape based on the game field and the shape's y-coordinate.
     *
     * @param matrix The game field matrix.
     * @param y      The y-coordinate of the shape's position.
     * @param shape  The current pentomino shape.
     * @return The x-coordinate for placing the shape.
     */
    private int getX(final int[][] matrix, final int y, final int[][] shape) {
        int x = 0;
        while (game.canPlace(matrix, shape, x, y)) {
            if (x + 1 > game.VERTICAL_GRID_SIZE - shape.length) {
                x++;
                break;
            }
            x++;
        }
        return x - 1;
    }

    /**
     * Writes the results of the bot's gameplay to a file.
     * The results include the bot's performance metrics and decisions made during the game.
     */
    public void writeResults() {
        try {
            final FileWriter writeResults = new FileWriter("src/main/java/metrics/GBotResults");
            writeResults.write(results);
            writeResults.close();
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Calculates the best position and rotation for the current pentomino piece.
     *
     * @param wh An array of weights used by the bot to evaluate positions.
     * @return An array where the first element is the column position and the second element is the rotation index.
     */
    public int[] calculate(final double[] wh) {
        return botPosRot(wh);
    }
}
