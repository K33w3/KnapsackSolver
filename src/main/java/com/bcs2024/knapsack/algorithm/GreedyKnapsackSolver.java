package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.model.ParcelPlacement;
import com.bcs2024.knapsack.renderer.UI;
import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.Arrays;
import java.util.Random;

public class GreedyKnapsackSolver {

    private final CargoSpace cargoSpace = UI.cargoSpace;
    private final ShapesAndRotations shapes;
    private final String[] parcelSequence;
    private final double[] weights;
    private final double[] actualWeights;

    /**
     * Constructs a new GreedyKnapsackSolver object for solving the knapsack problem
     * using a greedy algorithm.
     * This constructor initializes various properties, including weights, parcel
     * sequence,
     * and random initial values for weights. It also sets actualWeights with
     * predefined values.
     */
    public GreedyKnapsackSolver() {
        weights = new double[4];

        // FileUtil.writeInFile("resultsGtraining.txt", "");

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

    /**
     * Places parcels within the cargo space based on their properties and available
     * positions.
     * This method iterates through the cargo space, considering each position and
     * parcel type
     * in the specified sequence. It calculates the suitability of each placement
     * based on
     * various factors and selects the best placement for each parcel.
     *
     * @implNote This method applies a greedy algorithm to place parcels optimally
     * within the cargo space.
     */
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

                            if (cargoSpace.canPlace(shape, x, y, z)) {
                                final double perimeter = parcel.calculateSurfaceArea();
                                final double ratioVolume = parcel.getValueDensity();
                                final double volume = parcel.getVolume();
                                final int touchedPoints = touched(parcel, cargoSpace.getOccupied(), x, y, z);
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

                    final int[][][] shape = shapes.getShape(bestParcel, rotationMem);
                    final Parcel parcel = new Parcel(bestParcel, shape);
                    parcel.setType(bestParcel);

                    System.out.println("shape: " + Arrays.deepToString(shape));
                    System.out.println("parcel: " + Arrays.deepToString(parcel.getShape()));

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

                        final ParcelPlacement placement = new ParcelPlacement(parcel, bestX, bestY, bestZ);
                        placement.setShape(shape);
                        cargoSpace.placeParcel(placement);
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
    }

    /**
     * Solves the knapsack problem using a greedy algorithm by placing parcels
     * within the cargo space.
     * This method invokes the 'putShapes' method to place parcels optimally within
     * the cargo space.
     */
    public void solve() {
        this.putShapes();
    }

    /**
     * The main method for testing the GreedyKnapsackSolver by running it multiple
     * times.
     * This method creates instances of the GreedyKnapsackSolver and invokes the
     * 'putShapes' method
     * multiple times to solve the knapsack problem with a greedy algorithm for
     * testing purposes.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(final String[] args) {
        for (int i = 0; i < 10; i++) {
            final GreedyKnapsackSolver solver = new GreedyKnapsackSolver();
            solver.putShapes();
        }
    }

    /**
     * Returns the number of points touched by the parcel.
     * This method returns the number of points touched by the parcel.
     *
     * @param parcel The parcel.
     * @param matrix The matrix representing the cargo space.
     * @param posX   The x-coordinate of the parcel's position.
     * @param posY   The y-coordinate of the parcel's position.
     * @param posZ   The z-coordinate of the parcel's position.
     * @return The number of points touched by the parcel.
     */
    public int touched(final Parcel parcel, final int[][][] matrix, final int posX, final int posY, final int posZ) {
        final int[][][] shape = parcel.getShape();
        int touchedPoints = 0;

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    final int x = posX + i;
                    final int y = posY + j;
                    final int z = posZ + k;

                    if (x == 0 || y == 0 || z == 0 || x == matrix.length - 1 || y == matrix[0].length - 1
                            || z == matrix[0][0].length - 1) {
                        touchedPoints++;
                        continue;
                    }

                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            for (int dz = -1; dz <= 1; dz++) {
                                if (dx == 0 && dy == 0 && dz == 0)
                                    continue;

                                final int nx = x + dx;
                                final int ny = y + dy;
                                final int nz = z + dz;

                                if (nx >= 0 && ny >= 0 && nz >= 0 && nx < matrix.length && ny < matrix[0].length
                                        && nz < matrix[0][0].length) {
                                    if (matrix[nx][ny][nz] != 0) {
                                        touchedPoints++;
                                        break;
                                    }
                                }
                            }
                            if (touchedPoints > 0)
                                break;
                        }
                        if (touchedPoints > 0)
                            break;
                    }
                }
            }
        }
        return touchedPoints;
    }

    /**
     * Returns the cargo space.
     * This method returns the cargo space.
     *
     * @return The cargo space.
     */
    public CargoSpace getCargoSpace() {
        return cargoSpace;
    }
}
