package com.bcs2024.knapsack.model;

/**
 * Represents the cargo space with dimensions and tracking for occupied space.
 */
public class CargoSpace {
    private int length, width, height;
    private final boolean[][][] occupied;

    public double getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Constructs a new CargoSpace with specified dimensions.
     *
     * @param length The length of the cargo space.
     * @param width  The width of the cargo space.
     * @param height The height of the cargo space.
     */
    public CargoSpace(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.occupied = new boolean[ length][ width][ height];
    }

    /**
     * Checks if a specific area of the cargo space is available to occupy based on the given parcel placement.
     *
     * @param placement The ParcelPlacement object representing the parcel's placement in the cargo space.
     * @return true if the space is available, false otherwise.
     */
    public boolean canPlace(ParcelPlacement placement) {
        Parcel parcel = placement.getParcel();
        int[][][] parcelShape = parcel.getShape(placement.getOrientation()); // Get shape for specific orientation

        int startX = placement.getX();
        int startY = placement.getY();
        int startZ = placement.getZ();

        for (int i = 0; i < parcelShape.length; i++) {
            for (int j = 0; j < parcelShape[i].length; j++) {
                for (int k = 0; k < parcelShape[i][j].length; k++) {
                    if (parcelShape[i][j][k] == 1) {
                        int x = startX + i;
                        int y = startY + j;
                        int z = startZ + k;
                        if (x >= this.length || y >= this.width || z >= this.height || occupied[x][y][z]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * Marks a specific area of the cargo space as occupied based on the given parcel placement.
     *
     * @param placement The ParcelPlacement object representing the parcel's placement in the cargo space.
     */
    public void placeParcel(ParcelPlacement placement) {
        Parcel parcel = placement.getParcel();
        int[][][] parcelShape = parcel.getShape(placement.getOrientation()); // Get shape for specific orientation

        int startX = placement.getX();
        int startY = placement.getY();
        int startZ = placement.getZ();

        for (int i = 0; i < parcelShape.length; i++) {
            for (int j = 0; j < parcelShape[i].length; j++) {
                for (int k = 0; k < parcelShape[i][j].length; k++) {
                    if (parcelShape[i][j][k] == 1) {
                        int x = startX + i;
                        int y = startY + j;
                        int z = startZ + k;
                        occupied[x][y][z] = true;
                    }
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
