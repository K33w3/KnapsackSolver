package com.bcs2024.knapsack.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides a method to compute all possible rotations (orientations)
 * of a given 3D boolean matrix representing a pentomino shape.
 */
public class PentominoRotations {

    public static void main(String[] args) {
        boolean[][][] shape = {{{true}, {true}, {true}, {true}}, {{true}, {false}, {false}, {false}}};
        System.out.println(Arrays.deepToString(getAllRotations(shape)));
    }
    /**
     * Computes and returns all rotations for a given 3D shape.
     *
     * @param shape A 3D boolean array representing the pentomino shape.
     * @return An array of 3D boolean arrays representing all rotations of the shape.
     */
    public static boolean[][][][] getAllRotations(boolean[][][] shape) {
        // Create a list to store all unique rotations
        List<boolean[][][]> uniqueRotations = new ArrayList<>();

        // Apply rotations around X, Y, and Z axes
        for (int xRot = 0; xRot < 4; xRot++) {
            boolean[][][] xRotatedShape = rotateAroundAxis(shape, xRot, 'X');
            for (int yRot = 0; yRot < 4; yRot++) {
                boolean[][][] yRotatedShape = rotateAroundAxis(xRotatedShape, yRot, 'Y');
                for (int zRot = 0; zRot < 4; zRot++) {
                    boolean[][][] zRotatedShape = rotateAroundAxis(yRotatedShape, zRot, 'Z');
                    if (!containsRotation(uniqueRotations, zRotatedShape)) {
                        uniqueRotations.add(zRotatedShape);
                    }
                }
            }
        }

        // Convert the list of unique rotations to an array
        boolean[][][][] rotationsArray = new boolean[uniqueRotations.size()][][][];
        return uniqueRotations.toArray(rotationsArray);
    }

    private static boolean containsRotation(List<boolean[][][]> rotations, boolean[][][] newRotation) {
        for (boolean[][][] existingRotation : rotations) {
            if (areShapesEqual(existingRotation, newRotation)) {
                return true; // Found an existing rotation that matches the new rotation
            }
        }
        return false; // No matching rotation found
    }

    private static boolean areShapesEqual(boolean[][][] shape1, boolean[][][] shape2) {
        if (shape1.length != shape2.length) return false;
        for (int i = 0; i < shape1.length; i++) {
            if (shape1[i].length != shape2[i].length) return false;
            for (int j = 0; j < shape1[i].length; j++) {
                if (shape1[i][j].length != shape2[i][j].length) return false;
                for (int k = 0; k < shape1[i][j].length; k++) {
                    if (shape1[i][j][k] != shape2[i][j][k]) return false;
                }
            }
        }
        return true; // All corresponding elements are equal
    }

    private static boolean[][][] rotateAroundAxis(boolean[][][] shape, int rotations, char axis) {
        boolean[][][] rotatedShape = shape;
        for (int i = 0; i < rotations; i++) {
            if (axis == 'X') rotatedShape = rotateAroundXAxis(rotatedShape);
            else if (axis == 'Y') rotatedShape = rotateAroundYAxis(rotatedShape);
            else if (axis == 'Z') rotatedShape = rotateAroundZAxis(rotatedShape);
        }
        return rotatedShape;
    }

    private static boolean[][][] rotateAroundXAxis(boolean[][][] shape) {
        int depth = shape.length;
        int rows = shape[0].length;
        int cols = shape[0][0].length;

        boolean[][][] newShape = new boolean[cols][rows][depth]; // New dimensions after rotation

        for (int x = 0; x < depth; x++) {
            for (int y = 0; y < rows; y++) {
                for (int z = 0; z < cols; z++) {
                    newShape[z][y][depth - 1 - x] = shape[x][y][z];
                }
            }
        }

        return newShape;
    }

    private static boolean[][][] rotateAroundYAxis(boolean[][][] shape) {
        int depth = shape.length;
        int rows = shape[0].length;
        int cols = shape[0][0].length;

        boolean[][][] newShape = new boolean[depth][cols][rows]; // New dimensions after rotation

        for (int x = 0; x < depth; x++) {
            for (int y = 0; y < rows; y++) {
                for (int z = 0; z < cols; z++) {
                    newShape[x][z][rows - 1 - y] = shape[x][y][z];
                }
            }
        }

        return newShape;
    }

    private static boolean[][][] rotateAroundZAxis(boolean[][][] shape) {
        int depth = shape.length;
        int rows = shape[0].length;
        int cols = shape[0][0].length;

        boolean[][][] newShape = new boolean[rows][depth][cols]; // New dimensions after rotation

        for (int x = 0; x < depth; x++) {
            for (int y = 0; y < rows; y++) {
                for (int z = 0; z < cols; z++) {
                    newShape[rows - 1 - y][x][z] = shape[x][y][z];
                }
            }
        }

        return newShape;
    }
}
