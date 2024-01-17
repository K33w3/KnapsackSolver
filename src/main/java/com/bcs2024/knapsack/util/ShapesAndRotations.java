package com.bcs2024.knapsack.util;

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

    public int rotationNum(final String type) {
        switch (type) {
            case "A":
                return shapeA.length;
            case "B":
                return shapeB.length;
            case "C":
                return shapeC.length;
            case "L":
                return shapeL.length;
            case "P":
                return shapeP.length;
            case "T":
                return shapeT.length;
        }
        return 0;
    }

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
