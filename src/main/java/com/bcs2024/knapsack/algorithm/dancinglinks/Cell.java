package com.bcs2024.knapsack.algorithm.dancinglinks;

public class Cell {

    public int row;
    public Cell L;
    public Cell R;
    public Cell U;
    public Cell D;
    public Header C;

    public int pentID;
    public int rotation;
    public int x0;
    public int y0;
    public int z0;
    public int[][][] shape;

    public Cell(final Header header) {
        row = -1;
        L = this;
        R = this;
        U = this;
        D = this;
        C = header;

        pentID = -1;
        rotation = -1;
        x0 = -1;
        y0 = -1;
        z0 = -1;
        shape = new int[0][0][0];
    }

    public void InsertLeft(final Cell cell) {
        cell.L = L;
        L.R = cell;
        L = cell;
        cell.R = this;
    }

    public void InsertUp(final Cell cell) {
        cell.U = U;
        U.D = cell;
        U = cell;
        cell.D = this;
    }
}
