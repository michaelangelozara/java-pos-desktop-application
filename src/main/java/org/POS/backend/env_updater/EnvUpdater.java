package org.POS.backend.env_updater;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EnvUpdater {
    public static void updateEnvFile(String key, String value){
        try {
            Path path = Paths.get(".env");
            List<String> lines = Files.readAllLines(path);
            boolean updated = false;

            // Modify the specific key
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(key + "=")) {
                    lines.set(i, key + "=" + value);
                    updated = true;
                    break;
                }
            }

            // If key doesn't exist, add it
            if (!updated) {
                lines.add(key + "=" + value);
            }

            // Write changes back to the .env file
            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
