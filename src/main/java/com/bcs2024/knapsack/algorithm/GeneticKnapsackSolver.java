package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.renderer.UI;
import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GeneticKnapsackSolver {

    private final double MUTATION_RATE = 0.3;
    private final double CROSSOVER_RATE = 0.8;
    private final int MAX_GENERATIONS = 4;
    private final Random random = new Random();
    private final int POPULATION_SIZE = 100;
    private List<Chromosome> population;
    private int[] bestSolutionRotation;
    private String[] bestSolutionGene;
    private Chromosome bestChromosome;
    private CargoSpace cargoSpace = UI.cargoSpace;

    public static void main(final String[] args) {
        final GeneticKnapsackSolver solver = new GeneticKnapsackSolver();
        solver.solve();
    }

    public void solve() {
        initializePopulation();

        for (int i = 0; i < MAX_GENERATIONS; i++) {
            evaluateFitness(population);
            crossover();
            mutation();
            System.out.println("Generation: " + i + " done. -------------------------------------------------------------------------------" + "\n");
        }

        System.out.println("Rotation: " + Arrays.toString(bestSolutionRotation));
        System.out.println("Genes: " + Arrays.toString(bestSolutionGene));
        // applyBestSolution();
    }

    private void initializePopulation() {
        population = new ArrayList<>(POPULATION_SIZE);
        final String[] parcelTypes = {"A", "B", "C"}; // Define available parcel types
        final ShapesAndRotations shapesAndRotations = new ShapesAndRotations(); // To get the number of rotations

        for (int i = 0; i < POPULATION_SIZE; i++) {
            final Chromosome chromosome = new Chromosome();
            final String[] genes = chromosome.getGenes();
            final int[] rotations = chromosome.getRotations();

            for (int j = 0; j < chromosome.getGenes().length; j++) {
                // Randomly assign a parcel type
                final String parcelType = parcelTypes[random.nextInt(parcelTypes.length)];
                genes[j] = parcelType;

                // Randomly assign a rotation
                final int maxRotations = shapesAndRotations.rotationNum(parcelType);
                final int rotation = random.nextInt(maxRotations);
                rotations[j] = rotation;
            }

            chromosome.setGenes(genes);
            chromosome.setRotations(rotations);
            population.add(chromosome);
        }
    }

    private void evaluateFitness(final List<Chromosome> population) {
        final ShapesAndRotations shapes = new ShapesAndRotations();
        // final List<Chromosome> toRemove = new ArrayList<>();  // List to keep track of chromosomes to be removed

        for (final Chromosome chromo : population) {
            int parcelsUnusedCounter = 0;
            int totalValue = 0;
            int countA = 0;
            int countB = 0;
            int countC = 0;
            final CargoSpace localCargoSpace = new CargoSpace();
            final int[][][] occupied = localCargoSpace.getOccupied();
            //final boolean[] parcelUsed = new boolean[chromo.getGenes().length]; // To track which parcels are used TODO

            for (int i = 0; i < chromo.getGenes().length; i++) {
                for (int x = 0; x < occupied.length; x++) {
                    for (int y = 0; y < occupied[0].length; y++) {
                        for (int z = 0; z < occupied[0][0].length; z++) {
                            final String gene = chromo.getGenes()[i];
                            final int rotation = chromo.getRotationFromGene(i);
                            final int[][][] shape = shapes.getShape(gene, rotation);

                            if (localCargoSpace.canPlace(shape, x, y, z, occupied)) {
                                parcelsUnusedCounter++;
                                localCargoSpace.placeParcel(shape, x, y, z, occupied);

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

                                x = occupied.length;
                                y = occupied[0].length;
                                z = occupied[0][0].length;
                            }
                        }
                    }
                }
            }

//            if (parcelsUnusedCounter != chromo.getGenes().length - 1) {
//                System.out.println("Parcel couldn't be placed.");
//            } else {
//                System.out.println("Parcel placed.");
//            }

            //if (parcelsUnused) {
            // Optional: Add the chromosome to the toRemove list if you want to remove chromosomes with unused parcels
            //toRemove.add(chromo);
            //}

            chromo.setFitness(totalValue);

            if (parcelsUnusedCounter == chromo.getGenes().length - 1) {
                System.out.println("Chromosome Fitness: " + chromo.getFitness());
                System.out.println(Arrays.toString(chromo.getRotations()));
                System.out.println(Arrays.toString(chromo.getGenes()));
                System.out.println("A: " + countA + " B: " + countB + " C: " + countC);
                System.out.println("Length: " + (chromo.getGenes().length - 1));
            }
        }

        // population.removeAll(toRemove);
    }

//    private void evaluateFitnessd(final List<Chromosome> population) {
//        final ShapesAndRotations shapes = new ShapesAndRotations();
//
//        for (final Chromosome chromo : population) {
//            int totalValue = 0;
//            int countA = 0;
//            int countB = 0;
//            int countC = 0;
//            final CargoSpace localCargoSpace = new CargoSpace();
//            final boolean[] parcelUsed = new boolean[chromo.getGenes().length]; // To track which parcels are used
//
//            for (int i = 0; i < chromo.getGenes().length; i++) {
//                boolean parcelPlaced = false;
//                final String gene = chromo.getGenes()[i];
//                final int rotation = chromo.getRotationFromGene(i);
//                final int[][][] shape = shapes.getShape(gene, rotation);
//
//                for (int x = 0; x < localCargoSpace.getWidth() && !parcelPlaced; x++) {
//                    for (int y = 0; y < localCargoSpace.getHeight() && !parcelPlaced; y++) {
//                        for (int z = 0; z < localCargoSpace.getLength() && !parcelPlaced; z++) {
//                            if (localCargoSpace.canPlace(shape, x, y, z, localCargoSpace.getOccupied())) {
//                                localCargoSpace.placeParcel(shape, x, y, z, localCargoSpace.getOccupied());
//                                parcelPlaced = true;
//                                parcelUsed[i] = true; // Mark this parcel as used
//
//                                switch (gene) {
//                                    case "A" -> {
//                                        totalValue += 3;
//                                        countA++;
//                                    }
//                                    case "B" -> {
//                                        totalValue += 4;
//                                        countB++;
//                                    }
//                                    case "C" -> {
//                                        totalValue += 5;
//                                        countC++;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                if (!parcelPlaced) {
//                    System.out.println("Parcel " + gene + " couldn't be placed.");
//                }
//            }
//
//            // Count unused parcels
//            //final int unusedParcels = (int) Arrays.stream(parcelUsed).filter(used -> !used).count();
//
//            // Calculate fitness, optionally penalizing for unused parcels
//            final int penaltyPerUnusedParcel = 2; // Define penalty for each unused parcel
//            final int fitness = totalValue - (unusedParcels * penaltyPerUnusedParcel);
//            chromo.setFitness(fitness);
//
//            // Update best solution if current chromosome has a better fitness
//            if (bestChromosome == null || chromo.getFitness() > bestChromosome.getFitness()) {
//                bestChromosome = chromo;
//                bestSolutionRotation = Arrays.copyOf(chromo.getRotations(), chromo.getRotations().length);
//                bestSolutionGene = Arrays.copyOf(chromo.getGenes(), chromo.getGenes().length);
//            }
//
//            // Logging
//            System.out.println("Chromosome Fitness: " + chromo.getFitness());
//            System.out.println("Genes: " + Arrays.toString(chromo.getGenes()));
//            System.out.println("Rotations: " + Arrays.toString(chromo.getRotations()));
//            System.out.println("Counts - A: " + countA + ", B: " + countB + ", C: " + countC);
//            System.out.println("Unused Parcels: " + unusedParcels);
//        }
//    }


   /* private int getFitness(final int[][][] occupied, final int totalValue) {
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
    }*/

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

    private void crossover() {
        final List<Chromosome> newPopulation = new ArrayList<>();

        // Loop to create offspring in pairs
        for (int i = 0; i < POPULATION_SIZE; i += 2) {
            final Chromosome parent1 = tournamentSelection(2);
            final Chromosome parent2 = tournamentSelection(2);

            final List<String> bestSubstring1 = bestSubstring(parent1.getGenes());
            final List<String> bestSubstring2 = bestSubstring(parent2.getGenes());

            System.out.println("Length: " + bestSubstring1.size());
            System.out.println("Length: " + bestSubstring2.size());

            final int[] rotations1 = parent1.getRotations();
            final int[] rotations2 = parent2.getRotations();

            final int start = Integer.parseInt(bestSubstring1.get(bestSubstring1.size() - 1));
            final int end = start + bestSubstring1.size() - 1;

            final int[] subArray = Arrays.copyOfRange(rotations1, start, end);

            final int start2 = Integer.parseInt(bestSubstring2.get(bestSubstring2.size() - 1));
            final int end2 = start2 + bestSubstring2.size() - 1;

            final int[] subArray2 = Arrays.copyOfRange(rotations2, start2, end2);

            bestSubstring1.remove(bestSubstring1.size() - 1);
            bestSubstring2.remove(bestSubstring2.size() - 1);

            final List<String> concatenated = Stream.concat(bestSubstring1.stream(), bestSubstring2.stream()).toList();
            final int[] concatenatedRotations = IntStream.concat(Arrays.stream(subArray), Arrays.stream(subArray2)).toArray();

            System.out.println("Best Substring 1: " + bestSubstring1);
            System.out.println("Best Substring 2: " + bestSubstring2);
            System.out.println("Concatenated: " + concatenated);
            System.out.println("Concatenated Rotations: " + Arrays.toString(concatenatedRotations));

            // Determine the length for the offspring chromosomes (e.g., average length of parents)
            final int offspringLength = (parent1.getGenes().length + parent2.getGenes().length) / 2;

            final Chromosome offspring1 = new Chromosome(offspringLength);
            final Chromosome offspring2 = new Chromosome(offspringLength);

            int crossoverPoint1 = random.nextInt(offspringLength);
            int crossoverPoint2 = random.nextInt(offspringLength);

            // Ensure crossoverPoint1 is less than crossoverPoint2
            if (crossoverPoint1 > crossoverPoint2) {
                final int temp = crossoverPoint1;
                crossoverPoint1 = crossoverPoint2;
                crossoverPoint2 = temp;
            }

            if (random.nextDouble() <= CROSSOVER_RATE) {
                for (int j = 0; j < offspringLength; j++) {
                    if (j < crossoverPoint1 || j > crossoverPoint2) {
                        if (j < parent1.getGenes().length) {
                            offspring1.getGenes()[j] = parent1.getGenes()[j];
                            offspring1.getRotations()[j] = parent1.getRotations()[j];
                        }

                        if (j < parent2.getGenes().length) {
                            offspring2.getGenes()[j] = parent2.getGenes()[j];
                            offspring2.getRotations()[j] = parent2.getRotations()[j];
                        }
                    } else {
                        if (j < parent2.getGenes().length) {
                            offspring1.getGenes()[j] = parent2.getGenes()[j];
                            offspring1.getRotations()[j] = parent2.getRotations()[j];
                        }

                        if (j < parent1.getGenes().length) {
                            offspring2.getGenes()[j] = parent1.getGenes()[j];
                            offspring2.getRotations()[j] = parent1.getRotations()[j];
                        }
                    }
                }
            } else {
                System.arraycopy(parent1.getGenes(), 0, offspring1.getGenes(), 0, Math.min(offspringLength, parent1.getGenes().length));
                System.arraycopy(parent1.getRotations(), 0, offspring1.getRotations(), 0, Math.min(offspringLength, parent1.getRotations().length));
                System.arraycopy(parent2.getGenes(), 0, offspring2.getGenes(), 0, Math.min(offspringLength, parent2.getGenes().length));
                System.arraycopy(parent2.getRotations(), 0, offspring2.getRotations(), 0, Math.min(offspringLength, parent2.getRotations().length));
            }

            if (isValid(offspring1)) {
                newPopulation.add(offspring1);
            } else {
                final Chromosome offspringRepaired = new Chromosome(offspringLength);
                newPopulation.add(offspringRepaired);
            }

            if (isValid(offspring2)) {
                newPopulation.add(offspring2);
            } else {
                final Chromosome offspringRepaired2 = new Chromosome(offspringLength);
                newPopulation.add(offspringRepaired2);
            }
        }

        population = newPopulation;
    }

    private List<String> bestSubstring(final String[] string) {
        int highestValue = 0;
        int start = 0;
        List<String> bestSubstring = new ArrayList<>();

        for (int i = 0; i < string.length - 1; i++) {
            int value = 0;
            final int bound = Math.min(i + 30, string.length);

            for (int j = i; j < bound; j++) {
                value += getValue(string[j]);
            }
            if (value > highestValue) {
                highestValue = value;
                // Create a new ArrayList from the range of the array
                bestSubstring = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(string, i, string.length)));
                start = i;
            }
        }
        bestSubstring.add(String.valueOf(start)); // Now this should work
        return bestSubstring;
    }

    private int getValue(final String string) {
        return switch (string) {
            case "A" -> 3;
            case "B" -> 4;
            case "C" -> 5;
            default -> 0;
        };
    }

    private void mutation() {
        for (final Chromosome chromo : population) {
            if (random.nextDouble() < MUTATION_RATE) {
                final int index = random.nextInt(chromo.getGenes().length);
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
                        if (cargoSpace.canPlace(shape, x, y, z, occupied)) {
                            if ("A".equals(gene)) {
                                totalValue += 3;
                            } else if ("B".equals(gene)) {
                                totalValue += 4;
                            } else if ("C".equals(gene)) {
                                totalValue += 5;
                            }
                        } else {
                            return false;
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

                        if (bestCargoSpace.canPlace(shape, x, y, z, occupied)) {
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

        System.out.println("Best solution: " + bestChromosome.getFitness());
        cargoSpace.setOccupied(occupied); // Reflect the best solution in the CargoSpace
        System.out.println(Arrays.deepToString(cargoSpace.getOccupied()));
    }

}
