package day10;

import utils.MyUtilities;

import java.util.*;
import java.io.*;

public class SyntaxScoring {

    private static List<String> openSymbols = Arrays.asList("(", "[", "{", "<");
    private static List<String> closeSymbols = Arrays.asList(")", "]", "}", ">");

    private static long getScoreForCompletionLine(String completionLine) {
        long score = 0;
        int length = completionLine.length();
        for (int i = 0; i < length; i++) {
            String currentSymbol = String.valueOf(completionLine.charAt(i));
            score = score * 5;
            switch (currentSymbol) {
                case ")" -> score += 1;
                case "]" -> score += 2;
                case "}" -> score += 3;
                case ">" -> score += 4;
            }
        }
        return score;
    }

    private static String getClosingSymbol(String symbolFromStack) {
        String closingSymbol = "";
        switch (symbolFromStack) {
            case "(" -> closingSymbol = ")";
            case "[" -> closingSymbol = "]";
            case "{" -> closingSymbol = "}";
            case "<" -> closingSymbol = ">";
        }
        return closingSymbol;
    }

    private static String getCompletionLine(String incompleteLine) {
        StringBuilder completion = new StringBuilder();
        int lineSize = incompleteLine.length();
        Stack<String> symbolsStack = new Stack<>();
        for (int i = 0; i < lineSize; i++) {
            String currentChunk = String.valueOf(incompleteLine.charAt(i));
            if (openSymbols.contains(currentChunk)) {
                symbolsStack.push(currentChunk);
            } else {
                if (!isClosingSymbolNow(symbolsStack.peek(), currentChunk)) {
                    completion.append(getClosingSymbol(symbolsStack.peek()));
                }
                symbolsStack.pop();
            }
        }
        int stackSize = symbolsStack.size();
        for (int i = 0; i < stackSize; i++) {
            completion.append(getClosingSymbol(symbolsStack.peek()));
            symbolsStack.pop();
        }
        return completion.toString();
    }

    private static int getPointsForClosingSymbol(String chunk) {
        int points = 0;
        switch (chunk) {
            case ")" -> points = 3;
            case "]" -> points = 57;
            case "}" -> points = 1197;
            case ">" -> points = 25137;
        }
        return points;
    }

    private static boolean isClosingSymbolNow(String currentFromStack, String currentSymbol) {
        boolean isClosingSymbol = false;
        switch (currentFromStack) {
            case "(" -> isClosingSymbol = ")".equals(currentSymbol);
            case "[" -> isClosingSymbol = "]".equals(currentSymbol);
            case "{" -> isClosingSymbol = "}".equals(currentSymbol);
            case "<" -> isClosingSymbol = ">".equals(currentSymbol);
        }
        return isClosingSymbol;
    }

    private static long getMiddleScoreOfCompletionStrings(List<String> chunksLines) {
        List<Long> scoresForLines = new ArrayList<>();
        List<String> incompleteLines = new ArrayList<>();
        chunksLines.forEach(line -> {
            boolean isCorrupted = false;
            Stack<String> chunksStack = new Stack<>();
            int length = line.length();
            for (int i = 0; i < length; i++) {
                String currentChunk = String.valueOf(line.charAt(i));
                if (openSymbols.contains(currentChunk)) {
                    chunksStack.push(currentChunk);
                } else {
                    if (isClosingSymbolNow(chunksStack.peek(), currentChunk)) {
                        chunksStack.pop();
                    } else {
                        isCorrupted = true;
                        break;
                    }
                }
            }
            if (!isCorrupted) incompleteLines.add(line);
        });
        incompleteLines.forEach(incompleteLine -> {
            String completionLine = getCompletionLine(incompleteLine);
            long score = getScoreForCompletionLine(completionLine);
            scoresForLines.add(score);
        });

        Collections.sort(scoresForLines);
        return scoresForLines.get((scoresForLines.size() - 1) / 2);
    }

    private static int getTotalSyntaxErrorScore(List<String> chunksLines) {
        int[] score = {0};
        chunksLines
                .forEach(chunksLine -> {
                    Stack<String> symbolsStack = new Stack<>();
                    int lineLength = chunksLine.length();
                    for (int i = 0; i < lineLength; i++) {
                        String currentSymbol = String.valueOf(chunksLine.charAt(i));
                        if (openSymbols.contains(currentSymbol)) {
                            symbolsStack.push(currentSymbol);
                        } else {
                            if (isClosingSymbolNow(symbolsStack.peek(), currentSymbol)) {
                                symbolsStack.pop();
                            } else {
                                score[0] += getPointsForClosingSymbol(currentSymbol);
                                break;
                            }
                        }
                    }
                });
        return score[0];
    }

    public static void main(String[] args) throws IOException {
        List<String> chunksLines = MyUtilities.getInputLines("src/main/java/day10/input.txt");

        System.out.println("Part 1 = " + getTotalSyntaxErrorScore(chunksLines));
        System.out.println("Part 2 = " + getMiddleScoreOfCompletionStrings(chunksLines));
    }
}
