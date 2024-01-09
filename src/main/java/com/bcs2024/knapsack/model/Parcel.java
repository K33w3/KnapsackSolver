package com.bcs2024.knapsack.model;

/**
 * Represents a parcel with specific dimensions and value.
 * Each parcel has a type, length, width, height, and an associated value.
 */
public class Parcel {

    public static Parcel parcelA = new Parcel("A", 1.0, 1.0, 2.0, 1);
    public static Parcel parcelB = new Parcel("B", 1.0, 1.5, 2.0, 2);
    public static Parcel parcelC = new Parcel("C", 1.5, 1.5, 1.5, 3);

    private double length, width, height, value;
    private String type;

    /**
     * Constructs a new com.bcs2024.knapsack.model.Parcel with specified dimensions and value.
     *
     * @param type   The type of the parcel (e.g., "A", "B", "C").
     * @param length The length of the parcel.
     * @param width  The width of the parcel.
     * @param height The height of the parcel.
     * @param value  The value of the parcel.
     */
    public Parcel(final String type, final double length, final double width, final double height, final double value) {
        this.type = type;
        this.length = length;
        this.width = width;
        this.height = height;
        this.value = value;
    }

    /**
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
     * Sets the value of the parcel.
     *
     * @param value The new value of the parcel.
     */
    public void setValue(final double value) {
        this.value = value;
    }

    /**
     * Returns the type of the parcel.
     *
     * @return The type of the parcel.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the parcel.
     *
     * @param type The new type of the parcel.
     */
    public void setType(final String type) {
        this.type = type;
    }
}

