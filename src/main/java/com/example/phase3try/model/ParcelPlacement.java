package com.example.phase3try.model;

public class ParcelPlacement {
    private Parcel parcel;
    private int x, y, z, rotation;

    /**
     * Constructs a new ParcelPlacement with specified parcel, position and orientation.
     *
     * @param parcel The parcel to place.
     * @param x The x-coordinate in the cargo space.
     * @param y The y-coordinate in the cargo space.
     * @param z The z-coordinate in the cargo space.
     */
    public ParcelPlacement(Parcel parcel, int x, int y, int z, int rotation) {
        this.parcel = parcel;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
    }

    public int getRotation() {
        return this.rotation;
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

    /**
     * Checks if the parcel placement, including its orientation, is valid for the cargo space.
     *
     * @param cargoSpace The cargo space to check for.
     * @return true if the parcel placement is valid, false otherwise.
     */
//    public boolean isValidForCargoSpace(CargoSpace cargoSpace) {
//        return cargoSpace.canPlace(this);
//    }
}
