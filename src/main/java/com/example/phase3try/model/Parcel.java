package com.example.phase3try.model;

public class Parcel {
    private double length, width, height;
    private int value;
    private String type;
    private int[][][] shape;
    private ShapesAndRotations shapes;
    private int id;

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
                this.id = 1;
            }
            case "B" -> {
                this.length = 1.0;
                this.width = 1.5;
                this.height = 2.0;
                this.value = 4;
                this.id = 2;
            }
            case "C" -> {
                this.length = 1.5;
                this.width = 1.5;
                this.height = 1.5;
                this.value = 5;
                this.id = 3;
            }
            case "L" -> {
                this.value = 3;
                this.id = 4;
            }
            case "P" -> {
                this.value = 4;
                this.id = 5;
            }
            case "T" -> {
                this.value = 5;
                this.id = 6;
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

    public int getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public int getId() { return this.id; }
}
