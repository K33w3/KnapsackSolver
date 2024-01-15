package com.bcs2024.knapsack;

import com.bcs2024.knapsack.algorithm.GreedyKnapsackSolver;
import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.renderer.RenderCargo;
import javafx.application.Application;

public class KnapsackSolverApp {

  public static void main(String[] args) {
    GreedyKnapsackSolver greedyKnapsackSolver = new GreedyKnapsackSolver();
    greedyKnapsackSolver.solve();

    CargoSpace cargoSpace = greedyKnapsackSolver.getCargoSpace();
    launchRenderCargo(cargoSpace);
  }

  private static void launchRenderCargo(CargoSpace cargoSpace) {
    RenderCargo.setCargoSpace(cargoSpace);
    Application.launch(RenderCargo.class);
  }
}
