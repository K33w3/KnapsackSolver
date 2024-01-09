package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;

import java.util.List;

public interface KnapsackSolverStrategy {
    void solve(CargoSpace cargoSpace, List<Parcel> parcels);
}
