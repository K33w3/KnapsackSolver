package com.bcs2024.knapsack.model;

public class ParcelInfo {
    int id;
    public int parcelID;
    public int[][][] shape;
    public int x0;
    public int y0;
    public int z0;
    public int pieceValue;

    /**
     * Constructs a new instance of the ParcelInfo class.
     *
     * @param id        The parcel's ID.
     * @param x         The parcel's x-coordinate.
     * @param y         The parcel's y-coordinate.
     * @param z0        The parcel's z-coordinate.
     * @param parcelID  The parcel's parcel ID.
     * @param shape     The parcel's shape.
     * @param pieceValue The parcel's piece value.
     */
    public ParcelInfo(int id, int x, int y, int z0, int parcelID, int[][][] shape, int pieceValue) {
        this.id = id;
        this.x0 = x;
        this.y0 = y;
        this.z0 = z0;
        this.parcelID = parcelID;
        this.shape = shape;
        this.pieceValue = pieceValue;
    }
}
