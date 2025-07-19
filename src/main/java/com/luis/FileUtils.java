package com.luis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static String loadTextFile(String filename) throws IOException {
        return Files.readString(Path.of(filename));
    }
}
