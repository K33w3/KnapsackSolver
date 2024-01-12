package com.bcs2024.knapsack.model;

import javafx.scene.shape.Box;
import javafx.scene.transform.Transform;

import java.util.ArrayList;
import java.util.List;

public class ParcelPlacement extends Parcel {
    private Parcel parcel;
    private int x, y, z; // Position in the cargo space
    private int orientation; // Representing orientation

    /**
     * Constructs a new ParcelPlacement with specified parcel, position, and orientation.
     *
     * @param parcel      The parcel to place.
     * @param x           The x-coordinate in the cargo space.
     * @param y           The y-coordinate in the cargo space.
     * @param z           The z-coordinate in the cargo space.
     * @param orientation The orientation of the parcel.
     */
    public ParcelPlacement(Parcel parcel, int x, int y, int z, int orientation) {
        super(parcel.getType());
        this.parcel = parcel;
        this.x = x;
        this.y = y;
        this.z = z;
        this.orientation = orientation;
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

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
