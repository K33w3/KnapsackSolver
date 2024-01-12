package com.bcs2024.knapsack.renderer;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Parcel;
import com.bcs2024.knapsack.model.ParcelPlacement;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Render extends Application {
    private static final double SCENE_HEIGHT = 900;
    private static final double SCENE_WIDTH = 1600;

    private static final int sizeMultiplier = 35;
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

        CargoSpace cargoSpace = new CargoSpace(33 *sizeMultiplier, 5*sizeMultiplier, 8*sizeMultiplier); // multiply by 2 instead

        Box transparentBox = new Box(cargoSpace.getWidth(), cargoSpace.getHeight(), cargoSpace.getLength());
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.rgb(127, 127, 127, 0.3));
        transparentBox.setMaterial(material);
        transparentBox.getTransforms().addAll(rotateX, rotateY);

        // creating group for both outline object and block object
        Group groupObject = new Group();
        groupObject.getChildren().addAll(outlineBox, boxObject, transparentBox);

        // creating camera object
        Camera perspectiveCamera = new PerspectiveCamera();

        // creating scene object to add group object
        Scene sceneObject = new Scene(groupObject, SCENE_WIDTH, SCENE_HEIGHT);
        sceneObject.setFill(Color.LIGHTBLUE);
        sceneObject.setCamera(perspectiveCamera);

        // setting position of objects in 3D space
        boxObject.translateXProperty().set(SCENE_WIDTH / 2);
        boxObject.translateYProperty().set(SCENE_HEIGHT / 2);

        outlineBox.translateXProperty().set(SCENE_WIDTH / 2);
        outlineBox.translateYProperty().set(SCENE_HEIGHT / 2);

        transparentBox.translateXProperty().set(SCENE_WIDTH / 2);
        transparentBox.translateYProperty().set(SCENE_HEIGHT / 2);

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

    public static void displayParcel(ParcelPlacement parcel) {
        int length = (int) (parcel.getLength() * 2 * sizeMultiplier);
        int width = (int) (parcel.getWidth() * 2 * sizeMultiplier);
        int height = (int) (parcel.getHeight() * 2 * sizeMultiplier);

// Create a Box
        Box boxObject = new Box(width, height, length);

// Set position
        boxObject.setTranslateX(parcel.getX());
        boxObject.setTranslateY(parcel.getY());
        boxObject.setTranslateZ(parcel.getZ());

        // Create rotations
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

        // Set rotations
        boxObject.getTransforms().addAll(rotateX, rotateY);
    }

    private List<Transform> getRotationTransforms() {
        List<Transform> transforms = new ArrayList<>();
        // Add logic to determine the correct transforms based on orientation
        return transforms;
    }

    public static void main(String[] args) {
        launch(args);

        displayParcel(new ParcelPlacement(new Parcel("A"), 100, 100, 100, 0));
    }
}