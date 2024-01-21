package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.Arrays;
import java.util.Random;

public class Chromosome {

    private String[] genes;
    private int fitness;
    private int[] rotations;

    /**
     * Constructs a new Chromosome with the specified length, randomly initializing
     * its genes and rotations.
     *
     * @param length The length of the Chromosome, representing the number of genes.
     */
    public Chromosome(final int length) {
        this.genes = new String[length];
        this.rotations = new int[length];

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
    }

    /**
     * Retrieves the rotation associated with the gene at the specified index.
     *
     * @param index The index of the gene for which to retrieve the rotation.
     * @return The rotation value associated with the gene at the specified index.
     */
    public int getRotationFromGene(final int index) {
        return this.rotations[index];
    }

    /**
     * Retrieves the gene at the specified index.
     *
     * @return The gene at the specified index.
     */
    public int[] getRotations() {
        return this.rotations;
    }

    /**
     * Sets the fitness value for this chromosome.
     *
     * @param fitness The fitness value to be assigned to this chromosome.
     */
    public void setFitness(final int fitness) {
        this.fitness = fitness;
    }

    /**
     * Retrieves the length of the rotations array in this chromosome.
     *
     * @return The length of the rotations array.
     */
    public int getRotationLength() {
        return this.rotations.length;
    }

    /**
     * Retrieves the array of genes representing the chromosome.
     *
     * @return An array of genes.
     */
    public String[] getGenes() {
        return genes;
    }

    /**
     * Sets the array of genes representing the chromosome.
     */
    public void setRotations(final int[] rotations) {
        this.rotations = rotations;
    }

    /**
     * Sets the array of genes for the chromosome.
     *
     * @param genes An array of genes to set.
     */
    public void setGenes(final String[] genes) {
        this.genes = genes;
    }

    /**
     * Gets the fitness value of the chromosome.
     *
     * @return The fitness value of the chromosome.
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * Returns a string representation of the chromosome.
     *
     * @return A string representation of the chromosome, including its genes.
     */
    @Override
    public String toString() {
        return "Chromosome{" + "genes=" + Arrays.toString(genes) + '}';
    }
}
