package day17;

import utils.MyUtilities;

import java.io.IOException;
import java.util.List;

public class TrickShot {

    static class Probe {
        private int velocityX;
        private int velocityY;
        private int currentX;
        private int currentY;
        private TargetArea targetArea;
        private int counter;
        private int highestYPosition;

        public Probe(int currentX, int currentY, TargetArea targetArea) {
            this.currentX = currentX;
            this.currentY = currentY;
            this.targetArea = targetArea;
        }

        private void moveOneStep() {
            currentX += velocityX;
            currentY += velocityY;
            velocityY += -1;
            velocityX = velocityX == 0 ? 0 : velocityX < 0 ? velocityX + 1 : velocityX - 1;
        }

        private boolean isProbeAwayFromTarget() {
            return currentY < targetArea.getStartY() || currentX > targetArea.getEndX();
        }

        private boolean isProbeInsideTargetArea() {
            return (currentX >= targetArea.getStartX() && currentX <= targetArea.getEndX()
                    && currentY >= targetArea.getStartY() && currentY <= targetArea.getEndY());
        }

        public void runProbeLaunching() {
            counter = 0;
            highestYPosition = currentY;
            int lastCheckedXPosition = targetArea.getEndX() + 1;
            int firstCheckedYPosition = targetArea.getStartY();
            int lastCheckedYPosition = targetArea.getStartY() * (-1) + 1;

            for (int vx = 1; vx < lastCheckedXPosition; vx++) {
                for (int vy = firstCheckedYPosition; vy < lastCheckedYPosition; vy++) {
                    updateCoordsAndVelocity(vx, vy);
                    int currentHighestY = vy;
                    while (!isProbeAwayFromTarget()) {
                        moveOneStep();
                        currentHighestY = Math.max(currentHighestY, currentY);
                        if (isProbeInsideTargetArea()) {
                            counter++;
                            highestYPosition = Math.max(highestYPosition, currentHighestY);
                            break;
                        }
                    }
                }
            }
        }

        private void updateCoordsAndVelocity(int vx, int vy) {
            velocityX = vx;
            velocityY = vy;
            currentX = 0;
            currentY = 0;
        }

        public int getCounter() {
            return counter;
        }

        public int getHighestYPosition() {
            return highestYPosition;
        }
    }

    static class TargetArea {
        private int startX;
        private int endX;
        private int startY;
        private int endY;

        public TargetArea(int startX, int endX, int startY, int endY) {
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
        }

        public int getStartX() {
            return startX;
        }

        public int getEndX() {
            return endX;
        }

        public int getStartY() {
            return startY;
        }

        public int getEndY() {
            return endY;
        }
    }

    private static int getTargetXStart(String xValues) {
        return Integer.parseInt(xValues.substring(xValues.indexOf("=") + 1, xValues.indexOf(".")));
    }

    private static int getTargetXEnd(String xValues) {
        return Integer.parseInt(xValues.substring(xValues.lastIndexOf(".") + 1, xValues.length()));
    }

    private static int getTargetYStart(String yValues) {
        return Integer.parseInt(yValues.substring(yValues.indexOf("=") + 1, yValues.indexOf(".")));
    }

    private static int getTargetYEnd(String yValues) {
        return Integer.parseInt(yValues.substring(yValues.lastIndexOf(".") + 1, yValues.length()));
    }


    public static void main(String[] args) throws IOException {
        List<String> inputLines = MyUtilities.getInputLines("src/main/java/day17/input.txt");
        String targetArea = inputLines.get(0).strip();
        String xValues = (targetArea.substring(targetArea.indexOf(":"), targetArea.indexOf(","))).strip();
        String yValues = (targetArea.substring(targetArea.indexOf(",") + 1, targetArea.length())).strip();
        TargetArea targetAreaObj = new TargetArea(getTargetXStart(xValues), getTargetXEnd(xValues),
                getTargetYStart(yValues), getTargetYEnd(yValues));

        Probe probe = new Probe(0, 0, targetAreaObj);
        probe.runProbeLaunching();
        System.out.println("Part I = " + probe.getHighestYPosition());
        System.out.println("Part II = " + probe.getCounter());
    }
}
