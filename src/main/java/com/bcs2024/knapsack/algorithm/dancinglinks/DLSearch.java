package com.bcs2024.knapsack.algorithm.dancinglinks;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.ArrayList;
import java.util.List;

/**
 * DLSearch orchestrates the search process using the Dancing Links algorithm
 * for the knapsack problem. It manages the creation of all possible parcel positions
 * within the cargo space and initiates the algorithm to find the optimal arrangement.
 */
public class DLSearch {
    private final ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
    public static int length = (int) (CargoSpace.length);
    public static int height = (int) (CargoSpace.height);
    public static int width = (int) (CargoSpace.width);
    //    private String[] sequence = {"C", "B", "A"};
    private final String[] sequence = {"T", "P", "L"};
    DancingLinks dance = new DancingLinks(width * height * length);

    /**
     * Iterates through all parcels and their rotations, attempting to place them
     * in all possible positions within the cargo space. It then creates a sparse matrix
     * representation of these positions and uses the DancingLinks instance to solve the exact cover problem.
     */
    public void createPositions() {
        int nr = 0;
        for (final String type : sequence) {
            final Parcel parcel = new Parcel(type);
            final int currentPieceValue = parcel.getValue();

            for (int rotation = 0; rotation < shapesAndRotations.rotationNum(type); rotation++) {
                final int[][][] shape = shapesAndRotations.getShape(type, rotation);
                final int shapeWidth = shape[0][0].length;
                final int shapeHeight = shape[0].length;
                final int shapeLength = shape.length;
                for (int z = 0; z < length; z++) {
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (!canPlace(x, y, z, shape)) {
                                continue;
                            }
                            final List<Integer> xs = getOccupiedCellsX(shape, x);
                            final List<Integer> ys = getOccupiedCellsY(shape, y);
                            final List<Integer> zs = getOccupiedCellsZ(shape, z);

                            final int[] positions;

                            if (type.equals("L") || type.equals("P") || type.equals("T")) {
                                positions = new int[5];
                            } else {
                                positions = new int[shapeLength * shapeHeight * shapeWidth];
                            }


                            for (int i = 0; i < positions.length; i++) {
                                positions[i] = length * height * xs.get(i) + length * ys.get(i) + zs.get(i);
                            }

                            final ParcelInfo parcelInfo = new ParcelInfo(nr, x, y, z, parcel.getId(), shape, currentPieceValue);
                            dance.addParcelInfoToList(parcelInfo);
                            //dance.parcelInfo.add(new ParcelInfo(nr, x, y, z, parcel.getId(), shape, currentPieceValue));
                            dance.AddRow(nr, parcel.getId(), positions, shape);
                            nr++;
                        }
                    }
                }
            }
        }
        dance.algorithmX(0);
    }

    /**
     * Checks if a piece can be placed at a specified location within the cargo space.
     * It considers the dimensions of the piece and the boundaries of the cargo space.
     *
     * @param startX The starting X-coordinate in the cargo space.
     * @param startY The starting Y-coordinate in the cargo space.
     * @param startZ The starting Z-coordinate in the cargo space.
     * @param shape  The 3D array representing the shape of the piece.
     * @return true if the piece can be placed; false otherwise.
     */
    public boolean canPlace(final int startX, final int startY, final int startZ, final int[][][] shape) {

        final int shapeWidth = shape[0][0].length;
        final int shapeHeight = shape[0].length;
        final int shapeDepth = shape.length;

        if (startX + shapeWidth > width) {
            return false;
        }

        if (startY + shapeHeight > height) {
            return false;
        }

        return startZ + shapeDepth <= length;
    }

    /**
     * Calculates the X-coordinates of the cargo space occupied by a given piece when placed at a starting point.
     *
     * @param pieceToPlace The 3D array representing the shape of the piece to place.
     * @param x0           The starting X-coordinate in the cargo space.
     * @return A list of integers representing the occupied X-coordinates.
     */
    public List<Integer> getOccupiedCellsX(final int[][][] pieceToPlace, final int x0) {
        final List<Integer> xs = new ArrayList<>();

        for (int z = 0; z < pieceToPlace.length; z++) {
            for (int y = 0; y < pieceToPlace[0].length; y++) {
                for (int x = 0; x < pieceToPlace[0][0].length; x++) {
                    if (pieceToPlace[z][y][x] != 0) {
                        xs.add(x + x0);
                    }
                }
            }
        }
        return xs;

    }

    /**
     * Calculates the Y-coordinates of the cargo space occupied by a given piece when placed at a starting point.
     *
     * @param pieceToPlace The 3D array representing the shape of the piece to place.
     * @param y0           The starting Y-coordinate in the cargo space.
     * @return A list of integers representing the occupied Y-coordinates.
     */
    public List<Integer> getOccupiedCellsY(final int[][][] pieceToPlace, final int y0) {
        final List<Integer> ys = new ArrayList<>();

        for (int z = 0; z < pieceToPlace.length; z++) {
            for (int y = 0; y < pieceToPlace[0].length; y++) {
                for (int x = 0; x < pieceToPlace[0][0].length; x++) {
                    if (pieceToPlace[z][y][x] != 0) {
                        ys.add(y + y0);
                    }
                }
            }
        }
        return ys;
    }

    /**
     * Calculates the Z-coordinates of the cargo space occupied by a given piece when placed at a starting point.
     *
     * @param pieceToPlace The 3D array representing the shape of the piece to place.
     * @param z0           The starting Z-coordinate in the cargo space.
     * @return A list of integers representing the occupied Z-coordinates.
     */
    public List<Integer> getOccupiedCellsZ(final int[][][] pieceToPlace, final int z0) {
        final List<Integer> zs = new ArrayList<>();

        for (int i = 0; i < pieceToPlace.length; i++) {
            for (int j = 0; j < pieceToPlace[0].length; j++) {
                for (int k = 0; k < pieceToPlace[0][0].length; k++) {
                    if (pieceToPlace[i][j][k] != 0) {
                        zs.add(i + z0);
                    }
                }
            }
        }
        return zs;
    }

    /**
     * The main method to run the DLSearch algorithm. It initializes the search process.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(final String[] args) {
        final DLSearch dlx = new DLSearch();
        dlx.createPositions();
    }
}
