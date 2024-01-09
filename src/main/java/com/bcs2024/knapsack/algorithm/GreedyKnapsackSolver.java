package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.model.ParcelPlacement;

import java.util.Comparator;
import java.util.List;

/**
 * Class to solve the three-dimensional knapsack problem using a greedy approach.
 */
public class GreedyKnapsackSolver implements KnapsackSolverStrategy {

    private static final int MAX_ORIENTATIONS = 24;
    /**
     * Solves the knapsack problem for the given cargo space and list of parcels.
     * It places the parcels with the highest value density first.
     *
     * @param cargoSpace The cargo space where parcels are to be placed.
     * @param parcels The list of parcels to be placed.
     */
    @Override
    public void solve(CargoSpace cargoSpace, List<Parcel> parcels) {
        // Sort parcels by value density in descending order
        parcels.sort(Comparator.comparingDouble(this::calculateValueDensity).reversed());

        for (Parcel parcel : parcels) {
            if (tryPlaceParcel(cargoSpace, parcel)) {
                System.out.println("Successfully placed parcel " + parcel.getType() + " with value " + parcel.getValue());
            }
        }
    }

    /**
     * Tries to place a parcel in the cargo space.
     *
     * @param cargoSpace The cargo space to place the parcel in.
     * @param parcel The parcel to be placed.
     * @return true if the parcel was successfully placed, false otherwise.
     */
    private boolean tryPlaceParcel(CargoSpace cargoSpace, Parcel parcel) {
        // Iterate through possible orientations
        for (int orientation = 0; orientation < MAX_ORIENTATIONS; orientation++) {
            // Iterate through positions in cargo space
            for (int x = 0; x < cargoSpace.getLength(); x++) {
                for (int y = 0; y < cargoSpace.getWidth(); y++) {
                    for (int z = 0; z < cargoSpace.getHeight(); z++) {
                        ParcelPlacement placement = new ParcelPlacement(parcel, x, y, z, orientation);
                        if (cargoSpace.isSpaceAvailable(placement)) {
                            cargoSpace.occupySpace(placement);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * Returns the number of different orientations a parcel can have.
     * This could depend on the parcel type and its dimensions.
     *
     * @param parcel The parcel to check.
     * @return The number of orientations.
     */
    private int getNumberOfOrientations(Parcel parcel) {
        // This is a placeholder. Implement the actual logic based on parcel's characteristics.
        return 6; // For a cuboid, there can be 6 orientations. Adjust as needed.
    }

    /**
     * Calculates the value density of a parcel.
     *
     * @param parcel The parcel to calculate the value density for.
     * @return The value density of the parcel.
     */
    private double calculateValueDensity(Parcel parcel) {
        double volume = parcel.getLength() * parcel.getWidth() * parcel.getHeight();
        return parcel.getValue() / volume;
    }
}
