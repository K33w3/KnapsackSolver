package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.renderer.UI;
import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * GeneticKnapsackSolver implements a genetic algorithm to solve the knapsack problem.
 * It initializes a population of chromosomes (solutions), evaluates their fitness,
 * applies crossover and mutation to produce new generations, and selects the best solution.
 */
public class GeneticKnapsackSolver {
    private final double MUTATION_RATE = 0.3;
    private final double CROSSOVER_RATE = 0.8;
    private final int MAX_GENERATIONS = 1;
    private final Random random = new Random();
    private final int GENE_LENGTH = 60;
    private final int POPULATION_SIZE = 100;
    private List<Chromosome> population;
    private int[] bestSolutionRotation;
    private String[] bestSolutionGene;
    private Chromosome bestChromosome;
    private CargoSpace cargoSpace = UI.cargoSpace;

    /**
     * Main method to start the knapsack problem solver.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(final String[] args) {
        final GeneticKnapsackSolver solver = new GeneticKnapsackSolver();
        solver.solve();
    }

    /**
     * Solves the knapsack problem using a genetic algorithm.
     * The algorithm involves initializing a population of chromosomes, evaluating
     * the fitness of each chromosome,
     * applying crossover and mutation to create a new population, and repeating
     * until a maximum number of generations
     * is reached.
     */
    public void solve() {
        bestSolutionGene = new String[GENE_LENGTH];
        bestSolutionRotation = new int[GENE_LENGTH];

        initializePopulation();
        for (int i = 0; i < MAX_GENERATIONS; i++) {
            evaluateFitness(population);
            crossover();
            mutation();
            System.out.println("Generation: " + i + " done. -------------------------------------------------------------------------------" + "\n");
        }

        System.out.println("Rotation: " + Arrays.toString(bestSolutionRotation));
        System.out.println("Genes: " + Arrays.toString(bestSolutionGene));
        applyBestSolution();
    }

    /**
     * Initializes the population of chromosomes with random genes and rotations.
     */
    private void initializePopulation() {
        population = new ArrayList<>(POPULATION_SIZE);

        for (int i = 0; i < POPULATION_SIZE; i++) {
            final Chromosome chromosome = new Chromosome(GENE_LENGTH);
            population.add(chromosome);
        }
    }

    /**
     * Evaluates the fitness of each chromosome in the population by calculating
     * the total value of the cargo space.
     * The fitness is set to the total value of the cargo space.
     *
     * @param population The population of chromosomes to be evaluated.
     */
    private void evaluateFitness(final List<Chromosome> population) {
        final ShapesAndRotations shapes = new ShapesAndRotations();

        for (int ch = 0; ch < population.size(); ch++) {
            Chromosome chromo = population.get(ch);
            int totalValue = 0;
            int countA = 0;
            int countB = 0;
            int countC = 0;
            int countGenes = 0;
            final CargoSpace localCargoSpace = new CargoSpace();

            final int[][][] occupied = localCargoSpace.getOccupied();

            for (int i = 0; i < chromo.getGenes().length; i++) {
                for (int x = 0; x < occupied.length; x++) {
                    for (int y = 0; y < occupied[0].length; y++) {
                        for (int z = 0; z < occupied[0][0].length; z++) {
                            final String gene = chromo.getGenes()[i];
                            final int rotation = chromo.getRotationFromGene(i);
                            final int[][][] shape = shapes.getShape(gene, rotation);

                            if (localCargoSpace.canPlace(shape, x, y, z)) {
                                localCargoSpace.placeParcel(shape, x, y, z/*, occupied*/);

                                switch (gene) {
                                    case "A" -> {
                                        totalValue += 3;
                                        countA++;
                                    }
                                    case "B" -> {
                                        totalValue += 4;
                                        countB++;
                                    }
                                    case "C" -> {
                                        totalValue += 5;
                                        countC++;
                                    }
                                }

                                countGenes++;

                                x = occupied.length;
                                y = occupied[0].length;
                                z = occupied[0][0].length;
                            }
                        }
                    }
                }
            }
            if (countGenes == chromo.getGenes().length) {
                chromo.setFitness(totalValue);
                bestSolutionRotation = chromo.getRotations();
                bestSolutionGene = chromo.getGenes();
                bestChromosome = chromo;
                System.out.println("Chromosome Fitness: " + totalValue);
                System.out.println(Arrays.toString(chromo.getRotations()));
                System.out.println(Arrays.toString(chromo.getGenes()));
                System.out.println("A: " + countA + " B: " + countB + " C: " + countC);
            } else {
                population.set(ch, new Chromosome(GENE_LENGTH));
                ch--;
            }
        }
    }

    /**
     * Selects a chromosome from the population using tournament selection.
     * Tournament selection involves selecting a random subset of chromosomes from
     * the population and selecting the
     * chromosome with the highest fitness.
     *
     * @param tournamentSize The size of the tournament to be held.
     * @return The chromosome with the highest fitness from the tournament.
     */
    private Chromosome tournamentSelection(final int tournamentSize) {
        final List<Chromosome> tournamentParticipants = new ArrayList<>();

        if (tournamentSize > population.size() || tournamentSize < 1) {
            throw new IllegalArgumentException("Tournament size should be between 1 and population size.");
        }

        for (int i = 0; i < tournamentSize; i++) {
            tournamentParticipants.add(population.get(random.nextInt(population.size())));
        }

        Chromosome winner = tournamentParticipants.get(0);
        for (final Chromosome participant : tournamentParticipants) {
            if (participant.getFitness() > winner.getFitness()) {
                winner = participant;
            }
        }

        return winner;
    }

