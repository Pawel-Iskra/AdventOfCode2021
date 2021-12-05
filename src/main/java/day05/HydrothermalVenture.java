package day05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HydrothermalVenture {

    static class Start {
        private int x;
        private int y;

        public Start(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    static class End {
        private int x;
        private int y;

        public End(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

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

    private static int calculateResult(List<Start> starts, List<End> ends, String part) {
        int amountOfCoordinates = starts.size();
        int[][] diagram = new int[1000][1000];

        for (int i = 0; i < amountOfCoordinates; i++) {
            Start start = starts.get(i);
            End end = ends.get(i);
            int startX = start.getX();
            int startY = start.getY();
            int endX = end.getX();
            int endY = end.getY();

            boolean horizontal = (startY == endY);
            boolean vertical = (startX == endX);

            if (horizontal) {
                int diff = startX - endX;
                for (int j = 0; j <= Math.abs(diff); j++) {
                    int currentX = startX;
                    if (diff > 0) currentX -= j;
                    else currentX += j;

                    diagram[startY][currentX]++;
                }
            } else if (vertical) {
                int diff = startY - endY;
                for (int j = 0; j <= Math.abs(diff); j++) {
                    int currentY = startY;
                    if (diff > 0) currentY -= j;
                    else currentY += j;

                    diagram[currentY][startX]++;
                }
            } else if ("two".equals(part)) {
                int diffX = startX - endX;
                int diffY = startY - endY;
                for (int j = 0; j <= Math.abs(diffX); j++) {
                    int currentX = startX;
                    int currentY = startY;
                    if (diffX < 0) currentX += j;
                    else currentX -= j;
                    if (diffY < 0) currentY += j;
                    else currentY -= j;
                    diagram[currentY][currentX]++;
                }
            }
        }
        int counter = 0;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (diagram[i][j] > 1) counter++;
            }
        }
        return counter;
    }


    public static void main(String[] args) throws IOException {
        List<String> inputLines = getInputLines("src/main/java/day05/input.txt");
        int inputSize = inputLines.size();

        List<Start> starts = new ArrayList<>();
        List<End> ends = new ArrayList<>();

        for (int i = 0; i < inputSize; i++) {
            String[] line = inputLines.get(i).split("->");

            int startX = Integer.parseInt(line[0].split(",")[0].strip());
            int startY = Integer.parseInt(line[0].split(",")[1].strip());
            Start start = new Start(startX, startY);
            starts.add(start);

            int endX = Integer.parseInt(line[1].split(",")[0].strip());
            int endY = Integer.parseInt(line[1].split(",")[1].strip());
            End end = new End(endX, endY);
            ends.add(end);
        }

        System.out.println("Part 1 = " + calculateResult(starts, ends, "one"));
        System.out.println("Part 1 = " + calculateResult(starts, ends, "two"));
    }
}
