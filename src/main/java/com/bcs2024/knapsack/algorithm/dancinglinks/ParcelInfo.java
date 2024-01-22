package com.bcs2024.knapsack.algorithm.dancinglinks;

/**
 * Represents detailed information about a parcel in the context of the knapsack problem.
 * It includes the parcel's identifier, position, shape, and value.
 */
public class ParcelInfo {
    int id;
    public int parcelID;
    public int[][][] shape;
    public int x0;
    public int y0;
    public int z0;
    public int pieceValue;

    /**
     * Constructs a ParcelInfo object with specified parameters, representing a parcel's details.
     *
     * @param id         The unique identifier for this instance of parcel information.
     * @param x          The x-coordinate of the parcel's position in the cargo space.
     * @param y          The y-coordinate of the parcel's position in the cargo space.
     * @param z0         The z-coordinate of the parcel's position in the cargo space.
     * @param parcelID   The identifier of the parcel type.
     * @param shape      The 3D array representing the shape of the parcel.
     * @param pieceValue The value of the parcel.
     */
    public ParcelInfo(final int id, final int x, final int y, final int z0, final int parcelID, final int[][][] shape, final int pieceValue) {
        this.id = id;
        this.x0 = x;
        this.y0 = y;
        this.z0 = z0;
        this.parcelID = parcelID;
        this.shape = shape;
        this.pieceValue = pieceValue;
    }
}