    /**
     * Applies crossover to the chromosomes in the population with a probability
     * defined by CROSSOVER_RATE.
     * Crossover involves selecting two parents from the population using tournament
     * selection, selecting a random
     * crossover point, and swapping the genes of the parents to produce two
     * offspring.
     */
    private void crossover() {
        final List<Chromosome> newPopulation = new ArrayList<>();

        // Loop to create offspring in pairs
        for (int i = 0; i < POPULATION_SIZE; i += 2) {
            final Chromosome parent1 = tournamentSelection(2);
            final Chromosome parent2 = tournamentSelection(2);

            final Chromosome offspring1 = new Chromosome(GENE_LENGTH);
            final Chromosome offspring2 = new Chromosome(GENE_LENGTH);

            int crossoverPoint1 = random.nextInt(GENE_LENGTH);
            int crossoverPoint2 = random.nextInt(GENE_LENGTH);

            // Ensure crossoverPoint1 is less than crossoverPoint2
            if (crossoverPoint1 > crossoverPoint2) {
                final int temp = crossoverPoint1;
                crossoverPoint1 = crossoverPoint2;
                crossoverPoint2 = temp;
            }

            if (random.nextDouble() <= CROSSOVER_RATE) {
                for (int j = 0; j < GENE_LENGTH; j++) {
                    if (j < crossoverPoint1 || j > crossoverPoint2) {
                        offspring1.getGenes()[j] = parent1.getGenes()[j];
                        offspring1.getRotations()[j] = parent1.getRotations()[j];

                        offspring2.getGenes()[j] = parent2.getGenes()[j];
                        offspring2.getRotations()[j] = parent2.getRotations()[j];
                    } else {
                        offspring1.getGenes()[j] = parent2.getGenes()[j];
                        offspring1.getRotations()[j] = parent2.getRotations()[j];

                        offspring2.getGenes()[j] = parent1.getGenes()[j];
                        offspring2.getRotations()[j] = parent1.getRotations()[j];
                    }
                }
            } else {
                offspring1.setGenes(parent1.getGenes().clone());
                offspring1.setRotations(parent1.getRotations().clone());

                offspring2.setGenes(parent2.getGenes().clone());
                offspring2.setRotations(parent2.getRotations().clone());
            }

            if (isValid(offspring1)) {
                newPopulation.add(offspring1);
            } else {
                final Chromosome offspringRepaired = new Chromosome(GENE_LENGTH);
                newPopulation.add(offspringRepaired);
            }

            if (isValid(offspring2)) {
                newPopulation.add(offspring2);
            } else {
                final Chromosome offspringRepaired2 = new Chromosome(GENE_LENGTH);
                newPopulation.add(offspringRepaired2);
            }
        }

        population = newPopulation;
    }

    /**
     * Applies mutation to the chromosomes in the population with a probability
     * defined by the mutation rate. Mutation involves randomly changing the type
     * and rotation of genes in a chromosome.
     */
    private void mutation() {
        for (final Chromosome chromo : population) {
            if (random.nextDouble() < MUTATION_RATE) {
                final int index = random.nextInt(GENE_LENGTH);
                final String[] types = {"A", "B", "C"};
                final String newType = types[random.nextInt(types.length)];

                chromo.getGenes()[index] = newType;

                final ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
                final int newRotation = random.nextInt(shapesAndRotations.rotationNum(newType));
                chromo.getRotations()[index] = newRotation;
            }
        }
    }

    /**
     * Checks the validity of a chromosome by verifying if it can be placed in the
     * cargo space and has a positive total value.
     *
     * @param chromo The chromosome to be validated.
     * @return true if the chromosome is valid; otherwise, false.
     */
    private boolean isValid(final Chromosome chromo) {
        final ShapesAndRotations shapes = new ShapesAndRotations();
        int totalValue = 0;
        final CargoSpace cargoSpace = new CargoSpace();
        final int[][][] occupied = cargoSpace.getOccupied();

        for (int x = 0; x < occupied.length; x++) {
            for (int y = 0; y < occupied[0].length; y++) {
                for (int z = 0; z < occupied[0][0].length; z++) {
                    for (int i = 0; i < chromo.getGenes().length; i++) {
                        final String gene = chromo.getGenes()[i];
                        final int rotation = chromo.getRotationFromGene(i);
                        final int[][][] shape = shapes.getShape(gene, rotation);
                        if (cargoSpace.canPlace(shape, x, y, z)) {
                            if ("A".equals(gene)) {
                                totalValue += 3;
                            } else if ("B".equals(gene)) {
                                totalValue += 4;
                            } else if ("C".equals(gene)) {
                                totalValue += 5;
                            }
                        }
                    }
                }
            }
        }

        return totalValue > 0;
    }

    /**
     * Applies the best solution (chromosome) found by the genetic algorithm to the cargo space.
     */
    private void applyBestSolution() {
        if (bestChromosome == null) {
            System.out.println("No optimal solution found.");
            return;
        }
        final CargoSpace bestCargoSpace = new CargoSpace();
        final ShapesAndRotations shapes = new ShapesAndRotations();
        final int[][][] occupied = bestCargoSpace.getOccupied();

        for (int i = 0; i < bestChromosome.getGenes().length; i++) {
            final String gene = bestChromosome.getGenes()[i];
            final int rotation = bestChromosome.getRotationFromGene(i);
            final int[][][] shape = shapes.getShape(gene, rotation);
            
            for (int x = 0; x < occupied.length; x++) {
                for (int y = 0; y < occupied[0].length; y++) {
                    for (int z = 0; z < occupied[0][0].length; z++) {
                        if (bestCargoSpace.canPlace(shape, x, y, z)) {
                            bestCargoSpace.placeParcel(shape, x, y, z);
                            break;
                        }
                    }
                }
            }
        }

        cargoSpace.setOccupied(occupied);
        System.out.println(Arrays.deepToString(cargoSpace.getOccupied()));
    }
}
