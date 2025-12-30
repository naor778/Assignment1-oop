package levels;

import biuoop.DrawSurface;
import collidables.Block;
import geometry.Point;
import geometry.Rectangle;
import sprites.Sprite;
import sprites.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FinalFour implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return List.of(
                Velocity.fromAngleAndSpeed(320, 5),
                Velocity.fromAngleAndSpeed(0, 5),
                Velocity.fromAngleAndSpeed(40, 5)
        );
    }

    @Override
    public int paddleSpeed() {
        return 7;
    }

    @Override
    public int paddleWidth() {
        return 120;
    }

    @Override
    public String levelName() {
        return "Final Four";
    }

    @Override
    public Sprite getBackground() {
        return new FinalFourBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int blockWidth = 50;
        int blockHeight = 20;

        int rows = 4;
        int cols = 15;

        int startX = 25;     // מרווח קטן משמאל
        int startY = 100;

        Color[] rowColors = {
                new Color(120, 120, 120), // אפור
                Color.RED,
                Color.YELLOW,
                Color.GREEN
        };

        for (int row = 0; row < rows; row++) {
            int y = startY + row * blockHeight;
            Color c = rowColors[row];

            for (int col = 0; col < cols; col++) {
                int x = startX + col * blockWidth;
                Block b = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight), c);
                blocks.add(b);
            }
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }

    // -------- Background --------
    private static class FinalFourBackground implements Sprite {
        private final Random rnd = new Random();

        @Override
        public void drawOn(DrawSurface d) {
            // רקע כחול
            d.setColor(new Color(30, 90, 180));
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

            // "עננים" (2 עננים אפורים)
            drawCloud(d, 120, 120);
            drawCloud(d, 620, 160);

            // "גשם" קווים לבנים יורדים מתחת לכל ענן
            d.setColor(new Color(220, 220, 255));
            for (int i = 0; i < 120; i++) {
                int x = 60 + i * 2;
                d.drawLine(x, 160, x - 15, 300);
            }
            for (int i = 0; i < 120; i++) {
                int x = 560 + i * 2;
                d.drawLine(x, 200, x - 15, 340);
            }
        }

        private void drawCloud(DrawSurface d, int x, int y) {
            d.setColor(new Color(200, 200, 200));
            d.fillCircle(x, y, 25);
            d.fillCircle(x + 30, y - 10, 30);
            d.fillCircle(x + 60, y, 25);
            d.fillCircle(x + 20, y + 20, 25);
            d.fillCircle(x + 50, y + 20, 25);

            d.setColor(new Color(170, 170, 170));
            d.fillCircle(x + 10, y + 5, 25);
            d.fillCircle(x + 40, y + 10, 25);
            d.fillCircle(x + 70, y + 5, 25);
        }

        @Override
        public void timePassed() {
            // nothing
        }
    }
}
