package com.bcs2024.knapsack.renderer;

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

public class UI extends Application {

    public static CargoSpace getCargoSpace = new CargoSpace();
    private double anchorX, anchorY;
    private double anchorAngleX;
    private double anchorAngleY;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private Group group = new Group();
    private Camera camera;
    private Box box;
    private int length;
    private int height;
    private int width;

    public UI() {
        length = (int) (getCargoSpace.getLength()) * 30;
        height = (int) (getCargoSpace.getHeight()) * 30;
        width = (int) (getCargoSpace.getWidth()) * 30;
//        solution = GreedyKnapsackSolver.matrix;
        //solution = cargoSpace.getOccupied();
//        solution = Experiment.field;
    }

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

        stage.setTitle("KnapSack");
        stage.setScene(scene);
        stage.show();
    }

    private void drawContainer() {
        for (int i = 0; i < getCargoSpace.getOccupied().length; i++) {
            for (int j = 0; j < getCargoSpace.getOccupied()[0].length; j++) {
                for (int k = 0; k < getCargoSpace.getOccupied()[0][0].length; k++) {
                    if (getCargoSpace.getOccupied()[i][j][k] != -1) {
                        final double boxWidth = width / getCargoSpace.getOccupied().length;
                        final double boxHeight = height / getCargoSpace.getOccupied()[0].length;
                        final double boxDepth = length / getCargoSpace.getOccupied()[0][0].length;

                        box = new Box(boxWidth, boxHeight, boxDepth);

                        box.translateXProperty().set((i - getCargoSpace.getOccupied().length / 2) * boxWidth);
                        box.translateYProperty().set((j - getCargoSpace.getOccupied()[0].length / 2) * boxHeight);
                        box.translateZProperty().set((k - getCargoSpace.getOccupied()[0][0].length / 2) * boxDepth);

                        final PhongMaterial material = new PhongMaterial();
                        material.setDiffuseColor(getColorById(getCargoSpace.getOccupied()[i][j][k]));
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

    private Color getColorById(final int id) {
        System.out.println("ID: " + id); // Debug print
        return switch (id) {
            case 0 -> Color.ORANGE;
            case 1, 4 -> Color.BLUE;
            case 2, 5 -> Color.GREEN;
            case 3, 6 -> Color.RED;
            default -> Color.SILVER;
        };
    }


    private void initMouseControl(final Group group, final Scene scene) {
        final Rotate xRotate;
        final Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );

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

    public void setSolution(final int[][][] state) {
        // this.solution = state;
    }

    public void show() {
        launch();
    }
}
