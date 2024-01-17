package com.bcs2024.knapsack;

import com.bcs2024.knapsack.algorithm.GeneticKnapsackSolver;
import com.bcs2024.knapsack.algorithm.GreedyKnapsackSolver;
import com.bcs2024.knapsack.algorithm.dancinglinks.DLSearch;
import com.bcs2024.knapsack.renderer.UI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class KnapsackSolverApp {

    private static void handleInput() {
        System.out.println("Selected method");
        System.out.println("Greedy");
        System.out.println("Genetically Trained");
        System.out.println("Dancing Links");
    }

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int algorithmChoice;

        System.out.println("Select the algorithm: " +
                "1: Greedy " +
                "2: DLX " +
                "3: Genetic");

        try {
            algorithmChoice = scanner.nextInt();
        } catch (final InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
            scanner.nextLine();
            return;
        }

        switch (algorithmChoice) {
            case 1:
                final GreedyKnapsackSolver greedyKnapsackSolver = new GreedyKnapsackSolver();
                greedyKnapsackSolver.solve();
                break;
            case 2:
                final DLSearch dlx = new DLSearch();
                dlx.createPositions();
                break;
            case 3:
                final GeneticKnapsackSolver geneticKnapsackSolver = new GeneticKnapsackSolver();
                geneticKnapsackSolver.solve();
                break;
            default:
                System.out.println("Invalid algorithm choice. Please select 1 (Greedy), 2 (DLX), or 3 (Genetic).");
        }

        final UI ui = new UI();
        ui.show();

        // final HelloApplication visualization = new HelloApplication();
        // visualization.show();
    }
}
