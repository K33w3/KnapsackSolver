//package com.bcs2024.knapsack.algorithm;
//
//import com.bcs2024.knapsack.model.CargoSpace;
//import com.bcs2024.knapsack.model.Parcel;
//import com.bcs2024.knapsack.model.ParcelPlacement;
//import com.bcs2024.knapsack.renderer.UI;
//import com.bcs2024.knapsack.util.ShapesAndRotations;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//
///**
// * The GeneticKnapsackSolver class represents a genetic algorithm-based solver for the knapsack problem.
// * It is specifically tailored for solving problems related to packing parcels into a cargo space
// * in a manner that maximizes the total value of the parcels while ensuring they fit within the cargo space.
// */
//public class GeneticKnapsackSolver {
//
//    private final double MUTATION_RATE = 0.3;
//    private final double CROSSOVER_RATE = 0.8;
//    private final int MAX_GENERATIONS = 25;
//
//    private final int TOURNAMENT_SIZE = 4;
//    private final int GENE_LENGTH = 100;
//    private final Random random = new Random();
//    private final int POPULATION_SIZE = 200;
//    private List<Chromosome> population;
//    private int[] bestSolutionRotation;
//    private String[] bestSolutionGene;
//
//    private Chromosome bestChromosome;
//
//    private CargoSpace cargoSpace = UI.cargoSpace;
//
//    private final int numberOfElites = (int) (POPULATION_SIZE * 0.02); // For example, 5% of the population size
//
//    /**
//     * The main method to execute the genetic algorithm.
//     * It initializes the population, evaluates fitness, and applies genetic operators like crossover and mutation
//     * over a number of generations to evolve solutions towards maximizing the value of packed parcels.
//     */
//    public static void main(final String[] args) {
//        final GeneticKnapsackSolver solver = new GeneticKnapsackSolver();
//        solver.solve();
//    }
//
//    /**
//     * Solves the knapsack problem using a genetic algorithm.
//     * This involves initializing a population of solutions, evaluating their fitness, and iteratively applying
//     * genetic operators (selection, crossover, mutation) to evolve the population towards optimal solutions.
//     */
//    public void solve() {
//        bestSolutionGene = new String[GENE_LENGTH];
//        bestSolutionRotation = new int[GENE_LENGTH];// For example, 5% of the population size
//
//        initializePopulation();
//        for (int i = 0; i < MAX_GENERATIONS; i++) {
//            evaluateFitness(population);
//
//            population.sort((chromosome1, chromosome2) -> chromosome2.getFitness() - chromosome1.getFitness());
//
//            // Preserve the top solutions
//            final List<Chromosome> elites = new ArrayList<>();
//            for (int j = 0; j < numberOfElites; j++) {
//                elites.add(new Chromosome(population.get(j)));
//            }
//
//            crossover();
//            mutation();
//
//            // Add elites back into the population
//            population.addAll(elites);
//
//            System.out.println("Generation: " + i + " done. -------------------------------------------------------------------------------" + "\n");
//        }
//
//        System.out.println("Rotation: " + Arrays.toString(bestSolutionRotation));
//        System.out.println("Genes: " + Arrays.toString(bestSolutionGene));
//        applyBestSolution();
//    }
//
//    /**
//     * Initializes the population of chromosomes for the genetic algorithm.
//     * Each chromosome represents a potential solution to the knapsack problem.
//     */
//    private void initializePopulation() {
//        population = new ArrayList<>(POPULATION_SIZE);
//
//        for (int i = 0; i < POPULATION_SIZE; i++) {
//            final Chromosome chromosome = new Chromosome(GENE_LENGTH);
//            population.add(chromosome);
//        }
//    }
//
//    /**
//     * Evaluates the fitness of each chromosome in the population.
//     * Fitness is determined based on how well the parcels are packed in the cargo space and the total value of the packed parcels.
//     */
//    private void evaluateFitness(final List<Chromosome> population) {
//        final ShapesAndRotations shapes = new ShapesAndRotations();
//
//        for (final Chromosome chromo : population) {
//            int totalValue = 0;
//            final CargoSpace localCargoSpace = new CargoSpace();
//            final int[][][] occupied = localCargoSpace.getOccupied();
//            final boolean[] parcelUsed = new boolean[GENE_LENGTH]; // To track which parcels are used
//
//            // Get a sorted list of parcels based on their volume
//            final List<String> sortedParcelTypes = sortParcelsBySize(chromo.getGenes());
//
//            // Place parcels based on the sorted list
//            for (final String parcelType : sortedParcelTypes) {
//                for (int i = 0; i < chromo.getGenes().length; i++) {
//                    if (parcelType.equals(chromo.getGenes()[i]) && !parcelUsed[i]) {
//                        final ParcelPlacement bestPlacement = findBestFit(parcelType, occupied, shapes, localCargoSpace);
//                        if (bestPlacement != null) {
//                            localCargoSpace.placeParcel(bestPlacement.getParcel().getShape(), bestPlacement.getX(), bestPlacement.getY(), bestPlacement.getZ(), occupied);
//                            parcelUsed[i] = true; // Mark this parcel as used
//                            totalValue += bestPlacement.getValue(); // Add value based on parcel type
//                        }
//                    }
//                }
//            }
//
//            final int fitness = getFitness(occupied, totalValue);
//            chromo.setFitness(fitness);
//
//            // Store the best solution
//            if (bestChromosome == null || chromo.getFitness() > bestChromosome.getFitness()) {
//                bestChromosome = chromo;
//                bestSolutionRotation = chromo.getRotations();
//                bestSolutionGene = chromo.getGenes();
//            }
//
//            // Logging the fitness and gene details for the chromosome
//            System.out.println("Chromosome Fitness: " + chromo.getFitness());
//            System.out.println("Rotations: " + Arrays.toString(chromo.getRotations()));
//            System.out.println("Genes: " + Arrays.toString(chromo.getGenes()));
//        }
//    }
//
//    /**
//     * Calculates the fitness of a given cargo space configuration.
//     * The fitness is determined based on the total value of parcels and the utilization of space.
//     *
//     * @param occupied   The 3D array representing the occupied spaces in the cargo space.
//     * @param totalValue The total value of parcels placed in the cargo space.
//     * @return The calculated fitness value.
//     */
//    private int getFitness(final int[][][] occupied, final int totalValue) {
//        int emptySlots = 0;
//        for (final int[][] slice : occupied) {
//            for (final int[] row : slice) {
//                for (final int cell : row) {
//                    if (cell == -1) {
//                        emptySlots++;
//                    }
//                }
//            }
//        }
//
//        // Parameters for weighing the importance of space utilization
//        final double spaceUtilizationWeight = 0.5; // Can be adjusted
//        final double valueWeight = 1.0 - spaceUtilizationWeight;
//
//        return (int) (valueWeight * totalValue - spaceUtilizationWeight * emptySlots);
//    }
//
//    private List<String> sortParcelsBySize(final String[] parcelTypes) {
//        final ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
//        return Arrays.stream(parcelTypes)
//                .sorted((parcel1, parcel2) -> {
//                    final int volume1 = Arrays.stream(shapesAndRotations.getShape(parcel1, 0)).mapToInt(slice -> Arrays.stream(slice).mapToInt(row -> Arrays.stream(row).sum()).sum()).sum();
//                    final int volume2 = Arrays.stream(shapesAndRotations.getShape(parcel2, 0)).mapToInt(slice -> Arrays.stream(slice).mapToInt(row -> Arrays.stream(row).sum()).sum()).sum();
//                    return Integer.compare(volume2, volume1); // Sort in descending order of volume
//                }).collect(Collectors.toList());
//    }
//
//
//    /**
//     * Performs a tournament selection to select a chromosome from the population.
//     * A set of chromosomes is randomly chosen, and the chromosome with the highest fitness is selected.
//     *
//     * @param tournamentSize the number of chromosomes to be selected for the tournament.
//     * @return the winning chromosome with the highest fitness.
//     */
//    private Chromosome tournamentSelection(final int tournamentSize) {
//        final List<Chromosome> tournamentParticipants = new ArrayList<>();
//
//        if (tournamentSize > population.size() || tournamentSize < 1) {
//            throw new IllegalArgumentException("Tournament size should be between 1 and population size.");
//        }
//
//        for (int i = 0; i < tournamentSize; i++) {
//            tournamentParticipants.add(population.get(random.nextInt(population.size())));
//        }
//
//        Chromosome winner = tournamentParticipants.get(0);
//        for (final Chromosome participant : tournamentParticipants) {
//            if (participant.getFitness() > winner.getFitness()) {
//                winner = participant;
//            }
//        }
//
//        return winner;
//    }
//
//    private void crossover() {
//        final List<Chromosome> newPopulation = new ArrayList<>();
//
//        // Loop to create offspring in pairs
//        for (int i = 0; i < POPULATION_SIZE - numberOfElites; i += 2) {
//            final Chromosome parent1 = tournamentSelection(TOURNAMENT_SIZE);
//            final Chromosome parent2 = tournamentSelection(TOURNAMENT_SIZE);
//
//            final Chromosome offspring1 = new Chromosome(parent1);
//            final Chromosome offspring2 = new Chromosome(parent2);
//
//            int crossoverPoint1 = random.nextInt(GENE_LENGTH);
//            int crossoverPoint2 = random.nextInt(GENE_LENGTH);
//
//            // Ensure crossoverPoint1 is less than crossoverPoint2
//            if (crossoverPoint1 > crossoverPoint2) {
//                final int temp = crossoverPoint1;
//                crossoverPoint1 = crossoverPoint2;
//                crossoverPoint2 = temp;
//            }
//
//            if (random.nextDouble() <= CROSSOVER_RATE) {
//                for (int j = crossoverPoint1; j <= crossoverPoint2; j++) {
//                    offspring1.getGenes()[j] = parent2.getGenes()[j];
//                    offspring1.getRotations()[j] = parent2.getRotations()[j];
//
//                    offspring2.getGenes()[j] = parent1.getGenes()[j];
//                    offspring2.getRotations()[j] = parent1.getRotations()[j];
//                }
//            }
//
//            // Add a method for repairing invalid offspring if necessary
//            if (!isValid(offspring1)) {
//                repairChromosome(offspring1);
//            }
//            newPopulation.add(offspring1);
//
//            if (!isValid(offspring2)) {
//                repairChromosome(offspring2);
//            }
//            newPopulation.add(offspring2);
//        }
//
//        population = newPopulation;
//    }
//
//    /**
//     * Repairs an invalid chromosome. This method ensures that the chromosome represents a valid packing solution where parcels don't overlap and fit within the cargo space.
//     * The method tries to preserve as much genetic information as possible.
//     *
//     * @param chromosome the chromosome to repair.
//     */
//    private void repairChromosome(final Chromosome chromosome) {
//        final ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
//        final CargoSpace localCargoSpace = new CargoSpace(); // Create a local cargo space to test the placement of parcels
//        final boolean[] parcelUsed = new boolean[chromosome.getGenes().length]; // To track which parcels have been placed
//
//        // Attempt to place each parcel in the chromosome in the cargo space
//        for (int i = 0; i < chromosome.getGenes().length; i++) {
//            if (!parcelUsed[i]) {
//                final String parcelType = chromosome.getGenes()[i];
//                final int rotation = chromosome.getRotations()[i];
//                final int[][][] shape = shapesAndRotations.getShape(parcelType, rotation);
//
//                // Find a suitable position to place the parcel
//                for (int x = 0; x < localCargoSpace.getWidth(); x++) {
//                    for (int y = 0; y < localCargoSpace.getHeight(); y++) {
//                        for (int z = 0; z < localCargoSpace.getLength(); z++) {
//                            if (localCargoSpace.canPlace(shape, x, y, z)) {
//                                localCargoSpace.placeParcel(shape, x, y, z, localCargoSpace.getOccupied());
//                                parcelUsed[i] = true; // Mark this parcel as used
//                                break; // Break the loop once the parcel is placed
//                            }
//                        }
//                        if (parcelUsed[i]) break; // Break the loop once the parcel is placed
//                    }
//                    if (parcelUsed[i]) break; // Break the loop once the parcel is placed
//                }
//
//                if (!parcelUsed[i]) {
//                    // If the parcel could not be placed, remove it from the chromosome (set to a null or empty value)
//                    chromosome.getGenes()[i] = null; // Assuming null indicates an empty slot. Adjust based on your implementation.
//                    chromosome.getRotations()[i] = 0;
//                }
//            }
//        }
//
//        // Re-evaluate the fitness of the chromosome after repair
//        evaluateFitnessForSingleChromosome(chromosome, localCargoSpace);
//    }
//
//    /**
//     * Evaluates the fitness of a single chromosome.
//     * This can be a wrapper or a modified version of your existing evaluateFitness method that handles a single chromosome.
//     *
//     * @param chromosome the chromosome to evaluate.
//     * @param cargoSpace the cargo space after the chromosome's parcels have been placed.
//     */
//    private void evaluateFitnessForSingleChromosome(final Chromosome chromosome, final CargoSpace cargoSpace) {
//        final ShapesAndRotations shapes = new ShapesAndRotations();
//        int totalValue = 0;
//        final int[][][] occupied = cargoSpace.getOccupied();
//        final boolean[] parcelUsed = new boolean[chromosome.getGenes().length]; // To track which parcels are used
//
//        // Get a sorted list of parcels based on their volume
//        final List<String> sortedParcelTypes = sortParcelsBySize(chromosome.getGenes());
//
//        // Place parcels based on the sorted list
//        for (final String parcelType : sortedParcelTypes) {
//            for (int i = 0; i < chromosome.getGenes().length; i++) {
//                if (parcelType.equals(chromosome.getGenes()[i]) && !parcelUsed[i]) {
//                    final ParcelPlacement bestPlacement = findBestFit(parcelType, occupied, shapes, cargoSpace);
//                    if (bestPlacement != null) {
//                        cargoSpace.placeParcel(bestPlacement.getParcel().getShape(), bestPlacement.getX(), bestPlacement.getY(), bestPlacement.getZ(), occupied);
//                        parcelUsed[i] = true; // Mark this parcel as used
//                        totalValue += bestPlacement.getValue(); // Add value based on parcel type
//                    }
//                }
//            }
//        }
//
//        final int fitness = getFitness(occupied, totalValue);
//        chromosome.setFitness(fitness);
//
//        // Logging the fitness and gene details for the chromosome
//        System.out.println("Single Chromosome Fitness: " + chromosome.getFitness());
//        System.out.println("Rotations: " + Arrays.toString(chromosome.getRotations()));
//        System.out.println("Genes: " + Arrays.toString(chromosome.getGenes()));
//    }
//
//
//    /**
//     * Performs mutation on the population to maintain genetic diversity.
//     * This method applies mutation based on the MUTATION_RATE and alters the genes of the chromosomes randomly.
//     */
//    private void mutation() {
//        for (final Chromosome chromo : population) {
//            if (random.nextDouble() < MUTATION_RATE) {
//                final int index = random.nextInt(GENE_LENGTH);
//                final String[] types = {"A", "B", "C"};
//                final String newType = types[random.nextInt(types.length)];
//
//                chromo.getGenes()[index] = newType;
//
//                final ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
//                final int newRotation = random.nextInt(shapesAndRotations.rotationNum(newType));
//                chromo.getRotations()[index] = newRotation;
//            }
//        }
//    }
//
//    /**
//     * Validates if a chromosome represents a valid solution to the knapsack problem.
//     * This method checks if the parcels can be placed in the cargo space without overlapping and within the space limits.
//     *
//     * @param chromo the chromosome to validate.
//     * @return true if the chromosome represents a valid solution, false otherwise.
//     */
//    private boolean isValid(final Chromosome chromo) {
//        final ShapesAndRotations shapes = new ShapesAndRotations();
//        int totalValue = 0;
//        final CargoSpace cargoSpace = new CargoSpace();
//        final int[][][] occupied = cargoSpace.getOccupied();
//
//        for (int x = 0; x < occupied.length; x++) {
//            for (int y = 0; y < occupied[0].length; y++) {
//                for (int z = 0; z < occupied[0][0].length; z++) {
//                    for (int i = 0; i < chromo.getGenes().length; i++) {
//                        final String gene = chromo.getGenes()[i];
//                        final int rotation = chromo.getRotationFromGene(i);
//                        final int[][][] shape = shapes.getShape(gene, rotation);
//                        if (cargoSpace.canPlace(shape, x, y, z)) {
//                            switch (gene) {
//                                case "A" -> totalValue += 3;
//                                case "B" -> totalValue += 4;
//                                case "C" -> totalValue += 5;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return totalValue > 0;
//    }
//
//    /**
//     * Finds the best fit for a parcel in the cargo space.
//     * This method tries to place a parcel in the cargo space in a way that minimizes the number of empty slots around it.
//     *
//     * @param parcelType         the type of the parcel to be placed.
//     * @param cargoSpace         the 3D array representing the occupied spaces in the cargo space.
//     * @param shapesAndRotations the object containing the shapes and possible rotations of parcels.
//     * @param localCargoSpace    the local representation of the cargo space.
//     * @return the best parcel placement found.
//     */
//    private ParcelPlacement findBestFit(final String parcelType, final int[][][] cargoSpace, final ShapesAndRotations shapesAndRotations, final CargoSpace localCargoSpace) {
//        int bestFitScore = Integer.MAX_VALUE;
//        ParcelPlacement bestPlacement = null;
//
//        for (int rotation = 0; rotation < shapesAndRotations.rotationNum(parcelType); rotation++) {
//            final int[][][] shape = shapesAndRotations.getShape(parcelType, rotation);
//            for (int x = 0; x < cargoSpace.length; x++) {
//                for (int y = 0; y < cargoSpace[0].length; y++) {
//                    for (int z = 0; z < cargoSpace[0][0].length; z++) {
//                        if (localCargoSpace.canPlace(shape, x, y, z)) {
//                            final int fitScore = calculateFitScore(shape, x, y, z, cargoSpace);
//                            if (fitScore < bestFitScore) {
//                                final Parcel parcel = new Parcel(parcelType, shape);
//                                bestFitScore = fitScore;
//                                bestPlacement = new ParcelPlacement(parcel, x, y, z, fitScore);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return bestPlacement;
//    }
//
//    /**
//     * Calculates the fit score for placing a parcel at a given position in the cargo space.
//     * The fit score is determined based on the number of empty slots around the parcel.
//     *
//     * @param shape      the shape of the parcel.
//     * @param startX     the starting X coordinate for placing the parcel.
//     * @param startY     the starting Y coordinate for placing the parcel.
//     * @param startZ     the starting Z coordinate for placing the parcel.
//     * @param cargoSpace the 3D array representing the occupied spaces in the cargo space.
//     * @return the calculated fit score.
//     */
//    private int calculateFitScore(final int[][][] shape, final int startX, final int startY, final int startZ, final int[][][] cargoSpace) {
//        int emptySlots = 0;
//        for (int i = 0; i < shape.length; i++) {
//            for (int j = 0; j < shape[0].length; j++) {
//                for (int k = 0; k < shape[0][0].length; k++) {
//                    if (shape[i][j][k] != 0) {
//                        // Check surrounding spaces
//                        emptySlots += checkEmptySpacesAround(startX + k, startY + j, startZ + i, cargoSpace);
//                    }
//                }
//            }
//        }
//        return emptySlots;
//    }
//
//    /**
//     * Checks the empty spaces around a given position in the cargo space.
//     * This method is used to calculate the fit score for placing a parcel.
//     *
//     * @param x          the X coordinate of the position to check.
//     * @param y          the Y coordinate of the position to check.
//     * @param z          the Z coordinate of the position to check.
//     * @param cargoSpace the 3D array representing the occupied spaces in the cargo space.
//     * @return the number of empty slots around the given position.
//     */
//    private int checkEmptySpacesAround(final int x, final int y, final int z, final int[][][] cargoSpace) {
//        int emptySlots = 0;
//        final int[] dx = {-1, 1, 0, 0, 0, 0};
//        final int[] dy = {0, 0, -1, 1, 0, 0};
//        final int[] dz = {0, 0, 0, 0, -1, 1};
//
//        for (int i = 0; i < 6; i++) {
//            final int newX = x + dx[i];
//            final int newY = y + dy[i];
//            final int newZ = z + dz[i];
//            if (newX >= 0 && newX < cargoSpace.length &&
//                    newY >= 0 && newY < cargoSpace[0].length &&
//                    newZ >= 0 && newZ < cargoSpace[0][0].length &&
//                    cargoSpace[newX][newY][newZ] == -1) {
//                emptySlots++;
//            }
//        }
//        return emptySlots;
//    }
//
//    /**
//     * Applies the best solution found by the genetic algorithm to the cargo space.
//     * It updates the cargo space with the arrangement of parcels corresponding to the highest fitness chromosome.
//     */
//    private void applyBestSolution() {
//        if (bestChromosome == null) {
//            System.out.println("No optimal solution found.");
//            return;
//        }
//        final CargoSpace bestCargoSpace = new CargoSpace();
//        final ShapesAndRotations shapes = new ShapesAndRotations();
//        final int[][][] occupied = bestCargoSpace.getOccupied();
//
//        // Use the best chromosome to set the cargo space fields
//        for (int i = 0; i < bestChromosome.getGenes().length; i++) {
//            final String gene = bestChromosome.getGenes()[i];
//            final int rotation = bestChromosome.getRotationFromGene(i);
//            final int[][][] shape = shapes.getShape(gene, rotation);
//
//            boolean placed = false;
//            for (int x = 0; x < occupied.length && !placed; x++) {
//                for (int y = 0; y < occupied[0].length && !placed; y++) {
//                    for (int z = 0; z < occupied[0][0].length && !placed; z++) {
//                        if (bestCargoSpace.canPlace(shape, x, y, z)) {
//                            bestCargoSpace.placeParcel(shape, x, y, z, occupied);
//                            placed = true; // Parcel placed, move to next parcel
//                        }
//                    }
//                }
//            }
//
//            if (!placed) {
//                System.out.println("Could not place parcel: " + gene);
//            }
//        }
//
//        System.out.println("Best fitness: " + bestChromosome.getFitness());
//        cargoSpace.setOccupied(occupied); // Reflect the best solution in the CargoSpace
//        System.out.println(Arrays.deepToString(cargoSpace.getOccupied()));
//    }
//}
