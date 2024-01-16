package com.example.phase3try;

import com.example.phase3try.algorithm.GeneticKnapsackSolver;
import com.example.phase3try.algorithm.KnapsackSolverStrategy;
import com.example.phase3try.model.*;

import java.util.ArrayList;
import java.util.List;

public class KnapsackSolverApp {

    public static int typeToId(String type) {
        return switch (type) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            case "L" -> 4;
            case "P" -> 5;
            case "T" -> 6;
            default -> -1;
        };
    }

    public static void main(String[] args) {
        // Initialize the cargo space
        CargoSpace cargoSpace = new CargoSpace();

        // Initialize parcels
        List<Parcel> parcels = new ArrayList<>();
        parcels.add(new Parcel("A"));
        parcels.add(new Parcel("B"));
        parcels.add(new Parcel("C"));

        // Choose and apply the solving strategy
        KnapsackSolverStrategy solver;

        // TODO add logic to choose the solving strategy in GUI

        System.out.println("Solving using Greedy Algorithm");
        solver = new GeneticKnapsackSolver();
        solver.solve(cargoSpace, parcels);

        //--------------------------------------------------------------------------------------------------------------

        // Reset cargoSpace for next algorithm
        cargoSpace = new CargoSpace();

        System.out.println("Solving using Genetic Algorithm");
        solver = new GeneticKnapsackSolver();
        solver.solve(cargoSpace, parcels);
    }
}
