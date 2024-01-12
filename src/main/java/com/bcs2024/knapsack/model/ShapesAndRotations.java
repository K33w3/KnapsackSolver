package com.bcs2024.knapsack.model;

public class ShapesAndRotations {

  public int[][][][] shapeA = {
    { { { 1, 1, 1, 1 }, { 1, 1, 1, 1 } }, { { 1, 1, 1, 1 }, { 1, 1, 1, 1 } } },
    {
      { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } },
      { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } },
    },
    {
      { { 1, 1 }, { 1, 1 } },
      { { 1, 1 }, { 1, 1 } },
      { { 1, 1 }, { 1, 1 } },
      { { 1, 1 }, { 1, 1 } },
    },
  };
  public int[][][][] shapeB = {
    {
      { { 2, 2 }, { 2, 2 }, { 2, 2 } },
      { { 2, 2 }, { 2, 2 }, { 2, 2 } },
      { { 2, 2 }, { 2, 2 }, { 2, 2 } },
      { { 2, 2 }, { 2, 2 }, { 2, 2 } },
    },
    {
      { { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 } },
      { { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 } },
      { { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 } },
    },
    {
      { { 2, 2, 2 }, { 2, 2, 2 } },
      { { 2, 2, 2 }, { 2, 2, 2 } },
      { { 2, 2, 2 }, { 2, 2, 2 } },
      { { 2, 2, 2 }, { 2, 2, 2 } },
    },
    {
      { { 2, 2, 2 }, { 2, 2, 2 }, { 2, 2, 2 }, { 2, 2, 2 } },
      { { 2, 2, 2 }, { 2, 2, 2 }, { 2, 2, 2 }, { 2, 2, 2 } },
    },
    {
      { { 2, 2, 2, 2 }, { 2, 2, 2, 2 } },
      { { 2, 2, 2, 2 }, { 2, 2, 2, 2 } },
      { { 2, 2, 2, 2 }, { 2, 2, 2, 2 } },
    },
    {
      { { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 2, 2, 2, 2 } },
      { { 2, 2, 2, 2 }, { 2, 2, 2, 2 }, { 2, 2, 2, 2 } },
    },
  };
  public int[][][][] shapeC = {
    {
      { { 3, 3, 3 }, { 3, 3, 3 }, { 3, 3, 3 } },
      { { 3, 3, 3 }, { 3, 3, 3 }, { 3, 3, 3 } },
      { { 3, 3, 3 }, { 3, 3, 3 }, { 3, 3, 3 } },
    },
  };

  public int[][][][] shapeL = {
    { { { 1 }, { 1 }, { 1 }, { 1 } }, { { 1 }, { 0 }, { 0 }, { 0 } } },
    { { { 1, 1, 1, 1 } }, { { 1, 0, 0, 0 } } },
    { { { 1 }, { 1 }, { 1 }, { 1 } }, { { 0 }, { 0 }, { 0 }, { 1 } } },
    { { { 1, 1, 1, 1 } }, { { 0, 0, 0, 1 } } },
    { { { 1 }, { 0 }, { 0 }, { 0 } }, { { 1 }, { 1 }, { 1 }, { 1 } } },
    { { { 1, 0, 0, 0 } }, { { 1, 1, 1, 1 } } },
    { { { 0 }, { 0 }, { 0 }, { 1 } }, { { 1 }, { 1 }, { 1 }, { 1 } } },
    { { { 0, 0, 0, 1 } }, { { 1, 1, 1, 1 } } },
    { { { 1, 1 }, { 1, 0 }, { 1, 0 }, { 1, 0 } } },
    { { { 1, 0, 0, 0 }, { 1, 1, 1, 1 } } },
    { { { 0, 1 }, { 0, 1 }, { 0, 1 }, { 1, 1 } } },
    { { { 1, 1, 1, 1 }, { 0, 0, 0, 1 } } },
    { { { 1, 1 }, { 0, 1 }, { 0, 1 }, { 0, 1 } } },
    { { { 1, 1, 1, 1 }, { 1, 0, 0, 0 } } },
    { { { 1, 0 }, { 1, 0 }, { 1, 0 }, { 1, 1 } } },
    { { { 0, 0, 0, 1 }, { 1, 1, 1, 1 } } },
    { { { 1 }, { 0 } }, { { 1 }, { 0 } }, { { 1 }, { 0 } }, { { 1 }, { 1 } } },
    { { { 1, 0 } }, { { 1, 0 } }, { { 1, 0 } }, { { 1, 1 } } },
    { { { 0 }, { 1 } }, { { 0 }, { 1 } }, { { 0 }, { 1 } }, { { 1 }, { 1 } } },
    { { { 0, 1 } }, { { 0, 1 } }, { { 0, 1 } }, { { 1, 1 } } },
    { { { 1 }, { 1 } }, { { 1 }, { 0 } }, { { 1 }, { 0 } }, { { 1 }, { 0 } } },
    { { { 1, 1 } }, { { 1, 0 } }, { { 1, 0 } }, { { 1, 0 } } },
    { { { 1 }, { 1 } }, { { 0 }, { 1 } }, { { 0 }, { 1 } }, { { 0 }, { 1 } } },
    { { { 1, 1 } }, { { 0, 1 } }, { { 0, 1 } }, { { 0, 1 } } },
  };

  public int[][][][] shapeP = {
    { { { 1, 1 }, { 1, 1 }, { 1, 0 } } },
    { { { 1, 1, 0 }, { 1, 1, 1 } } },
    { { { 0, 1 }, { 1, 1 }, { 1, 1 } } },
    { { { 1, 1, 1 }, { 0, 1, 1 } } },
    { { { 1, 1 }, { 1, 1 }, { 0, 1 } } },
    { { { 1, 1, 1 }, { 1, 1, 0 } } },
    { { { 1, 0 }, { 1, 1 }, { 1, 1 } } },
    { { { 0, 1, 1 }, { 1, 1, 1 } } },
    { { { 1 }, { 1 }, { 0 } }, { { 1 }, { 1 }, { 1 } } },
    { { { 1, 1, 0 } }, { { 1, 1, 1 } } },
    { { { 0 }, { 1 }, { 1 } }, { { 1 }, { 1 }, { 1 } } },
    { { { 0, 1, 1 } }, { { 1, 1, 1 } } },
    { { { 1 }, { 1 }, { 1 } }, { { 1 }, { 1 }, { 0 } } },
    { { { 1, 1, 1 } }, { { 1, 1, 0 } } },
    { { { 1 }, { 1 }, { 1 } }, { { 0 }, { 1 }, { 1 } } },
    { { { 1, 1, 1 } }, { { 0, 1, 1 } } },
    { { { 1 }, { 0 } }, { { 1 }, { 1 } }, { { 1 }, { 1 } } },
    { { { 1, 0 } }, { { 1, 1 } }, { { 1, 1 } } },
    { { { 0 }, { 1 } }, { { 1 }, { 1 } }, { { 1 }, { 1 } } },
    { { { 0, 1 } }, { { 1, 1 } }, { { 1, 1 } } },
    { { { 1 }, { 1 } }, { { 1 }, { 1 } }, { { 0 }, { 1 } } },
    { { { 1, 1 } }, { { 1, 1 } }, { { 0, 1 } } },
    { { { 1 }, { 1 } }, { { 1 }, { 1 } }, { { 1 }, { 0 } } },
    { { { 1, 1 } }, { { 1, 1 } }, { { 1, 0 } } },
  };

  public int[][][][] shapeT = {
    { { { 1, 1, 1 }, { 0, 1, 0 }, { 0, 1, 0 } } },
    { { { 1, 0, 0 }, { 1, 1, 1 }, { 1, 0, 0 } } },
    { { { 0, 1, 0 }, { 0, 1, 0 }, { 1, 1, 1 } } },
    { { { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 1 } } },
    {
      { { 1 }, { 0 }, { 0 } },
      { { 1 }, { 1 }, { 1 } },
      { { 1 }, { 0 }, { 0 } },
    },
    { { { 1, 0, 0 } }, { { 1, 1, 1 } }, { { 1, 0, 0 } } },
    {
      { { 0 }, { 0 }, { 1 } },
      { { 1 }, { 1 }, { 1 } },
      { { 0 }, { 0 }, { 1 } },
    },
    { { { 0, 0, 1 } }, { { 1, 1, 1 } }, { { 0, 0, 1 } } },
    { { { 0, 1 }, { 0, 1 }, { 0, 1 } }, { { 0, 1 }, { 0, 1 }, { 1, 1 } } },
    { { { 0, 1, 0 }, { 0, 1, 0 }, { 1, 1, 1 } } },
    { { { 1, 1 }, { 1, 0 }, { 1, 0 } }, { { 1, 1 }, { 0, 1 }, { 0, 1 } } },
    { { { 1, 1, 1 }, { 0, 1, 0 }, { 0, 1, 0 } } },
    { { { 1, 1 }, { 0, 1 }, { 0, 1 } }, { { 1, 1 }, { 1, 0 }, { 1, 0 } } },
  };

  public int[][][] getL(int rotation) {
    // int[][][] shapeL = new int[3][3][3];
    return shapeL[rotation];
  }

  public int[][][] getP(int rotation) {
    return shapeP[rotation];
  }

  public int[][][] getT(int rotation) {
    return shapeT[rotation];
  }

  public int[][][] getA(int rotation) {
    return shapeA[rotation];
  }

  public int[][][] getB(int rotation) {
    return shapeB[rotation];
  }

  public int[][][] getC(int rotation) {
    return shapeC[rotation];
  }

  public int rotationNum(String type) {
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

  public int[][][] getShape(String parcelType, int rotation) {
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
