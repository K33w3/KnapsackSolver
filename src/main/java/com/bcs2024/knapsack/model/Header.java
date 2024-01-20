package com.bcs2024.knapsack.model;

public class Header extends Cell {
    public int name;
    public int size;

    /**
     * Constructs a new instance of the Header class.
     *
     * @param name The name of the header.
     */
    public Header(int name) {
        super(null);
        size = 0;
        this.name = name;
        C = this;
    }
}

