package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.model.ParcelInfo;
import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.ArrayList;
import java.util.List;

public class DLSearch {
    private ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
    public static int length = (int) (CargoSpace.length * 2);
    public static int height = (int) (CargoSpace.height * 2);
    public static int width = (int) (CargoSpace.width * 2);
    public static int totalValue = 0;
    private String[] sequence = { "A", "B", "C" };
    // private String[] sequence = {"T", "P", "L"};
    DancingLinks dance = new DancingLinks(width * height * length);
    public static List<ParcelInfo> parcelInfo = new ArrayList<ParcelInfo>();
    public static int pieceCount = 0;
    private int currentPieceValue;

    /**
     * Generates positions for parcels of different types and rotations within the
     * cargo space.
     * For each parcel type and rotation, it iterates through the cargo space to
     * find valid positions
     * and adds them to the Dancing Links matrix for solving.
     */
    public void createPositions() {
        int nr = 0;
        for (String type : sequence) {
            Parcel parcel = new Parcel(type);
            currentPieceValue = (int) parcel.getValue();
            for (int rotation = 0; rotation < shapesAndRotations.rotationNum(type); rotation++) {
                int[][][] shape = shapesAndRotations.getShape(type, rotation);
                int shapeWidth = shape[0][0].length;
                int shapeHeight = shape[0].length;
                int shapeLength = shape.length;
                for (int z = 0; z < length; z++) {
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (!canPlace(x, y, z, shape)) {
                                continue;
                            }
                            List<Integer> xs = getOccupiedCellsX(shape, x);
                            List<Integer> ys = getOccupiedCellsY(shape, y);
                            List<Integer> zs = getOccupiedCellsZ(shape, z);

                            int[] positions = null;

                            if (type == "L" || type == "P" || type == "T") {
                                positions = new int[5];
                            } else {
                                positions = new int[shapeLength * shapeHeight * shapeWidth];
                            }

                            for (int i = 0; i < positions.length; i++) {
                                positions[i] = length * height * xs.get(i) + length * ys.get(i) + zs.get(i);
                            }

                            parcelInfo.add(new ParcelInfo(nr, x, y, z, parcel.getId(), shape, currentPieceValue));
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
     * Checks if a given shape can be placed at the specified starting position
     * within the cargo space.
     *
     * @param startX The starting X-coordinate within the cargo space.
     * @param startY The starting Y-coordinate within the cargo space.
     * @param startZ The starting Z-coordinate within the cargo space.
     * @param shape  The shape to be placed, represented as a 3D array.
     * @return True if the shape can be placed without exceeding the bounds of the
     *         cargo space, false otherwise.
     */
    public boolean canPlace(int startX, int startY, int startZ, int[][][] shape) {

        int shapeWidth = shape[0][0].length;
        int shapeHeight = shape[0].length;
        int shapeDepth = shape.length;

        if (startX + shapeWidth > width) {
            return false;
        }

        if (startY + shapeHeight > height) {
            return false;
        }

        return startZ + shapeDepth <= length;
    }

    /**
     * Gets the list of X-coordinates of occupied cells within the given 3D shape
     * when placed at the specified X-coordinate.
     *
     * @param pieceToPlace The 3D shape to be placed, represented as a 3D array.
     * @param x0           The X-coordinate at which the shape is placed.
     * @return A list of X-coordinates of occupied cells within the shape when
     *         placed at the specified X-coordinate.
     */
    public List<Integer> getOccupiedCellsX(int[][][] pieceToPlace, int x0) {
        List<Integer> xs = new ArrayList<Integer>();

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
     * Gets the list of Y-coordinates of occupied cells within the given 3D shape
     * when placed at the specified Y-coordinate.
     *
     * @param pieceToPlace The 3D shape to be placed, represented as a 3D array.
     * @param y0           The Y-coordinate at which the shape is placed.
     * @return A list of Y-coordinates of occupied cells within the shape when
     *         placed at the specified Y-coordinate.
     */
    public List<Integer> getOccupiedCellsY(int[][][] pieceToPlace, int y0) {
        List<Integer> ys = new ArrayList<Integer>();

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
     * Gets the list of Z-coordinates of occupied cells within the given 3D shape
     * when placed at the specified Z-coordinate.
     *
     * @param pieceToPlace The 3D shape to be placed, represented as a 3D array.
     * @param z0           The Z-coordinate at which the shape is placed.
     * @return A list of Z-coordinates of occupied cells within the shape when
     *         placed at the specified Z-coordinate.
     */
    public List<Integer> getOccupiedCellsZ(int[][][] pieceToPlace, int z0) {
        List<Integer> zs = new ArrayList<Integer>();

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
     * Prints the solution to the console.
     * This method prints the solution to the console.
     *
     * @param solution The solution to be printed.
     */
    public static void main(String[] args) {
        DLSearch dlx = new DLSearch();
        dlx.createPositions();
    }
}
