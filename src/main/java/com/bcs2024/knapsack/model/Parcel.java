package com.bcs2024.knapsack.model;

/**
 * Represents a parcel with specific dimensions and value.
 * Each parcel has a type, length, width, height, and an associated value.
 */
public class Parcel {

  private double length, width, height, value;
  private String type;

  private boolean[][][] shape; // 3D array to represent complex shapes

  // Constructor for parcels (A, B, C, L, P, T)
  public Parcel(final String type, boolean[][][] shape) {
    this.type = type;
    this.shape = shape;
    initializeParcel();
  }

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
      case "L", "P", "T" -> {
        this.length = 0.5;
        this.width = 0.5;
        this.height = 0.5;
        // Set value based on type or other criteria
        this.value = determineValueForComplexParcel();
      }
      default -> System.out.println("Invalid type");
    }
  }

  // TODO create logic for determining value of complex parcels
  private double determineValueForComplexParcel() {
    return 0;
  }

  /**
   * Returns the shape of the parcel.
   *
   * @return The shape of the parcel.
   */
  public boolean[][][] getShape() {
    return shape;
  }

  /**
   * Sets the shape of the parcel.
   *
   * @param shape The new shape of the parcel.
   */
  public void setShape(boolean[][][] shape) {
    this.shape = shape;
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
