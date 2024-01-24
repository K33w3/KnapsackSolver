package com.bcs2024.knapsack.model;

import java.util.Arrays;
import java.util.List;

import com.bcs2024.knapsack.algorithm.GreedyKnapsackSolver;
import com.bcs2024.knapsack.algorithm.dancinglinks.DLSearch;
import com.bcs2024.knapsack.algorithm.dancinglinks.DancingLinks;

public class EquilibriumAndDistribution {

    private CargoSpace cargoSpace;
    private int[] rotations;
    private String[] genes;
    private int[][][] matrix;

    public EquilibriumAndDistribution() {

    }

    public double[] calculateCenterOfMass(List<ParcelPlacement> placements) {
        // System.out.println(Arrays.toString(placements.toArray()));
        double totalWeight = 0.0;
        double weightedSumX = 0.0;
        double weightedSumY = 0.0;
        double weightedSumZ = 0.0;

        for (final ParcelPlacement placement : placements) {
            final Parcel parcel = placement.getParcel();
            final double parcelWeight = getParcelWeight(parcel.getType());

            totalWeight += parcelWeight;
            weightedSumX += parcelWeight * placement.getX();
            weightedSumY += parcelWeight * placement.getY();
            weightedSumZ += parcelWeight * placement.getZ();
        }

        if (totalWeight != 0) {
            double maxX = 33.0;
            double maxY = 5.0;
            double maxZ = 8.0;

            // Calculate the center of mass
            double centerOfMassX = weightedSumX / totalWeight;
            double centerOfMassY = weightedSumY / totalWeight;
            double centerOfMassZ = weightedSumZ / totalWeight;

            // Ensure the center of mass is within the bounds of the matrix
            centerOfMassX = Math.min(maxX - 1, Math.max(0, centerOfMassX));
            centerOfMassY = Math.min(maxY - 1, Math.max(0, centerOfMassY));
            centerOfMassZ = Math.min(maxZ - 1, Math.max(0, centerOfMassZ));

            return new double[] { centerOfMassX, centerOfMassY, centerOfMassZ };
        } else {
            return null;
        }
    }

    private double getParcelWeight(String parcelType) {
        switch (parcelType) {
            case "A": // Type A
                return 1.0;
            case "B": // Type B
                return 2.0;
            case "C": // Type C
                return 3.0;
            default:
                return 0.0;
        }
    }

    public double calculateAverageCenterOfMass(List<ParcelPlacement> placements) {
        final double[] centerOfMass = calculateCenterOfMass(placements);

        if (centerOfMass != null) {
            final double sum = centerOfMass[0] + centerOfMass[1] + centerOfMass[2];
            return sum / 3.0;
        } else {
            return 0.0;
        }
    }

    public void analyzeDistribution(int[][][] matrix) {
        int totalSlots = matrix.length * matrix[0].length * matrix[0][0].length;

        if (totalSlots == 0) {
            System.out.println("Cargo space is empty.");
            return;
        }

        int frontSlots = matrix.length / 2;
        int backSlots = matrix.length - frontSlots;

        int filledFrontSlots = 0;
        int filledBackSlots = 0;

        int heavyCountFront = 0;
        int mediumCountFront = 0;
        int lightCountFront = 0;

        int heavyCountBack = 0;
        int mediumCountBack = 0;
        int lightCountBack = 0;

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    int parcelType = matrix[x][y][z];

                    // Update counters for parcel distribution
                    if (x < frontSlots) {
                        filledFrontSlots++;
                        switch (parcelType) {
                            case 1: // Type A
                                lightCountFront++;
                                break;
                            case 2: // Type B
                                mediumCountFront++;
                                break;
                            case 3: // Type C
                                heavyCountFront++;
                                break;
                        }
                    } else {
                        filledBackSlots++;
                        switch (parcelType) {
                            case 1: // Type A
                                lightCountBack++;
                                break;
                            case 2: // Type B
                                mediumCountBack++;
                                break;
                            case 3: // Type C
                                heavyCountBack++;
                                break;
                        }
                    }
                }
            }
        }

        printDistribution("Front", filledFrontSlots, heavyCountFront, mediumCountFront, lightCountFront);
        printDistribution("Back", filledBackSlots, heavyCountBack, mediumCountBack, lightCountBack);
    }

    private void printDistribution(String area, int filledSlots, int heavyCount, int mediumCount, int lightCount) {
        System.out.println(area + " Area:");
        System.out.println("Filled Slots: " + filledSlots);
        System.out.println("Heavy Parcel (Type C) Percentage: " + calculatePercentage(heavyCount, filledSlots) + "%");
        System.out.println("Medium Parcel (Type B) Percentage: " + calculatePercentage(mediumCount, filledSlots) + "%");
        System.out.println("Light Parcel (Type A) Percentage: " + calculatePercentage(lightCount, filledSlots) + "%");
        System.out.println();
    }

    private double calculatePercentage(int count, int total) {
        return (total == 0) ? 0.0 : ((double) count / total) * 100.0;
    }

    public void printResults(List<ParcelPlacement> placements, CargoSpace cargoSpace) {
        EquilibriumAndDistribution equilibrium = new EquilibriumAndDistribution();

        long startTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Time Completion GreedySolver: " + elapsedTime);
        System.out.println(Arrays.toString(equilibrium.calculateCenterOfMass(placements)));
        System.out.println(placements.size());
        equilibrium.analyzeDistribution(cargoSpace.getOccupied());
        System.out.println("Average Center of Mass: " + equilibrium.calculateAverageCenterOfMass(placements));
        System.out.println("----------------------------------------------------------------------------");
    }

    public static void main(final String[] args) {
        for (int i = 0; i < 15000; i++) {
            GreedyKnapsackSolver greedySolver = new GreedyKnapsackSolver();
            EquilibriumAndDistribution equilibrium = new EquilibriumAndDistribution();

            long startTime = System.currentTimeMillis();
            greedySolver.solve();
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            System.out.println("Time Completion GreedySolver: " + elapsedTime);
            System.out.println(Arrays.toString(equilibrium.calculateCenterOfMass(greedySolver.placements)));
            System.out.println(greedySolver.placements.size());
            equilibrium.analyzeDistribution(greedySolver.getCargoSpace().getOccupied());
            System.out.println("Average Center of Mass: " +
            equilibrium.calculateAverageCenterOfMass(greedySolver.placements));
            System.out.println("----------------------------------------------------------------------------");
            }

            // DLSearch dlxSearch = new DLSearch();
            // EquilibriumAndDistribution equilibrium = new EquilibriumAndDistribution();

            // long startTime = System.currentTimeMillis();
            // dlxSearch.start();
            // long endTime = System.currentTimeMillis();
            // long elapsedTime = endTime - startTime;

            // DancingLinks dance = dlxSearch.dance;

            // System.out.println("Time Completion GreedySolver: " + elapsedTime);
            // System.out.println(Arrays.toString(equilibrium.calculateCenterOfMass(dance.placements)));
            // System.out.println(dance.placements.size());
            // equilibrium.analyzeDistribution(dance.cargoSpace.getOccupied());
            // System.out.println("Average Center of Mass: " + equilibrium.calculateAverageCenterOfMass(dance.placements));
            // System.out.println("----------------------------------------------------------------------------");
        }
    }


