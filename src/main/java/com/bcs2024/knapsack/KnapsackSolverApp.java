package com.bcs2024.knapsack;

import com.bcs2024.knapsack.algorithm.GeneticKnapsackSolver;
import com.bcs2024.knapsack.algorithm.GreedyKnapsackSolver;
import com.bcs2024.knapsack.algorithm.dancinglinks.DLSearch;
import com.bcs2024.knapsack.renderer.UI;


public class KnapsackSolverApp {

    /**
     * The main entry point of the application. It displays a graphical dialog to
     * allow the user to choose a knapsack solving algorithm and initializes the
     * selected algorithm, ultimately showing the user interface.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(final String[] args) {
        final UI ui = new UI();
        ui.show();
}
}
