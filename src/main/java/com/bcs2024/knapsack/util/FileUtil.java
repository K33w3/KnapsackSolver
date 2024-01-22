package com.bcs2024.knapsack.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Provides utility functions for file operations, such as writing text to a file.
 */
public class FileUtil {

    /**
     * Writes the specified text to a file. If the file does not exist, it is created.
     * If the file already exists, the text is appended to the file.
     * The file is located in the 'output' directory within the project's root path.
     *
     * @param fileName The name of the file where the text should be written.
     * @param text     The text to write to the file.
     */
    public static void writeInFile(final String fileName, final String text) {
        final String projectPath = System.getProperty("user.dir"); // Gets the project's root path
        final String relativePath = projectPath + File.separator + "output" + File.separator + fileName; // Constructs the relative path

        final File file = new File(relativePath);

        if (!file.exists()) {
            try {
                file.createNewFile(); // Create the file if it doesn't exist
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(text);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
