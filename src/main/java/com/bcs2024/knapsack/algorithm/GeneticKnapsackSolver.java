package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.model.ParcelPlacement;
import com.bcs2024.knapsack.renderer.UI;
import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.*;

public class GeneticKnapsackSolver {

    private final double MUTATION_RATE = 0.3;

    private double dynamicMutationRate = MUTATION_RATE;
    private final double CROSSOVER_RATE = 0.8;
    private final int MAX_GENERATIONS = 10;
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
            final double currentDiversity = calculateDiversity(population);
            System.out.println("Diversity after generation " + i + ": " + currentDiversity);
            System.out.println("Generation: " + i + " done. -------------------------------------------------------------------------------" + "\n");
        }

        System.out.println("Rotation: " + Arrays.toString(bestSolutionRotation));
        System.out.println("Genes: " + Arrays.toString(bestSolutionGene));
        applyBestSolution();
    }

    private void initializePopulation() {
        population = new ArrayList<>(POPULATION_SIZE);
        final String[] parcelTypes = {"A", "B", "C"}; // Define available parcel types
        final ShapesAndRotations shapesAndRotations = new ShapesAndRotations(); // To get the number of rotations

        for (int i = 0; i < POPULATION_SIZE; i++) {
            final Chromosome chromosome = new Chromosome(GENE_LENGTH);
            final String[] genes = chromosome.getGenes();
            final int[] rotations = chromosome.getRotations();

            for (int j = 0; j < GENE_LENGTH; j++) {
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

        for (final Chromosome chromo : population) {
            int totalValue = 0;
            int countA = 0;
            int countB = 0;
            int countC = 0;
            final CargoSpace localCargoSpace = new CargoSpace();
            final int[][][] occupied = localCargoSpace.getOccupied();
            final boolean[] parcelUsed = new boolean[GENE_LENGTH]; // To track which parcels are used

            for (int x = 0; x < occupied.length; x++) {
                for (int y = 0; y < occupied[0].length; y++) {
                    for (int z = 0; z < occupied[0][0].length; z++) {
                        for (int i = 0; i < chromo.getGenes().length; i++) {
                            final String gene = chromo.getGenes()[i];
                            final int rotation = chromo.getRotationFromGene(i);
                            final int[][][] shape = shapes.getShape(gene, rotation);

                            final Parcel parcel = new Parcel(gene, shape);
                            final ParcelPlacement placement = new ParcelPlacement(parcel, x, y, z);

                            if (localCargoSpace.canPlace(placement)) {
                                localCargoSpace.placeParcel(placement/*shape, x, y, z, occupied*/);
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
        final double preCrossoverDiversity = calculateDiversity(population);

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
        final double postCrossoverDiversity = calculateDiversity(population);
        System.out.println("Diversity change after crossover: " + (postCrossoverDiversity - preCrossoverDiversity));

        // Adapt mutation rate based on diversity change
        adaptMutationRate(preCrossoverDiversity, postCrossoverDiversity);
    }

    private void mutation() {
        final double preMutationDiversity = calculateDiversity(population);

        for (final Chromosome chromo : population) {
            if (random.nextDouble() < dynamicMutationRate) { // Use dynamicMutationRate
                final int index = random.nextInt(GENE_LENGTH);
                final String[] types = {"A", "B", "C"};
                final String newType = types[random.nextInt(types.length)];

                chromo.getGenes()[index] = newType;

                final ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
                final int newRotation = random.nextInt(shapesAndRotations.rotationNum(newType));
                chromo.getRotations()[index] = newRotation;
            }
        }
        final double postMutationDiversity = calculateDiversity(population);
        System.out.println("Diversity change after mutation: " + (postMutationDiversity - preMutationDiversity));

        // Adapt mutation rate based on diversity change
        adaptMutationRate(preMutationDiversity, postMutationDiversity);
    }

    private void adaptMutationRate(final double preDiversity, final double postDiversity) {
        if (postDiversity < preDiversity) {
            // If diversity has decreased, increase the mutation rate to introduce more diversity
            dynamicMutationRate = Math.min(dynamicMutationRate + 0.1, 1.0);
        } else {
            // If diversity has increased or stayed the same, decrease the mutation rate to allow the population to stabilize
            dynamicMutationRate = Math.max(dynamicMutationRate - 0.05, 0.1);
        }
        System.out.println("Adapted Mutation Rate: " + dynamicMutationRate);
    }

    private double calculateShapeDiversity(final List<CargoSpace> population) {
        final Set<int[][][]> uniqueShapes = new HashSet<>();
        for (final CargoSpace cargoSpace : population) {
            for (final ParcelPlacement placement : cargoSpace.getPlacements()) {
                final int[][][] shapeSignature = getShapeSignature(placement);
                uniqueShapes.add(shapeSignature);
            }
        }

        return uniqueShapes.size();
    }

    private int[][][] getShapeSignature(final ParcelPlacement placement) {
        return placement.getShape();
    }

    private double calculatePositionalDiversity(final List<CargoSpace> population) {
        double totalDistance = 0.0;
        int comparisons = 0;

        for (int i = 0; i < population.size() - 1; i++) {
            for (int j = i + 1; j < population.size(); j++) {
                final CargoSpace space1 = population.get(i);
                final CargoSpace space2 = population.get(j);
                // Compare placements in space1 and space2
                for (final ParcelPlacement placement1 : space1.getPlacements()) {
                    for (final ParcelPlacement placement2 : space2.getPlacements()) {
                        if (Arrays.deepEquals(getShapeSignature(placement1), getShapeSignature(placement2))) {
                            totalDistance += calculateDistance(placement1, placement2);
                            comparisons++;
                        }
                    }
                }
            }
        }

        return comparisons > 0 ? totalDistance / comparisons : 0;
    }

    private double calculateDistance(final ParcelPlacement placement1, final ParcelPlacement placement2) {
        // Euclidean distance between two parcel placements
        return Math.sqrt(Math.pow(placement1.getX() - placement2.getX(), 2) +
                Math.pow(placement1.getY() - placement2.getY(), 2) +
                Math.pow(placement1.getZ() - placement2.getZ(), 2));
    }

    private double calculateDiversity(final List<Chromosome> chromosomePopulation) {
        // Convert each chromosome to a corresponding CargoSpace
        final List<CargoSpace> cargoSpacePopulation = new ArrayList<>();
        for (final Chromosome chromosome : chromosomePopulation) {
            cargoSpacePopulation.add(chromosomeToCargoSpace(chromosome));
        }

        // Now calculate diversity based on the CargoSpace objects
        final double shapeDiversityScore = calculateShapeDiversity(cargoSpacePopulation);
        final double positionalDiversityScore = calculatePositionalDiversity(cargoSpacePopulation);

        // Combine the scores. Adjust the weights if necessary.
        return shapeDiversityScore * 0.5 + positionalDiversityScore * 0.5;
    }

    private CargoSpace chromosomeToCargoSpace(final Chromosome chromosome) {
        final CargoSpace cargoSpace = new CargoSpace();
        final ShapesAndRotations shapes = new ShapesAndRotations();

        for (int i = 0; i < chromosome.getGenes().length; i++) {
            final String gene = chromosome.getGenes()[i];
            final int rotation = chromosome.getRotationFromGene(i);
            final int[][][] shape = shapes.getShape(gene, rotation);

            // Find a place to put the parcel. This is a simplified placement logic.
            // You might have a more complex placement strategy.
            for (int x = 0; x < cargoSpace.getOccupied().length; x++) {
                for (int y = 0; y < cargoSpace.getOccupied()[0].length; y++) {
                    for (int z = 0; z < cargoSpace.getOccupied()[0][0].length; z++) {
                        final Parcel parcel = new Parcel(gene, shape);
                        final ParcelPlacement placement = new ParcelPlacement(parcel, x, y, z);
                        if (cargoSpace.canPlace(placement)) {
                            cargoSpace.placeParcel(placement);
                            // Break the loops after placing the parcel
                            x = cargoSpace.getOccupied().length;
                            y = cargoSpace.getOccupied()[0].length;
                            z = cargoSpace.getOccupied()[0][0].length;
                        }
                    }
                }
            }
        }

        return cargoSpace;
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

                        final Parcel parcel = new Parcel(gene);
                        parcel.setShape(shape);
                        final ParcelPlacement placement = new ParcelPlacement(parcel, x, y, z);

                        if (bestCargoSpace.canPlace(placement)) {
                            bestCargoSpace.placeParcel(placement/*shape, x, y, z, occupied*/);
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
