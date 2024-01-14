package com.bcs2024.knapsack;

import com.bcs2024.knapsack.renderer.Renderer2;

import javafx.application.Application;
import javafx.scene.paint.Color;

public class Launch {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Renderer2.awaitStart(); // Wait for the application to start
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Renderer2 renderer2Instance = Renderer2.getInstance();

            //method parameters
            //renderer2Instance.addBlockAfterDelay(x, y, z, width, height, depth, millisecond to place after program start, COLOR.YOUR_COLOR);
            renderer2Instance.addBlockAfterDelay(120, 150, 100, 50, 50, 50, 1, Color.BLUE);

        }).start();

        Application.launch(Renderer2.class, args);
    }
}