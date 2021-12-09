package day09;

import utils.MyUtilities;

import java.io.*;
import java.util.*;

public class SmokeBasin {

    private static boolean goUpDownRightLeftAndAddFlag(int i, int j, int[][] basin, int[][] heightMap) {
        int currentLowest = heightMap[i][j] + 1;
        int rows = heightMap.length;
        int columns = heightMap[0].length;
        boolean flag = false;

        for (int p = i - 1; p >= 0; p--) { // up
            if (heightMap[p][j] == 9) break;
            if (heightMap[p][j] <= currentLowest) {
                if (basin[p][j] == 0) {
                    flag = true;
                    basin[p][j] = 1;
                    currentLowest++;
                }
            }
        }
        currentLowest = heightMap[i][j] + 1;
        for (int p = i + 1; p < rows; p++) { // down
            if (heightMap[p][j] == 9) break;
            if (heightMap[p][j] <= currentLowest) {
                if (basin[p][j] == 0) {
                    flag = true;
                    basin[p][j] = 1;
                    currentLowest++;
                }
            }
        }
        currentLowest = heightMap[i][j] + 1;
        for (int p = j + 1; p < columns; p++) { // right
            if (heightMap[i][p] == 9) break;
            if (heightMap[i][p] <= currentLowest) {
                if (basin[i][p] == 0) {
                    flag = true;
                    basin[i][p] = 1;
                    currentLowest++;
                }
            }
        }
        currentLowest = heightMap[i][j] + 1;
        for (int p = j - 1; p >= 0; p--) { // left
            if (heightMap[i][p] == 9) break;
            if (heightMap[i][p] <= currentLowest) {
                if (basin[i][p] == 0) {
                    flag = true;
                    basin[i][p] = 1;
                    currentLowest++;
                }
            }
        }
        return flag;
    }

