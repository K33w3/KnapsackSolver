package com.bcs2024.knapsack.algorithm.dancinglinks;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.renderer.UI;
import com.bcs2024.knapsack.util.ShapesAndRotations;

public class Experiment {
    public static int[][][] field = new int[5][8][33];
    private static final CargoSpace cargoSpace = new CargoSpace();
    private static final ShapesAndRotations shapesAndRotations = new ShapesAndRotations();

    public static void main(final String[] args) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                for (int k = 0; k < field[0][0].length; k++) {
                    field[i][j][k] = -1;
                }
            }
        }
//      --------------------- AAAAAAAAAA ----------------------------
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 0);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 2);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 4);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 6);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 8);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 10);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 12);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 14);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 16);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 18);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 20);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 22);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 24);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 26);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 28);

        System.out.println(cargoSpace.canPlace(shapesAndRotations.getA(1), 0, 4, 29));
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 31);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 29);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 27);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 25);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 23);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 21);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 19);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 17);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 15);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 13);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 11);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 9);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 7);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 5);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 3);

//      --------------------- BBBBBBBBBB ----------------------------
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 0);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 0);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 4);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 4);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 8);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 8);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 12);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 12);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 16);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 16);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 20);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 20);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 24);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 24);

        cargoSpace.placeParcel(shapesAndRotations.getB(1), 0, 0, 30);

        cargoSpace.placeParcel(shapesAndRotations.getB(3), 2, 0, 28);

//        System.out.println(cargoSpace.canPlace(shapesAndRotations.getB(4), 3, 6, 29));
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 29);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 26);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 25);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 22);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 21);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 18);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 17);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 14);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 13);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 10);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 9);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 6);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 5);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 2);


        cargoSpace.placeParcel(shapesAndRotations.getB(1), 0, 4, 0);
        cargoSpace.placeParcel(shapesAndRotations.getB(3), 2, 4, 0);

//        --------------------CCCCCCCCCCCC--------------------------
//        System.out.println(cargoSpace.canPlace(shapesAndRotations.getC(0), 2, 3, 30));
        cargoSpace.placeParcel(shapesAndRotations.getC(0), 2, 0, 30);
        cargoSpace.placeParcel(shapesAndRotations.getC(0), 2, 3, 30);

//        field[2][6][29] = 0;

        final UI ui = new UI();
        ui.show();


        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                for (int k = 0; k < field[0][0].length; k++) {
                    System.out.print(field[i][j][k]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
