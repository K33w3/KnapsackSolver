package com.bcs2024.knapsack.algorithm.dancinglinks;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.ArrayList;
import java.util.List;

public class DLSearch {
    private ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
    public static int length = (int) (CargoSpace.length);
    public static int height = (int) (CargoSpace.height);
    public static int width = (int) (CargoSpace.width);
    public static int totalValue = 0;
    //    private String[] sequence = {"C", "B", "A"};
    private String[] sequence = {"T", "P", "L"};
    DancingLinks dance = new DancingLinks(width * height * length);
    public static List<ParcelInfo> parcelInfo = new ArrayList<>();
    public static int pieceCount = 0;
    private int currentPieceValue;

    public void createPositions() {
        int nr = 0;
        for (final String type : sequence) {
            final Parcel parcel = new Parcel(type);
            currentPieceValue = parcel.getValue();
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


    public static void main(final String[] args) {
        final DLSearch dlx = new DLSearch();
        dlx.createPositions();
    }
}