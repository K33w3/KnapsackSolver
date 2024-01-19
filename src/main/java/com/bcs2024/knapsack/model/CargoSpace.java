package com.bcs2024.knapsack.model;

import java.util.ArrayList;
import java.util.List;

public class CargoSpace {

    public static final double length = 16.5 * 2;
    public static final double width = 2.5 * 2;
    public static final double height = 4 * 2;
    private final List<ParcelPlacement> placements = new ArrayList<>();
    private int[][][] occupied;
    private int filledSlotsCount = 0;

    public CargoSpace() {
        final int x = (int) (width);
        final int y = (int) (height);
        final int z = (int) (length);
        occupied = new int[x][y][z];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    occupied[i][j][k] = -1;
                }
            }
        }
    }

    public boolean canPlace(final int[][][] shape, final int startX, final int startY, final int startZ, final int[][][] occupied) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        final int x = startX + k;
                        final int y = startY + j;
                        final int z = startZ + i;
                        if (x >= (int) (width) || y >= (int) (height) || z >= (int) (length) || occupied[x][y][z] != -1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean canPlace(final ParcelPlacement placement) {
        for (int i = 0; i < placement.getShape().length; i++) {
            for (int j = 0; j < placement.getShape()[0].length; j++) {
                for (int k = 0; k < placement.getShape()[0][0].length; k++) {
                    if (placement.getShape()[i][j][k] != 0) {
                        final int x = placement.getX() + k;
                        final int y = placement.getY() + j;
                        final int z = placement.getZ() + i;
                        if (x >= (int) (width) || y >= (int) (height) || z >= (int) (length) || occupied[x][y][z] != -1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void placeParcel(final ParcelPlacement placement) {
        final int[][][] shape = placement.getShape();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        final int x = placement.getX() + k;
                        final int y = placement.getY() + j;
                        final int z = placement.getZ() + i;

                        this.occupied[x][y][z] = shape[i][j][k];
                        filledSlotsCount++;
                    }
                }
            }
        }

        placements.add(placement);
    }

    public void placeParcel(final int[][][] shape, final int startX, final int startY, final int startZ, final int[][][] destination) { // TODO
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        final int x = startX + k;
                        final int y = startY + j;
                        final int z = startZ + i;
                        destination[x][y][z] = shape[i][j][k];
                    }
                }
            }
        }
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int[][][] getOccupied() {
        return this.occupied;
    }

    public void setOccupied(final int[][][] occupied) {
        this.occupied = occupied;
    }

    public int getFilledSlotsCount() {
        return this.filledSlotsCount;
    }

    public List<ParcelPlacement> getPlacements() {
        return placements;
    }
}
