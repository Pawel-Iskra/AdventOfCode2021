package day15;

import utils.MyUtilities;

import java.io.IOException;
import java.util.*;


public class Chiton {

    static class DirectedEdge {
        private int from;
        private int to;
        private int weight;

        public DirectedEdge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return String.format("%d -> %d (%d)", from, to, weight);
        }
    }

    static class DirectedGraph {
        private int vertices;
        private int edges;
        private List<DirectedEdge>[] directedEdgesList;

        public DirectedGraph(int vertices) {
            this.vertices = vertices;
            this.edges = 0;
            this.directedEdgesList = new List[vertices];
            for (int i = 0; i < vertices; i++) {
                directedEdgesList[i] = new ArrayList<>();
            }
        }

        public int getVertices() {
            return vertices;
        }

        public int getEdges() {
            return edges;
        }

        public List<DirectedEdge>[] getDirectedEdgesList() {
            return directedEdgesList;
        }

        public void addEdge(DirectedEdge directedEdge) {
            directedEdgesList[directedEdge.getFrom()].add(directedEdge);
            edges++;
        }

        public List<DirectedEdge> getDirectedEdgesListFromNode(int vertex) {
            return directedEdgesList[vertex];
        }
    }

    static class CurrentDistanceToStart {
        private int from;
        private int to;
        private long dist;

        public CurrentDistanceToStart(int from, int to, long dist) {
            this.from = from;
            this.to = to;
            this.dist = dist;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public long getDist() {
            return dist;
        }
    }

    static class DijkstraShortestPath {
        private int start;
        private DirectedGraph directedGraph;
        private int vertices;
        private long[] shortestPaths;
        private int[] previous;
        private boolean[] visited;

        public DijkstraShortestPath(int start, DirectedGraph directedGraph) {
            this.start = start;
            this.directedGraph = directedGraph;
            this.vertices = directedGraph.getVertices();
            this.visited = new boolean[vertices];
            this.shortestPaths = new long[vertices];
            this.previous = new int[vertices];
            for (int i = 0; i < vertices; i++) {
                shortestPaths[i] = Long.MAX_VALUE;
            }
            shortestPaths[start] = 0;
            previous[start] = start;
        }


        public void runDijkstraAlgorithm() {
            Queue<CurrentDistanceToStart> currentDistancesQueue =
                    new PriorityQueue<>(vertices, Comparator.comparingLong(o -> o.getDist()));
            currentDistancesQueue.add(new CurrentDistanceToStart(start, start, 0));

            while (!currentDistancesQueue.isEmpty()) {
                visited[start] = true;
                start = currentDistancesQueue.poll().getTo();

                List<DirectedEdge> directedEdgesFromNode = directedGraph.getDirectedEdgesListFromNode(start);
                long currDistToSrc = shortestPaths[start];

                //if... -> update shortestPaths for neighbors, update previous
                int size = directedEdgesFromNode.size();
                for (int i = 0; i < size; i++) {
                    DirectedEdge current = directedEdgesFromNode.get(i);
                    long calcDist = currDistToSrc + current.getWeight();
                    int to = current.getTo();
                    if (calcDist < shortestPaths[to]) {
                        shortestPaths[to] = calcDist;
                        previous[to] = start;
                        if (!visited[to]) {
                            currentDistancesQueue.add(
                                    new CurrentDistanceToStart(current.getFrom(), current.getTo(), calcDist));
                        }
                    }
                }
            }
        }

        public long[] getShortestPaths() {
            return shortestPaths;
        }

        public int[] getPrevious() {
            return previous;
        }

        public boolean[] getVisited() {
            return visited;
        }
    }

    public static DirectedGraph getDirectedGraphFromInputLines(List<String> inputLines, int vertices) {
        DirectedGraph directedGraph = new DirectedGraph(vertices);
        int rows = inputLines.size();
        int columns = inputLines.get(0).length();

        for (int i = 0; i < vertices; i++) {
            int currentRow = i / columns;
            int currentColumn = i - currentRow * columns;

            int upperRow = currentRow - 1;
            if (upperRow >= 0) { // go up
                directedGraph.addEdge(new DirectedEdge(i, (i - columns),
                        Integer.parseInt(String.valueOf(inputLines.get(upperRow).charAt(currentColumn)))));
            }

            int lowerRow = currentRow + 1;
            if (lowerRow < rows) { // go down
                directedGraph.addEdge(new DirectedEdge(i, (i + columns),
                        Integer.parseInt(String.valueOf(inputLines.get(lowerRow).charAt(currentColumn)))));
            }

            int rightColumn = currentColumn + 1;
            if (rightColumn < columns) { // go right
                directedGraph.addEdge(new DirectedEdge(i, (i + 1),
                        Integer.parseInt(String.valueOf(inputLines.get(currentRow).charAt(rightColumn)))));
            }

            int leftColumn = currentColumn - 1;
            if (leftColumn >= 0) { // go left
                directedGraph.addEdge(new DirectedEdge(i, (i - 1),
                        Integer.parseInt(String.valueOf(inputLines.get(currentRow).charAt(leftColumn)))));
            }
        }
        return directedGraph;
    }

    // building map for part two
    private static String getNextPartOfCharsAsString(String input) {
        StringBuilder result = new StringBuilder();
        int columns = input.length();
        for (int i = 0; i < columns; i++) {
            int intToAdd = Integer.parseInt(String.valueOf(input.charAt(i)));
            intToAdd = (intToAdd + 1) % 10;
            if (intToAdd == 0) intToAdd = 1;
            result.append(intToAdd);
        }
        return result.toString();
    }

    // building map for part two
    private static List<String> getInputLinesForPartTwo(List<String> inputLines) {
        List<String> result = new ArrayList<>(inputLines);

        int rows = inputLines.size();
        int columns = inputLines.get(0).length();

        // build to right
        for (int i = 0; i < rows; i++) {
            StringBuilder currentRow = new StringBuilder(inputLines.get(i));
            for (int j = 0; j < 4; j++) {
                currentRow.append(getNextPartOfCharsAsString(currentRow.substring(j * columns, j * columns + columns)));
            }
            result.set(i, currentRow.toString());
        }

        // build down
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < rows; j++) {
                String currentRow = result.get(i * rows + j);
                StringBuilder newRowToAdd = new StringBuilder(currentRow.substring(columns, 5 * columns));
                newRowToAdd.append(getNextPartOfCharsAsString(newRowToAdd.substring(3 * columns, 4 * columns)));
                result.add(newRowToAdd.toString());
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {

        List<String> inputLines = MyUtilities.getInputLines("src/main/java/day15/input.txt");
        int vertices = inputLines.size() * inputLines.get(0).length();
        int start = 0;

        // RUN FOR PART I
        DirectedGraph directedGraph = getDirectedGraphFromInputLines(inputLines, vertices);
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(start, directedGraph);
        dijkstraShortestPath.runDijkstraAlgorithm();
        System.out.println("Part I = " + dijkstraShortestPath.getShortestPaths()[vertices - 1]);

        // RUN FOR PART II
        List<String> inputLinesForPartTwo = getInputLinesForPartTwo(inputLines);
        int verticesPartTwo = inputLinesForPartTwo.size() * inputLinesForPartTwo.get(0).length();
        DirectedGraph directedGraphPartTwo = getDirectedGraphFromInputLines(inputLinesForPartTwo, verticesPartTwo);
        DijkstraShortestPath dijkstraShortestPathPartTwo = new DijkstraShortestPath(start, directedGraphPartTwo);
        dijkstraShortestPathPartTwo.runDijkstraAlgorithm();
        System.out.println("Part II = " + dijkstraShortestPathPartTwo.getShortestPaths()[verticesPartTwo - 1]);

    }
}
