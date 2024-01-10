package com.bcs2024.knapsack.model;

/**
 * Represents a parcel with specific dimensions and value.
 * Each parcel has a type, length, width, height, and an associated value.
 */
public class Parcel {
    private double length, width, height, value;
    private String type;

<<<<<<< Updated upstream
    private boolean[][][][] shape; // 4D array to represent 3D shapes with orientation

    // Constructor for parcels (A, B, C, L, P, T)
    public Parcel(final String type, boolean[][][][] shape) {
=======
    private int[][][] shape;
    private int rotation;

    // Constructor for parcels (A, B, C, L, P, T)
    public Parcel(String type, int rotation) {
>>>>>>> Stashed changes
        this.type = type;
        this.rotation = rotation;
        this.shape = ShapesAndRotations.getShape(type, rotation);
        initializeParcel();
    }

    // TODO find a way to change the length, width, height, and value of the parcel based on its rotation
    private void initializeParcel() {
        switch (type) {
            case "A" -> {
                this.length = 1.0;
                this.width = 1.0;
                this.height = 2.0;
                this.value = 1;
            }
            case "B" -> {
                this.length = 1.0;
                this.width = 1.5;
                this.height = 2.0;
                this.value = 2;
            }
            case "C" -> {
                this.length = 1.5;
                this.width = 1.5;
                this.height = 1.5;
                this.value = 3;
            }
            case "L" -> {
                this.length = 0.5;
                this.width = 0.5;
                this.height = 0.5;
                this.value = 3;
            }
            case "P" -> {
                this.length = 0.5;
                this.width = 0.5;
                this.height = 0.5;
                this.value = 4;
            }
            case "T" -> {
                this.length = 0.5;
                this.width = 0.5;
                this.height = 0.5;
                this.value = 5;
            }
            default -> System.out.println("Invalid type");
        }

        this.length *= 2;
        this.width *= 2;
        this.height *= 2;
    }

    /**
     * Returns the shape of the parcel with its orientation.
     *
     * @return The shape of the parcel.
     */
<<<<<<< Updated upstream
    public boolean[][][][] getShape() {
=======
    public int[][][] getShape() {
>>>>>>> Stashed changes
        return shape;
    }

    /**
<<<<<<< Updated upstream
     * Sets the shape of the parcel with its orientation.
     *
     * @param shape The new shape of the parcel.
     */
    public void setShape(boolean[][][][] shape) {
        this.shape = shape;
    }

    /**
=======
>>>>>>> Stashed changes
     * Returns the length of the parcel.
     *
     * @return The length of the parcel.
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets the length of the parcel.
     *
     * @param length The new length of the parcel.
     */
    public void setLength(final double length) {
        this.length = length;
    }

    /**
     * Returns the width of the parcel.
     *
     * @return The width of the parcel.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the parcel.
     *
     * @param width The new width of the parcel.
     */
    public void setWidth(final double width) {
        this.width = width;
    }

    /**
     * Returns the height of the parcel.
     *
     * @return The height of the parcel.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the parcel.
     *
     * @param height The new height of the parcel.
     */
    public void setHeight(final double height) {
        this.height = height;
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
