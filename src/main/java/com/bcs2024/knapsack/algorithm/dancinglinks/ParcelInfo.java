package com.bcs2024.knapsack.algorithm.dancinglinks;

public class ParcelInfo {
    int id;
    public int parcelID;
    public int[][][] shape;
    public int x0;
    public int y0;
    public int z0;
    public int pieceValue;

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
