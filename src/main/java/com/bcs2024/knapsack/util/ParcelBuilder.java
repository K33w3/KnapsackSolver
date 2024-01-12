package com.bcs2024.knapsack.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class responsible for generating and managing the database of parcel shapes.
 */
public class ParcelBuilder {

	public static ArrayList<int[][][][]> database = new ArrayList<>();
	private static final int[][][][] basicDatabase = {
			{
					// 3D L pentomino
					{{1, 0, 0}, {1, 0, 0}, {1, 1, 1}},
			},
			{
					// 3D P pentomino
					{{1, 1, 0}, {1, 1, 0}, {1, 0, 0}},
			},
			{
					// 3D T pentomino
					{{1, 1, 1}, {0, 1, 0}, {0, 1, 0}},
			},

			// Parcel Type A: 1.0 x 1.0 x 2.0
			{
					{
							{1, 1},
					},
			},
			// Parcel Type B: 1.0 x 1.5 x 2.0
			{
					{
							{1, 1},
							{1, 1},
					},
			},
			// Parcel Type C: 1.5 x 1.5 x 1.5
			{
					{
							{1, 1},
							{1, 1},
							{1, 1},
					},
			},
	};


	/**
	 * Generates the database of parcel shapes based on predefined basic shapes.
	 * It expands these shapes to all possible rotations and flips.
	 */
	public static void makeDatabase() {
		database.clear(); // Clear existing database

		// Generate rotations for each shape in the basic database
		for (int[][][] shape : basicDatabase) {
			List<int[][][]> rotationsList = new ArrayList<>();

			// Generate and add rotations around all axes
			for (int xRot = 0; xRot < 4; xRot++) {
				int[][][] xRotatedShape = rotateAroundXAxis(shape, xRot);
				for (int yRot = 0; yRot < 4; yRot++) {
					int[][][] yRotatedShape = rotateAroundYAxis(xRotatedShape, yRot);
					for (int zRot = 0; zRot < 4; zRot++) {
						int[][][] zRotatedShape = rotateAroundZAxis(yRotatedShape, zRot);
						zRotatedShape = moveToAbove(zRotatedShape);
						zRotatedShape = eraseEmptySpace(zRotatedShape);

						if (!containsInList(rotationsList, zRotatedShape)) {
							rotationsList.add(zRotatedShape);
						}
					}
				}
			}

			// Convert list to array
			int[][][][] rotationsArray = rotationsList.toArray(new int[rotationsList.size()][][][]);
			// Add unique rotations to the database
			database.add(rotationsArray);
		}
	}

	private static boolean containsInList(List<int[][][]> list, int[][][] shape) {
		for (int[][][] existingShape : list) {
			if (isEqual(existingShape, shape)) {
				return true; // Found a matching shape
			}
		}
		return false; // No matching shape found
	}

	private static int[][][] rotateAroundZAxis(int[][][] shape, int rotations) {
		int[][][] rotatedShape = shape;
		for (int i = 0; i < rotations; i++) {
			int depth = rotatedShape.length;
			int rows = rotatedShape[0].length;
			int cols = rotatedShape[0][0].length;

			int[][][] newShape = new int[depth][cols][rows]; // New dimensions after rotation

			for (int z = 0; z < depth; z++) {
				for (int x = 0; x < rows; x++) {
					for (int y = 0; y < cols; y++) {
						newShape[z][y][rows - 1 - x] = rotatedShape[z][x][y];
					}
				}
			}
			rotatedShape = newShape;
		}
		return rotatedShape;
	}


	private static int[][][] rotateAroundXAxis(int[][][] shape, int rotations) {
		int[][][] rotatedShape = shape;
		for (int i = 0; i < rotations; i++) {
			int depth = rotatedShape.length;
			int rows = rotatedShape[0].length;
			int cols = rotatedShape[0][0].length;

			int[][][] newShape = new int[depth][cols][rows]; // New dimensions after rotation

			for (int x = 0; x < depth; x++) {
				for (int y = 0; y < rows; y++) {
					for (int z = 0; z < cols; z++) {
						newShape[x][z][rows - 1 - y] = rotatedShape[x][y][z];
					}
				}
			}
			rotatedShape = newShape;
		}
		return rotatedShape;
	}

