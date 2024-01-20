package com.bcs2024.knapsack.renderer;

import com.bcs2024.knapsack.algorithm.DancingLinks;
import com.bcs2024.knapsack.model.CargoSpace;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    public static CargoSpace cargoSpace = new CargoSpace();
    private double anchorX, anchorY;
    private double anchorAngleX;
    private double anchorAngleY;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private static int[][][] solution;
    private Group group = new Group();
    private Camera camera;
    private Box box;
    private int length;
    private int height;
    private int width;

    /**
     * Constructs a new instance of the HelloApplication class.
     * Initializes the dimensions (length, height, width) based on the cargo space's
     * dimensions,
     * and sets the solution array by obtaining the occupied status from the cargo
     * space
     * or by assigning the DancingLinks field.
     */
    public HelloApplication() {
        length = (int) (cargoSpace.getLength()) * 30;
        height = (int) (cargoSpace.getHeight()) * 30;
        width = (int) (cargoSpace.getWidth()) * 30;
        solution = cargoSpace.getOccupied(); // GreedyKnapsackSolver.getSolution();
        solution = DancingLinks.field;
    }

    /**
     * Initializes and sets up the JavaFX application's main scene.
     * This method configures the 3D scene with a camera, handles mouse control,
     * draws the container, and displays the 3D container in the main stage.
     *
     * @param stage The JavaFX stage in which the 3D container is displayed.
     */
    @Override
    public void start(final Stage stage) {
        camera = new PerspectiveCamera();

        final Scene scene = new Scene(group, 1400, 1000, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        initMouseControl(group, scene);

        drawContainer();

        group.translateXProperty().set(700);
        group.translateYProperty().set(500 - height / 2);

        stage.setTitle("3D Container");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Draws the container based on the solution array.
     * This method iterates through the solution array and draws a box for each
     * occupied space.
     * The box is then translated to its correct position in the container.
     */
    private void drawContainer() {
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[0].length; j++) {
                for (int k = 0; k < solution[0][0].length; k++) {
                    if (solution[i][j][k] != -1) {
                        final double boxWidth = width / solution.length;
                        final double boxHeight = height / solution[0].length;
                        final double boxDepth = length / solution[0][0].length;

                        box = new Box(boxWidth, boxHeight, boxDepth);

                        box.translateXProperty().set((i - solution.length / 2) * boxWidth);
                        box.translateYProperty().set((j - solution[0].length / 2) * boxHeight);
                        box.translateZProperty().set((k - solution[0][0].length / 2) * boxDepth);

                        final PhongMaterial material = new PhongMaterial();
                        material.setDiffuseColor(getColorById(solution[i][j][k]));
                        box.setMaterial(material);
                        group.getChildren().add(box);
                    }
                }
            }
        }

        final Box containerBox = new Box(width + 3, height + 3, length + 3);
        containerBox.setMaterial(new PhongMaterial(Color.color(0.7, 0.7, 0.7, 0.2)));
        containerBox.translateYProperty().set(-15);
        group.getChildren().add(containerBox);
    }

    /**
     * Returns a color based on the ID of the box.
     * This method returns a color based on the ID of the box.
     * The ID is obtained from the solution array.
     *
     * @param id The ID of the box.
     * @return The color of the box.
     */
    private Color getColorById(final int id) {
        switch (id) {
            case 0:
                return Color.ORANGE;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.RED;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.BLACK;
        }
        return Color.SILVER;
    }

    /**
     * Initializes mouse control for the 3D container.
     * This method initializes mouse control for the 3D container.
     * It allows the user to rotate the container by dragging the mouse.
     *
     * @param group The group containing the 3D container.
     * @param scene The scene containing the group.
     */
    private void initMouseControl(final Group group, final Scene scene) {
        final Rotate xRotate;
        final Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS));

        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(e -> {
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(e -> {
            angleX.set(anchorAngleX - (anchorY - e.getSceneY()));
            angleY.set(anchorAngleY + anchorX - e.getSceneX());
        });
    }

    /**
     * Displays the 3D container.
     * This method launches the JavaFX application.
     */
    public void show() {
        launch();
    }
}
