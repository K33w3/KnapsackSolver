package com.bcs2024.knapsack.model;

public class CargoSpace {
    private double length, width, height;
    private final boolean[][][] occupied;

    public double getLength() {
        return length;
    }

    public void setLength(final double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(final double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(final double height) {
        this.height = height;
    }

    public CargoSpace(final double length, final double width, final double height) {
        this.length = length;
        this.width = width;
        this.height = height;

        // Initialize the occupied array
        // Assuming each unit in the array represents a 1x1x1 volume
        occupied = new boolean[(int) length][(int) width][(int) height];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    occupied[i][j][k] = false;
                }
            }
        }
    }

    // Manage space occupancy
    // Checks if a space is available
    public boolean isSpaceAvailable(final int startL, final int startW, final int startH, final int parcelLength, final int parcelWidth, final int parcelHeight) {
        for (int i = startL; i < startL + parcelLength; i++) {
            for (int j = startW; j < startW + parcelWidth; j++) {
                for (int k = startH; k < startH + parcelHeight; k++) {
                    if (occupied[i][j][k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Set a space as occupied
    public void setOccupied(final int startL, final int startW, final int startH, final int parcelLength, final int parcelWidth, final int parcelHeight) {
        if (!isValidPlacement(startL, startW, startH, parcelLength, parcelWidth, parcelHeight)) {
            for (int i = startL; i < startL + parcelLength; i++) {
                for (int j = startW; j < startW + parcelWidth; j++) {
                    for (int k = startH; k < startH + parcelHeight; k++) {
                        occupied[i][j][k] = true;
                    }
                }
            }
        }
    }

    private boolean isValidPlacement(final int startL, final int startW, final int startH, final int length, final int width, final int height) {
        // Check if the placement is within the bounds of the cargo space
        return startL + length <= this.length && startW + width <= this.width && startH + height <= this.height;
    }
}
