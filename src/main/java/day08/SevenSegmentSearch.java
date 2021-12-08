package day08;

import utils.MyUtilities;

import java.util.*;
import java.io.*;

public class SevenSegmentSearch {

    private static boolean containsNumber(String signal, String number, int expectedCommon) {
        int signalLength = signal.length();
        int counter = 0;
        for (int i = 0; i < signalLength; i++) {
            char currentDigit = signal.charAt(i);
            for (int j = 0; j < number.length(); j++) {
                if (currentDigit == number.charAt(j)) counter++;
            }
        }
        return counter == expectedCommon;
    }

    private static String[] makeAnalysisFromInputSignals(String inputSignals) {
        String[] inputSignalsArray = inputSignals.strip().split(" ");
        int numOfSignals = inputSignalsArray.length;
        String[] analysis = new String[numOfSignals];
        for (int i = 0; i < numOfSignals; i++) {
            String currentSignal = inputSignalsArray[i].strip();
            int currentSignalLength = currentSignal.length();
            switch (currentSignalLength) {
                case 2 -> analysis[1] = currentSignal; // must be digit 1
                case 3 -> analysis[7] = currentSignal; // must be digit 7
                case 4 -> analysis[4] = currentSignal; // must be digit 4
                case 7 -> analysis[8] = currentSignal; // must be digit 8
            }
        }
        for (int i = 0; i < numOfSignals; i++) {
            String currentSignal = inputSignalsArray[i].strip();
            int signalLength = currentSignal.length();
            switch (signalLength) {
                case 5 -> { // digit 2 or 3 or 5
                    if (containsNumber(currentSignal, analysis[7], analysis[7].length()))
                        analysis[3] = currentSignal; // must be 3
                    else if (containsNumber(currentSignal, analysis[4], analysis[4].length() / 2))
                        analysis[2] = currentSignal; // must be 2
                    else analysis[5] = currentSignal; // must be 5
                }
                case 6 -> { // digit 0 or 6 or 9
                    if (containsNumber(currentSignal, analysis[4], analysis[4].length()))
                        analysis[9] = currentSignal; // must be 9
                    else if (containsNumber(currentSignal, analysis[7], analysis[7].length())
                            && !containsNumber(currentSignal, analysis[4], analysis[4].length()))
                        analysis[0] = currentSignal; // must be 0
                    else analysis[6] = currentSignal; // must be 6
                }
            }
        }
        return analysis;
    }

    private static boolean isThatNumber(String currentSignal, String numberToCompare) {
        int counter = 0;
        for (int i = 0; i < currentSignal.length(); i++) {
            Character currentChar = currentSignal.charAt(i);
            if (numberToCompare.contains(String.valueOf(currentChar))) counter++;
        }
        return counter == numberToCompare.length();
    }

    private static String getDigitFromSignal(String[] analysis, String currentSignal) {
        String digit = "0";
        int currentSignalLength = currentSignal.length();
        switch (currentSignalLength) {
            case 2 -> digit = "1";
            case 3 -> digit = "7";
            case 4 -> digit = "4";
            case 5 -> {
                if (isThatNumber(currentSignal, analysis[2])) digit = "2";
                if (isThatNumber(currentSignal, analysis[3])) digit = "3";
                if (isThatNumber(currentSignal, analysis[5])) digit = "5";
            }
            case 6 -> {
                if (isThatNumber(currentSignal, analysis[0])) digit = "0";
                if (isThatNumber(currentSignal, analysis[6])) digit = "6";
                if (isThatNumber(currentSignal, analysis[9])) digit = "9";
            }
            case 7 -> digit = "8";
        }
        return digit;
    }

    private static String decodeOutputNumber(String[] analysis, String outputSignals) {
        String decodedNumber = "";
        String[] outputSignalsArray = outputSignals.strip().split(" ");
        int outputNumberLength = outputSignalsArray.length;
        for (int i = 0; i < outputNumberLength; i++) {
            String currentSignal = outputSignalsArray[i];
            String currentDigit = getDigitFromSignal(analysis, currentSignal);
            decodedNumber += currentDigit;
        }
        return decodedNumber;
    }

    private static int calculateSumOfOutputValues(List<String> entrySignalLines) {
        int[] sum = {0};
        entrySignalLines
                .forEach(signalLine -> {
                    signalLine = signalLine.replace("|", "&");
                    String[] inputOutputSignals = signalLine.strip().split("&");
                    String inputSignals = inputOutputSignals[0].strip();
                    String[] analysis = makeAnalysisFromInputSignals(inputSignals);
                    String outputSignals = inputOutputSignals[1].strip();
                    String outputDigits = decodeOutputNumber(analysis, outputSignals);
                    sum[0] += Integer.parseInt(outputDigits);
                });
        return sum[0];
    }

    private static int calculateAmountOfSpecificDigitsInOutputSignal(List<String> entrySignalLines, List<Integer> digitLengths) {
        int[] counter = {0};
        entrySignalLines
                .forEach(line -> {
                    line = line.replace("|", "&");
                    String[] inputOutput = line.strip().split("&");
                    String[] outputDigits = inputOutput[1].strip().split(" ");
                    Arrays.stream(outputDigits)
                            .forEach(digitString -> {
                                int length = digitString.strip().length();
                                if (digitLengths.contains(length)) counter[0]++;
                            });
                });
        return counter[0];
    }

    public static void main(String[] args) throws IOException {
        List<String> entrySignalLines = MyUtilities.getInputLines("src/main/java/day08/input.txt");

        // digits 1,4,7,8 have unique signal output length
        List<Integer> uniqueLengths = Arrays.asList(2, 3, 4, 7);
        System.out.println("Part 1 = " + calculateAmountOfSpecificDigitsInOutputSignal(entrySignalLines, uniqueLengths));
        System.out.println("Part 2 = " + calculateSumOfOutputValues(entrySignalLines));
    }
}
