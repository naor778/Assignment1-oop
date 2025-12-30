package levels;

import collidables.Block;
import geometry.Point;
import geometry.Rectangle;
import sprites.Sprite;
import sprites.Velocity;

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.List;

public class DirectHit implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        // אמור לעוף ישר למעלה ולפגוע בבלוק היחיד
        return List.of(Velocity.fromAngleAndSpeed(0, 5));
    }

    @Override
    public int paddleSpeed() {
        return 5;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new DirectHitBackground();
    }

    @Override
    public List<Block> blocks() {
        // בלוק אחד אדום במרכז המטרה (בערך כמו בתמונה)
        Block b = new Block(
                new Rectangle(new Point(400 - 15, 150 - 15), 30, 30),
                Color.RED
        );
        return List.of(b);
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }

    // -------- Background --------
    private static class DirectHitBackground implements Sprite {
        @Override
        public void drawOn(DrawSurface d) {
            // רקע שחור
            d.setColor(Color.BLACK);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

            // מטרה כחולה (עיגולים + קווים)
            int cx = 400;
            int cy = 150;

            d.setColor(Color.BLUE);

            d.drawCircle(cx, cy, 50);
            d.drawCircle(cx, cy, 100);
            d.drawCircle(cx, cy, 150);

            // קווים אופקי/אנכי
            d.drawLine(cx - 150, cy, cx + 150, cy);
            d.drawLine(cx, cy - 150, cx, cy + 150);
        }

        @Override
        public void timePassed() {
            // nothing
        }
    }
}
