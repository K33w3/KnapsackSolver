package com.bcs2024.knapsack.model;

import javafx.scene.paint.Color;

public class ParcelPlacement extends Parcel {
  private Parcel parcel;
  private int x, y, z; // Position in the cargo space

  private Color color;

  /**
   * Constructs a new ParcelPlacement with specified parcel, position, and orientation.
   *
   * @param parcel      The parcel to place.
   * @param x           The x-coordinate in the cargo space.
   * @param y           The y-coordinate in the cargo space.
   * @param z           The z-coordinate in the cargo space.
   */
  public ParcelPlacement(Parcel parcel, int x, int y, int z) {
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

  public Color getColor() {
    return color;
  }
}
