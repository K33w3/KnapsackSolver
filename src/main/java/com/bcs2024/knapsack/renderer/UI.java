package com.bcs2024.knapsack.renderer;

import com.bcs2024.knapsack.model.CargoSpace;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class UI extends Application {

    public static CargoSpace cargoSpace = new CargoSpace();
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
        length = (int) (cargoSpace.getLength()) * 30;
        height = (int) (cargoSpace.getHeight()) * 30;
        width = (int) (cargoSpace.getWidth()) * 30;
        // solution = GreedyKnapsackSolver.matrix;
        // solution = cargoSpace.getOccupied();
        // solution = Experiment.field;
    }

    @Override
    public void start(final Stage stage) {
        camera = new PerspectiveCamera();
        Image icon = new Image("https://minecraft.wiki/images/Red_Concrete.png");
        drawContainer();

        // 3d world centering
        group.translateXProperty().set(700);
        group.translateYProperty().set(500 - height / 2);

        SubScene subScene3D = new SubScene(group, 1400, 1000, true, SceneAntialiasing.BALANCED);
        subScene3D.setFill(Color.SILVER);
        subScene3D.setCamera(camera);

        // 2d settings panel
        VBox settings = new VBox();
        settings.setAlignment(Pos.TOP_CENTER);
        settings.setSpacing(5);

        // title
        Label titleLabel = new Label("Settings");
        titleLabel.setFont(new Font("Arial", 30));
        settings.getChildren().add(titleLabel);

        // group 39
        Label group39 = new Label("Group 39");
        group39.setFont(new Font("Arial", 15));
        settings.getChildren().add(group39);

        // spacer 1
        Region spacer1 = new Region();
        spacer1.setPrefHeight(40);
        settings.getChildren().add(spacer1);

        // zoom slider
        Label zoomLabel = new Label("Zoom Level");
        zoomLabel.setFont(new Font("Arial", 15));
        settings.getChildren().add(zoomLabel);

        Slider zoomSlider = new Slider();
        zoomSlider.setMin(-1500);
        zoomSlider.setMax(1500);
        zoomSlider.setValue(0);
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            camera.translateZProperty().set(newValue.doubleValue());
        });
        settings.getChildren().add(zoomSlider);

        // spacer 2
        Region spacer2 = new Region();
        spacer2.setPrefHeight(40);
        settings.getChildren().add(spacer2);

        // color selector logic
        Map<String, Boolean> colorState = new HashMap<>(); // why hashmap you may ask? Because it is the easiest way to
                                                           // do it lol (and cheap as well) :)
        colorState.put("Red", false); // false indicates the color is not hidden initially
        colorState.put("Green", false);
        colorState.put("Blue", false);

        ComboBox<String> optionsComboBox = new ComboBox<>();
        optionsComboBox.getItems().addAll("Red", "Green", "Blue");
        Button actionButton = new Button("Perform Action");

        Label colorLabel = new Label("Hide/Show Color");
        colorLabel.setFont(new Font("Arial", 15));
        settings.getChildren().add(colorLabel);

        actionButton.setOnAction(event -> {
            String selectedOption = optionsComboBox.getValue();
            if (selectedOption != null) {
                // Toggle between hiding and showing the color
                if (colorState.get(selectedOption)) {
                    showColor(selectedOption);
                    colorState.put(selectedOption, false);
                } else {
                    hideColor(selectedOption);
                    colorState.put(selectedOption, true);
                }
            } else {
                System.out.println("No option selected");
            }
        });

        settings.getChildren().addAll(optionsComboBox, actionButton);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(settings, subScene3D);
        splitPane.setDividerPositions(0.2);

        // mouse handling method
        initMouseControl(group, splitPane);

        // root pane
        Scene mainScene = new Scene(splitPane, 1700, 1000);

        // staging of stage
        stage.setTitle("KnapSack");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.show();
    }

    private void drawContainer() {
        for (int i = 0; i < cargoSpace.getOccupied().length; i++) {
            for (int j = 0; j < cargoSpace.getOccupied()[0].length; j++) {
                for (int k = 0; k < cargoSpace.getOccupied()[0][0].length; k++) {
                    if (cargoSpace.getOccupied()[i][j][k] != -1) {
                        final double boxWidth = width / cargoSpace.getOccupied().length;
                        final double boxHeight = height / cargoSpace.getOccupied()[0].length;
                        final double boxDepth = length / cargoSpace.getOccupied()[0][0].length;

                        box = new Box(boxWidth, boxHeight, boxDepth);

                        box.translateXProperty().set((i - cargoSpace.getOccupied().length / 2) * boxWidth);
                        box.translateYProperty().set((j - cargoSpace.getOccupied()[0].length / 2) * boxHeight);
                        box.translateZProperty().set((k - cargoSpace.getOccupied()[0][0].length / 2) * boxDepth);

                        final PhongMaterial material = new PhongMaterial();
                        material.setDiffuseColor(getColorById(cargoSpace.getOccupied()[i][j][k]));
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

    private void initMouseControl(final Group group, final SplitPane splitPane) {
        final Rotate xRotate;
        final Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS));

        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        splitPane.setOnMousePressed(e -> {
            anchorX = e.getSceneX();
            anchorY = e.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        splitPane.setOnMouseDragged(e -> {
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

    private void hideColor(String colorStr) {
        Color colorToHide = stringToColor(colorStr);
        for (Node node : group.getChildren()) {
            if (node instanceof Box) {
                Box box = (Box) node;
                PhongMaterial material = (PhongMaterial) box.getMaterial();
                if (material.getDiffuseColor().equals(colorToHide)) {
                    box.setVisible(false);
                }
            }
        }
        System.out.println("Hiding " + colorStr);
    }
    
    private void showColor(String colorStr) {
        Color colorToShow = stringToColor(colorStr);
        for (Node node : group.getChildren()) {
            if (node instanceof Box) {
                Box box = (Box) node;
                PhongMaterial material = (PhongMaterial) box.getMaterial();
                if (material.getDiffuseColor().equals(colorToShow)) {
                    box.setVisible(true);
                }
            }
        }
        System.out.println("Showing " + colorStr);
    }
    
    private Color stringToColor(String colorStr) {
        return switch (colorStr) {
            case "Red" -> Color.RED;
            case "Green" -> Color.GREEN;
            case "Blue" -> Color.BLUE;
            default -> null;
        };
    }
    
}
