package day06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lanternfish {

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

    private static long calculateNumberOfFishInOcean(long[] numberOfFishInSpecificPeriod) {
        long counter = 0;
        int arrayLength = numberOfFishInSpecificPeriod.length;
        for (int i = 0; i < arrayLength; i++) {
            counter += numberOfFishInSpecificPeriod[i];
        }
        return counter;
    }

    private static void processADay(long[] numberOfFishInSpecificPeriod) {
        int arrayLength = numberOfFishInSpecificPeriod.length;
        long readyToGiveBirth = numberOfFishInSpecificPeriod[0];
        for (int i = 1; i < arrayLength; i++) {
            numberOfFishInSpecificPeriod[i - 1] = numberOfFishInSpecificPeriod[i];
        }
        numberOfFishInSpecificPeriod[6] += readyToGiveBirth;
        numberOfFishInSpecificPeriod[8] = readyToGiveBirth;
    }

    private static long calculateNumberOfFishAfterDays(long[] numberOfFishInSpecificPeriod, int days) {
        for (int i = 0; i < days; i++) {
            processADay(numberOfFishInSpecificPeriod);
        }
        return calculateNumberOfFishInOcean(numberOfFishInSpecificPeriod);
    }

    private static long[] getNumberOfFishInSpecificPeriod(List<String> inputLines, int numberOfPeriods) {
        long[] numberOfFishInSpecificPeriod = new long[numberOfPeriods];
        Arrays.stream(inputLines.get(0).split(","))
                .forEach(number -> numberOfFishInSpecificPeriod[Integer.parseInt(number)]++);
        return numberOfFishInSpecificPeriod;
    }

    public static void main(String[] args) throws IOException {
        List<String> inputLines = getInputLines("src/main/java/day06/input.txt");

        System.out.println("Part 1 = " + calculateNumberOfFishAfterDays(getNumberOfFishInSpecificPeriod(inputLines, 9), 80));
        System.out.println("Part 2 = " + calculateNumberOfFishAfterDays(getNumberOfFishInSpecificPeriod(inputLines, 9), 256));
    }
}
