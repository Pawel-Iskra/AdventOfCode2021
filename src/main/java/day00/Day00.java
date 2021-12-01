package day00;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day00 {

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

    public static void main(String[] args) throws IOException {
        List<String> inputLines = getInputLines("src/main/java/day01/input.txt");
        int inputSize = inputLines.size();

        int increasesCounter = 0;
        int slidingWindowCounter = 0;
        for (int i = 3; i < inputSize; i++) {
            int depth1 = Integer.parseInt(inputLines.get(i - 3).strip().replace("[^0-9]+", ""));
            int depth2 = Integer.parseInt(inputLines.get(i - 2).strip().replace("[^0-9]+", ""));
            int depth3 = Integer.parseInt(inputLines.get(i - 1).strip().replace("[^0-9]+", ""));
            int depth4 = Integer.parseInt(inputLines.get(i).strip().replace("[^0-9]+", ""));

            if (depth2 > depth1) increasesCounter++;
            if ((depth2 + depth3 + depth4) > (depth1 + depth2 + depth3)) slidingWindowCounter++;

            System.out.println("depth1 = " + depth1);
            System.out.println("depth2 = " + depth2);
            System.out.println("depth3 = " + depth3);
            System.out.println("depth4 = " + depth4);
            System.out.println("=======================");
        }

        System.out.println("Part1 = " + increasesCounter);
        System.out.println("Part2 = " + slidingWindowCounter);
    }
}
