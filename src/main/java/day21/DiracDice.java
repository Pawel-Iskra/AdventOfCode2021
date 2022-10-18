package day21;

import utils.MyUtilities;

import java.io.IOException;
import java.util.List;

public class DiracDice {

    static class Player {
        private int playerPosition;
        private int playerScore;
        private boolean scoredOneThousand;

        public Player(int playerPosition) {
            this.playerPosition = playerPosition;
            this.playerScore = 0;
            this.scoredOneThousand = false;
        }

        public int getPlayerPosition() {
            return playerPosition;
        }

        public void setPlayerPosition(int playerPosition) {
            this.playerPosition = playerPosition;
        }

        public int getPlayerScore() {
            return playerScore;
        }

        public void setPlayerScore(int playerScore) {
            this.playerScore = playerScore;
        }

        public boolean isScoredOneThousand() {
            return scoredOneThousand;
        }

        public void setScoredOneThousand(boolean scoredOneThousand) {
            this.scoredOneThousand = scoredOneThousand;
        }
    }

    static class GamePartOneConditions {
        private Player playerOne;
        private Player playerTwo;
        private int rollCounter;
        private int dice;

        public GamePartOneConditions(Player playerOne, Player playerTwo) {
            this.playerOne = playerOne;
            this.playerTwo = playerTwo;
            this.rollCounter = 0;
            this.dice = 0;
        }

        public void playGame() {
            while (true) {
                playOnePlayerTurn(playerOne);
                if (playerOne.isScoredOneThousand()) break;

                playOnePlayerTurn(playerTwo);
                if (playerTwo.isScoredOneThousand()) break;
            }
        }

        private void playOnePlayerTurn(Player player) {
            int playerDiceScore = 0;
            for (int i = 0; i < 3; i++) {
                rollDice();
                playerDiceScore += dice;
            }
            int playerPosition = player.getPlayerPosition();
            playerPosition += playerDiceScore;
            playerPosition = playerPosition > 10 ? playerPosition % 10 == 0 ? 10 : playerPosition % 10 : playerPosition;
            player.setPlayerPosition(playerPosition);
            player.setPlayerScore(player.getPlayerScore() + playerPosition);
            if (player.getPlayerScore() >= 1000) player.setScoredOneThousand(true);
        }

        public int getResultAfterGamePlay() {
            return Math.min(playerOne.getPlayerScore(), playerTwo.getPlayerScore()) * rollCounter;
        }

        private void rollDice() {
            dice++;
            rollCounter++;
            if (dice == 101) dice = 1;
        }

    }

    public static void main(String[] args) throws IOException {
        List<String> inputLines = MyUtilities.getInputLines("src/main/java/day21/input.txt");
        int playerOneStartPosition = Integer.parseInt(inputLines.get(0).substring(inputLines.get(0).indexOf(":") + 1).strip());
        int playerTwoStartPosition = Integer.parseInt(inputLines.get(1).substring(inputLines.get(1).indexOf(":") + 1).strip());

        Player playerOne = new Player(playerOneStartPosition);
        Player playerTwo = new Player(playerTwoStartPosition);

        GamePartOneConditions gamePartOneConditions = new GamePartOneConditions(playerOne, playerTwo);
        gamePartOneConditions.playGame();
        System.out.println("Part I = " + gamePartOneConditions.getResultAfterGamePlay());
    }
}
