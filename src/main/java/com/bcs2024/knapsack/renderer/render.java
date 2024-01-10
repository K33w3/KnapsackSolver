package com.bcs2024.knapsack.renderer;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Render extends Application {
    @Override
    public void start(Stage outputStageObject) {
        // setting the title to the application
        outputStageObject.setTitle("Sphere Demo");
        // creating sphere object
        Sphere sphereObject = new Sphere(50);
        // creating group object
        Group groupObject = new Group();
        groupObject.getChildren().add(sphereObject);
        // creating camera object
        Camera perspectiveCamera = new PerspectiveCamera();
        // creating scene object for adding group object
        Scene sceneObject = new Scene(groupObject, 500, 500);
        sceneObject.setFill(Color.GREEN);
        sceneObject.setCamera(perspectiveCamera);
        // setting translate property for sphere object
        sphereObject.translateXProperty().set(500 / 2);
        sphereObject.translateYProperty().set(500 / 2);
        // Key pressed event handler
        outputStageObject.addEventHandler(KeyEvent.KEY_PRESSED, eventHandler -> {
            switch (eventHandler.getCode()) {
                // increase the sphere size if we press A
                case A:
                    sphereObject.translateZProperty().set(sphereObject.getTranslateZ() + 300);
                    break;
                // decrease the sphere size if we press B
                case B:
                    sphereObject.translateZProperty().set(sphereObject.getTranslateZ() - 300);
                    break;
            }
        });
        outputStageObject.setScene(sceneObject);
        // displaying output
        outputStageObject.show();
    }

    public static void main(String[] args) {
        // JVM calls start method automatically
        launch(args);
    }
}