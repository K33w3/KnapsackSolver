package com.bcs2024.knapsack.model;

import java.util.Arrays;

/**
 * Represents the cargo space with dimensions and tracking for occupied space.
 */
public class CargoSpace {
    private double length, width, height;
    private final boolean[][][] occupied;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Constructs a new CargoSpace with specified dimensions.
     *
     * @param length The length of the cargo space.
     * @param width  The width of the cargo space.
     * @param height The height of the cargo space.
     */
    public CargoSpace(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.occupied = new boolean[(int) length][(int) width][(int) height];
    }

    /**
     * Checks if a specific area of the cargo space is available to occupy based on the given parcel placement.
     *
     * @param placement The ParcelPlacement object representing the parcel's placement in the cargo space.
     * @return true if the space is available, false otherwise.
     */
    public boolean isSpaceAvailable(ParcelPlacement placement) {
        Parcel parcel = placement.getParcel();
        int orientation = placement.getOrientation();

        // Get the shape of the parcel based on its orientation
        boolean[][][] parcelShape = getParcelShape(parcel, orientation);

        int startX = placement.getX();
        int startY = placement.getY();
        int startZ = placement.getZ();

        for (int i = 0; i < parcelShape.length; i++) {
            for (int j = 0; j < parcelShape[i].length; j++) {
                for (int k = 0; k < parcelShape[i][j].length; k++) {
                    if (parcelShape[i][j][k]) {
                        int x = startX + i;
                        int y = startY + j;
                        int z = startZ + k;
                        if (x >= this.length || y >= this.width || z >= this.height || occupied[x][y][z]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    private boolean[][][] getParcelShape(Parcel parcel, int orientation) {
        if (parcel.getShape() != null) {
            // If parcel has a complex shape
            boolean[][][][] rotations = ShapesAndRotations.getRotations(parcel.getType());
            return rotations[orientation % rotations.length];
        } else {
            // If parcel is a regular shape, create a filled 3D array based on its dimensions
            boolean[][][] shape = new boolean[(int)parcel.getLength()][(int)parcel.getWidth()][(int)parcel.getHeight()];

            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    Arrays.fill(shape[i][j], true);
                }
            }
            return shape;
        }
    }


    /*public int[] getAdjustedDimensions(Parcel parcel, int orientation) {
        boolean[][][][] rotations = switch (parcel.getType()) {
            case "L" -> ShapesAndRotations.getL();
            case "P" -> ShapesAndRotations.getP();
            case "T" -> ShapesAndRotations.getT();
            case "A" -> ShapesAndRotations.getA();
            case "B" -> ShapesAndRotations.getB();
            case "C" -> ShapesAndRotations.getC();
            default -> throw new IllegalArgumentException("Invalid parcel type");
        };

        // Select the correct shape and its rotations based on the parcel type

        // Use the orientation to select the correct rotation
        boolean[][][] selectedRotation = rotations[orientation % rotations.length];

        // Calculate dimensions based on the selected rotation
        int length = selectedRotation.length;
        int width = selectedRotation[0].length;
        int height = selectedRotation[0][0].length;

        return new int[]{length, width, height};
    }*/

    /**
     * Marks a specific area of the cargo space as occupied based on the given parcel placement.
     * This method uses the coordinates and dimensions from the ParcelPlacement object to determine
     * the space that the parcel will occupy. It updates the occupied array accordingly.
     *
     * @param placement The ParcelPlacement object representing the parcel's placement in the cargo space.
     */
    public void occupySpace(ParcelPlacement placement) {
        Parcel parcel = placement.getParcel();
        int orientation = placement.getOrientation();

        // Get the shape of the parcel based on its orientation
        boolean[][][] parcelShape = getParcelShape(parcel, orientation);

        int startX = placement.getX();
        int startY = placement.getY();
        int startZ = placement.getZ();

        for (int i = 0; i < parcelShape.length; i++) {
            for (int j = 0; j < parcelShape[i].length; j++) {
                for (int k = 0; k < parcelShape[i][j].length; k++) {
                    if (parcelShape[i][j][k]) {
                        int x = startX + i;
                        int y = startY + j;
                        int z = startZ + k;
                        occupied[x][y][z] = true;
                    }
                }
            }
        }
    }

    public void printOccupiedSpacePositions() {
        for (int i = 0; i < occupied.length; i++) {
            for (int j = 0; j < occupied[i].length; j++) {
                for (int k = 0; k < occupied[i][j].length; k++) {
                    System.out.println("Position (" + i + ", " + j + ", " + k + "): " + (occupied[i][j][k] ? "Occupied" : "Free"));
                }
            }
        }
    }
}
