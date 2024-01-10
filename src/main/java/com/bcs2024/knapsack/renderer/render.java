package com.bcs2024.knapsack.renderer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Render extends Application {

    private static final double SPACE_SIZE = 400;

    @Override
    public void start(Stage primaryStage) {
        // Create a 3D box
        Box box = new Box(SPACE_SIZE, SPACE_SIZE, SPACE_SIZE);
        box.setDrawMode(DrawMode.LINE);
        box.setTranslateX(SPACE_SIZE / 2);
        box.setTranslateY(SPACE_SIZE / 2);
        box.setTranslateZ(SPACE_SIZE / 2);

        // Create a group and add the box to it
        Group root = new Group(box);

        // Create a scene with the group as the root and set the background color
        Scene scene = new Scene(root, SPACE_SIZE, SPACE_SIZE, Color.WHITE);

        // Create a perspective camera and add it to the scene
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateX(SPACE_SIZE / 2);
        camera.setTranslateY(SPACE_SIZE / 2);
        camera.setTranslateZ(-SPACE_SIZE * 2);
        scene.setCamera(camera);

        // Rotate the box continuously
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        box.getTransforms().addAll(rotateX, rotateY);

        // Add event handlers to rotate the box when the mouse is dragged
        scene.setOnMouseDragged(event -> {
            rotateX.setAngle(rotateX.getAngle() - event.getSceneY());
            rotateY.setAngle(rotateY.getAngle() + event.getSceneX());
        });

        // Set the title and show the stage
        primaryStage.setTitle("3D Box Environment");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

