package com.bcs2024.knapsack.algorithm.dancinglinks;

/**
 * Represents a header cell in the sparse matrix used by the Dancing Links algorithm.
 * The Header class extends the Cell class, inheriting its structure and adding specific fields
 * to manage the column's metadata, such as its name and the number of non-zero elements (size).
 */
public class Header extends Cell {
    public int name;
    public int size;

    /**
     * Constructs a Header cell with a specified name and initializes its size to zero.
     * Sets the column header of this cell to itself, marking it as a column header.
     *
     * @param name The identifier or name for this column header.
     */
    public Header(final int name) {
        super(null);
        size = 0;
        this.name = name;
        C = this;
    }
}
