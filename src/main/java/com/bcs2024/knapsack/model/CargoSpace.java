package com.bcs2024.knapsack.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a three-dimensional cargo space in the context of a knapsack problem.
 * The CargoSpace class manages parcel placements, dimensions of the cargo space,
 * and tracks the state of occupied slots within the cargo space.
 */
public class CargoSpace {

    public static final double length = 16.5 * 2;
    public static final double width = 2.5 * 2;
    public static final double height = 4 * 2;
    private final List<ParcelPlacement> placements = new ArrayList<>();
    private int[][][] occupied;
    private int filledSlotsCount = 0;

    /**
     * Constructs a new CargoSpace object representing a three-dimensional cargo
     * space.
     * This constructor initializes the dimensions (x, y, z) based on the width,
     * height, and length,
     * and creates an occupied array to represent the cargo space, initializing all
     * elements to -1.
     */
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

    /**
     * Checks if a parcel can be placed in the cargo space at the specified position,
     * considering its shape.
     *
     * @param shape  The 3D array representing the shape of the parcel.
     * @param startX The x-coordinate of the position where the parcel is to be placed.
     * @param startY The y-coordinate of the position where the parcel is to be placed.
     * @param startZ The z-coordinate of the position where the parcel is to be placed.
     * @return true if the parcel can be placed; false otherwise.
     */
    public boolean canPlace(final int[][][] shape, final int startX, final int startY, final int startZ) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        final int x = startX + k;
                        final int y = startY + j;
                        final int z = startZ + i;
                        if (x >= (int) (width) || y >= (int) (height) || z >= (int) (length)
                                || occupied[x][y][z] != -1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Places a parcel in the cargo space.
     * This method places a parcel in the cargo space by setting the occupied array
     * elements to the parcel's ID.
     *
     * @param placement The parcel placement object.
     */
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

    /**
     * Places a parcel in the cargo space.
     * This method places a parcel in the cargo space by setting the occupied array
     * elements to the parcel's ID.
     *
     * @param shape  The parcel's shape.
     * @param startX The parcel's x-coordinate.
     * @param startY The parcel's y-coordinate.
     * @param startZ The parcel's z-coordinate.
     */
    public void placeParcel(final int[][][] shape, final int startX, final int startY, final int startZ) { // TODO
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        final int x = startX + k;
                        final int y = startY + j;
                        final int z = startZ + i;
                        occupied[x][y][z] = shape[i][j][k];
                    }
                }
            }
        }
    }

    /**
     * Returns the length of the cargo space.
     * This method returns the length of the cargo space.
     *
     * @return The length of the cargo space.
     */
    public double getLength() {
        return length;
    }

    /**
     * Returns the width of the cargo space.
     * This method returns the width of the cargo space.
     *
     * @return The width of the cargo space.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the cargo space.
     * This method returns the height of the cargo space.
     *
     * @return The height of the cargo space.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the occupied array representing the cargo space.
     * This method returns the occupied array representing the cargo space.
     *
     * @return The occupied array representing the cargo space.
     */
    public int[][][] getOccupied() {
        return this.occupied;
    }

    /**
     * Sets the occupied array representing the cargo space.
     * This method sets the occupied array representing the cargo space.
     *
     * @param occupied The occupied array representing the cargo space.
     */
    public void setOccupied(final int[][][] occupied) {
        this.occupied = occupied;
    }

    /**
     * Sets the value of the occupied array at the specified indices.
     *
     * @param i     The x-coordinate.
     * @param j     The y-coordinate.
     * @param k     The z-coordinate.
     * @param value The value to set at the specified indices.
     */
    public void setOccupiedCell(final int i, final int j, final int k, final int value) {
        this.occupied[i][j][k] = value;
    }

    /**
     * Returns the number of filled slots in the cargo space.
     * This method returns the number of filled slots in the cargo space.
     *
     * @return The number of filled slots in the cargo space.
     */
    public int getFilledSlotsCount() {
        return this.filledSlotsCount;
    }

    /**
     * Returns the list of parcel placements in the cargo space.
     * This method returns the list of parcel placements in the cargo space.
     *
     * @return The list of parcel placements in the cargo space.
     */
    public List<ParcelPlacement> getPlacements() {
        return placements;
    }
}
