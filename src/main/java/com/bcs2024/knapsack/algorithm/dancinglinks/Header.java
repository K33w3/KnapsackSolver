package com.bcs2024.knapsack.algorithm.dancinglinks;

public class Header extends Cell {
    public int name;
    public int size;

    public Header(final int name) {
        super(null);
        size = 0;
        this.name = name;
        C = this;
    }
}
