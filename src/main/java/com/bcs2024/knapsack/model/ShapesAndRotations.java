package com.bcs2024.knapsack.model;

/**
 * This class provides static methods to retrieve all possible rotations (orientations)
 * of different types of parcels. Each parcel type has its unique set of rotations
 * represented as 3D boolean arrays. Each array element indicates whether a part
 * of the parcel occupies a specific position in the given rotation.
 */
public class ShapesAndRotations {

    /**
     * Returns all rotations for 'L' type parcel.
     * Each rotation is represented as a 3D int array.
     *
     * @return An array of 3D int arrays representing all rotations of 'L' parcel.
     */
    public static int[][][] getL(int rotation) {
        int[][][][] shapeL =
                {{{{1}, {1}, {1}, {1}}, {{1}, {0}, {0}, {0}}},
                {{{1, 1, 1, 1}}, {{1, 0, 0, 0}}},
                {{{1}, {1}, {1}, {1}}, {{0}, {0}, {0}, {1}}},
                {{{1, 1, 1, 1}}, {{0, 0, 0, 1}}},
                {{{1}, {0}, {0}, {0}}, {{1}, {1}, {1}, {1}}},
                {{{1, 0, 0, 0}}, {{1, 1, 1, 1}}},
                {{{0}, {0}, {0}, {1}}, {{1}, {1}, {1}, {1}}},
                {{{0, 0, 0, 1}}, {{1, 1, 1, 1}}},
                {{{1, 1}, {1, 0}, {1, 0}, {1, 0}}},
                {{{1, 0, 0, 0}, {1, 1, 1, 1}}},
                {{{0, 1}, {0, 1}, {0, 1}, {1, 1}}},
                {{{1, 1, 1, 1}, {0, 0, 0, 1}}},
                {{{1, 1}, {0, 1}, {0, 1}, {0, 1}}},
                {{{1, 1, 1, 1}, {1, 0, 0, 0}}},
                {{{1, 0}, {1, 0}, {1, 0}, {1, 1}}},
                {{{0, 0, 0, 1}, {1, 1, 1, 1}}},
                {{{1}, {0}}, {{1}, {0}}, {{1}, {0}}, {{1}, {1}}},
                {{{1, 0}}, {{1, 0}}, {{1, 0}}, {{1, 1}}},
                {{{0}, {1}}, {{0}, {1}}, {{0}, {1}}, {{1}, {1}}},
                {{{0, 1}}, {{0, 1}}, {{0, 1}}, {{1, 1}}},
                {{{1}, {1}}, {{1}, {0}}, {{1}, {0}}, {{1}, {0}}},
                {{{1, 1}}, {{1, 0}}, {{1, 0}}, {{1, 0}}},
                {{{1}, {1}}, {{0}, {1}}, {{0}, {1}}, {{0}, {1}}},
                {{{1, 1}}, {{0, 1}}, {{0, 1}}, {{0, 1}}}};

        return shapeL[rotation];
    }

    /**
     * Returns all rotations for 'P' type parcel.
     * Each rotation is represented as a 3D int array.
     *
     * @return An array of 3D int arrays representing all rotations of 'P' parcel.
     */
    public static int[][][] getP(int rotation) {
        int[][][][] shapeP =
                {{{{1, 1}, {1, 1}, {1, 0}}},
                {{{1, 1, 0}, {1, 1, 1}}},
                {{{0, 1}, {1, 1}, {1, 1}}},
                {{{1, 1, 1}, {0, 1, 1}}},
                {{{1, 1}, {1, 1}, {0, 1}}},
                {{{1, 1, 1}, {1, 1, 0}}},
                {{{1, 0}, {1, 1}, {1, 1}}},
                {{{0, 1, 1}, {1, 1, 1}}},
                {{{1}, {1}, {0}}, {{1}, {1}, {1}}},
                {{{1, 1, 0}}, {{1, 1, 1}}},
                {{{0}, {1}, {1}}, {{1}, {1}, {1}}},
                {{{0, 1, 1}}, {{1, 1, 1}}},
                {{{1}, {1}, {1}}, {{1}, {1}, {0}}},
                {{{1, 1, 1}}, {{1, 1, 0}}},
                {{{1}, {1}, {1}}, {{0}, {1}, {1}}},
                {{{1, 1, 1}}, {{0, 1, 1}}},
                {{{1}, {0}}, {{1}, {1}}, {{1}, {1}}},
                {{{1, 0}}, {{1, 1}}, {{1, 1}}},
                {{{0}, {1}}, {{1}, {1}}, {{1}, {1}}},
                {{{0, 1}}, {{1, 1}}, {{1, 1}}},
                {{{1}, {1}}, {{1}, {1}}, {{0}, {1}}},
                {{{1, 1}}, {{1, 1}}, {{0, 1}}},
                {{{1}, {1}}, {{1}, {1}}, {{1}, {0}}},
                {{{1, 1}}, {{1, 1}}, {{1, 0}}}};

        return shapeP[rotation];
    }

