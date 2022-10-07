package day12;

import utils.MyUtilities;

import java.io.IOException;
import java.util.*;

public class PassagePathing {

    private static final String START = "start";
    private static final String END = "end";

    static class UndirectedGraph {
        private Map<String, List<String>> adjacencyMap;

        public UndirectedGraph() {
            this.adjacencyMap = new HashMap<>();
        }

        public void addEdge(String from, String to) {
            adjacencyMap.computeIfAbsent(from, k -> new ArrayList<>()).add(to);

        }

        public List<String> getAdjacencyList(String vertex) {
            return adjacencyMap.get(vertex);
        }
    }

    static class DepthFirstSearch {
        private UndirectedGraph graph;
        private int counter;

        public DepthFirstSearch(UndirectedGraph graph) {
            this.counter = 0;
            this.graph = graph;
        }

        public void runModifiedDfsAlgorithm(String start, List<String> currentPath, int part) {
            currentPath.add(start);
            if (END.equals(start)) {
                counter++;
                return;
            }
            for (String cave : graph.getAdjacencyList(start)) {
                if (canVisit(cave, currentPath, part)) {
                    runModifiedDfsAlgorithm(cave, new ArrayList<>(currentPath), part);
                }
            }
        }

        private boolean canVisit(String currentCave, List<String> currentPath, int part) {
            boolean canVisit = switch (part) {
                case 1 -> checkConditionsOfPartOne(currentCave, currentPath);
                case 2 -> checkConditionsOfPartTwo(currentCave, currentPath);
                default -> false;
            };
            return canVisit;
        }

        private boolean checkConditionsOfPartOne(String currentCave, List<String> currentPath) {
            if (START.equals(currentCave)) return false;
            else if (Character.isUpperCase(currentCave.charAt(0))) return true;
            else return !currentPath.contains(currentCave);
        }

        private boolean checkConditionsOfPartTwo(String currentCave, List<String> currentPath) {
            if (START.equals(currentCave)) return false;
            else if (Character.isUpperCase(currentCave.charAt(0))) return true;
            else {
                List<String> smallCaves = currentPath.stream()
                        .filter(cave -> Character.isLowerCase(cave.charAt(0)))
                        .toList();
                Set<String> setOfSmallCaves = new HashSet<>(smallCaves);
                return smallCaves.size() <= (setOfSmallCaves.size() + 1);
            }
        }

        public int getCounter() {
            return counter;
        }

        public void resetCounter() {
            counter = 0;
        }

    }

    public static void main(String[] args) throws IOException {
        List<String> inputLines = MyUtilities.getInputLines("src/main/java/day12/input.txt");

        UndirectedGraph graph = new UndirectedGraph();
        inputLines.forEach(line -> {
            String[] lineArray = line.split("-");
            graph.addEdge(lineArray[0], lineArray[1]);
            graph.addEdge(lineArray[1], lineArray[0]);
        });
        DepthFirstSearch dfs = new DepthFirstSearch(graph);

        // RUN FOR PART I
        for (String caveNextToStart : graph.getAdjacencyList(START)) {
            dfs.runModifiedDfsAlgorithm(caveNextToStart, new ArrayList<>(List.of(START)), 1);
        }
        System.out.println("Part 1 = " + dfs.getCounter());

        // RUN FOR PART II
        dfs.resetCounter();
        for (String caveNextToStart : graph.getAdjacencyList(START)) {
            dfs.runModifiedDfsAlgorithm(caveNextToStart, new ArrayList<>(List.of(START)), 2);
        }
        System.out.println("Part 2 = " + dfs.getCounter());


    }
}
