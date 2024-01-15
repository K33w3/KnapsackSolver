package com.bcs2024.knapsack;

import com.bcs2024.knapsack.algorithm.GreedyKnapsackSolver;
import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.renderer.HelloApplication;
import com.bcs2024.knapsack.renderer.RenderCargo;
import javafx.application.Application;

public class KnapsackSolverApp {

    public static void main(final String[] args) {
        final GreedyKnapsackSolver greedyKnapsackSolver = new GreedyKnapsackSolver();
        greedyKnapsackSolver.solve();

        //final CargoSpace cargoSpace = greedyKnapsackSolver.getCargoSpace();
        final HelloApplication visualization = new HelloApplication();

        //visualization.setApplication(cargoSpace);
        visualization.show();

        //CargoSpace cargoSpace = greedyKnapsackSolver.getCargoSpace();
        //launchRenderCargo(cargoSpace);
    }

    private static void launchRenderCargo(final CargoSpace cargoSpace) {
        RenderCargo.setCargoSpace(cargoSpace);
        Application.launch(RenderCargo.class);
    }
}
