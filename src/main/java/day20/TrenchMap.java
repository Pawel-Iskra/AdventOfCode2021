package day20;

import utils.MyUtilities;

import java.io.IOException;
import java.util.List;

public class TrenchMap {

    public static int countLitPixels(char[][] matrix) {
        int columns = matrix[0].length;
        int counter = 0;
        for (char[] row : matrix) {
            for (int i = 0; i < columns; i++) {
                if (row[i] == '#') counter++;
            }
        }
        return counter;
    }

    private static char getCharForCurrentPixel(int row, int col, char[][] matrix, String imageEnhancementAlgorithm) {
        StringBuilder binaryNumber = new StringBuilder();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                binaryNumber.append(matrix[i][j] == '#' ? 1 : 0);
            }
        }
        int value = Integer.parseInt(binaryNumber.toString(), 2);
        return imageEnhancementAlgorithm.charAt(value);
    }

    private static char[][] fillBoardersWithGivenChar(char[][] imageMatrix, char filler) {
        int rows = imageMatrix.length;
        int columns = imageMatrix[0].length;
        for (int i = 0; i < rows; i++) {
            imageMatrix[i][0] = filler;
            imageMatrix[i][columns - 1] = filler;
        }
        for (int j = 0; j < columns; j++) {
            imageMatrix[0][j] = filler;
            imageMatrix[rows - 1][j] = filler;
        }
        return imageMatrix;
    }

    public static char[][] goOneStep(char[][] inputMatrix, String imageEnhancementAlgorithm, int step) {
        int rows = inputMatrix.length;
        int columns = inputMatrix[0].length;
        char[][] result = new char[rows][columns];
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < columns - 1; j++) {
                result[i][j] = getCharForCurrentPixel(i, j, inputMatrix, imageEnhancementAlgorithm);
            }
        }

        int digit = inputMatrix[0][0] == '#' ? 1 : 0;
        char filler = imageEnhancementAlgorithm.charAt(Integer.parseInt(String.valueOf(digit).repeat(9), 2));
        fillBoardersWithGivenChar(result, filler);
        return result;
    }

    public static char[][] goNumberOfSteps(char[][] inputMatrix, String imageEnhancementAlgorithm, int steps) {
        for (int i = 1; i < steps + 1; i++) {
            inputMatrix = goOneStep(inputMatrix, imageEnhancementAlgorithm, i);
            inputMatrix = extendBordersOfMatrixWithGivenChar(inputMatrix, inputMatrix[0][0]);
        }
        return inputMatrix;
    }

    private static char[][] extendBordersOfMatrixWithGivenChar(char[][] matrixImage, char filler) {
        int rows = matrixImage.length;
        int columns = matrixImage[0].length;
        char[][] result = new char[rows + 4][columns + 4];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrixImage[i], 0, result[i + 2], 2, columns);
        }
        for (int i = 0; i < result.length; i++) {
            result[i][0] = filler;
            result[i][1] = filler;
            result[i][result[0].length - 1] = filler;
            result[i][result[0].length - 2] = filler;
        }
        for (int j = 0; j < result[0].length; j++) {
            result[0][j] = filler;
            result[1][j] = filler;
            result[result.length - 1][j] = filler;
            result[result.length - 2][j] = filler;
        }
        return result;
    }

    private static char[][] getMatrixFromLines(List<String> inputImage) {
        int rows = inputImage.size();
        int columns = inputImage.get(0).length();
        char[][] result = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = inputImage.get(i).charAt(j);
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        List<String> inputLines = MyUtilities.getInputLines("src/main/java/day20/input.txt");

        String imageEnhancementAlgorithm = inputLines.get(0);
        List<String> inputImage = inputLines.stream().skip(2).toList();
        char[][] matrixImage = getMatrixFromLines(inputImage);
        char[][] extendedStartImage = extendBordersOfMatrixWithGivenChar(matrixImage, '.');

        char[][] afterTwoSteps = goNumberOfSteps(extendedStartImage, imageEnhancementAlgorithm, 2);
        System.out.println("Part I = " + countLitPixels(afterTwoSteps));
        char[][] afterFiftySteps = goNumberOfSteps(extendedStartImage, imageEnhancementAlgorithm, 50);
        System.out.println("Part II = " + countLitPixels(afterFiftySteps));
    }
}
