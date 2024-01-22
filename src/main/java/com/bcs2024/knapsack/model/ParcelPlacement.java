package com.bcs2024.knapsack.model;

import javafx.scene.paint.Color;

/**
 * Represents the placement of a parcel within a cargo space, extending the Parcel class to include
 * position and color information for visualization purposes.
 */
public class ParcelPlacement extends Parcel {
    private Parcel parcel;
    private int x, y, z; // Position in the cargo space
    private Color color;

    /**
     * Constructs a new ParcelPlacement with specified parcel, position, and orientation.
     *
     * @param parcel The parcel to place.
     * @param x      The x-coordinate in the cargo space.
     * @param y      The y-coordinate in the cargo space.
     * @param z      The z-coordinate in the cargo space.
     */
    public ParcelPlacement(final Parcel parcel, final int x, final int y, final int z) {
        super(parcel.getType());

        switch (parcel.getType()) {
            case "A" -> this.color = Color.rgb(255, 0, 0);
            case "B" -> this.color = Color.rgb(0, 255, 0);
            case "C" -> this.color = Color.rgb(0, 0, 255);
            case "L" -> this.color = Color.rgb(255, 255, 0);
            case "P" -> this.color = Color.rgb(0, 255, 255);
            case "T" -> this.color = Color.rgb(255, 0, 255);
        }

        this.parcel = parcel;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Retrieves the parcel associated with this placement.
     *
     * @return The Parcel object.
     */
    public Parcel getParcel() {
        return parcel;
    }

    /**
     * Sets the parcel for this placement.
     *
     * @param parcel The new Parcel object.
     */
    public void setParcel(final Parcel parcel) {
        this.parcel = parcel;
    }

    /**
     * Retrieves the x-coordinate of the parcel's placement in the cargo space.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the parcel's placement in the cargo space.
     *
     * @param x The new x-coordinate.
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Retrieves the y-coordinate of the parcel's placement in the cargo space.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the parcel's placement in the cargo space.
     *
     * @param y The new y-coordinate.
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * Retrieves the z-coordinate of the parcel's placement in the cargo space.
     *
     * @return The z-coordinate.
     */
    public int getZ() {
        return z;
    }

    /**
     * Sets the z-coordinate of the parcel's placement in the cargo space.
     *
     * @param z The new z-coordinate.
     */
    public void setZ(final int z) {
        this.z = z;
    }

    /**
     * Retrieves the color associated with the parcel.
     *
     * @return The Color object representing the color of the parcel.
     */
    public Color getColor() {
        return color;
    }
}
