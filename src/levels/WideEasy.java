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

public class WideEasy implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        // 10 כדורים בזוויות "קשת" כלפי מעלה
        List<Velocity> v = new ArrayList<>();
        int speed = 5;

        int[] angles = {300, 315, 330, 345, 0, 15, 30, 45, 60, 75};
        for (int a : angles) {
            v.add(Velocity.fromAngleAndSpeed(a, speed));
        }
        return v;
    }

    @Override
    public int paddleSpeed() {
        return 5;
    }

    @Override
    public int paddleWidth() {
        return 600; // פאדל ממש רחב
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        return new WideEasyBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int blockWidth = 50;
        int blockHeight = 20;
        int y = 250;
        int startX = 25; // נותן מרווח קטן משמאל

        Color[] colors = new Color[] {
                Color.RED, Color.RED,
                Color.ORANGE, Color.ORANGE,
                Color.YELLOW, Color.YELLOW,
                Color.GREEN, Color.GREEN, Color.GREEN,
                Color.BLUE, Color.BLUE,
                Color.PINK, Color.PINK,
                Color.CYAN, Color.CYAN
        };

        for (int i = 0; i < 15; i++) {
            int x = startX + i * blockWidth;
            Block b = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight), colors[i]);
            blocks.add(b);
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 15;
    }

    // -------- Background --------
    private static class WideEasyBackground implements Sprite {
        @Override
        public void drawOn(DrawSurface d) {
            // רקע לבן
            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

            // שמש בצד שמאל למעלה + קרניים
            int sunX = 120;
            int sunY = 100;

            d.setColor(new Color(255, 230, 150)); // צהוב בהיר
            d.fillCircle(sunX, sunY, 60);

            d.setColor(Color.YELLOW);
            d.fillCircle(sunX, sunY, 50);

            // קרניים לכיוון שורת הבלוקים (בערך y=250)
            d.setColor(new Color(255, 230, 150));
            for (int x = 0; x <= d.getWidth(); x += 10) {
                d.drawLine(sunX, sunY, x, 250);
            }
        }

        @Override
        public void timePassed() {
            // nothing
        }
    }
}
