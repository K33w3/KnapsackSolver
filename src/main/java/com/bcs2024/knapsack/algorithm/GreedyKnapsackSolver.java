package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;

import java.util.Comparator;
import java.util.List;

/**
 * Class to solve the three-dimensional knapsack problem using a heuristic approach.
 */
public class GreedyKnapsackSolver implements KnapsackSolverStrategy {

    /**
     * Solves the knapsack problem for the given cargo space and list of parcels.
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
                // TODO Implement any additional logic if needed when a parcel is placed
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
        for (int x = 0; x < cargoSpace.getLength(); x++) {
            for (int y = 0; y < cargoSpace.getWidth(); y++) {
                for (int z = 0; z < cargoSpace.getHeight(); z++) {
                    if (cargoSpace.isSpaceAvailable(parcel, x, y, z)) {
                        cargoSpace.occupySpace(parcel, x, y, z);
                        return true;
                    }
                }
            }
        }
        return false;
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
