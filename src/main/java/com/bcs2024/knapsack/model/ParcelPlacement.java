package com.bcs2024.knapsack.model;

/**
 * Represents a parcel with specific dimensions and value.
 * Each parcel has a type, length, width, height, and an associated value.
 */
public class ParcelPlacement {
    private Parcel parcel;
    private int x, y, z; // Position in the cargo space

    /**
     * Constructs a new ParcelPlacement with specified parcel, position and orientation.
     *
     * @param parcel The parcel to place.
     * @param x The x-coordinate in the cargo space.
     * @param y The y-coordinate in the cargo space.
     * @param z The z-coordinate in the cargo space.
     */
    public ParcelPlacement(Parcel parcel, int x, int y, int z) {
        this.parcel = parcel;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
