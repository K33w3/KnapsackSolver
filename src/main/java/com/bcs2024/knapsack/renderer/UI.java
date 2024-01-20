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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private Font font;
    private Font fontTitle;
    private Font fontButton;

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
        // font import
        try {
            font = Font.loadFont(new FileInputStream(new File("src/main/resources/outline_pixel-7_solid.ttf")), 20);
            fontTitle = Font.loadFont(new FileInputStream(new File("src/main/resources/outline_pixel-7_solid.ttf")),
                    45);
            fontButton = Font.loadFont(new FileInputStream(new File("src/main/resources/outline_pixel-7_solid.ttf")),
                    15);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            font = new Font("Arial", 30);
            fontTitle = new Font("Arial", 45);
            fontButton = new Font("Arial", 15);
        }

        camera = new PerspectiveCamera();

        Image icon = new Image("https://minecraft.wiki/images/Red_Concrete.png");
        drawContainer();

        group.translateXProperty().set(700);
        group.translateYProperty().set(500 - height / 2);
        SubScene subScene3D = new SubScene(group, 1400, 1000, true, SceneAntialiasing.BALANCED);
        subScene3D.setFill(Color.SILVER);
        subScene3D.setCamera(camera);

        // 2d settings panel
        VBox settings = new VBox();
        settings.setAlignment(Pos.TOP_CENTER);
        settings.setSpacing(5);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(settings, subScene3D);
        splitPane.setDividerPositions(0.2);

        // title
        Label titleLabel = new Label("Settings");
        titleLabel.setFont(fontTitle);
        settings.getChildren().add(titleLabel);

        // group 39
        Label group39 = new Label("Group 39");
        group39.setFont(font);
        settings.getChildren().add(group39);

        // spacer 1
        Region spacer1 = new Region();
        spacer1.setPrefHeight(40);
        settings.getChildren().add(spacer1);

        // Create the Zoom Slider
        Slider zoomSlider = new Slider();
        zoomSlider.setMin(-1500);
        zoomSlider.setMax(1500);
        zoomSlider.setValue(0);
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            camera.translateZProperty().set(newValue.doubleValue());
        });

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
        optionsComboBox.setPromptText("Select Color");
        Button actionButton = new Button("Perform Action");
        actionButton.setFont(fontButton);

        Label colorLabel = new Label("Hide/Show Color");
        colorLabel.setFont(font);
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

        AnchorPane root = new AnchorPane();
        root.getChildren().add(splitPane);
        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);

        root.getChildren().add(zoomSlider);
        AnchorPane.setBottomAnchor(zoomSlider, 10.0);
        AnchorPane.setLeftAnchor(zoomSlider, (1700 - 300) / 2.0); // Assuming slider width is 300
        zoomSlider.toFront();

        // Create the Scene with the root AnchorPane
        Scene mainScene = new Scene(root, 1700, 1000);

        initMouseControl(group, subScene3D);

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

    private void initMouseControl(final Group group, final SubScene subScene3D) {
        final Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
        final Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        group.getTransforms().addAll(xRotate, yRotate);

        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        subScene3D.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        subScene3D.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
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

    private Slider createZoomSlider() {
        Slider zoomSlider = new Slider();
        zoomSlider.setMin(-1500);
        zoomSlider.setMax(1500);
        zoomSlider.setValue(0);
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            camera.translateZProperty().set(newValue.doubleValue());
        });
        zoomSlider.setPrefWidth(300); // Set a preferred width for the slider
        zoomSlider.setMaxWidth(Region.USE_PREF_SIZE);
        zoomSlider.setMinWidth(Region.USE_PREF_SIZE);
        return zoomSlider;
    }

}
