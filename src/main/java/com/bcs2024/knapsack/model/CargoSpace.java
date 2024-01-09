package com.bcs2024.knapsack.model;

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

        // Adjust parcel dimensions based on orientation
        int[] adjustedDimensions = getAdjustedDimensions(parcel, orientation);
        int parcelLength = adjustedDimensions[0];
        int parcelWidth = adjustedDimensions[1];
        int parcelHeight = adjustedDimensions[2];

        int startX = placement.getX();
        int startY = placement.getY();
        int startZ = placement.getZ();

        for (int x = startX; x < startX + parcelLength; x++) {
            for (int y = startY; y < startY + parcelWidth; y++) {
                for (int z = startZ; z < startZ + parcelHeight; z++) {
                    if (x >= this.length || y >= this.width || z >= this.height || occupied[x][y][z]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int[] getAdjustedDimensions(Parcel parcel, int orientation) {
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
    }

    /**
     * Marks a specific area of the cargo space as occupied based on the given parcel placement.
     * This method uses the coordinates and dimensions from the ParcelPlacement object to determine
     * the space that the parcel will occupy. It updates the occupied array accordingly.
     *
     * @param placement The ParcelPlacement object representing the parcel's placement in the cargo space.
     */
    public void occupySpace(ParcelPlacement placement) {
        int x = placement.getX();
        int y = placement.getY();
        int z = placement.getZ();
        Parcel parcel = placement.getParcel();
        int orientation = placement.getOrientation();

        // Adjust parcel dimensions based on orientation
        int[] adjustedDimensions = getAdjustedDimensions(parcel, orientation);
        int parcelLength = adjustedDimensions[0];
        int parcelWidth = adjustedDimensions[1];
        int parcelHeight = adjustedDimensions[2];

        for (int i = x; i < x + parcelLength; i++) {
            for (int j = y; j < y + parcelWidth; j++) {
                for (int k = z; k < z + parcelHeight; k++) {
                    occupied[i][j][k] = true;
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
