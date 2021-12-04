package day04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiantSquid {

    static class BingoBoard {
        private int[][] board;

        private void createBoard(List<String> boardLines) {
            int[][] intBoard = new int[5][5];
            for (int i = 0; i < 5; i++) {
                String boardLine = boardLines.get(i);
                for (int j = 0; j < 5; j++) {
                    intBoard[i][j] = Integer.parseInt(boardLine.substring(3 * j, 3 * j + 2).strip());
                }
            }
            this.board = intBoard;
        }

        public void markNumberOnBoard(int numberDrawn) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board[i][j] == numberDrawn) {
                        board[i][j] = -1;
                    }
                }
            }
        }

        public boolean checkIfWin() {
            int currentSum = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board[i][j] == -1) {
                        currentSum += -1;
                    }
                    if (currentSum == -5) return true;
                }
                currentSum = 0;
            }

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board[j][i] == -1) {
                        currentSum += -1;
                    }
                    if (currentSum == -5) return true;
                }
                currentSum = 0;
            }

            return false;
        }

        public int calculateResultOnBoard() {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board[i][j] != -1) {
                        sum += board[i][j];
                    }
                }
            }
            return sum;
        }

        @Override
        public String toString() {
            return "\nBingoBoard{" +
                    "board=" + Arrays.deepToString(board) +
                    '}';
        }
    }

    private static void markDrawnNumberOnBoards(int numberDrawn, List<BingoBoard> bingoBoardList) {
        for (BingoBoard bingoBoard : bingoBoardList) {
            bingoBoard.markNumberOnBoard(numberDrawn);
        }
    }

    private static int calculateResultFirstWinningBoard(List<BingoBoard> bingoBoardList, String[] numbersDrawn) {
        int amountOfNumbers = numbersDrawn.length;
        int numberDrawn = 0;
        int resultFromBoard = 0;
        for (int i = 0; i < amountOfNumbers; i++) {
            numberDrawn = Integer.parseInt(numbersDrawn[i]);
            markDrawnNumberOnBoards(numberDrawn, bingoBoardList);

            boolean flag = false;
            for (BingoBoard bingoBoard : bingoBoardList) {
                if (bingoBoard.checkIfWin()) {
                    resultFromBoard = bingoBoard.calculateResultOnBoard();
                    flag = true;
                }
            }
            if (flag) break;
        }
        return resultFromBoard * numberDrawn;
    }

    private static int calculateResultLastWinningBoard(List<BingoBoard> bingoBoardList, String[] numbersDrawn) {
        int amountOfNumbers = numbersDrawn.length;
        int numberDrawn = 0;
        int resultFromBoard = 0;
        boolean flag = false;
        for (int i = 0; i < amountOfNumbers; i++) {
            numberDrawn = Integer.parseInt(numbersDrawn[i]);
            markDrawnNumberOnBoards(numberDrawn, bingoBoardList);

            if (bingoBoardList.size() == 1) {
                BingoBoard lastBingoBoard = bingoBoardList.get(0);
                if (lastBingoBoard.checkIfWin()) {
                    resultFromBoard = lastBingoBoard.calculateResultOnBoard();
                    flag = true;
                }
            }
            if (flag) break;
            bingoBoardList.removeIf(BingoBoard::checkIfWin);
        }
        return resultFromBoard * numberDrawn;
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

    public static void main(String[] args) throws IOException {
        List<String> input = getInputLines("src/main/java/day04/input.txt");

        String[] numbersDrawn = input.get(0).split(",");
        int numOfBingoBoards = (input.size() - 1) / 6;

        List<BingoBoard> bingoBoardList = new ArrayList<>();
        for (int i = 0; i < numOfBingoBoards; i++) {
            BingoBoard bingoBoard = new BingoBoard();
            bingoBoard.createBoard(input.subList(6 * i + 2, 6 * i + 7));
            bingoBoardList.add(bingoBoard);
        }

        System.out.println("Part 1 = " + calculateResultFirstWinningBoard(bingoBoardList, numbersDrawn));
        System.out.println("Part 2 = " + calculateResultLastWinningBoard(bingoBoardList, numbersDrawn));
    }
}
