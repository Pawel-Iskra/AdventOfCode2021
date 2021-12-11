package day11;

import utils.MyUtilities;

import java.util.*;
import java.io.*;

public class DumboOctopus {

    private static class ReadyToFlash {
        private int row;
        private int column;
        private boolean isReady;

        public ReadyToFlash() {
            this.isReady = false;
        }

        public ReadyToFlash(int row, int column, boolean isReady) {
            this.row = row;
            this.column = column;
            this.isReady = isReady;
        }

        public boolean isReady() {
            return isReady;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

    private static void addEnergyToNeighbors(int[][] octopusesGrid, int row, int column) {
        int rows = octopusesGrid.length;
        int columns = octopusesGrid[0].length;
        for (int i = row - 1; i <= row + 1; i++) {
            if ((i < 0) || (i > (rows - 1))) continue;
            for (int j = column - 1; j <= column + 1; j++) {
                if ((j < 0) || (j > (columns - 1))) continue;
                if (octopusesGrid[i][j] != 0) octopusesGrid[i][j]++;
            }
        }
    }

    private static ReadyToFlash getReadyToFlashAndCheckIfThereIsAnyReadyToFlash(int[][] octopusesGrid) {
        int rows = octopusesGrid.length;
        int columns = octopusesGrid[0].length;
        ReadyToFlash readyToFlash = new ReadyToFlash();
        boolean flag = false;
        for (int i = 0; i < rows; i++) {
            if (flag) break;
            for (int j = 0; j < columns; j++) {
                if (octopusesGrid[i][j] > 9) {
                    readyToFlash = new ReadyToFlash(i, j, true);
                    flag = true;
                    break;
                }
            }
        }
        return readyToFlash;
    }

    private static int processOneStep(int[][] octopusesGrid) {
        int rows = octopusesGrid.length;
        int columns = octopusesGrid[0].length;
        int flashesInStep = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                octopusesGrid[i][j]++;
            }
        }
        ReadyToFlash readyToFlash;
        while ((readyToFlash = getReadyToFlashAndCheckIfThereIsAnyReadyToFlash(octopusesGrid)).isReady()) {
            int row = readyToFlash.getRow();
            int column = readyToFlash.getColumn();
            flashesInStep++;
            octopusesGrid[row][column] = 0;
            addEnergyToNeighbors(octopusesGrid, row, column);
        }
        return flashesInStep;
    }

    private static int[][] getOctopusesGridFromInput(List<String> octopusEnergyLevelsLines) {
        int rows = octopusEnergyLevelsLines.size();
        int columns = octopusEnergyLevelsLines.get(0).length();
        int[][] octopusesGrid = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            String octopusEnergyLevelsLine = octopusEnergyLevelsLines.get(i);
            for (int j = 0; j < columns; j++) {
                octopusesGrid[i][j] = Integer.parseInt(String.valueOf(octopusEnergyLevelsLine.charAt(j)));
            }
        }
        return octopusesGrid;
    }

    private static int getFirstStepWhenAllOctopusesFlash(List<String> octopusEnergyLevelsLines) {
        int numOfOctopuses = octopusEnergyLevelsLines.size() * octopusEnergyLevelsLines.get(0).length();
        int[][] octopusesGrid = getOctopusesGridFromInput(octopusEnergyLevelsLines);
        int numberOfSteps = 0;
        while (processOneStep(octopusesGrid) != numOfOctopuses) {
            numberOfSteps++;
        }
        return ++numberOfSteps;
    }

    private static int getNumberOfFlashesAfterSteps(List<String> octopusEnergyLevelsLines, int steps) {
        int flashes = 0;
        int[][] octopusesGrid = getOctopusesGridFromInput(octopusEnergyLevelsLines);
        for (int i = 0; i < steps; i++) {
            flashes += processOneStep(octopusesGrid);
        }
        return flashes;
    }

    public static void main(String[] args) throws IOException {
        List<String> octopusEnergyLevelsLines = MyUtilities.getInputLines("src/main/java/day11/input.txt");

        System.out.println("Part 1 = " + getNumberOfFlashesAfterSteps(octopusEnergyLevelsLines, 100));
        System.out.println("Part 2 = " + getFirstStepWhenAllOctopusesFlash(octopusEnergyLevelsLines));
    }
}