	private static int[][][] rotateAroundYAxis(int[][][] shape, int rotations) {
		int[][][] rotatedShape = shape;
		for (int i = 0; i < rotations; i++) {
			int depth = rotatedShape.length;
			int rows = rotatedShape[0].length;
			int cols = rotatedShape[0][0].length;

			int[][][] newShape = new int[cols][rows][depth]; // New dimensions after rotation

			for (int x = 0; x < depth; x++) {
				for (int y = 0; y < rows; y++) {
					for (int z = 0; z < cols; z++) {
						newShape[cols - 1 - z][y][x] = rotatedShape[x][y][z];
					}
				}
			}
			rotatedShape = newShape;
		}
		return rotatedShape;
	}



	/**
	 * Converts a character representing a parcel to its corresponding ID.
	 *
	 * @param character The character representation of a parcel.
	 * @return The ID of the parcel.
	 */
	public static int characterToId(int character) {
		return switch (character) {
			case 'T' -> 1;
			case 'L' -> 2;
			case 'P' -> 3;
			case 'A' -> 4;
			case 'B' -> 5;
			case 'C' -> 6;
			default -> -1;
		};
	}

	/**
	 * Rotate the matrix x times over 90 degrees
	 * Assume that the matrix is a square!
	 * It does not make a copy, so the return matrix does not have to be used
	 *
	 * @param data:     a matrix
	 * @param rotation: amount of rotation
	 * @return the rotated matrix
	 */
	public static int[][] rotate(int[][] data, int rotation) {
		int[][] tempData1 = new int[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				tempData1[i][j] = data[i][j];
			}
		}

