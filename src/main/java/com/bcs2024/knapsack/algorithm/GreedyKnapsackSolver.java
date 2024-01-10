package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.model.ParcelPlacement;
import com.bcs2024.knapsack.model.ShapesAndRotations;

import java.util.Comparator;
import java.util.List;

/**
 * Class to solve the three-dimensional knapsack problem using a greedy approach.
 */
public class GreedyKnapsackSolver implements KnapsackSolverStrategy {

    @Override
    public void solve(CargoSpace cargoSpace, List<Parcel> parcels) {

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
