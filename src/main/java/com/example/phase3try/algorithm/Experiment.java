package com.example.phase3try.algorithm;

import com.example.phase3try.UI;
import com.example.phase3try.model.CargoSpace;
import com.example.phase3try.model.ShapesAndRotations;

public class Experiment {
    public static int[][][] field = new int[5][8][33];
    private static CargoSpace cargoSpace = new CargoSpace();
    private static ShapesAndRotations shapesAndRotations = new ShapesAndRotations();

    public static void main(String[] args) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                for (int k = 0; k < field[0][0].length; k++) {
                    field[i][j][k] = -1;
                }
            }
        }
//      --------------------- AAAAAAAAAA ----------------------------
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 0, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 2, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 4, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 6, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 8, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 10, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 12, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 14, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 16, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 18, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 20, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 22, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 24, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 26, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 0, 28, field);

        System.out.println(cargoSpace.canPlace(shapesAndRotations.getA(1), 0, 4, 29, field));
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 31, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 29, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 27, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 25, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 23, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 21, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 19, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 17, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 15, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 13, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 11, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 9, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 7, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 5, field);
        cargoSpace.placeParcel(shapesAndRotations.getA(1), 0, 4, 3, field);

//      --------------------- BBBBBBBBBB ----------------------------
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 0, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 0, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 4, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 4, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 8, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 8, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 12, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 12, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 16, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 16, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 20, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 20, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 0, 24, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 2, 24, field);

        cargoSpace.placeParcel(shapesAndRotations.getB(1), 0, 0, 30, field);

        cargoSpace.placeParcel(shapesAndRotations.getB(3), 2, 0, 28, field);

//        System.out.println(cargoSpace.canPlace(shapesAndRotations.getB(4), 3, 6, 29, field));
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 29, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 26, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 25, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 22, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 21, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 18, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 17, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 14, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 13, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 10, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 9, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 6, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 6, 5, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(2), 2, 4, 2, field);


        cargoSpace.placeParcel(shapesAndRotations.getB(1), 0, 4, 0, field);
        cargoSpace.placeParcel(shapesAndRotations.getB(3), 2, 4, 0, field);

//        --------------------CCCCCCCCCCCC--------------------------
//        System.out.println(cargoSpace.canPlace(shapesAndRotations.getC(0), 2, 3, 30, field));
        cargoSpace.placeParcel(shapesAndRotations.getC(0), 2, 0, 30, field);
        cargoSpace.placeParcel(shapesAndRotations.getC(0), 2, 3, 30, field);

//        field[2][6][29] = 0;

        UI ui = new UI();
        ui.show();


//        for (int i = 0; i < field.length; i++) {
//            for (int j = 0; j < field[0].length; j++) {
//                for (int k = 0; k < field[0][0].length; k++) {
//                    System.out.print(field[i][j][k]);
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
    }
}
