package com.example.phase3try;

import com.example.phase3try.algorithm.DancingLinks;
import com.example.phase3try.algorithm.Experiment;
import com.example.phase3try.algorithm.GreedyKnapsackSolver;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.phase3try.model.CargoSpace;

public class UI extends Application {

    private CargoSpace cargoSpace = new CargoSpace();
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

    public UI() {
        length = (int) (cargoSpace.getLength() * 2) * 30;
        height = (int) (cargoSpace.getHeight() * 2) * 30;
        width = (int) (cargoSpace.getWidth() * 2) * 30;
//        solution = GreedyKnapsackSolver.matrix;
        solution = DancingLinks.field;
//        solution = Experiment.field;
    }

    @Override
    public void start(Stage stage) throws IOException {
        camera = new PerspectiveCamera();

        Scene scene = new Scene(group, 1400, 1000, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.SILVER);
        scene.setCamera(camera);

        initMouseControl(group, scene);

        drawContainer();

        group.translateXProperty().set(700);
        group.translateYProperty().set(500 - height/2);

        stage.setTitle("KnapSack");
        stage.setScene(scene);
        stage.show();
    }

    private void drawContainer() {
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[0].length; j++) {
                for (int k = 0; k < solution[0][0].length; k++) {
                    if (solution[i][j][k] != -1) {
                        double boxWidth = width / solution.length;
                        double boxHeight = height / solution[0].length;
                        double boxDepth = length / solution[0][0].length;

                        box = new Box(boxWidth, boxHeight, boxDepth);

                        box.translateXProperty().set((i - solution.length / 2) * boxWidth);
                        box.translateYProperty().set((j - solution[0].length / 2) * boxHeight);
                        box.translateZProperty().set((k - solution[0][0].length / 2) * boxDepth);

                        PhongMaterial material = new PhongMaterial();
                        material.setDiffuseColor(getColorById(solution[i][j][k]));
                        box.setMaterial(material);
                        group.getChildren().add(box);
                    }
                }
            }
        }

        Box containerBox = new Box(width + 3, height + 3, length + 3);
        containerBox.setMaterial(new PhongMaterial(Color.color(0.7, 0.7, 0.7, 0.2)));
        containerBox.translateYProperty().set(-15);
        group.getChildren().add(containerBox);
    }

    private Color getColorById(int id) {
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
                return Color.BLUE;
            case 5:
                return Color.GREEN;
            case 6:
                return Color.RED;
        }
        return Color.SILVER;
    }

    private void initMouseControl(Group group, Scene scene) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );

        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed( e -> {
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

    public void setSolution(int[][][] state) {
        this.solution = state;
    }

    public void show() {
        launch();
    }
}
