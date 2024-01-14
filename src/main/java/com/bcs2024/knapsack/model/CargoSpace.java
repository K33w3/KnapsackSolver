package com.bcs2024.knapsack.model;

import java.util.ArrayList;
import java.util.List;

public class CargoSpace {

    private final double length = 16.5;
    private final double width = 2.5;
    private final double height = 4;
    private int[][][] occupied;
    private int filledSlotsCount = 0;
    private List<ParcelPlacement> placements = new ArrayList<>();

    public CargoSpace() {
        int x = (int) (width * 2);
        int y = (int) (height * 2);
        int z = (int) (length * 2);
        occupied = new int[x][y][z];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    occupied[i][j][k] = -1;
                }
            }
        }
    }

    public boolean canPlace(int[][][] shape, int startX, int startY, int startZ) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        int x = startX + k;
                        int y = startY + j;
                        int z = startZ + i;
                        if (x >= (int) (this.width * 2) || y >= (int) (this.height * 2) || z >= (int) (this.length * 2) || occupied[x][y][z] != -1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void placeParcel(ParcelPlacement placement) {
        for (int i = 0; i < placement.getShape().length; i++) {
            for (int j = 0; j < placement.getShape()[0].length; j++) {
                for (int k = 0; k < placement.getShape()[0][0].length; k++) {
                    if (placement.getShape()[i][j][k] != 0) {
                        int x = placement.getX() + k;
                        int y = placement.getY() + j;
                        int z = placement.getZ() + i;

                        // Check if indices are within the bounds
                        if (x >= 0 && x < (int)(this.width * 2) && y >= 0 && y < (int)(this.height * 2) && z >= 0 && z < (int)(this.length * 2)) {
                            this.occupied[x][y][z] = placement.getShape()[i][j][k];
                            filledSlotsCount++;
                        }
                    }
                }
            }
        }
        placements.add(placement);
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

    public int getFilledSlotsCount(){return this.filledSlotsCount;}
    public List<ParcelPlacement> getPlacements() {
        return placements;
    }
    public void setOccupied(int[][][] occupied) {
        this.occupied = occupied;
    }
}
