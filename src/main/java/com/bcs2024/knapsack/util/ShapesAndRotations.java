package com.bcs2024.knapsack.util;

/**
 * ShapesAndRotations manages the predefined shapes and rotations of parcels
 * used in the knapsack problem. It provides methods to access these shapes
 * and their rotations, based on parcel type.
 */
public class ShapesAndRotations {
    public int[][][][] shapeA =
            {
                    {{{1, 1, 1, 1}, {1, 1, 1, 1}}, {{1, 1, 1, 1}, {1, 1, 1, 1}}},
                    {{{1, 1}, {1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}, {1, 1}}},
                    {{{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}}
            };
    public int[][][][] shapeB =
            {
                    {{{2, 2}, {2, 2}, {2, 2}}, {{2, 2}, {2, 2}, {2, 2}}, {{2, 2}, {2, 2}, {2, 2}}, {{2, 2}, {2, 2}, {2, 2}}},
                    {{{2, 2}, {2, 2}, {2, 2}, {2, 2}}, {{2, 2}, {2, 2}, {2, 2}, {2, 2}}, {{2, 2}, {2, 2}, {2, 2}, {2, 2}}},
                    {{{2, 2, 2}, {2, 2, 2}}, {{2, 2, 2}, {2, 2, 2}}, {{2, 2, 2}, {2, 2, 2}}, {{2, 2, 2}, {2, 2, 2}}},
                    {{{2, 2, 2}, {2, 2, 2}, {2, 2, 2}, {2, 2, 2}}, {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}, {2, 2, 2}}},
                    {{{2, 2, 2, 2}, {2, 2, 2, 2}}, {{2, 2, 2, 2}, {2, 2, 2, 2}}, {{2, 2, 2, 2}, {2, 2, 2, 2}}},
                    {{{2, 2, 2, 2}, {2, 2, 2, 2}, {2, 2, 2, 2}}, {{2, 2, 2, 2}, {2, 2, 2, 2}, {2, 2, 2, 2}}}
            };
    public int[][][][] shapeC =
            {
                    {{{3, 3, 3}, {3, 3, 3}, {3, 3, 3}}, {{3, 3, 3}, {3, 3, 3}, {3, 3, 3}}, {{3, 3, 3}, {3, 3, 3}, {3, 3, 3}}}
            };

    public int[][][][] shapeL =
            {{{{4}, {4}, {4}, {4}}, {{4}, {0}, {0}, {0}}},
                    {{{4, 4, 4, 4}}, {{4, 0, 0, 0}}},
                    {{{4}, {4}, {4}, {4}}, {{0}, {0}, {0}, {4}}},
                    {{{4, 4, 4, 4}}, {{0, 0, 0, 4}}},
                    {{{4}, {0}, {0}, {0}}, {{4}, {4}, {4}, {4}}},
                    {{{4, 0, 0, 0}}, {{4, 4, 4, 4}}},
                    {{{0}, {0}, {0}, {4}}, {{4}, {4}, {4}, {4}}},
                    {{{0, 0, 0, 4}}, {{4, 4, 4, 4}}},
                    {{{4, 4}, {4, 0}, {4, 0}, {4, 0}}},
                    {{{4, 0, 0, 0}, {4, 4, 4, 4}}},
                    {{{0, 4}, {0, 4}, {0, 4}, {4, 4}}},
                    {{{4, 4, 4, 4}, {0, 0, 0, 4}}},
                    {{{4, 4}, {0, 4}, {0, 4}, {0, 4}}},
                    {{{4, 4, 4, 4}, {4, 0, 0, 0}}},
                    {{{4, 0}, {4, 0}, {4, 0}, {4, 4}}},
                    {{{0, 0, 0, 4}, {4, 4, 4, 4}}},
                    {{{4}, {0}}, {{4}, {0}}, {{4}, {0}}, {{4}, {4}}},
                    {{{4, 0}}, {{4, 0}}, {{4, 0}}, {{4, 4}}},
                    {{{0}, {4}}, {{0}, {4}}, {{0}, {4}}, {{4}, {4}}},
                    {{{0, 4}}, {{0, 4}}, {{0, 4}}, {{4, 4}}},
                    {{{4}, {4}}, {{4}, {0}}, {{4}, {0}}, {{4}, {0}}},
                    {{{4, 4}}, {{4, 0}}, {{4, 0}}, {{4, 0}}},
                    {{{4}, {4}}, {{0}, {4}}, {{0}, {4}}, {{0}, {4}}},
                    {{{4, 4}}, {{0, 4}}, {{0, 4}}, {{0, 4}}}};

