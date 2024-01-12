package com.bcs2024.knapsack.model;

public class Parcel {

  private double length, width, height, value;
  private String type;
  private int[][][] shape;
  private ShapesAndRotations shapes;

  public Parcel(String type) {
    shapes = new ShapesAndRotations();
    this.type = type;
    this.shape = shapes.getShape(type, 0);
    initializeParcel();
  }

  private void initializeParcel() {
    switch (type) {
      case "A" -> {
        this.length = 1.0;
        this.width = 1.0;
        this.height = 2.0;
        this.value = 3;
      }
      case "B" -> {
        this.length = 1.0;
        this.width = 1.5;
        this.height = 2.0;
        this.value = 4;
      }
      case "C" -> {
        this.length = 1.5;
        this.width = 1.5;
        this.height = 1.5;
        this.value = 5;
      }
      case "L", "P", "T" -> {
        this.length = 0.5;
        this.width = 0.5;
        this.height = 0.5;
        this.value = 0;
      }
      default -> System.out.println("Invalid type");
    }
  }

  public double getVolume(String type) {
    return this.length * this.height * this.width;
  }

  public int[][][] getShape() {
    return shape;
  }

  public double getLength() {
    return length;
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  public double getValue() {
    return value;
  }

  public String getType() {
    return type;
  }
}
