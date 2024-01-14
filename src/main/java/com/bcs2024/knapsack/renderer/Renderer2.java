package com.bcs2024.knapsack.renderer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class Renderer2 extends Application {

    private static final double CONTAINER_WIDTH = 600;
    private static final double CONTAINER_HEIGHT = 400;
    private static final double CONTAINER_DEPTH = 300;

    private static final double BLOCK_SIZE = 50;

    private Group container;
    private Rotate rotateX;
    private Rotate rotateY;

    private static Renderer2 instance;
    private static final CountDownLatch latch = new CountDownLatch(1);

    public Renderer2() {
        instance = this;
    }

    public static Renderer2 getInstance() {
        return instance;
    }

    public static void awaitStart() throws InterruptedException {
        latch.await();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        container = new Group();
        container.setTranslateX(CONTAINER_WIDTH / 2);
        container.setTranslateY(CONTAINER_HEIGHT / 2);
        container.setTranslateZ(CONTAINER_DEPTH / 2);

        rotateX = new Rotate(0, Rotate.X_AXIS);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        container.getTransforms().addAll(rotateX, rotateY);

        Scene scene = new Scene(container, CONTAINER_WIDTH, CONTAINER_HEIGHT, true);
        scene.setFill(Color.WHITE);
        scene.setCamera(new PerspectiveCamera());

        scene.setOnMouseDragged(this::handleMouseDragged);

        Box block = new Box(10, 10, 10);
        block.setTranslateX(10);
        block.setTranslateY(10);
        block.setTranslateZ(10);

        // Set the color of the block
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.WHITE);
        block.setMaterial(material);

        container.getChildren().add(block);

        primaryStage.setTitle("Block Placer");
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            // cleanup code that might be needed in the future can be added here
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();

        latch.countDown(); // Notify other threads that the application has started
    }

    private void handleMouseDragged(MouseEvent event) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();

        double rotateXAngle = (mouseY - CONTAINER_HEIGHT / 2) / CONTAINER_HEIGHT * 360;
        double rotateYAngle = (mouseX - CONTAINER_WIDTH / 2) / CONTAINER_WIDTH * 360;

        rotateX.setAngle(rotateXAngle);
        rotateY.setAngle(rotateYAngle);
    }

    private void addBlock(double x, double y, double z, double width, double height, double depth, Color color) {
        System.out.println("Adding block at (" + x + ", " + y + ", " + z + ") with dimensions (" + width + ", " + height + ", " + depth + ") and color " + color);
        Box block = new Box(width, height, depth);
        block.setTranslateX(x);
        block.setTranslateY(y);
        block.setTranslateZ(z);
        block.setMaterial(new PhongMaterial(color));
        container.getChildren().add(block);
    }
    
    private void addBlockLater(double x, double y, double z, double width, double height, double depth, Color color) {
        System.out.println("Scheduling block to be added later at (" + x + ", " + y + ", " + z + ") with dimensions (" + width + ", " + height + ", " + depth + ") and color " + color);
        Platform.runLater(() -> addBlock(x, y, z, width, height, depth, color));
    }

    public void addBlockAfterDelay(double x, double y, double z, double width, double height, double depth, long delayMillis, Color color) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                addBlockLater(x, y, z, width, height, depth, color);
            }
        }, delayMillis);
    }
}