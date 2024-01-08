//package com.bcs2024.knapsack.algorithm;
//
//import java.io.FileWriter;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
///**
// * Represents a genetic bot used for training and improving game strategies.
// */
//public class GBot {
//
//    public static List<Integer> results;
//    public List<double[]> weights;
//    public static int repetitions;
//
//    /**
//     * Constructs a com.bcs2024.knapsack.algorithm.GBot instance.
//     * Initializes the results list, weights list, and sets the default number of repetitions.
//     */
//    public GBot() {
//        this.results = new ArrayList<>();
//        this.weights = new ArrayList<>();
//        this.repetitions = 10;
//    }
//
//    /**
//     * Generates the first set of random weights for the bot.
//     * This method creates an initial set of weights used by the genetic algorithm and writes them to a file.
//     */
//    public static void firstWeights() {
//        try {
//            final Random rn = new Random();
//            final FileWriter writer = new FileWriter("src/main/java/metrics/readableResults");
//            for (int i = 0; i < Game.repetitions; i++) {
//                final double[] nums = new double[6];
//                for (int j = 0; j < nums.length; j++) {
//                    nums[j] = rn.nextDouble();
//                }
//                final String nn = Arrays.toString(nums);
//                writer.write(nn.substring(1, nn.length() - 1) + "\n");
//            }
//            writer.close();
//        } catch (final Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    /**
//     * Rewrites the results of the bot's performance.
//     * This method reads the existing results, processes them, and writes the updated results back to a file.
//     */
//    public static void rewriteResults() {
//        try {
//            final String filePath = "src/main/java/metrics/GBotResults";
//            final List<String> lines = Files.readAllLines(Paths.get(filePath));
//            final FileWriter writer = new FileWriter("src/main/java/metrics/readableResults");
//
//            for (int i = lines.size() / 2; i < lines.size(); i++) {
//                final String line = lines.get(i).substring(lines.get(i).indexOf('[') + 1, lines.get(i).indexOf(']'));
//                writer.write(line + "\n");
//            }
//            writer.close();
//        } catch (final Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    /**
//     * Initializes the weights for the bot from a file.
//     * This method reads weights from a file and stores them in the weights list for use by the bot.
//     */
//    public void initialiseWeights() {
//        try {
//            final String filePath = "src/main/java/metrics/readableResults";
//            final List<String> lines = Files.readAllLines(Paths.get(filePath));
//
//            for (final String line : lines) {
//                final String[] stringNums = line.split(", ");
//                final double[] nums = new double[6];
//                for (int i = 0; i < stringNums.length; i++) {
//                    nums[i] = Double.parseDouble(stringNums[i]);
//                }
//                this.weights.add(nums);
//            }
//
//        } catch (final Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    /**
//     * Combines results from different runs to create a new set of weights.
//     * This method averages pairs of weights from previous runs and writes the new combined weights to a file.
//     */
//    public static void combineResults() {
//        try {
//
//            final List<double[]> toCombine = new ArrayList<>();
//            final List<double[]> results = new ArrayList<>();
//
//            final String filePath = "src/main/java/metrics/readableResults";
//            final List<String> lines = Files.readAllLines(Paths.get(filePath));
//            final FileWriter writer = new FileWriter(filePath);
//
//            for (final String line : lines) {
//                final String[] stringNums = line.split(", ");
//                final double[] nums = new double[6];
//                for (int i = 0; i < stringNums.length; i++) {
//                    nums[i] = Double.parseDouble(stringNums[i]);
//                }
//                toCombine.add(nums);
//            }
//
//            for (int i = 0; i < toCombine.size() - 1; i += 2) {
//                final double[] combined2 = new double[6];
//                for (int j = 0; j < 6; j++) {
//                    combined2[j] = (toCombine.get(i)[j] + toCombine.get(i + 1)[j]) / 2;
//                }
//                results.add(combined2);
//            }
//
//            for (final var i : results) {
//                String nn = Arrays.toString(i);
//                nn = nn.substring(nn.indexOf('[') + 1, nn.indexOf(']'));
//                writer.write(nn + "\n");
//            }
//            writer.close();
//            Game.repetitions = results.size();
//        } catch (final Exception e) {
//            System.out.println(e);
//        }
//    }
//}
