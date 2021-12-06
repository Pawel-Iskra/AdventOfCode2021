package day03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinaryDiagnostic {

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

    private static List<int[]> calculateOnesAndZeros(List<String> binaryNumberLines) {
        int binaryNumberLength = binaryNumberLines.get(0).strip().length();
        int[] ones = new int[binaryNumberLength];
        int[] zeros = new int[binaryNumberLength];
        for (int i = 0; i < binaryNumberLines.size(); i++) {
            String binaryNumberLine = binaryNumberLines.get(i);
            for (int j = 0; j < binaryNumberLength; j++) {
                if ('1' == binaryNumberLine.charAt(j)) ones[j]++;
                if ('0' == binaryNumberLine.charAt(j)) zeros[j]++;
            }
        }
        List<int[]> resultArrays = new ArrayList<>();
        resultArrays.add(zeros);
        resultArrays.add(ones);
        return resultArrays;
    }

    private static String getCommonBits(List<String> binaryNumberLines, String type) {
        int binaryNumberLength = binaryNumberLines.get(0).strip().length();
        List<int[]> zerosAndOnes = calculateOnesAndZeros(binaryNumberLines);

        String commonBits = "";
        switch (type) {
            case "most":
                for (int i = 0; i < binaryNumberLength; i++) {
                    if (zerosAndOnes.get(0)[i] > zerosAndOnes.get(1)[i]) commonBits += "0";
                    else commonBits += "1";
                }
                break;
            case "least":
                for (int i = 0; i < binaryNumberLength; i++) {
                    if (zerosAndOnes.get(0)[i] <= zerosAndOnes.get(1)[i]) commonBits += "0";
                    else commonBits += "1";
                }
                break;
        }
        return commonBits;
    }

    private static int getOxygenGeneratorRating(List<String> binaryNumberLines, int numOfBits) {
        int oxygenGeneratorRating = 0;
        List<String> remainingNumbers = new ArrayList<>(binaryNumberLines);
        for (int i = 0; i < numOfBits; i++) {
            String mostCommonBits = getCommonBits(remainingNumbers, "most");
            List<String> numbersThatMeetCriteria = new ArrayList<>();
            for (String binaryNumber : remainingNumbers) {
                if (binaryNumber.charAt(i) == mostCommonBits.charAt(i)) {
                    numbersThatMeetCriteria.add(binaryNumber);
                }
            }
            remainingNumbers = new ArrayList<>(numbersThatMeetCriteria);
            if (remainingNumbers.size() == 1) {
                oxygenGeneratorRating = Integer.parseInt(remainingNumbers.get(0), 2);
                break;
            }
        }
        return oxygenGeneratorRating;
    }

    private static int getCo2ScrubberRating(List<String> binaryNumberLines, int numOfBits) {
        int co2ScrubberRating = 0;
        List<String> remainingNumbers = new ArrayList<>(binaryNumberLines);
        for (int i = 0; i < numOfBits; i++) {
            String leastCommonBits = getCommonBits(remainingNumbers, "least");
            List<String> numbersThatMeetCriteria = new ArrayList<>();
            for (String binaryNumber : remainingNumbers) {
                if (binaryNumber.charAt(i) == leastCommonBits.charAt(i)) {
                    numbersThatMeetCriteria.add(binaryNumber);
                }
            }
            remainingNumbers = new ArrayList<>(numbersThatMeetCriteria);
            if (remainingNumbers.size() == 1) {
                co2ScrubberRating = Integer.parseInt(remainingNumbers.get(0), 2);
                break;
            }
        }
        return co2ScrubberRating;
    }

    private static int calculateLifeSupportRating(List<String> binaryNumberLines) {
        int numOfBits = binaryNumberLines.get(0).length();
        int oxygenGeneratorRating = getOxygenGeneratorRating(binaryNumberLines, numOfBits);
        int co2ScrubberRating = getCo2ScrubberRating(binaryNumberLines, numOfBits);
        return oxygenGeneratorRating * co2ScrubberRating;
    }

    private static int calculatePowerConsumption(List<String> binaryNumberLines) {
        String mostCommonBits = getCommonBits(binaryNumberLines, "most");
        int numOfBits = mostCommonBits.length();
        int gammaRate = Integer.parseInt(mostCommonBits, 2);
        int epsilonRate = ~gammaRate;
        String epsilonRateString = Integer.toBinaryString(epsilonRate).substring(32 - numOfBits);
        epsilonRate = Integer.parseInt(epsilonRateString, 2);
        return gammaRate * epsilonRate;
    }

    public static void main(String[] args) throws IOException {
        List<String> binaryNumberLines = getInputLines("src/main/java/day03/input.txt");

        System.out.println("Part 1 = " + calculatePowerConsumption(binaryNumberLines));
        System.out.println("Part 2 = " + calculateLifeSupportRating(binaryNumberLines));
    }
}
