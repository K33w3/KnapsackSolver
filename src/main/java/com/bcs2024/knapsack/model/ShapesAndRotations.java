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
     * Each rotation is represented as a 3D boolean array.
     *
     * @return An array of 3D boolean arrays representing all rotations of 'L' parcel.
     */
    public static boolean[][][][] getL(){
        boolean[][][][] LInt =
                {{{{true}, {true}, {true}, {true}}, {{true}, {false}, {false}, {false}}},
                {{{true, true, true, true}}, {{true, false, false, false}}},
                {{{true}, {true}, {true}, {true}}, {{false}, {false}, {false}, {true}}},
                {{{true, true, true, true}}, {{false, false, false, true}}},
                {{{true}, {false}, {false}, {false}}, {{true}, {true}, {true}, {true}}},
                {{{true, false, false, false}}, {{true, true, true, true}}},
                {{{false}, {false}, {false}, {true}}, {{true}, {true}, {true}, {true}}},
                {{{false, false, false, true}}, {{true, true, true, true}}},
                {{{true, true}, {true, false}, {true, false}, {true, false}}},
                {{{true, false, false, false}, {true, true, true, true}}},
                {{{false, true}, {false, true}, {false, true}, {true, true}}},
                {{{true, true, true, true}, {false, false, false, true}}},
                {{{true, true}, {false, true}, {false, true}, {false, true}}},
                {{{true, true, true, true}, {true, false, false, false}}},
                {{{true, false}, {true, false}, {true, false}, {true, true}}},
                {{{false, false, false, true}, {true, true, true, true}}},
                {{{true}, {false}}, {{true}, {false}}, {{true}, {false}}, {{true}, {true}}},
                {{{true, false}}, {{true, false}}, {{true, false}}, {{true, true}}},
                {{{false}, {true}}, {{false}, {true}}, {{false}, {true}}, {{true}, {true}}},
                {{{false, true}}, {{false, true}}, {{false, true}}, {{true, true}}},
                {{{true}, {true}}, {{true}, {false}}, {{true}, {false}}, {{true}, {false}}},
                {{{true, true}}, {{true, false}}, {{true, false}}, {{true, false}}},
                {{{true}, {true}}, {{false}, {true}}, {{false}, {true}}, {{false}, {true}}},
                {{{true, true}}, {{false, true}}, {{false, true}}, {{false, true}}}};

        return LInt;
    }

    /**
     * Returns all rotations for 'P' type parcel.
     * Each rotation is represented as a 3D boolean array.
     *
     * @return An array of 3D boolean arrays representing all rotations of 'P' parcel.
     */
    public static boolean[][][][] getP(){
        boolean[][][][] PInt =
                {{{{true, true}, {true, true}, {true, false}}},
                {{{true, true, false}, {true, true, true}}},
                {{{false, true}, {true, true}, {true, true}}},
                {{{true, true, true}, {false, true, true}}},
                {{{true, true}, {true, true}, {false, true}}},
                {{{true, true, true}, {true, true, false}}},
                {{{true, false}, {true, true}, {true, true}}},
                {{{false, true, true}, {true, true, true}}},
                {{{true}, {true}, {false}}, {{true}, {true}, {true}}},
                {{{true, true, false}}, {{true, true, true}}},
                {{{false}, {true}, {true}}, {{true}, {true}, {true}}},
                {{{false, true, true}}, {{true, true, true}}},
                {{{true}, {true}, {true}}, {{true}, {true}, {false}}},
                {{{true, true, true}}, {{true, true, false}}},
                {{{true}, {true}, {true}}, {{false}, {true}, {true}}},
                {{{true, true, true}}, {{false, true, true}}},
                {{{true}, {false}}, {{true}, {true}}, {{true}, {true}}},
                {{{true, false}}, {{true, true}}, {{true, true}}},
                {{{false}, {true}}, {{true}, {true}}, {{true}, {true}}},
                {{{false, true}}, {{true, true}}, {{true, true}}},
                {{{true}, {true}}, {{true}, {true}}, {{false}, {true}}},
                {{{true, true}}, {{true, true}}, {{false, true}}},
                {{{true}, {true}}, {{true}, {true}}, {{true}, {false}}},
                {{{true, true}}, {{true, true}}, {{true, false}}}};

        return PInt;
    }

    /**
     * Returns all rotations for 'T' type parcel.
     * Each rotation is represented as a 3D boolean array.
     *
     * @return An array of 3D boolean arrays representing all rotations of 'T' parcel.
     */
    public static boolean[][][][] getT(){
        boolean[][][][] TInt =
            {{{{true, true, true}, {false, true, false}, {false, true, false}}},
            {{{true, false, false}, {true, true, true}, {true, false, false}}},
            {{{false, true, false}, {false, true, false}, {true, true, true}}},
            {{{false, false, true}, {true, true, true}, {false, false, true}}},
            {{{true}, {false}, {false}}, {{true}, {true}, {true}}, {{true}, {false}, {false}}},
            {{{true, false, false}}, {{true, true, true}}, {{true, false, false}}},
            {{{false}, {false}, {true}}, {{true}, {true}, {true}}, {{false}, {false}, {true}}},
            {{{false, false, true}}, {{true, true, true}}, {{false, false, true}}},
            {{{false}, {true}, {false}}, {{false}, {true}, {false}}, {{true}, {true}, {true}}},
            {{{false, true, false}}, {{false, true, false}}, {{true, true, true}}},
            {{{true}, {true}, {true}}, {{false}, {true}, {false}}, {{false}, {true}, {false}}},
            {{{true, true, true}}, {{false, true, false}}, {{false, true, false}}}};

        return TInt;
    }

    /**
     * Returns all rotations for 'A' type parcel.
     * Each rotation is represented as a 3D boolean array.
     *
     * @return An array of 3D boolean arrays representing all rotations of 'A' parcel.
     */
    public static boolean[][][][] getA(){
        boolean[][][][] AInt =
                {{{{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true}}},
                {{{true,true},{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true},{true,true}}},
                {{{true,true},{true,true}},{{true,true},{true,true}},{{true,true},{true,true}},{{true,true},{true,true}}}};

        return AInt;
    }

    /**
     * Returns all rotations for 'B' type parcel.
     * Each rotation is represented as a 3D boolean array.
     *
     * @return An array of 3D boolean arrays representing all rotations of 'B' parcel.
     */
    public static boolean[][][][] getB(){
        boolean[][][][] BInt =
                {{{{true,true,true,true},{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true},{true,true,true,true}}},
                {{{true,true,true},{true,true,true},{true,true,true},{true,true,true}},{{true,true,true},{true,true,true},{true,true,true},{true,true,true}}},
                {{{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true}}},
                {{{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true}}},
                {{{true,true,true},{true,true,true}},{{true,true,true},{true,true,true}},{{true,true,true},{true,true,true}},{{true,true,true},{true,true,true}}},
                {{{true,true},{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true},{true,true}}}};

        return BInt;
    }

    /**
     * Returns all rotations for 'C' type parcel.
     * Each rotation is represented as a 3D boolean array.
     *
     * @return An array of 3D boolean arrays representing all rotations of 'C' parcel.
     */
    public static boolean[][][][] getC(){
        boolean[][][][] CInt =
                {{{{true,true,true},{true,true,true},{true,true,true}},{{true,true,true},{true,true,true},{true,true,true}},{{true,true,true},{true,true,true},{true,true,true}}}};
        return CInt;
    }

    /**
     * Retrieves the rotations for a given parcel type.
     *
     * @param parcelType The type of the parcel for which rotations are required.
     * @return An array of 3D boolean arrays representing all rotations of the specified parcel type.
     * @throws IllegalArgumentException If the parcel type is invalid.
     */
    public static boolean[][][][] getRotations(String parcelType) {
        return switch (parcelType) {
            case "L" -> getL();
            case "P" -> getP();
            case "T" -> getT();

            default -> throw new IllegalArgumentException("Invalid parcel type");
        };
    }
}
