package com.bcs2024.knapsack.model;

/**
 * Represents the cargo space with dimensions and tracking for occupied space.
 */
public class CargoSpace {
    private double length, width, height;
    private boolean[][][] occupied;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Constructs a new CargoSpace with specified dimensions.
     *
     * @param length The length of the cargo space.
     * @param width  The width of the cargo space.
     * @param height The height of the cargo space.
     */
    public CargoSpace(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.occupied = new boolean[(int) length][(int) width][(int) height];
    }

    /**
     * Checks if a specific area of the cargo space is available to occupy.
     *
     * @param parcel The parcel to check space for.
     * @param x The x-coordinate in the cargo space.
     * @param y The y-coordinate in the cargo space.
     * @param z The z-coordinate in the cargo space.
     * @return true if the space is available, false otherwise.
     */
    public boolean isSpaceAvailable(Parcel parcel, int x, int y, int z) {
        int parcelLength = (int) parcel.getLength();
        int parcelWidth = (int) parcel.getWidth();
        int parcelHeight = (int) parcel.getHeight();

        for (int i = x; i < x + parcelLength; i++) {
            for (int j = y; j < y + parcelWidth; j++) {
                for (int k = z; k < z + parcelHeight; k++) {
                    if (i >= length || j >= width || k >= height || occupied[i][j][k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Marks a specific area of the cargo space as occupied by a parcel.
     *
     * @param parcel The parcel occupying the space.
     * @param x The x-coordinate where the parcel starts.
     * @param y The y-coordinate where the parcel starts.
     * @param z The z-coordinate where the parcel starts.
     */
    public void occupySpace(Parcel parcel, int x, int y, int z) {
        int parcelLength = (int) parcel.getLength();
        int parcelWidth = (int) parcel.getWidth();
        int parcelHeight = (int) parcel.getHeight();

        for (int i = x; i < x + parcelLength; i++) {
            for (int j = y; j < y + parcelWidth; j++) {
                for (int k = z; k < z + parcelHeight; k++) {
                    occupied[i][j][k] = true;
                }
            }
        }
    }

    public void printOccupiedSpacePositions() {
        for (int i = 0; i < occupied.length; i++) {
            for (int j = 0; j < occupied[i].length; j++) {
                for (int k = 0; k < occupied[i][j].length; k++) {
                    System.out.println("Position (" + i + ", " + j + ", " + k + "): " + (occupied[i][j][k] ? "Occupied" : "Free"));
                }
            }
        }
    }
}