    public int[][][][] shapeP =
            {{{{5, 5}, {5, 5}, {5, 0}}},
                    {{{5, 5, 0}, {5, 5, 5}}},
                    {{{0, 5}, {5, 5}, {5, 5}}},
                    {{{5, 5, 5}, {0, 5, 5}}},
                    {{{5, 5}, {5, 5}, {0, 5}}},
                    {{{5, 5, 5}, {5, 5, 0}}},
                    {{{5, 0}, {5, 5}, {5, 5}}},
                    {{{0, 5, 5}, {5, 5, 5}}},
                    {{{5}, {5}, {0}}, {{5}, {5}, {5}}},
                    {{{5, 5, 0}}, {{5, 5, 5}}},
                    {{{0}, {5}, {5}}, {{5}, {5}, {5}}},
                    {{{0, 5, 5}}, {{5, 5, 5}}},
                    {{{5}, {5}, {5}}, {{5}, {5}, {0}}},
                    {{{5, 5, 5}}, {{5, 5, 0}}},
                    {{{5}, {5}, {5}}, {{0}, {5}, {5}}},
                    {{{5, 5, 5}}, {{0, 5, 5}}},
                    {{{5}, {0}}, {{5}, {5}}, {{5}, {5}}},
                    {{{5, 0}}, {{5, 5}}, {{5, 5}}},
                    {{{0}, {5}}, {{5}, {5}}, {{5}, {5}}},
                    {{{0, 5}}, {{5, 5}}, {{5, 5}}},
                    {{{5}, {5}}, {{5}, {5}}, {{0}, {5}}},
                    {{{5, 5}}, {{5, 5}}, {{0, 5}}},
                    {{{5}, {5}}, {{5}, {5}}, {{5}, {0}}},
                    {{{5, 5}}, {{5, 5}}, {{5, 0}}}};

    public int[][][][] shapeT =
            {{{{6, 6, 6}, {0, 6, 0}, {0, 6, 0}}},
                    {{{6, 0, 0}, {6, 6, 6}, {6, 0, 0}}},
                    {{{0, 6, 0}, {0, 6, 0}, {6, 6, 6}}},
                    {{{0, 0, 6}, {6, 6, 6}, {0, 0, 6}}},
                    {{{6}, {0}, {0}}, {{6}, {6}, {6}}, {{6}, {0}, {0}}},
                    {{{6, 0, 0}}, {{6, 6, 6}}, {{6, 0, 0}}},
                    {{{0}, {0}, {6}}, {{6}, {6}, {6}}, {{0}, {0}, {6}}},
                    {{{0, 0, 6}}, {{6, 6, 6}}, {{0, 0, 6}}},
                    {{{0, 6}, {0, 6}, {0, 6}}, {{0, 6}, {0, 6}, {6, 6}}},
                    {{{0, 6, 0}, {0, 6, 0}, {6, 6, 6}}},
                    {{{6, 6}, {6, 0}, {6, 0}}, {{6, 6}, {0, 6}, {0, 6}}},
                    {{{6, 6, 6}, {0, 6, 0}, {0, 6, 0}}},
                    {{{6, 6}, {0, 6}, {0, 6}}, {{6, 6}, {6, 0}, {6, 0}}}};

    public int[][][] getL(final int rotation) {
        return shapeL[rotation];
    }

    public int[][][] getP(final int rotation) {
        return shapeP[rotation];
    }

    public int[][][] getT(final int rotation) {
        return shapeT[rotation];
    }

    public int[][][] getA(final int rotation) {
        return shapeA[rotation];
    }

    public int[][][] getB(final int rotation) {
        return shapeB[rotation];
    }

    public int[][][] getC(final int rotation) {
        return shapeC[rotation];
    }

    /**
     * Retrieves the number of rotations available for a given parcel type.
     *
     * @param type The type of the parcel (e.g., "A", "B", "C", "L", "P", "T").
     * @return The number of available rotations for the specified parcel type.
     */
    public int rotationNum(final String type) {
        return switch (type) {
            case "A" -> shapeA.length;
            case "B" -> shapeB.length;
            case "C" -> shapeC.length;
            case "L" -> shapeL.length;
            case "P" -> shapeP.length;
            case "T" -> shapeT.length;
            default -> 0;
        };
    }

    /**
     * Retrieves a specific shape and rotation for a given parcel type.
     *
     * @param parcelType The type of the parcel (e.g., "A", "B", "C", "L", "P", "T").
     * @param rotation   The rotation index of the shape.
     * @return The 3D array representing the shape of the parcel in the specified rotation.
     */
    public int[][][] getShape(final String parcelType, final int rotation) {
        return switch (parcelType) {
            case "L" -> getL(rotation);
            case "P" -> getP(rotation);
            case "T" -> getT(rotation);
            case "A" -> getA(rotation);
            case "B" -> getB(rotation);
            case "C" -> getC(rotation);

            default -> throw new IllegalArgumentException("Invalid parcel type");
        };
    }
}