    private static boolean goAroundAndCheckIfIsNewOne(int[][] basin, int[][] heightmap) {
        boolean flag = false;
        int rows = heightmap.length;
        int columns = heightmap[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (basin[i][j] == 1) {
                    flag = goUpDownRightLeftAndAddFlag(i, j, basin, heightmap);
                }
            }
        }
        return flag;
    }

    private static int[][] makeBasin(int[][] basin, int[][] heightmap, int i, int j) {
        int rows = heightmap.length;
        int columns = heightmap[0].length;
        basin[i][j] = 1;
        for (int p = 0; p < rows; p++) {
            for (int r = 0; r < columns; r++) {
                goAroundAndCheckIfIsNewOne(basin, heightmap);
            }
        }
        return basin;
    }


    private static int countBasinSize(int[][] heightmap, int i, int j) {
        int basinSize = 0;
        int rows = heightmap.length;
        int columns = heightmap[0].length;

        int[][] basin = new int[rows][columns];
        makeBasin(basin, heightmap, i, j);

        for (int p = 0; p < rows; p++) {
            for (int r = 0; r < columns; r++) {
                if (basin[p][r] == 1) basinSize++;
            }
        }
        return basinSize;
    }

    private static boolean isLowestInNeighborhood(int[][] heightmap, int row, int column) {
        int numOfRows = heightmap.length;
        int numOfColumns = heightmap[0].length;
        int currentPoint = heightmap[row][column];
        int expected = 0;
        int flag = 0;
        if (row == 0 && column == 0) {
            expected = 2;
            if (currentPoint < heightmap[0][1]) flag++;
            if (currentPoint < heightmap[1][0]) flag++;
        } else if (row == 0 && column == numOfColumns - 1) {
            expected = 2;
            if (currentPoint < heightmap[0][numOfColumns - 2]) flag++;
            if (currentPoint < heightmap[1][numOfColumns - 1]) flag++;
        } else if (row == numOfRows - 1 && column == 0) {
            expected = 2;
            if (currentPoint < heightmap[numOfRows - 2][0]) flag++;
            if (currentPoint < heightmap[numOfRows - 1][1]) flag++;
        } else if (row == numOfRows - 1 && column == numOfColumns - 1) {
            expected = 2;
            if (currentPoint < heightmap[numOfRows - 2][numOfColumns - 1]) flag++;
            if (currentPoint < heightmap[numOfRows - 1][numOfColumns - 2]) flag++;
        } else {
            if (row == 0) {
                expected = 3;
                if (currentPoint < heightmap[0][column - 1]) flag++;
                if (currentPoint < heightmap[0][column + 1]) flag++;
                if (currentPoint < heightmap[1][column]) flag++;
            } else if (row == numOfRows - 1) {
                expected = 3;
                if (currentPoint < heightmap[numOfRows - 1][column - 1]) flag++;
                if (currentPoint < heightmap[numOfRows - 1][column + 1]) flag++;
                if (currentPoint < heightmap[numOfRows - 2][column]) flag++;
            } else if (column == 0) {
                expected = 3;
                if (currentPoint < heightmap[row - 1][0]) flag++;
                if (currentPoint < heightmap[row + 1][0]) flag++;
                if (currentPoint < heightmap[row][column + 1]) flag++;
            } else if (column == numOfColumns - 1) {
                expected = 3;
                if (currentPoint < heightmap[row - 1][column]) flag++;
                if (currentPoint < heightmap[row + 1][column]) flag++;
                if (currentPoint < heightmap[row][column - 1]) flag++;
            } else {
                expected = 4;
                if (currentPoint < heightmap[row - 1][column]) flag++;
                if (currentPoint < heightmap[row + 1][column]) flag++;
                if (currentPoint < heightmap[row][column - 1]) flag++;
                if (currentPoint < heightmap[row][column + 1]) flag++;
            }
        }
        return flag == expected;
    }

    private static int[][] getHeightMapFromInputLines(List<String> inputHeightMapLines) {
        int rows = inputHeightMapLines.size();
        int columns = inputHeightMapLines.get(0).length();
        int[][] heightmap = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            String heightMapLine = inputHeightMapLines.get(i);
            for (int j = 0; j < columns; j++) {
                heightmap[i][j] = Integer.parseInt(String.valueOf(heightMapLine.charAt(j)));
            }
        }
        return heightmap;
    }

    private static int getResultForPartTwo(List<String> inputHeightMapLines) {
        int rows = inputHeightMapLines.size();
        int columns = inputHeightMapLines.get(0).length();
        int[][] heightmap = getHeightMapFromInputLines(inputHeightMapLines);

        List<Integer> basinSizes = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (isLowestInNeighborhood(heightmap, i, j)) {
                    int basinSize = countBasinSize(heightmap, i, j);
                    basinSizes.add(basinSize);
                }
            }
        }

        Collections.sort(basinSizes);
        int size = basinSizes.size();
        return basinSizes.get(size - 1) * basinSizes.get(size - 2) * basinSizes.get(size - 3);
    }

    private static int getSumOfRiskLevelsOfAllLowPoints(List<String> inputHeightMapLines) {
        int rows = inputHeightMapLines.size();
        int columns = inputHeightMapLines.get(0).length();
        int[][] heightmap = getHeightMapFromInputLines(inputHeightMapLines);

        int sumOfRiskLevel = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (isLowestInNeighborhood(heightmap, i, j)) {
                    sumOfRiskLevel += heightmap[i][j] + 1;
                }
            }
        }
        return sumOfRiskLevel;
    }

    public static void main(String[] args) throws IOException {
        List<String> inputHeightMapLines = MyUtilities.getInputLines("src/main/java/day09/input.txt");

        System.out.println("Part 1 = " + getSumOfRiskLevelsOfAllLowPoints(inputHeightMapLines));
        System.out.println("Part 2 = " + getResultForPartTwo(inputHeightMapLines));
    }
}