		//do it for the amount of times it needs to be rotated
		for (int k = 0; k < rotation; k++) {
			//make a matrix of the same size
			int[][] tempData2 = new int[tempData1.length][tempData1[0].length];
			//rotate it once and put it in tempData
			for (int i = 0; i < tempData1.length; i++) {
				for (int j = 0; j < tempData1[i].length; j++) {
					tempData2[i][j] = tempData1[j][tempData1.length - i - 1];
				}
			}
			//put it back in the starting matrix so you can do it again
			for (int i = 0; i < tempData1.length; i++) {
				for (int j = 0; j < tempData1[i].length; j++) {
					tempData1[i][j] = tempData2[i][j];
				}
			}
		}
		return tempData1;
	}

	/**
	 * Flip the matrix vertically
	 * It makes a copy, the input matrix stays unchanged
	 *
	 * @param data: a matrix
	 * @return the flipped matrix
	 */
	public static int[][] verticalFlip(int[][] data) {
		//make a matrix of the same size
		int[][] returnData = new int[data.length][data[0].length];
		//flip the matrix to the return matrix
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				returnData[i][j] = data[i][data[i].length - j - 1];
			}
		}
		return returnData;
	}

	/**
	 * Flip the matrix horizontally
	 * It makes a copy, the input matrix stays unchanged
	 *
	 * @param data a matrix
	 * @return the flipped matrix
	 */
	public static int[][] horizontalFlip(int[][] data) {
		//make a matrix of the same size
		int[][] returnData = new int[data.length][data[0].length];
		//flip the matrix to the return matrix
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				returnData[i][j] = data[data.length - i - 1][j];
			}
		}
		return returnData;
	}

	/**
	 * Expands a smaller than size*size matrix to a size*size matrix
	 * It makes a copy, the input matrix stays unchanged
	 * Assume that the input is smaller than size!!
	 *
	 * @param data: a matrix
	 * @param size: the square size of the new matrix
	 * @return the size*size matrix
	 */
	public static int[][][] makeBigger(int[][][] data, int size) {
		int depth = data.length;
		int[][][] returnData = new int[depth][size][size];

		for (int z = 0; z < depth; z++) {
			for (int i = 0; i < data[z].length; i++) {
				for (int j = 0; j < data[z][i].length; j++) {
					returnData[z][i][j] = data[z][i][j];
				}
			}
		}
		return returnData;
	}


	/**
	 * Move matrix to the left above corner
	 * Does not make a copy!
	 *
	 * @return the modified matrix
	 */
	public static int[][][] moveToAbove(int[][][] shape) {
		// Find the minimal x, y, z where the shape has a block (not empty)
		int minX = shape.length, minY = shape[0].length, minZ = shape[0][0].length;
		for (int x = 0; x < shape.length; x++) {
			for (int y = 0; y < shape[x].length; y++) {
				for (int z = 0; z < shape[x][y].length; z++) {
					if (shape[x][y][z] == 1) {
						if (x < minX) minX = x;
						if (y < minY) minY = y;
						if (z < minZ) minZ = z;
					}
				}
			}
		}

		// Move the shape to the top-left-front corner
		int[][][] movedShape = new int[shape.length][shape[0].length][shape[0][0].length];
		for (int x = minX; x < shape.length; x++) {
			for (int y = minY; y < shape[x].length; y++) {
				for (int z = minZ; z < shape[x][y].length; z++) {
					movedShape[x - minX][y - minY][z - minZ] = shape[x][y][z];
				}
			}
		}
		return movedShape;
	}


	/**
	 * Erase duplicates in a array of matrices
	 * The input matrix stays unchanged
	 *
	 * @return the array of matrices without duplicates
	 */
	public static int[][][][] eraseDuplicates(int[][][][] rotations) {
		ArrayList<int[][][]> uniqueRotations = new ArrayList<>();

		for (int[][][] rotation : rotations) {
			boolean isDuplicate = false;
			for (int[][][] uniqueRotation : uniqueRotations) {
				if (Arrays.deepEquals(rotation, uniqueRotation)) {
					isDuplicate = true;
					break;
				}
			}
			if (!isDuplicate) {
				uniqueRotations.add(rotation);
			}
		}

		return uniqueRotations.toArray(new int[uniqueRotations.size()][][][]);
	}


	/**
	 * Check if two matrices are equal
	 * Assume they have the same size
	 *
	 * @param data1: the first matrix
	 * @param data2: the second matrix
	 * @return true if equal, false otherwise
	 */
	public static boolean isEqual(int[][][] data1, int[][][] data2) {
		if (data1.length != data2.length) return false;
		for (int z = 0; z < data1.length; z++) {
			if (data1[z].length != data2[z].length) return false;
			for (int i = 0; i < data1[z].length; i++) {
				if (!Arrays.equals(data1[z][i], data2[z][i])) return false;
			}
		}
		return true; // All corresponding elements are equal
	}



	/**
	 * Erase rows and columns that contain only zeros
	 *
	 * @return the shrinken matrix
	 */
	public static int[][][] eraseEmptySpace(int[][][] shape) {
		// Find boundaries of non-empty space
		int maxX = 0, maxY = 0, maxZ = 0;

		for (int x = 0; x < shape.length; x++) {
			for (int y = 0; y < shape[x].length; y++) {
				for (int z = 0; z < shape[x][y].length; z++) {
					if (shape[x][y][z] == 1) {
						if (x > maxX) maxX = x;
						if (y > maxY) maxY = y;
						if (z > maxZ) maxZ = z;
					}
				}
			}
		}

		// Create a new shape with the minimum required size
		int[][][] newShape = new int[maxX + 1][maxY + 1][maxZ + 1];
		for (int x = 0; x <= maxX; x++) {
			for (int y = 0; y <= maxY; y++) {
				for (int z = 0; z <= maxZ; z++) {
					newShape[x][y][z] = shape[x][y][z];
				}
			}
		}
		return newShape;
	}



	/**
	 * Main method to generate a CSV file of parcel shapes.
	 *
	 * @param args Command line arguments.
     */
	public static void main(String[] args) {
		makeDatabase(); // Generate the database of 3D parcel shapes

		// Specify the path relative to the project root
		String resourcePath = "src/main/resources/parcel.csv"; // Adjust the path based on your project structure

		try (PrintWriter writer = new PrintWriter(resourcePath, StandardCharsets.UTF_8)) {
			// Iterate over each shape type in the database
			for (int shapeType = 0; shapeType < database.size(); shapeType++) {
				int[][][][] rotations = database.get(shapeType);

				// Iterate over each rotation of the current shape
				for (int rotationIndex = 0; rotationIndex < rotations.length; rotationIndex++) {
					int[][][] shape = rotations[rotationIndex];

					// Write shape type, rotation index, and shape dimensions
					writer.print(shapeType + "," + rotationIndex + "," + shape.length + "," + shape[0].length + "," + shape[0][0].length);

					// Iterate over the 3D array to write each block's presence (1 or 0)
                    for (int[][] ints : shape) {
                        for (int[] anInt : ints) {
                            for (int i : anInt) {
                                writer.print("," + i);
                            }
                        }
                    }
					writer.println(); // New line after each shape rotation
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Retrieves the basic database of parcel shapes.
	 *
	 * @return The basic database of parcel shapes.
	 */
	public static int[][][][] getBasicDatabase() {
		return basicDatabase;
	}
}
