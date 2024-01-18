package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.Arrays;
import java.util.Random;

public class Chromosome {

    private String[] genes;
    private int fitness;
    private int[] rotations;

    public Chromosome(final Chromosome otherChromosome) {
        // Copy fitness, genes, and rotations from the other chromosome
        this.fitness = otherChromosome.fitness;
        this.genes = Arrays.copyOf(otherChromosome.genes, otherChromosome.genes.length);
        this.rotations = Arrays.copyOf(otherChromosome.rotations, otherChromosome.rotations.length);
    }

    // New constructor for creating a new random chromosome
    public Chromosome(final int length) {
        this.genes = new String[length];
        this.rotations = new int[length];
        initializeRandomly();
    }

    // Method to initialize genes and rotations randomly
    public void initializeRandomly() {
        final Random random = new Random();
        final ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
        for (int i = 0; i < this.genes.length; i++) {
            final String[] types = {"A", "B", "C"};
            final String type = types[random.nextInt(types.length)];
            final int rotation = random.nextInt(shapesAndRotations.rotationNum(type));

            this.genes[i] = type;
            this.rotations[i] = rotation;
        }
    }

    /*public Chromosome(final Chromosome otherChromosome) {
        this.fitness = otherChromosome.fitness;
        this.genes = Arrays.copyOf(otherChromosome.genes, otherChromosome.genes.length);
        this.rotations = Arrays.copyOf(otherChromosome.rotations, otherChromosome.rotations.length);

        final Random random = new Random();
        final ShapesAndRotations shapesAndRotations = new ShapesAndRotations(); // You'll need to create this class or method
        for (int i = 0; i < length; i++) {
            final String[] types = {"A", "B", "C"};
            final String type = types[random.nextInt(types.length)];
            final int rotations = random.nextInt((shapesAndRotations.rotationNum(type)));

            // Assuming rotationNum returns the number of rotations for a type
            this.genes[i] = type; // Combine type and rotation into a single string
            this.rotations[i] = rotations;
        }
    }*/

    public int getRotationFromGene(final int index) {
        return this.rotations[index];
    }

    public int[] getRotations() {
        return this.rotations;
    }

    public void setFitness(final int fitness) {
        this.fitness = fitness;
    }

    public int getRotationLength() {
        return this.rotations.length;
    }

    public String[] getGenes() {
        return genes;
    }

    public void setRotations(final int[] rotations) {
        this.rotations = rotations;
    }

    public void setGenes(final String[] genes) {
        this.genes = genes;
    }

    public int getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        return "Chromosome{" + "genes=" + Arrays.toString(genes) + '}';
    }
}