    /**
     * Returns all rotations for 'T' type parcel.
     * Each rotation is represented as a 3D int array.
     *
     * @return An array of 3D int arrays representing all rotations of 'T' parcel.
     */
    public static int[][][] getT(int rotation) {
        int[][][][] shapeT =
                {{{{1, 1, 1}, {0, 1, 0}, {0, 1, 0}}},
                {{{1, 0, 0}, {1, 1, 1}, {1, 0, 0}}},
                {{{0, 1, 0}, {0, 1, 0}, {1, 1, 1}}},
                {{{0, 0, 1}, {1, 1, 1}, {0, 0, 1}}},
                {{{1}, {0}, {0}}, {{1}, {1}, {1}}, {{1}, {0}, {0}}},
                {{{1, 0, 0}}, {{1, 1, 1}}, {{1, 0, 0}}},
                {{{0}, {0}, {1}}, {{1}, {1}, {1}}, {{0}, {0}, {1}}},
                {{{0, 0, 1}}, {{1, 1, 1}}, {{0, 0, 1}}},
                {{{0, 1}, {0, 1}, {0, 1}}, {{0, 1}, {0, 1}, {1, 1}}},
                {{{0, 1, 0}, {0, 1, 0}, {1, 1, 1}}},
                {{{1, 1}, {1, 0}, {1, 0}}, {{1, 1}, {0, 1}, {0, 1}}},
                {{{1, 1, 1}, {0, 1, 0}, {0, 1, 0}}},
                {{{1, 1}, {0, 1}, {0, 1}}, {{1, 1}, {1, 0}, {1, 0}}}};

        return shapeT[rotation];
    }

    /**
     * Returns all rotations for 'A' type parcel.
     * Each rotation is represented as a 3D int array.
     *
     * @return An array of 3D int arrays representing all rotations of 'A' parcel.
     */
    public static int[][][] getA(int rotation) {
        int[][][][] shapeA =
                {{{{1, 1, 1, 1}, {1, 1, 1, 1}}, {{1, 1, 1, 1}, {1, 1, 1, 1}}},
                {{{1, 1}, {1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}, {1, 1}}},
                {{{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}}};

        return shapeA[rotation];
    }

    /**
     * Returns all rotations for 'B' type parcel.
     * Each rotation is represented as a 3D int array.
     *
     * @return An array of 3D int arrays representing all rotations of 'B' parcel.
     */
    public static int[][][] getB(int rotation) {
        int[][][][] shapeB =
                {{{{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}}, {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}}},
                {{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}}},
                {{{1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}}},
                {{{1, 1, 1, 1}, {1, 1, 1, 1}}, {{1, 1, 1, 1}, {1, 1, 1, 1}}, {{1, 1, 1, 1}, {1, 1, 1, 1}}},
                {{{1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}}},
                {{{1, 1}, {1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}, {1, 1}}}};

        return shapeB[rotation];
    }

    /**
     * Returns all rotations for 'C' type parcel.
     * Each rotation is represented as a 3D int array.
     *
     * @return An array of 3D int arrays representing all rotations of 'C' parcel.
     */
    public static int[][][] getC (int rotation) {
        int[][][][] shapeC =
                {{{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}}};
        return shapeC[rotation];
    }

    /**
     * Retrieves the rotations for a given parcel type.
     *
     * @param parcelType The type of the parcel for which rotations are required.
     * @return An array of 3D int arrays representing all rotations of the specified parcel type.
     * @throws IllegalArgumentException If the parcel type is invalid.
     */
    public static int[][][] getShape(String parcelType, int rotation) {
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
