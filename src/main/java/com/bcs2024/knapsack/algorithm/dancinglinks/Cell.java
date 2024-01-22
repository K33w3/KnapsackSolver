package com.bcs2024.knapsack.algorithm.dancinglinks;

/**
 * Represents a cell in the sparse matrix used by the Dancing Links algorithm.
 * The Cell class also includes additional fields to store information specific to the knapsack problem,
 * such as the identifier of the parcel (pentID), its rotation, position, and shape.
 */
public class Cell {

    public int row; // The row index of the cell in the sparse matrix.
    public Cell L; // The cell to the left of this cell.
    public Cell R; // The cell to the right of this cell.
    public Cell U; // The cell above this cell.
    public Cell D; // The cell below this cell.
    public Header C; // The column header of this cell.
    public int pentID; // The identifier of the parcel.
    public int rotation; // The rotation index of the parcel.
    public int x0; // The x-coordinate of the parcel's position in the cargo space.
    public int y0; // The y-coordinate of the parcel's position in the cargo space.
    public int z0; // The z-coordinate of the parcel's position in the cargo space.
    public int[][][] shape; // The 3D array representing the shape of the parcel.


    /**
     * Constructs a Cell and links it to its column header.
     * Initializes the parcel-specific information to default values.
     *
     * @param header The column header associated with this cell.
     */
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

    /**
     * Inserts a cell to the left of this cell, updating the necessary links.
     *
     * @param cell The cell to be inserted.
     */
    public void InsertLeft(final Cell cell) {
        cell.L = L;
        L.R = cell;
        L = cell;
        cell.R = this;
    }

    /**
     * Inserts a cell above this cell, updating the necessary links.
     *
     * @param cell The cell to be inserted.
     */
    public void InsertUp(final Cell cell) {
        cell.U = U;
        U.D = cell;
        U = cell;
        cell.D = this;
    }
}
