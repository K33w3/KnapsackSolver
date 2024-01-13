package com.bcs2024.knapsack.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public static void writeInFile(String fileName, String text) {
        String projectPath = System.getProperty("user.dir"); // Gets the project's root path
        String relativePath = projectPath + File.separator + "output" + File.separator + fileName; // Constructs the relative path

        File file = new File(relativePath);

        if (!file.exists()) {
            try {
                file.createNewFile(); // Create the file if it doesn't exist
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
