package com.bcs2024.knapsack.model;

import com.bcs2024.knapsack.util.ShapesAndRotations;

public class Parcel {

    private int value;
    private String type;
    private int[][][] shape;
    private int id;

    private int id;

    public Parcel(final String type, final int[][][] shape) {
        this.type = type;
        this.shape = shape;
        initializeParcelByType();
    }

    public Parcel(final int id, final int[][][] shape) {
        this.id = id;
        this.shape = shape;
        initializeParcelById();
    }

    public Parcel(final String type) {
        this.type = type;
        this.shape = new ShapesAndRotations().getShape(type, 0);
        initializeParcelByType();
    }

    private void initializeParcelByType() {
        switch (type) {
            case "A" -> {
                this.value = 3;
                this.id = 1;
            }
            case "B" -> {
                this.value = 4;
                this.id = 2;
            }
            case "C" -> {
                this.value = 5;
                this.id = 3;
            }
            case "L" -> {
                this.value = 3;
                this.id = 4;
            }
            case "P" -> {
                this.value = 4;
                this.id = 5;
            }
            case "T" -> {
                this.value = 5;
                this.id = 6;
            }
            default -> System.out.println("Invalid type");
        }
    }

    private void initializeParcelById() {
        switch (id) {
            case 1 -> {
                this.value = 3;
                this.type = "A";
            }
            case 2 -> {
                this.value = 4;
                this.type = "B";
            }
            case 3 -> {
                this.value = 5;
                this.type = "C";
            }
            case 4 -> {
                this.value = 3;
                this.type = "L";
            }
            case 5 -> {
                this.value = 4;
                this.type = "P";
            }
            case 6 -> {
                this.value = 5;
                this.type = "T";
            }
            default -> System.out.println("Invalid type");
        }
    }

    private int setId(String type) {
        return switch (type) {
            case "A" ->  1;
            case "B" ->  2;
            case "C" ->  3;
            case "L" ->  4;
            case "P" ->  5;
            case "T" ->  6;
            default -> -1;
        };
    }

    public int[][][] getShape() {
        return shape;
    }

    public int getValue() {
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

    public int getId() {
        return id;
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

    public int getId() {
        return this.id;
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
