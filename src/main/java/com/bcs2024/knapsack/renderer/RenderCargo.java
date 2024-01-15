package com.bcs2024.knapsack.renderer;

import com.bcs2024.knapsack.model.CargoSpace;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class RenderCargo extends Application {

    private static final double SCENE_HEIGHT = 900;
    private static final double SCENE_WIDTH = 1600;
    private static final int sizeMultiplier = 35;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    private double anchorX, anchorY;

    private static CargoSpace cargoSpace;

    @Override
    public void start(Stage stage) {
        // setting the title to the application
        stage.setTitle("Knapsack Solver");

        // Create a group to hold all the cells
        Group cellsGroup = new Group();
        CargoSpace cargoSpace = new CargoSpace();
        Box cargoSpaceBox = new Box(cargoSpace.getWidth() * sizeMultiplier, cargoSpace.getHeight() * sizeMultiplier, cargoSpace.getLength() * sizeMultiplier);
        cargoSpaceBox.setMaterial(new PhongMaterial(Color.TRANSPARENT));
        cargoSpaceBox.getTransforms().addAll(rotateX, rotateY);

        // Assuming cargoSpace.getOccupied() returns a 3D boolean array where true means the cell is occupied
        int[][][] occupied = cargoSpace.getOccupied(); // TODO

        for (int x = 0; x < occupied.length; x++) {
            for (int y = 0; y < occupied[0].length; y++) {
                for (int z = 0; z < occupied[0][0].length; z++) {
                    if (occupied[x][y][z] != 0) { // Assuming non-zero indicates an occupied cell
                        Box cell = new Box(sizeMultiplier, sizeMultiplier, sizeMultiplier);
                        cell.setTranslateX(x * sizeMultiplier);
                        cell.setTranslateY(y * sizeMultiplier);
                        cell.setTranslateZ(z * sizeMultiplier);

                        PhongMaterial material = new PhongMaterial();
                        switch(occupied[x][y][z]) {
                            case 1: // Type A Parcel
                                material.setDiffuseColor(Color.RED);
                                break;
                            case 2: // Type B Parcel
                                material.setDiffuseColor(Color.GREEN);
                                break;
                            case 3: // Type C Parcel
                                material.setDiffuseColor(Color.BLUE);
                                break;
                            default:
                                material.setDiffuseColor(Color.GRAY);
                        }

                        cell.setMaterial(material);
                        cellsGroup.getChildren().add(cell);
                    }
                }
            }
        }

        Group root = new Group();
        root.getChildren().add(cargoSpaceBox);
        root.getChildren().add(cellsGroup);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, true);
        scene.setFill(Color.WHITE);
        stage.setScene(scene);

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-500);
        scene.setCamera(camera);

        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(SCENE_WIDTH / 2);
        light.setTranslateY(SCENE_HEIGHT / 2);
        root.getChildren().add(light);


        // creating camera object
        Camera perspectiveCamera = new PerspectiveCamera();

        // creating scene object to add group object
        Scene sceneObject = new Scene(cellsGroup, SCENE_WIDTH, SCENE_HEIGHT);
        sceneObject.setFill(Color.WHITE);
        sceneObject.setCamera(perspectiveCamera);

        // setting position of objects in 3D space
        cellsGroup.setTranslateX(SCENE_WIDTH / 2);
        cellsGroup.setTranslateY(SCENE_HEIGHT / 2);

        // adding rotation to the camera
        sceneObject.setOnMousePressed((MouseEvent event) -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = rotateX.getAngle() - event.getSceneX();
            anchorAngleY = rotateY.getAngle() - event.getSceneY();
        });

        sceneObject.setOnMouseDragged((MouseEvent event) -> {
            rotateX.setAngle(anchorAngleX + event.getSceneY());
            rotateY.setAngle(anchorAngleY + event.getSceneX());
        });

        stage.setResizable(false);
        stage.setScene(sceneObject);
        stage.show();
    }

    private Box createCell(int x, int y, int z) {
        Box cell = new Box(sizeMultiplier, sizeMultiplier, sizeMultiplier);
        cell.setTranslateX(x * sizeMultiplier);
        cell.setTranslateY(y * sizeMultiplier);
        cell.setTranslateZ(z * sizeMultiplier);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.rgb(127, 127, 127, 0.7)); // Semi-transparent color
        cell.setMaterial(material);

        return cell;
    }

    public static void setCargoSpace(CargoSpace space) {
        cargoSpace = space;
    }

    public static void main(String[] args) {
        launch(args);
    }
}