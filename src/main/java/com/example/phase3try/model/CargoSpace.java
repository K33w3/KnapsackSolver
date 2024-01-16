package com.example.phase3try.model;

public class CargoSpace {
    public static final double length = 16.5;
    public static final double width = 2.5;
    public static final double height = 4;
    private int[][][] cargoMatrix;
    private ShapesAndRotations shapes;

    public CargoSpace() {
        shapes = new ShapesAndRotations();
        int x = (int) (width * 2);
        int y = (int) (height * 2);
        int z = (int) (length * 2);
        cargoMatrix = new int[x][y][z];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    cargoMatrix[i][j][k] = -1;
                }
            }
        }
    }

    public boolean canPlace(int[][][] shape, int startX, int startY, int startZ, int[][][] destination) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        int x = startX + k;
                        int y = startY + j;
                        int z = startZ + i;
                        if (x >= (int) (this.width * 2) || y >= (int) (this.height * 2) || z >= (int) (this.length * 2) || destination[x][y][z] != -1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void placeParcel(int[][][] shape, int startX, int startY, int startZ, int[][][] destination) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] != 0) {
                        int x = startX + k;
                        int y = startY + j;
                        int z = startZ + i;
                        destination[x][y][z] = shape[i][j][k];
                    }
                }
            }
        }
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

    public int[][][] getCargoMatric() {
        return this.cargoMatrix;
    }
}
