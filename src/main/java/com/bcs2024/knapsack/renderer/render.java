package com.bcs2024.knapsack.renderer;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Render extends Application {
    private static final double SCENE_SIZE = 500;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    @Override
    public void start(Stage outputStageObject) {

        // setting the title to the application
        outputStageObject.setTitle("Box Demo");

        // creating box object
        Box boxObject = new Box(50, 100, 50);
        boxObject.getTransforms().addAll(rotateX, rotateY);

        // creating outline box object
        Box outlineBox = new Box(52, 102, 52); 
        outlineBox.setMaterial(new PhongMaterial(Color.BLACK)); 
        outlineBox.getTransforms().addAll(rotateX, rotateY);

        // creating group for both outline object and block object
        Group groupObject = new Group();
        groupObject.getChildren().addAll(outlineBox, boxObject); 

        // creating camera object
        Camera perspectiveCamera = new PerspectiveCamera();

        // creating scene object to add group object
        Scene sceneObject = new Scene(groupObject, SCENE_SIZE, SCENE_SIZE);
        sceneObject.setFill(Color.LIGHTBLUE);
        sceneObject.setCamera(perspectiveCamera);

        // setting position of objects in 3D space
        boxObject.translateXProperty().set(SCENE_SIZE / 2);
        boxObject.translateYProperty().set(SCENE_SIZE / 2);

        outlineBox.translateXProperty().set(SCENE_SIZE / 2);
        outlineBox.translateYProperty().set(SCENE_SIZE / 2);

        // mouse pressed event handler
        sceneObject.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = rotateX.getAngle();
            anchorAngleY = rotateY.getAngle();
        });

        // mouse drag event handler
        sceneObject.setOnMouseDragged(event -> {
            rotateX.setAngle(anchorAngleX - (anchorY - event.getSceneY()));
            rotateY.setAngle(anchorAngleY + (anchorX - event.getSceneX()));
        });

        // display properties
        outputStageObject.setResizable(false);
        outputStageObject.setScene(sceneObject);
        outputStageObject.show();
    }

    public static void main(String[] args) {
        // launch JVM
        launch(args);
    }
}