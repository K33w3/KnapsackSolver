package com.bcs2024.knapsack.model;

import java.util.ArrayList;
import java.util.List;

public class CargoSpace {

    public static final double length = 16.5;
    public static final double width = 2.5;
    public static final double height = 4;
    private final List<ParcelPlacement> placements = new ArrayList<>();
    private int[][][] occupied;
    private int filledSlotsCount = 0;

    public CargoSpace() {
        final int x = (int) (width * 2);
        final int y = (int) (height * 2);
        final int z = (int) (length * 2);
        occupied = new int[x][y][z];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    occupied[i][j][k] = -1;
                }
            }
        }
    }

    public boolean canPlace(final int[][][] shape, final int startX, final int startY, final int startZ) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        final int x = startX + k;
                        final int y = startY + j;
                        final int z = startZ + i;
                        if (x >= (int) (this.width * 2) || y >= (int) (this.height * 2) || z >= (int) (this.length * 2) || occupied[x][y][z] != -1) {
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

    public void placeParcel(int[][][] shape, int startX, int startY, int startZ, int[][][] destination) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        int x = startX + k;
                        int y = startY + j;
                        int z = startZ + i;
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
