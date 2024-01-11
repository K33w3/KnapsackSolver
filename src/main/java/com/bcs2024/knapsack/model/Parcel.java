package com.bcs2024.knapsack.model;

/**
 * Represents a parcel with specific dimensions and value.
 * Each parcel has a type, length, width, height, and an associated value.
 */
public class Parcel {
    private int value;
    private final String type;
    private int[][][][] shapeOrientations; // 4D array to represent 3D shapes with orientations

    // Constructor for parcels (A, B, C, L, P, T)
    public Parcel(final String type) {
        this.type = type;
        this.shapeOrientations = ShapesAndRotations.getRotations(this.type);
        initializeParcel();
    }

    private void initializeParcel() {
        switch (type) {
            case "A" -> this.value = 1;
            case "B" -> this.value = 2;
            case "C", "L" -> this.value = 3;
            case "P" -> this.value = 4;
            case "T" -> this.value = 5;
            default -> System.out.println("Invalid type");
        }
    }

    /**
     * Retrieves a specific orientation of the parcel's shape.
     *
     * @param orientationIndex The index of the desired orientation.
     * @return The 3D boolean array representing the specified orientation of the shape.
     * @throws IllegalArgumentException if the orientation index is out of bounds.
     */
    public int[][][] getShape(int orientationIndex) {
        if (orientationIndex < 0 || orientationIndex >= shapeOrientations.length) {
            throw new IllegalArgumentException("Invalid orientation index");
        }
        return shapeOrientations[orientationIndex];
    }

    /**
     * Returns the shape of the parcel with its orientation.
     *
     * @return The shape of the parcel.
     */
    public int[][][][] getShapeOrientations() {
        return shapeOrientations;
    }

    /**
     * Sets the shape of the parcel with its orientation.
     *
     * @param shapeOrientations The new shape of the parcel.
     */
    public void setShapeOrientations(int[][][][] shapeOrientations) {
        this.shapeOrientations = shapeOrientations;
    }

    /**
     * Returns the value of the parcel.
     *
     * @return The value of the parcel.
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns the type of the parcel.
     *
     * @return The type of the parcel.
     */
    public String getType() {
        return type;
    }
}

