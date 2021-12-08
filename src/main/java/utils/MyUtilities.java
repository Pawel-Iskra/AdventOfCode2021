package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class MyUtilities {

    private MyUtilities() {
    }

    public static List<String> getInputLines(String pathToFile) throws IOException {
        File input = new File(pathToFile);
        BufferedReader br = new BufferedReader(new FileReader(input));
        List<String> inputLines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            inputLines.add(line);
        }
        return inputLines;
    }
}
