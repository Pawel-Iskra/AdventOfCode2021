package day14;

import utils.MyUtilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ExtendedPolymerization {

    public static Map<String, Long> goOneStep(Map<String, Long> pairOccurrencesMap, Map<String, String> pairInsertionRulesMap) {
        Map<String, Long> resultMap = new HashMap<>();
        for (Map.Entry<String, Long> entry : pairOccurrencesMap.entrySet()) {
            String key = entry.getKey();
            long value = pairOccurrencesMap.get(key);

            if (pairInsertionRulesMap.containsKey(key)) {
                String elementToInsert = pairInsertionRulesMap.get(key);
                String newFirstPair = key.charAt(0) + elementToInsert;
                String newSecondPair = elementToInsert + key.charAt(1);
                addToMap(newFirstPair, value, resultMap);
                addToMap(newSecondPair, value, resultMap);
            } else {
                resultMap.put(key, value);
            }
        }
        return resultMap;
    }

    public static void addToMap(String key, long value, Map<String, Long> pairOccurrencesMap) {
        pairOccurrencesMap.computeIfPresent(key, (k, v) -> v + value);
        pairOccurrencesMap.putIfAbsent(key, value);
    }

    public static Map<String, Long> getPairOccurrencesMapAfterNumberOfSteps(Map<String, Long> pairOccurrencesMap,
                                                                            Map<String, String> pairInsertionRulesMap,
                                                                            int steps) {
        for (int i = 0; i < steps; i++) {
            pairOccurrencesMap = goOneStep(pairOccurrencesMap, pairInsertionRulesMap);
        }
        return pairOccurrencesMap;
    }

    public static long getDifference(Map<String, Long> pairOccurrencesMap, String polymerTemplate) {
        long[] amounts = new long[26];
        pairOccurrencesMap.forEach((key, value) -> {
            amounts[(int) (key.charAt(0) - 65)] += value;
        });
        amounts[(int) polymerTemplate.charAt(polymerTemplate.length() - 1) - 65]++;
        long max = Arrays.stream(amounts).max().getAsLong();
        long min = Arrays.stream(amounts).filter(value -> value > 0).min().getAsLong();

        return max - min;
    }


    public static void main(String[] args) throws IOException {
        List<String> inputLines = MyUtilities.getInputLines("src/main/java/day14/input.txt");

        String polymerTemplate = inputLines.get(0).strip();
        Map<String, String> pairInsertionRulesMap = new HashMap<>();
        inputLines.stream()
                .filter(line -> line.contains("->"))
                .forEach(line -> {
                    String[] lineArray = line.split(" -> ");
                    pairInsertionRulesMap.put(lineArray[0], lineArray[1]);
                });

        Map<String, Long> pairOccurrencesMap = new HashMap<>();
        IntStream.range(0, polymerTemplate.length() - 1)
                .forEach(index -> {
                    String currentPair = polymerTemplate.substring(index, index + 2);
                    pairOccurrencesMap.computeIfPresent(currentPair, (k, v) -> v + 1);
                    pairOccurrencesMap.putIfAbsent(currentPair, 1L);
                });

        System.out.println("Part I = " + getDifference(getPairOccurrencesMapAfterNumberOfSteps(
                pairOccurrencesMap, pairInsertionRulesMap, 10), polymerTemplate));
        System.out.println("Part II = " + getDifference(getPairOccurrencesMapAfterNumberOfSteps(
                pairOccurrencesMap, pairInsertionRulesMap, 40), polymerTemplate));
    }
}
