package com.bcs2024.knapsack.model;

import com.bcs2024.knapsack.util.ShapesAndRotations;

public class Parcel {

    private int value;
    private String type;
    private int[][][] shape;

    public Parcel(final String type, final int[][][] shape) {
        this.type = type;
        this.shape = shape;
        initializeParcel();
    }

    public Parcel(final String type) {
        this.type = type;
        this.shape = new ShapesAndRotations().getShape(type, 0);
        initializeParcel();
    }

    private void initializeParcel() {
        switch (type) {
            case "A", "L" -> this.value = 3;
            case "B", "P" -> this.value = 4;
            case "C", "T" -> this.value = 5;
            default -> System.out.println("Invalid type");
        }
    }

    public int[][][] getShape() {
        return shape;
    }

    public double getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public void setShape(final int[][][] shape) {
        this.shape = shape;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public double getVolume() {
        final double volume = switch (type) {
            case "A" -> 1.0 * 1.0 * 2.0;
            case "B" -> 1.0 * 1.5 * 2.0;
            case "C" -> 1.5 * 1.5 * 1.5;
            case "L", "P", "T" ->
                // Dimensions for pentomino shapes (L, P, T): 5 cubes of 0.5 x 0.5 x 0.5
                    5 * (0.5 * 0.5 * 0.5);
            default -> throw new IllegalArgumentException("Unknown parcel type: " + type);
        };

        return volume;
    }

    public double getValueDensity() {
        final double valueDensity = switch (type) {
            case "A" -> 1.0 / 2.0;
            case "B" -> 1.5 / 2.0;
            case "C" -> 1.5 / 1.5;
            case "L", "P", "T" -> 5 * (0.5 / 0.5);
            default -> throw new IllegalArgumentException("Unknown parcel type: " + type);
        };

        return valueDensity;
    }

    public double calculateSurfaceArea() {
        // Assuming shape array dimensions represent length, width, and height
        // and the parcel is a rectangular prism
        final int length = shape.length;
        final int width = shape[0].length;
        final int height = shape[0][0].length;

        // Calculate the surface area of the parcel
        final double surfaceArea = 2.0 * (length * width + width * height + height * length);
        return surfaceArea;
    }
}
