package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.renderer.UI;
import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GeneticKnapsackSolver {

    private final double MUTATION_RATE = 0.3;
    private final double CROSSOVER_RATE = 0.8;
    private final int MAX_GENERATIONS = 2;
    private final int GENE_LENGTH = 54;
    private final Random random = new Random();
    private final int POPULATION_SIZE = 100;
    private List<Chromosome> population;
    private int[] bestSolutionRotation;
    private String[] bestSolutionGene;

    private Chromosome bestChromosome;

    private CargoSpace cargoSpace = UI.cargoSpace;

    public static void main(final String[] args) {
        /*final List<Parcel> parcels = new ArrayList<>();
        parcels.add(new Parcel("A"));
        parcels.add(new Parcel("B"));
        parcels.add(new Parcel("C"));*/

        final GeneticKnapsackSolver solver = new GeneticKnapsackSolver();
        solver.solve();
    }

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

    private void initializePopulation() {
        population = new ArrayList<>(POPULATION_SIZE);

        for (int i = 0; i < POPULATION_SIZE; i++) {
            final Chromosome chromosome = new Chromosome(GENE_LENGTH);
            population.add(chromosome);
        }
    }

    private void evaluateFitness(final List<Chromosome> population) {
        final ShapesAndRotations shapes = new ShapesAndRotations();

        for (final Chromosome chromo : population) {
            int totalValue = 0;
            int countA = 0;
            int countB = 0;
            int countC = 0;
            final CargoSpace localCargoSpace = new CargoSpace();
            final int[][][] occupied = localCargoSpace.getOccupied();
            final boolean[] parcelUsed = new boolean[GENE_LENGTH]; // To track which parcels are used

            // Get a sorted list of parcels based on their volume
            final List<String> sortedParcelTypes = sortParcelsBySize(chromo.getGenes());

            for (int x = 0; x < occupied.length; x++) {
                for (int y = 0; y < occupied[0].length; y++) {
                    for (int z = 0; z < occupied[0][0].length; z++) {
                        for (int i = 0; i < chromo.getGenes().length; i++) {
                            final String gene = chromo.getGenes()[i];
                            final int rotation = chromo.getRotationFromGene(i);
                            final int[][][] shape = shapes.getShape(gene, rotation);

                            if (localCargoSpace.canPlace(shape, x, y, z)) {
                                localCargoSpace.placeParcel(shape, x, y, z, occupied);
                                parcelUsed[i] = true; // Mark this parcel as used

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
                            }
                        }
                    }
                }
            }

            final int fitness = getFitness(occupied, totalValue);
            chromo.setFitness(fitness);

            // Store the best solution
            if (bestChromosome == null || chromo.getFitness() > bestChromosome.getFitness()) {
                bestChromosome = chromo;
                bestSolutionRotation = chromo.getRotations();
                bestSolutionGene = chromo.getGenes();
            }

            System.out.println("Chromosome Fitness: " + chromo.getFitness());
            System.out.println(Arrays.toString(chromo.getRotations()));
            System.out.println(Arrays.toString(chromo.getGenes()));
            System.out.println("A: " + countA + " B: " + countB + " C: " + countC);
        }
    }

    private int getFitness(final int[][][] occupied, final int totalValue) {
        int emptySlots = 0;
        for (final int[][] slice : occupied) {
            for (final int[] row : slice) {
                for (final int cell : row) {
                    if (cell == -1) {
                        emptySlots++;
                    }
                }
            }
        }

        // Parameters for weighing the importance of space utilization
        final double spaceUtilizationWeight = 0.5; // Can be adjusted
        final double valueWeight = 1.0 - spaceUtilizationWeight;

        return (int) (valueWeight * totalValue - spaceUtilizationWeight * emptySlots);
    }

    private List<String> sortParcelsBySize(final String[] parcelTypes) {
        final ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
        return Arrays.stream(parcelTypes)
                .sorted((parcel1, parcel2) -> {
                    final int volume1 = Arrays.stream(shapesAndRotations.getShape(parcel1, 0)).mapToInt(slice -> Arrays.stream(slice).mapToInt(row -> Arrays.stream(row).sum()).sum()).sum();
                    final int volume2 = Arrays.stream(shapesAndRotations.getShape(parcel2, 0)).mapToInt(slice -> Arrays.stream(slice).mapToInt(row -> Arrays.stream(row).sum()).sum()).sum();
                    return Integer.compare(volume2, volume1); // Sort in descending order of volume
                }).collect(Collectors.toList());
    }


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

    // private Chromosome repair(Chromosome chromosome) {
    //   int currentPopulationSize = population.size();
    //   int tournamentSize = Math.min(2, currentPopulationSize); // Adjust based on your requirement

    //   while (true) {
    //     Chromosome newChromo = tournamentSelection(tournamentSize);
    //     if (isValid(newChromo)) {
    //       return newChromo;
    //     }
    //   }
    // }

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

    private void applyBestSolution() {
        if (bestChromosome == null) {
            System.out.println("No optimal solution found.");
            return;
        }
        final CargoSpace bestCargoSpace = new CargoSpace();
        final ShapesAndRotations shapes = new ShapesAndRotations();
        final int[][][] occupied = bestCargoSpace.getOccupied();

        // Use the best chromosome to set the cargo space fields
        for (int i = 0; i < bestChromosome.getGenes().length; i++) {
            final String gene = bestChromosome.getGenes()[i];
            final int rotation = bestChromosome.getRotationFromGene(i);
            final int[][][] shape = shapes.getShape(gene, rotation);

            boolean placed = false;
            for (int x = 0; x < occupied.length && !placed; x++) {
                for (int y = 0; y < occupied[0].length && !placed; y++) {
                    for (int z = 0; z < occupied[0][0].length && !placed; z++) {
                        if (bestCargoSpace.canPlace(shape, x, y, z)) {
                            bestCargoSpace.placeParcel(shape, x, y, z, occupied);
                            placed = true; // Parcel placed, move to next parcel
                        }
                    }
                }
            }

            if (!placed) {
                System.out.println("Could not place parcel: " + gene);
            }
        }

        cargoSpace.setOccupied(occupied); // Reflect the best solution in the CargoSpace
        System.out.println(Arrays.deepToString(cargoSpace.getOccupied()));
    }

}
