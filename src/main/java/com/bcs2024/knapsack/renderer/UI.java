package com.bcs2024.knapsack.renderer;

import com.bcs2024.knapsack.algorithm.GeneticKnapsackSolver;
import com.bcs2024.knapsack.algorithm.GreedyKnapsackSolver;
import com.bcs2024.knapsack.algorithm.dancinglinks.DLSearch;
import com.bcs2024.knapsack.model.CargoSpace;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

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

    /**
     * Constructs a new UI object to display cargo space dimensions.
     * The UI is initialized with the dimensions of the provided cargo space
     * scaled by a factor of 30 for graphical representation.
     */
    public UI() {
        length = (int) (cargoSpace.getLength()) * 30;
        height = (int) (cargoSpace.getHeight()) * 30;
        width = (int) (cargoSpace.getWidth()) * 30;
    }

    /**
     * Initializes and configures the graphical user interface (UI) for the
     * application.
     * This method sets up various elements such as fonts, camera, 3D scene,
     * settings panel,
     * zoom slider, color selector, and the main application window.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(final Stage stage) {
        try {
            font = Font.loadFont(new FileInputStream("src/main/resources/outline_pixel-7_solid.ttf"), 20);
            fontTitle = Font.loadFont(new FileInputStream("src/main/resources/outline_pixel-7_solid.ttf"),
                    45);
            fontButton = Font.loadFont(new FileInputStream("src/main/resources/outline_pixel-7_solid.ttf"),
                    15);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            font = new Font("Arial", 30);
            fontTitle = new Font("Arial", 45);
            fontButton = new Font("Arial", 15);
        }

        camera = new PerspectiveCamera();

        final Image icon = new Image("https://minecraft.wiki/images/Red_Concrete.png");
        drawContainer();

        group.translateXProperty().set(700);
        group.translateYProperty().set(500 - height / 2);
        final SubScene subScene3D = new SubScene(group, 1400, 1000, true, SceneAntialiasing.BALANCED);
        subScene3D.setFill(Color.SILVER);
        subScene3D.setCamera(camera);

        // 2d settings panel
        final VBox settings = new VBox();
        settings.setAlignment(Pos.CENTER);
        settings.setSpacing(5);

        final SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(settings, subScene3D);
        splitPane.setDividerPositions(0.2);

        // title
        final Label titleLabel = new Label("Settings");
        titleLabel.setFont(fontTitle);
        settings.getChildren().add(titleLabel);

        // group 39
        final Label group39 = new Label("Group 39");
        group39.setFont(font);
        settings.getChildren().add(group39);

        // spacer 1
        final Region spacer1 = new Region();
        spacer1.setPrefHeight(40);
        settings.getChildren().add(spacer1);

        // Create the Zoom Slider
        final Slider zoomSlider = new Slider();
        zoomSlider.setMin(-1500);
        zoomSlider.setMax(1500);
        zoomSlider.setValue(0);
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            camera.translateZProperty().set(newValue.doubleValue());
        });

        // combo-box label
        final Label algorithmLabel = new Label("Re-Run Algorithm");
        algorithmLabel.setFont(font);

        // combo-box 3 options
        final ComboBox<String> optionsComboBoxAlgorithm = new ComboBox<>();
        optionsComboBoxAlgorithm.getItems().addAll("Greedy", "Genetic", "Dancing Links");
        optionsComboBoxAlgorithm.setPromptText("Select Algorithm");
        final Button actionButton3 = new Button("Run Algorithm");
        actionButton3.setFont(fontButton);

        settings.getChildren().addAll(algorithmLabel, optionsComboBoxAlgorithm, actionButton3);

        // action button 3 handling
        actionButton3.setOnAction(event -> {
            final String selectedOption = optionsComboBoxAlgorithm.getValue();
            switch (selectedOption) {
                case "Greedy":
                    cargoSpace = new CargoSpace();
                    System.out.println("Greedy selected");
                    final GreedyKnapsackSolver greedyKnapsackSolver = new GreedyKnapsackSolver();
                    greedyKnapsackSolver.solve();
                    cleanContainer();
                    drawContainer();
                    break;
                case "Genetic":
                    cargoSpace = new CargoSpace();
                    System.out.println("Genetic selected");
                    final GeneticKnapsackSolver geneticKnapsackSolver = new GeneticKnapsackSolver();
                    geneticKnapsackSolver.solve();
                    cleanContainer();
                    drawContainer();
                    break;
                case "Dancing Links":
                    cargoSpace = new CargoSpace();
                    System.out.println("Dancing Links selected");
                    javafx.application.Platform.runLater(() -> {
                        final DLSearch dlx = new DLSearch();
                        dlx.createPositions();
                        cleanContainer();
                        drawContainer();
                    });
                    break;
                default:
                    System.out.println("No option selected");
                    break;
            }
        });

        // spacer 2
        final Region spacer2 = new Region();
        spacer2.setPrefHeight(40);
        settings.getChildren().add(spacer2);

        // color selector logic
        final Map<String, Boolean> colorState = new HashMap<>(); // hasmap is super easy and cheap to use
        colorState.put("Red", false); // false indicates the color is not hidden initially
        colorState.put("Green", false);
        colorState.put("Blue", false);

        final ComboBox<String> optionsComboBox = new ComboBox<>();
        optionsComboBox.getItems().addAll("Red", "Green", "Blue");
        optionsComboBox.setPromptText("Select Color");
        final Button actionButton = new Button("Perform Action");
        actionButton.setFont(fontButton);

        final Label colorLabel = new Label("Hide/Show Color");
        colorLabel.setFont(font);
        settings.getChildren().add(colorLabel);

        actionButton.setOnAction(event -> {
            final String selectedOption = optionsComboBox.getValue();
            if (selectedOption != null) {
                // toggle between hiding and showing the color
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

        final Region spacer3 = new Region();
        spacer3.setPrefHeight(40);
        settings.getChildren().add(spacer3);

        final AnchorPane root = new AnchorPane();
        root.getChildren().add(splitPane);
        AnchorPane.setTopAnchor(splitPane, 0.0);
        AnchorPane.setBottomAnchor(splitPane, 0.0);
        AnchorPane.setLeftAnchor(splitPane, 0.0);
        AnchorPane.setRightAnchor(splitPane, 0.0);

        root.getChildren().add(zoomSlider);
        AnchorPane.setBottomAnchor(zoomSlider, 35.0);
        zoomSlider.setPrefWidth(750);
        AnchorPane.setLeftAnchor(zoomSlider, (1200) / 2.0);
        zoomSlider.toFront();

        // create the Scene with the root AnchorPane
        final Scene mainScene = new Scene(root, 1650, 1000);

        initMouseControl(group, subScene3D);

        // staging of stage
        stage.setTitle("KnapSack Visualizer");
        stage.getIcons().add(icon);
        stage.setResizable(true);

        // Adjust the size of the subScene3D relative to the splitPane size
        subScene3D.widthProperty().bind(splitPane.widthProperty().multiply(0.82)); // Adjust this value as needed
        subScene3D.heightProperty().bind(splitPane.heightProperty().multiply(0.82)); // Adjust this value as needed

        // Define a binding for the camera's Z position that includes the zoom slider's value
        final DoubleBinding cameraZBinding = subScene3D.widthProperty().add(subScene3D.heightProperty()).divide(-4.0).add(zoomSlider.valueProperty()).add(-500); // Adjust this as needed
        camera.translateZProperty().bind(cameraZBinding);

        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);

        // Bind the group's translate properties to center it within subScene3D
        final DoubleBinding centerXBinding = subScene3D.widthProperty().divide(2).subtract(width / 2.0);
        final DoubleBinding centerYBinding = subScene3D.heightProperty().divide(2).subtract(height / 2.0);

        group.translateXProperty().bind(centerXBinding);
        group.translateYProperty().bind(centerYBinding);

        // Adjust group translateZ if the object is off-center in depth
        group.translateZProperty().set(length / 2.0); // Adjust this based on your object's size and preferred position

        stage.setScene(mainScene);
        stage.show();
    }

    /**
     * Draws 3D boxes representing occupied spaces within the cargo space and
     * creates an outer container box to enclose the cargo space.
     * The method iterates through the cargo space data, creating boxes for each
     * occupied space, and positions them based on the cargo space dimensions.
     * It also adds an outer container box to enclose the cargo space for
     * visualization.
     */
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

    /**
     * Retrieves and returns a Color based on the given ID.
     *
     * @param id The ID used to determine the Color.
     * @return A Color associated with the provided ID.
     */
    private Color getColorById(final int id) {
        // System.out.println("ID: " + id); // Debug print
        return switch (id) {
            case 0 -> Color.ORANGE;
            case 1, 4 -> Color.BLUE;
            case 2, 5 -> Color.GREEN;
            case 3, 6 -> Color.RED;
            default -> Color.SILVER;
        };
    }

    /**
     * Initializes mouse control for adjusting the view orientation in the 3D scene.
     * This method allows the user to interactively rotate the 3D view by dragging
     * the mouse.
     *
     * @param group      The 3D scene's group to which rotation transforms are
     *                   applied.
     * @param subScene3D The SubScene representing the 3D scene for mouse
     *                   interaction.
     */
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

    /**
     * Sets the solution state for a problem represented by a three-dimensional
     * integer array.
     *
     * @param state A three-dimensional integer array representing the solution
     *              state.
     */
    public void setSolution(final int[][][] state) {
        // this.solution = state;
    }

    /**
     * Launches and displays the graphical user interface (GUI) for the application.
     * This method initiates the application's main window and user interface
     * components.
     */
    public void show() {
        launch();
    }

    /**
     * Hides 3D boxes of a specific color within the 3D scene.
     * This method iterates through the 3D scene's children, identifies boxes
     * with the specified color, and sets them to be invisible.
     *
     * @param colorStr A string representation of the color to hide (e.g., "Red",
     *                 "Green").
     */
    private void hideColor(final String colorStr) {
        final Color colorToHide = stringToColor(colorStr);
        for (final Node node : group.getChildren()) {
            if (node instanceof Box) {
                final Box box = (Box) node;
                final PhongMaterial material = (PhongMaterial) box.getMaterial();
                if (material.getDiffuseColor().equals(colorToHide)) {
                    box.setVisible(false);
                }
            }
        }
        System.out.println("Hiding " + colorStr);
    }

    /**
     * Shows 3D boxes of a specific color within the 3D scene.
     * This method iterates through the 3D scene's children, identifies boxes
     * with the specified color, and sets them to be visible.
     *
     * @param colorStr A string representation of the color to show (e.g., "Red",
     *                 "Green").
     */
    private void showColor(final String colorStr) {
        final Color colorToShow = stringToColor(colorStr);
        for (final Node node : group.getChildren()) {
            if (node instanceof Box) {
                final Box box = (Box) node;
                final PhongMaterial material = (PhongMaterial) box.getMaterial();
                if (material.getDiffuseColor().equals(colorToShow)) {
                    box.setVisible(true);
                }
            }
        }
        System.out.println("Showing " + colorStr);
    }

    /**
     * Converts a string representation of a color to a corresponding JavaFX Color
     * object.
     *
     * @param colorStr A string representing a color (e.g., "Red", "Green", "Blue").
     * @return The JavaFX Color object corresponding to the input color string, or
     * null if not found.
     */
    private Color stringToColor(final String colorStr) {
        return switch (colorStr) {
            case "Red" -> Color.RED;
            case "Green" -> Color.GREEN;
            case "Blue" -> Color.BLUE;
            default -> null;
        };
    }

    private void cleanContainer() {
        group.getChildren().clear();
    }

}
