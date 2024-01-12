package com.bcs2024.knapsack.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ParcelDatabase {
    // Stores the 3D parcel data
    public static int[][][][][] data;

    /**
     * Loads and decodes the CSV file containing 3D parcel shapes and permutations.
     *
     * @param fileName The name of the CSV file to be used.
     * @return A 5D array representing the list of parcel pieces.
     *         Dimensions: 1-Piece ID; 2-Mutation; 3-Depth; 4-Row; 5-Column.
     * @throws RuntimeException if the file is not found.
     */
    private static int[][][][][] loadData(String fileName) {
        ArrayList<ArrayList<int[][][]>> dynamicList = new ArrayList<>();

        // Use ClassLoader to get the resource as a stream
        InputStream is = ParcelDatabase.class.getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            throw new RuntimeException("Resource not found: " + fileName);
        }

        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                int id = Integer.parseInt(values[0]);
                int permutation = Integer.parseInt(values[1]);
                int depth = Integer.parseInt(values[2]);
                int rows = Integer.parseInt(values[3]);
                int cols = Integer.parseInt(values[4]);
                int[][][] piece = new int[depth][rows][cols];

                for (int d = 0; d < depth; d++) {
                    for (int r = 0; r < rows; r++) {
                        for (int c = 0; c < cols; c++) {
                            piece[d][r][c] = Integer.parseInt(values[5 + d * rows * cols + r * cols + c]);
                        }
                    }
                }

                // Ensure the list is big enough to hold the new ID and permutation
                while (id >= dynamicList.size()) {
                    dynamicList.add(new ArrayList<>());
                }
                while (permutation >= dynamicList.get(id).size()) {
                    dynamicList.get(id).add(null);
                }

                // Add piece to the correct permutation list
                dynamicList.get(id).set(permutation, piece);
            }
        }

        // Convert dynamic list to static array
        int[][][][][] staticList = new int[dynamicList.size()][][][][];
        for (int i = 0; i < dynamicList.size(); i++) {
            staticList[i] = new int[dynamicList.get(i).size()][][][];
            for (int j = 0; j < dynamicList.get(i).size(); j++) {
                staticList[i][j] = dynamicList.get(i).get(j);
            }
        }

        return staticList;
    }


    public static void main(String[] args) {
        data = loadData("parcel.csv");

        for (int i = 0; i < data.length; i++) { // parcel ID
            for (int j = 0; j < data[i].length; j++) { // Permutation index
                for (int k = 0; k < data[i][j].length; k++) { // Depth (Z)
                    for (int l = 0; l < data[i][j][k].length; l++) { // Rows (Y)
                        for (int m = 0; m < data[i][j][k][l].length; m++) { // Columns (X)
                            // Print ID, Permutation, Depth, Rows, Columns, followed by the 3D shape
                            System.out.print(i + "," + j + "," + k + "," + data[i][j][k].length + "," + data[i][j][k][l].length + "," + data[i][j][k][l][m]);
                            System.out.print(" "); // Add space for readability
                        }
                        System.out.println(); // New line for each row
                    }
                    System.out.println(); // Separate each layer with a new line
                }
                System.out.println(); // Separate each permutation with a new line
            }
            System.out.println(); // Separate each parcel ID with a new line
        }
    }

}
