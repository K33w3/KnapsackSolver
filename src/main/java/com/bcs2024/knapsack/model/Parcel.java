package com.bcs2024.knapsack.model;

import com.bcs2024.knapsack.util.ShapesAndRotations;

public class Parcel {

    private int value;
    private String type;
    private int[][][] shape;
    private int id;

    /**
     * Constructs a new Parcel object with the specified type and shape.
     * This constructor initializes the Parcel's type and shape properties and
     * performs initialization based on the parcel type.
     *
     * @param type  The type of the parcel (e.g., "Box", "Cylinder", etc.).
     * @param shape A three-dimensional array representing the shape of the parcel.
     */
    public Parcel(final String type, final int[][][] shape) {
        this.type = type;
        this.shape = shape;
        initializeParcelByType();
    }

    /**
     * Constructs a new Parcel object with the specified ID and shape.
     * This constructor initializes the Parcel's ID and shape properties and
     * performs initialization based on the parcel ID.
     *
     * @param id    The ID of the parcel.
     * @param shape A three-dimensional array representing the shape of the parcel.
     */
    public Parcel(final int id, final int[][][] shape) {
        this.id = id;
        this.shape = shape;
        initializeParcelById();
    }

    /**
     * Constructs a new Parcel object with the specified type.
     * This constructor initializes the Parcel's type property and
     * performs initialization based on the parcel type.
     *
     * @param type The type of the parcel (e.g., "Box", "Cylinder", etc.).
     */
    public Parcel(final String type) {
        this.type = type;
        this.shape = new ShapesAndRotations().getShape(type, 0);
        initializeParcelByType();
    }

    /**
     * Constructs a new Parcel object with the specified ID.
     * This constructor initializes the Parcel's ID property and
     * performs initialization based on the parcel ID.
     */
    private void initializeParcelByType() {
        switch (type) {
            case "A" -> {
                this.value = 3;
                this.id = 1;
            }
            case "B" -> {
                this.value = 4;
                this.id = 2;
            }
            case "C" -> {
                this.value = 5;
                this.id = 3;
            }
            case "L" -> {
                this.value = 3;
                this.id = 4;
            }
            case "P" -> {
                this.value = 4;
                this.id = 5;
            }
            case "T" -> {
                this.value = 5;
                this.id = 6;
            }
            default -> System.out.println("Invalid type");
        }
    }

    /**
     * Initializes the Parcel's properties (value and type) based on its unique
     * identifier (id).
     * This method assigns the appropriate value and type to the parcel based on its
     * id.
     * If the id is invalid, it displays a message indicating that the type is
     * invalid.
     */
    private void initializeParcelById() {
        switch (id) {
            case 1 -> {
                this.value = 3;
                this.type = "A";
            }
            case 2 -> {
                this.value = 4;
                this.type = "B";
            }
            case 3 -> {
                this.value = 5;
                this.type = "C";
            }
            case 4 -> {
                this.value = 3;
                this.type = "L";
            }
            case 5 -> {
                this.value = 4;
                this.type = "P";
            }
            case 6 -> {
                this.value = 5;
                this.type = "T";
            }
            default -> System.out.println("Invalid type");
        }
    }

    /**
     * Returns the ID of the parcel based on its type.
     * This method returns the ID of the parcel based on its type.
     *
     * @param type The type of the parcel.
     * @return The ID of the parcel.
     */
    private int setId(final String type) {
        return switch (type) {
            case "A" -> 1;
            case "B" -> 2;
            case "C" -> 3;
            case "L" -> 4;
            case "P" -> 5;
            case "T" -> 6;
            default -> -1;
        };
    }

    /**
     * Returns the shape of the parcel.
     * This method returns the shape of the parcel.
     *
     * @return The shape of the parcel.
     */
    public int[][][] getShape() {
        return shape;
    }

    /**
     * Returns the value of the parcel.
     * This method returns the value of the parcel.
     *
     * @return The value of the parcel.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the type of the parcel.
     * This method returns the type of the parcel.
     *
     * @return The type of the parcel.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the parcel.
     * This method sets the value of the parcel.
     */
    public void setShape(final int[][][] shape) {
        this.shape = shape;
    }

    /**
     * Sets the value of the parcel.
     * This method sets the value of the parcel.
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Sets the value of the parcel.
     * This method sets the value of the parcel.
     */
    public double getVolume() {
        final double volume = switch (type) {
            case "A" -> 1.0 * 1.0 * 2.0;
            case "B" -> 1.0 * 1.5 * 2.0;
            case "C" -> 1.5 * 1.5 * 1.5;
            case "L", "P", "T" ->
                // Dimensions for pentomino shapes (L, P, T): 5 cubes of 0.5 x 0.5 x 0.5
                    5 * (0.5 * 0.5 * 0.5);
            default -> throw new IllegalArgumentException("Unknown parcel type: " + type);
        };

        return volume;
    }

    /**
     * Sets the value of the parcel.
     * This method sets the value of the parcel.
     */
    public double getValueDensity() {
        final double valueDensity = switch (type) {
            case "A" -> 1.0 / 2.0;
            case "B" -> 1.5 / 2.0;
            case "C" -> 1.5 / 1.5;
            case "L", "P", "T" -> 5 * (0.5 / 0.5);
            default -> throw new IllegalArgumentException("Unknown parcel type: " + type);
        };

        return valueDensity;
    }

    /**
     * Returns the ID of the parcel.
     * This method returns the ID of the parcel.
     *
     * @return The ID of the parcel.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the surface area of the parcel.
     * This method returns the surface area of the parcel.
     *
     * @return The surface area of the parcel.
     */
    public double calculateSurfaceArea() {
        // Assuming shape array dimensions represent length, width, and height
        // and the parcel is a rectangular prism
        final int length = shape.length;
        final int width = shape[0].length;
        final int height = shape[0][0].length;

        // Calculate the surface area of the parcel
        return 2.0 * (length * width + width * height + height * length);
    }
}
