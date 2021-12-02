package day02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dive {

    private static List<String> getInputLines(String pathToFile) throws IOException {
        File input = new File(pathToFile);
        BufferedReader br = new BufferedReader(new FileReader(input));
        List<String> inputLines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            inputLines.add(line);
        }
        return inputLines;
    }

    private static int getResultForPart1(List<String> commandLines) {
        int[] horizontal = {0};
        int[] depth = {0};
        commandLines.forEach(
                commandLine -> {
                    String command = commandLine.split(" ")[0].strip();
                    int value = Integer.parseInt(commandLine.split(" ")[1].strip());
                    switch (command) {
                        case "forward" -> horizontal[0] += value;
                        case "down" -> depth[0] += value;
                        case "up" -> depth[0] -= value;
                    }
                });
        return horizontal[0] * depth[0];
    }

    private static int getResultForPart2(List<String> commandLines) {
        int[] horizontal = {0};
        int[] depth = {0};
        int[] aim = {0};
        commandLines.forEach(
                commandLine -> {
                    String command = commandLine.split(" ")[0].strip();
                    int value = Integer.parseInt(commandLine.split(" ")[1].strip());
                    switch (command) {
                        case "forward" -> {
                            horizontal[0] += value;
                            depth[0] += aim[0] * value;
                        }
                        case "down" -> aim[0] += value;
                        case "up" -> aim[0] -= value;
                    }
                });
        return horizontal[0] * depth[0];
    }

    public static void main(String[] args) throws IOException {
        List<String> commandLines = getInputLines("src/main/java/day02/input.txt");

        System.out.println("Part 1 = " + getResultForPart1(commandLines));
        System.out.println("Part 2 = " + getResultForPart2(commandLines));
    }
}
