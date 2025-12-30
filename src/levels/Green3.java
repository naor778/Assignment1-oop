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

public class Green3 implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return List.of(
                Velocity.fromAngleAndSpeed(330, 5),
                Velocity.fromAngleAndSpeed(30, 5)
        );
    }

    @Override
    public int paddleSpeed() {
        return 7;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Green 3";
    }

    @Override
    public Sprite getBackground() {
        return new Green3Background();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int blockWidth = 50;
        int blockHeight = 20;

        int startX = 800 - 20 - blockWidth; // לפני ה-border הימני (עובי 20)
        int startY = 150;

        // 5 שורות, כל שורה ארוכה יותר (כמו בתמונה)
        int[] blocksInRow = {10, 10, 10, 10, 10}; // אפשר להשאיר קבוע ולהזיז startX
        // בפועל: נבנה 5 שורות, בכל שורה מספר בלוקים יורד/עולה? בתמונה זה “מדורג”.
        // נבנה מדורג: בשורה הראשונה 10, אחרי זה 9, 8, 7, 6
        int count = 10;

        Color[] rowColors = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.WHITE};

        for (int row = 0; row < 5; row++) {
            int y = startY + row * blockHeight;
            Color c = rowColors[row];

            for (int i = 0; i < count; i++) {
                int x = startX - i * blockWidth;
                Block b = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight), c);
                blocks.add(b);
            }
            count--; // מדורג
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }

    // -------- Background --------
    private static class Green3Background implements Sprite {
        @Override
        public void drawOn(DrawSurface d) {
            // רקע ירוק
            d.setColor(new Color(20, 120, 20));
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

            // "בניין" בצד ימין (בערך כמו בתמונה)
            int buildingX = 580;
            int buildingY = 420;
            int buildingW = 100;
            int buildingH = 200;

            d.setColor(new Color(40, 40, 40));
            d.fillRectangle(buildingX, buildingY, buildingW, buildingH);

            // חלונות
            d.setColor(Color.WHITE);
            for (int i = 0; i < 5; i++) {
                d.fillRectangle(buildingX + 10, buildingY + 10 + i * 35, 15, 25);
                d.fillRectangle(buildingX + 35, buildingY + 10 + i * 35, 15, 25);
                d.fillRectangle(buildingX + 60, buildingY + 10 + i * 35, 15, 25);
            }

            // אנטנה/עמוד
            int poleX = buildingX + buildingW / 2;
            d.setColor(new Color(80, 80, 80));
            d.fillRectangle(poleX - 5, buildingY - 150, 10, 150);

            // "אור" למעלה
            d.setColor(new Color(255, 180, 0));
            d.fillCircle(poleX, buildingY - 150, 12);
            d.setColor(new Color(255, 220, 100));
            d.fillCircle(poleX, buildingY - 150, 8);
        }

        @Override
        public void timePassed() {
            // nothing
        }
    }
}
