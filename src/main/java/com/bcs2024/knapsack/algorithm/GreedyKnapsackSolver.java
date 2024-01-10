package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.model.ParcelPlacement;
import com.bcs2024.knapsack.model.ShapesAndRotations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.collections;

/**
 * Class to solve the three-dimensional knapsack problem using a greedy approach.
 */
public class GreedyKnapsackSolver implements KnapsackSolverStrategy {

  /**
   * Solves the knapsack problem for the given cargo space and list of parcels.
   * It places the parcels with the highest value density first.
   *
   * @param cargoSpace The cargo space where parcels are to be placed.
   * @param parcels The list of parcels to be placed.
   */

  private CargoSpace duplicatCargo;
  private ArrayList<Double> sortedParcelsValueRatio;

  public void createDuplicateMatrix(CargoSpace cargoSpace) {
    this.duplicatCargo = cargoSpace;
  }

  //PARAMETERS : perimeter, ratio volume, volume , untouched , count the gaps

  public void GreedySolver(CargoSpace cargoSpace, List<Parcel> parcels) {}

  @Override
  public void solve(CargoSpace cargoSpace, List<Parcel> parcels) {
    // Sort parcels by value density in descending order

    for (Parcel parcel : parcels) {
      if (tryPlaceParcel(cargoSpace, parcel)) {
        System.out.println(
          "Successfully placed parcel " +
          parcel.getType() +
          " with value " +
          parcel.getValue()
        );
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
    int maxOrientations = ShapesAndRotations.getRotations(parcel.getType())
      .length;

    // Iterate through possible orientations
    for (int orientation = 0; orientation < maxOrientations; orientation++) {
      // Iterate through positions in cargo space
      for (int x = 0; x < cargoSpace.getLength(); x++) {
        for (int y = 0; y < cargoSpace.getWidth(); y++) {
          for (int z = 0; z < cargoSpace.getHeight(); z++) {
            ParcelPlacement placement = new ParcelPlacement(
              parcel,
              x,
              y,
              z,
              orientation
            );
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
