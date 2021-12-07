package day07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TheTreacheryOfWhales {

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

    private static List<Integer> getCrabPositions(List<String> crabPositionsStringList) {
        String[] crabPositionsStrings = crabPositionsStringList.get(0).split(",");
        return Arrays.stream(crabPositionsStrings).
                map(pos -> Integer.parseInt(pos))
                .collect(Collectors.toList());
    }

    private static int calculateFuelCostsParts(List<Integer> crabPositionsIntsList, int highestPosition, int part) {
        int[] fuelCosts = new int[highestPosition];
        for (int i = 0; i < fuelCosts.length; i++) {
            for (int j = 0; j < crabPositionsIntsList.size(); j++) {
                int crabPosition = crabPositionsIntsList.get(j);
                int diff = Math.abs(crabPosition - i);
                int sumToAdd = 0;
                switch (part) {
                    case 1 -> sumToAdd = diff;
                    case 2 -> sumToAdd = IntStream.range(1, diff + 1).sum();
                }
                fuelCosts[i] += sumToAdd;
            }
        }
        return Arrays.stream(fuelCosts)
                .min()
                .orElse(Integer.MIN_VALUE);
    }


    public static void main(String[] args) throws IOException {
        List<String> crabPositionsStringLine = getInputLines("src/main/java/day07/input.txt");
        List<Integer> crabPositionsIntsList = getCrabPositions(crabPositionsStringLine);
        int numOfCrabs = crabPositionsIntsList.size();

        Collections.sort(crabPositionsIntsList);
        int highestPosition = crabPositionsIntsList.get(numOfCrabs - 1);

        System.out.println("Part 1 = " + calculateFuelCostsParts(crabPositionsIntsList, highestPosition, 1));
        System.out.println("Part 2 = " + calculateFuelCostsParts(crabPositionsIntsList, highestPosition, 2));
    }
}
