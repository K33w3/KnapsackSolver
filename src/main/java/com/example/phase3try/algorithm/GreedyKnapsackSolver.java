package com.example.phase3try.algorithm;

import com.example.phase3try.UI;
import com.example.phase3try.model.CargoSpace;
import com.example.phase3try.model.Parcel;
import com.example.phase3try.model.ShapesAndRotations;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class GreedyKnapsackSolver implements KnapsackSolverStrategy {

    private CargoSpace cargoSpace;
    private ShapesAndRotations shapes;
    public static String[] parcelSequence;
    public static int[][][] matrix;
    private Parcel parcel;
    private double[] weights;
    private Random random;
    private static FileWriter writer;
    private UI visualization;

    public GreedyKnapsackSolver() {
        weights = new double[3];
        visualization = new UI();

        try {
            writer = new FileWriter("/Users/admin/Octavian/Masa de lucru/IdeaProjects/Phase3/src/main/java/com/bcs2024/knapsack/algorithm/resultsGBOT.txt", true);
        } catch (IOException e) {
            System.out.print(e);
        }

        cargoSpace = new CargoSpace();
        shapes = new ShapesAndRotations();
        parcelSequence = new String[] { "A", "B", "C" };
//        parcelSequence = new String[] { "L", "P", "T" };
        matrix = cargoSpace.getCargoMatric();
        random = new Random();
        for (int i = 0; i < 3; i++) {
            weights[i] = random.nextDouble();
        }
    }

    public void putShapes() {
        int count = 0;

        int lastX = -1;
        int lastY = -1;
        int lastZ = -1;
        int bestX = -1;
        int bestY = -1;
        int bestZ = -1;
        boolean isNewPosition = true;
        String bestParcel = "A";
        int rotationMem = 0;
        int countA = 0;
        int countB = 0;
        int countC = 0;

        double[] newWeights= new double[] {
                0.7934029214856616,
                0.9831091831983482,
                0.4224751952092841,
                0.3275013351194519
        };

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    double highestWeight = Double.NEGATIVE_INFINITY;
                    for (String p : parcelSequence) {
                        parcel = new Parcel(p);
                        for (int rotation = 0; rotation < shapes.rotationNum(p); rotation++) {
                            int[][][] shape = shapes.getShape(p, rotation);
                            if (cargoSpace.canPlace(shape, x, y, z, this.matrix)) {
                                double perimeter = calculatePerimeter(parcel);
                                double ratioVolume = calculateValueDensity(parcel);
                                double volume = calculateVolume(parcel);
                                int touchedPoints = touched(parcel, matrix, x, y, z);
//                                System.out.println(touchedPoints);
                                double weight = newWeights[0] * perimeter + newWeights[1] * ratioVolume + newWeights[2] * volume + newWeights[3] * touchedPoints;

                                if (weight > highestWeight) {
                                    highestWeight = weight;
                                    bestX = x;
                                    bestY = y;
                                    bestZ = z;
                                    bestParcel = p;
                                    rotationMem = rotation;
                                }
                            }
                        }
                    }

                    //     System.out.println("X: " + bestX + " Y : " + bestY + " Z :" + bestZ);

                    int[][][] shape = shapes.getShape(bestParcel, rotationMem);

                    if (bestX == lastX && bestY == lastY && bestZ == lastZ) {
                        isNewPosition = false;
                    } else {
                        isNewPosition = true;
                        lastX = bestX;
                        lastY = bestY;
                        lastZ = bestZ;
                    }

                    if (isNewPosition) {
                        if (bestParcel.equals("A") || bestParcel.equals("L")) {
                            countA++;
                            count += 3;
                        } else if (bestParcel.equals("B") || bestParcel.equals("P")) {
                            countB++;
                            count += 4;
                        } else if (bestParcel.equals("C") || bestParcel.equals("T")) {
                            countC++;
                            count += 5;
                        }
                        cargoSpace.placeParcel(shape, bestX, bestY, bestZ, matrix);
                    }
                }
            }
        }

//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                for (int k = 0; k < matrix[0][0].length; k++) {
//                    System.out.print(matrix[i][j][k]);
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
//        System.out.println(Arrays.toString(weights));
        try {
            writer.write(count + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.print(e);
        }
        System.out.println("A: " + countA + " B: " + countB + " C: " + countC);
        System.out.println(count);
        visualization.show();
    }

    @Override
    public void solve(CargoSpace cargoSpace, List<Parcel> parcels) {

    }

    public static void main(String[] args) {
        //    for (int i = 0; i < 2000; i++) {
        GreedyKnapsackSolver solver = new GreedyKnapsackSolver();
        solver.putShapes();
        //  }
    }

    public double calculatePerimeter(Parcel parcel) {
        double perimeter = parcel.getHeight();
        return perimeter;
    }

    public double calculateVolume(Parcel parcel) {
        return parcel.getHeight() * parcel.getLength() * parcel.getWidth();
    }

    public double calculateValueDensity(Parcel parcel) {
        double volume = parcel.getLength() * parcel.getWidth() * parcel.getHeight();
        return parcel.getValue() / volume;
    }

    public int touched(Parcel parcel, int[][][] matrix, int posX, int posY, int posZ) {
        int[][][] shape = parcel.getShape();
        int touchedPoints = 0;

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    int x = posX + i;
                    int y = posY + j;
                    int z = posZ + k;

                    // Check if the current point is on the boundary of the matrix
                    if (x == 0 || y == 0 || z == 0 || x == matrix.length - 1 || y == matrix[0].length - 1
                            || z == matrix[0][0].length - 1) {
                        touchedPoints++;
                        continue;
                    }

                    // Check the 26 neighboring points around the current point
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            for (int dz = -1; dz <= 1; dz++) {
                                if (dx == 0 && dy == 0 && dz == 0)
                                    continue; // Skip the current point

                                int nx = x + dx;
                                int ny = y + dy;
                                int nz = z + dz;

                                // Ensure neighboring indices are within bounds
                                if (nx >= 0 && ny >= 0 && nz >= 0 && nx < matrix.length && ny < matrix[0].length
                                        && nz < matrix[0][0].length) {
                                    // Check if the neighboring point has a shape
                                    if (matrix[nx][ny][nz] != 0) { // Assuming 0 represents empty space
                                        touchedPoints++;
                                        break; // Exit the innermost loop as we found a neighboring point with a shape
                                    }
                                }
                            }
                            if (touchedPoints > 0)
                                break; // Exit the middle loop if we already found a neighboring point with a shape
                        }
                        if (touchedPoints > 0)
                            break; // Exit the outer loop if we already found a neighboring point with a shape
                    }
                }
            }
        }
        return touchedPoints;
    }
}