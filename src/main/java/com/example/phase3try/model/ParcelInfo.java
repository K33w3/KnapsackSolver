package com.example.phase3try.model;

public class ParcelInfo {
    int id;
    public int parcelID;
    public int[][][] shape;
    public int x0;
    public int y0;
    public int z0;
    public int pieceValue;


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
