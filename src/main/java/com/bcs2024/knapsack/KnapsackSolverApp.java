package com.bcs2024.knapsack;

import com.bcs2024.knapsack.algorithm.GeneticKnapsackSolver;
import com.bcs2024.knapsack.algorithm.GreedyKnapsackSolver;
import com.bcs2024.knapsack.algorithm.dancinglinks.DLSearch;
import com.bcs2024.knapsack.renderer.UI;
import javax.swing.JOptionPane;

public class KnapsackSolverApp {

    /**
     * The main entry point of the application. It displays a graphical dialog to
     * allow the user to choose a knapsack solving algorithm and initializes the
     * selected algorithm, ultimately showing the user interface.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(final String[] args) {
        // options array
        String[] options = { "Greedy", "Genetic", "Dancing Links" };

        // pop up
        int selectedOption = JOptionPane.showOptionDialog(null,
                "Choose an Algorithm",
                "Algorithm Selector",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        // handle selection
        final UI ui = new UI();
        switch (selectedOption) {
            case 0:
                System.out.println("Greedy selected");
                final GreedyKnapsackSolver greedyKnapsackSolver = new GreedyKnapsackSolver();
                greedyKnapsackSolver.solve();
                ui.show();
                break;
            case 1:
                System.out.println("Genetic selected");
                final GeneticKnapsackSolver geneticKnapsackSolver = new GeneticKnapsackSolver();
                geneticKnapsackSolver.solve();
                ui.show();
                break;
            case 2:
                System.out.println("Dancing Links selected");
                final DLSearch dlx = new DLSearch();
                dlx.createPositions();
                ui.show();
                break;
            default:
                System.out.println("No option selected");
                break;
        }
    }
}
