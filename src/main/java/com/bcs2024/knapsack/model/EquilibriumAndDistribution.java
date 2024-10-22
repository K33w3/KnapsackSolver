package com.bcs2024.knapsack.model;

import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.Arrays;
import java.util.List;

/**
 * EquilibriumAndDistribution handles the placement of parcels in a CargoSpace
 * and calculates metrics like the center of mass and distribution of parcels
 * within the cargo space.
 */
public class EquilibriumAndDistribution {
    private final CargoSpace cargoSpace;

    /**
     * Constructs a new EquilibriumAndDistribution object.
     *
     * @param cargoSpace The CargoSpace object representing the cargo space.
     * @param rotations  Array of rotations corresponding to each gene.
     * @param genes      Array of genes representing parcel types.
     */
    public EquilibriumAndDistribution(final CargoSpace cargoSpace, final int[] rotations, final String[] genes) {
        this.cargoSpace = cargoSpace;
    }

    /**
     * Fills the container represented by the CargoSpace object with parcels.
     *
     * @param cargoSpace The CargoSpace object representing the cargo space.
     * @param genes      Array of genes representing parcel types.
     * @param rotations  Array of rotations corresponding to each gene.
     */
    public void fillContainer(final CargoSpace cargoSpace, final String[] genes, final int[] rotations) {
        final ShapesAndRotations shapes = new ShapesAndRotations();

        final int[][][] matrix = cargoSpace.getOccupied();

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    for (int i = 0; i < genes.length; i++) {

                        final int rotation = rotations[i];
                        final int[][][] shape = shapes.getShape(genes[i], rotation);
                        final Parcel parcel = new Parcel(genes[i], shape);

                        if (cargoSpace.canPlace(shape, x, y, z)) {
                            final ParcelPlacement placement = new ParcelPlacement(
                                    parcel,
                                    x,
                                    y,
                                    z
                            );
                            cargoSpace.placeParcel(placement);
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates the center of mass based on the placements of parcels within the cargo space.
     *
     * @return A double array representing the x, y, and z coordinates of the center of mass.
     */
    public double[] calculateCenterOfMass() {
        double totalVolume = 0.0;
        double weightedSumX = 0.0;
        double weightedSumY = 0.0;
        double weightedSumZ = 0.0;

        final List<ParcelPlacement> placements = cargoSpace.getPlacements();

        for (final ParcelPlacement placement : placements) {
            final Parcel parcel = placement.getParcel();
            final double parcelVolume = parcel.getVolume();
            totalVolume += parcelVolume;
            weightedSumX += parcelVolume * placement.getX();
            weightedSumY += parcelVolume * placement.getY();
            weightedSumZ += parcelVolume * placement.getZ();
        }

        if (totalVolume != 0) {
            final double centerOfMassX = weightedSumX / totalVolume;
            final double centerOfMassY = weightedSumY / totalVolume;
            final double centerOfMassZ = weightedSumZ / totalVolume;

            return new double[]{centerOfMassX, centerOfMassY, centerOfMassZ};
        }

        // Handle the case when there are no parcels placed
        return new double[]{0.0, 0.0, 0.0};
    }

    /**
     * Calculates the average of the x, y, and z coordinates of the center of mass.
     *
     * @return The average value of the center of mass coordinates.
     */
    public double calculateAverageCenterOfMass() {
        final double[] centerOfMass = calculateCenterOfMass();
        final double sum = centerOfMass[0] + centerOfMass[1] + centerOfMass[2];
        return sum / 3.0;
    }

    /**
     * Calculates the distribution of parcels within the cargo space.
     *
     * @return The distribution ratio of filled slots to total slots in the cargo space.
     */
    public double calculateDistribution() {
        final int totalSlots = cargoSpace.getOccupied().length *
                cargoSpace.getOccupied()[0].length *
                cargoSpace.getOccupied()[0][0].length;
        final int filledSlots = cargoSpace.getFilledSlotsCount();

        if (totalSlots == 0) {
            return 0.0;
        }

        return (double) filledSlots / totalSlots;
    }

    /**
     * Main method for testing the EquilibriumAndDistribution class by running it multiple
     * times and calculating metrics like center of mass and distribution.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(final String[] args) {

        final String[] genes = {"C", "C", "B", "B", "A", "A", "C", "A", "C", "B", "C", "C", "A", "C", "A", "A", "A", "C", "C", "B", "B", "A", "A", "B", "B", "C", "C", "A", "A", "C", "B", "B", "B", "A", "B", "B", "C", "C", "C", "B", "A", "C", "B", "B", "C", "C", "C", "A", "C", "C", "A", "A", "A", "B"};
        final int[] rotations = {0, 0, 4, 4, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 3, 2, 0, 0, 2, 1, 0, 1, 5, 1, 1, 4, 1, 0, 0, 0, 3, 1, 0, 1, 3, 0, 0, 0, 1, 0, 0, 1, 0, 1, 5};

        final CargoSpace cargoSpace = new CargoSpace();

        final EquilibriumAndDistribution equilibrium = new EquilibriumAndDistribution(cargoSpace, rotations, genes);

        equilibrium.fillContainer(cargoSpace, genes, rotations);

        // Calculate center of mass
        final double[] centerOfMass = equilibrium.calculateCenterOfMass();
        System.out.println("Center of Mass: " + Arrays.toString(centerOfMass));
        // Calculate center of mass Average
        final double averageCenterOfMass = equilibrium.calculateAverageCenterOfMass();
        System.out.println("Average Center of Mass: " + averageCenterOfMass);
        // Calculate distribution
        final double distribution = equilibrium.calculateDistribution();
        System.out.println("Distribution: " + distribution);
    }

}

