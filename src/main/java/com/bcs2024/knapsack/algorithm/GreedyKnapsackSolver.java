package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.model.ParcelPlacement;
import com.bcs2024.knapsack.renderer.HelloApplication;
import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.Arrays;
import java.util.Random;

public class GreedyKnapsackSolver implements KnapsackSolverStrategy {

    private final CargoSpace cargoSpace = HelloApplication.cargoSpace;
    private final ShapesAndRotations shapes;
    private final String[] parcelSequence;
    private final double[] weights;
    private final double[] actualWeights;
    //private final HelloApplication visualization;

    public GreedyKnapsackSolver() { // TODO
        weights = new double[4];
        // visualization = new HelloApplication();

        // FileUtil.writeInFile("resultsGtraining.txt", ""); TODO uncomment this line to
        // write to file

        //cargoSpace = new CargoSpace();
        //matrix = cargoSpace.getOccupied();

        shapes = new ShapesAndRotations();
        parcelSequence = new String[]{"A", "B", "C"};

        final Random random = new Random();
        for (int i = 0; i < 4; i++) {
            weights[i] = random.nextDouble();
        }

        actualWeights = new double[]{
                0.7934029214856616,
                0.9831091831983482,
                0.4224751952092841,
                0.3275013351194519,
        };
    }

    private void putShapes() {
        int count = 0;

        int lastX = -1;
        int lastY = -1;
        int lastZ = -1;

        int bestX = -1;
        int bestY = -1;
        int bestZ = -1;

        boolean isNewPosition;
        String bestParcel = "A";
        int rotationMem = 0;

        int countA = 0;
        int countB = 0;
        int countC = 0;

        for (int x = 0; x < cargoSpace.getOccupied().length; x++) {
            for (int y = 0; y < cargoSpace.getOccupied()[0].length; y++) {
                for (int z = 0; z < cargoSpace.getOccupied()[0][0].length; z++) {
                    double highestWeight = Double.NEGATIVE_INFINITY;

                    for (final String parcelType : parcelSequence) {
                        final Parcel parcel = new Parcel(parcelType);

                        for (int rotation = 0; rotation < shapes.rotationNum(parcelType); rotation++) {
                            final int[][][] shape = shapes.getShape(parcelType, rotation);

                            if (cargoSpace.canPlace(shape, x, y, z, cargoSpace.getOccupied())) {
                                final double perimeter = calculatePerimeter(parcel);
                                final double ratioVolume = calculateValueDensity(parcel);
                                final double volume = calculateVolume(parcel);
                                final int touchedPoints = touched(parcel, cargoSpace.getOccupied(), x, y, z);
                                // System.out.println(touchedPoints);
                                final double weight = actualWeights[0] * perimeter + actualWeights[1] * ratioVolume
                                        + actualWeights[2] * volume + actualWeights[3] * touchedPoints;

                                if (weight > highestWeight) {
                                    highestWeight = weight;
                                    bestX = x;
                                    bestY = y;
                                    bestZ = z;
                                    bestParcel = parcelType;
                                    rotationMem = rotation;
                                }
                            }
                        }
                    }

                    // System.out.println("X: " + bestX + " Y : " + bestY + " Z :" + bestZ);

                    final int[][][] shape = shapes.getShape(bestParcel, rotationMem);
                    final Parcel parcel = new Parcel(bestParcel);
                    parcel.setShape(shape);
                    parcel.setType(bestParcel);

                    if (bestX == lastX && bestY == lastY && bestZ == lastZ) {
                        isNewPosition = false;
                    } else {
                        isNewPosition = true;
                        lastX = bestX;
                        lastY = bestY;
                        lastZ = bestZ;
                    }

                    if (isNewPosition) {
                        switch (bestParcel) {
                            case "A" -> {
                                countA++;
                                count += 3;
                            }
                            case "B" -> {
                                countB++;
                                count += 4;
                            }
                            case "C" -> {
                                countC++;
                                count += 5;
                            }
                        }
                        final ParcelPlacement placement = new ParcelPlacement(parcel, lastX, lastY, lastZ);

                        System.out.println("placement: " + Arrays.deepToString(placement.getShape()));

                        cargoSpace.placeParcel(shape, lastX, lastY, lastZ, cargoSpace.getOccupied());
                    }
                }
            }
        }

        for (final int[][] layer : cargoSpace.getOccupied()) {
            for (int j = 0; j < cargoSpace.getOccupied()[0].length; j++) {
                for (int k = 0; k < cargoSpace.getOccupied()[0][0].length; k++) {
                    System.out.print(layer[j][k]);
                }
                System.out.println();
            }

            System.out.println();
        }

        // System.out.println("A: " + countA + " B: " + countB + " C: " + countC);
        System.out.println(count);
        // visualization.setSolution(matrix);
        // visualization.show();

        // System.out.println(Arrays.toString(actualWeights));
        // try {
        // writer.write(Arrays.toString(weights) + "\n");
        // writer.write("Count: " + count + "\n");
        // writer.close();
        // } catch (IOException e) {
        // System.out.print(e);
        // }
        System.out.println("A: " + countA + " B: " + countB + " C: " + countC);
        System.out.println(count);

    }

    @Override
    public void solve() {
        this.putShapes();
        System.out.println(Arrays.deepToString(getCargoSpace().getOccupied()));
    }

    public static void main(final String[] args) {
        for (int i = 0; i < 10; i++) {
            final GreedyKnapsackSolver solver = new GreedyKnapsackSolver();
            solver.putShapes();
        }
    }

    public double calculatePerimeter(final Parcel parcel) {
        return parcel.getHeight();
    }

    public double calculateVolume(final Parcel parcel) {
        return parcel.getHeight() * parcel.getLength() * parcel.getWidth();
    }

    public double calculateValueDensity(final Parcel parcel) {
        final double volume = parcel.getLength() * parcel.getWidth() * parcel.getHeight();
        return parcel.getValue() / volume;
    }

    public int touched(final Parcel parcel, final int[][][] matrix, final int posX, final int posY, final int posZ) {
        final int[][][] shape = parcel.getShape();
        int touchedPoints = 0;

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    final int x = posX + i;
                    final int y = posY + j;
                    final int z = posZ + k;

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

                                final int nx = x + dx;
                                final int ny = y + dy;
                                final int nz = z + dz;

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

    public CargoSpace getCargoSpace() {
        return cargoSpace;
    }
}
