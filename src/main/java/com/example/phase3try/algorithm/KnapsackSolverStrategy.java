package com.example.phase3try.algorithm;

import com.example.phase3try.model.CargoSpace;
import com.example.phase3try.model.Parcel;

import java.util.List;

public interface KnapsackSolverStrategy {
    void solve(CargoSpace cargoSpace, List<Parcel> parcels);
}
