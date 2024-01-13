package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.util.ShapesAndRotations;

import java.util.Arrays;
import java.util.Random;

public class Chromosome {

  private String[] genes;
  private int fitness;
  private int[] rotations;

  public Chromosome(int length) {
    this.genes = new String[length];
    this.rotations = new int[length];

    Random random = new Random();
    ShapesAndRotations shapesAndRotations = new ShapesAndRotations(); // You'll need to create this class or method
    for (int i = 0; i < length; i++) {
      String[] types = { "A", "B", "C" };
      String type = types[random.nextInt(types.length)];
      int rotations = random.nextInt((shapesAndRotations.rotationNum(type)));

      // Assuming rotationNum returns the number of rotations for a type
      this.genes[i] = type; // Combine type and rotation into a single string
      this.rotations[i] = rotations;
    }
  }

  public int getRotationFromGene(int index) {
    return this.rotations[index];
  }

  public int[] getRotations() {
    return this.rotations;
  }

  public void setFitness(int fitness) {
    this.fitness = fitness;
  }

  public int getRotationLength() {
    return this.rotations.length;
  }

  public String[] getGenes() {
    return genes;
  }

  public void setRotations(int[] rotations) {
    this.rotations = rotations;
  }

  public void setGenes(String[] genes) {
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
