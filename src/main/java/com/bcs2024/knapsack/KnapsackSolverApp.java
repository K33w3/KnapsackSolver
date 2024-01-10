package com.bcs2024.knapsack;

import com.bcs2024.knapsack.algorithm.GeneticKnapsackSolver;
import com.bcs2024.knapsack.algorithm.GreedyKnapsackSolver;
import com.bcs2024.knapsack.algorithm.KnapsackSolverStrategy;
import com.bcs2024.knapsack.model.*;

import java.util.ArrayList;
import java.util.List;

public class KnapsackSolverApp {
    public static void main(String[] args) {
        // Initialize the cargo space
        CargoSpace cargoSpace = new CargoSpace(33, 5, 8); // multiply by 2 instead

        // Initialize parcels
        List<Parcel> parcels = new ArrayList<>();
<<<<<<< Updated upstream
        //parcels.add(new Parcel("A", 1.0, 1.0, 2.0, 1));
        //parcels.add(new Parcel("B", 1.0, 1.5, 2.0, 2));
        //parcels.add(new Parcel("C", 1.5, 1.5, 1.5, 3));
=======
        parcels.add(new Parcel("A", 0));
        parcels.add(new Parcel("B", 0));
        parcels.add(new Parcel("C", 0));
>>>>>>> Stashed changes

        // Choose and apply the solving strategy
        KnapsackSolverStrategy solver;

        // TODO add logic to choose the solving strategy in GUI

        System.out.println("Solving using Greedy Algorithm");
        solver = new GreedyKnapsackSolver();
        solver.solve(cargoSpace, parcels);
        // Display the result or analysis of the solution
        cargoSpace.printOccupiedSpacePositions();

        //--------------------------------------------------------------------------------------------------------------

        // Reset cargoSpace for next algorithm
        cargoSpace = new CargoSpace(33, 5, 8);

        System.out.println("Solving using Genetic Algorithm");
        solver = new GeneticKnapsackSolver();
        solver.solve(cargoSpace, parcels);
        // Display the result or analysis of the solution
        cargoSpace.printOccupiedSpacePositions();
    }
}
