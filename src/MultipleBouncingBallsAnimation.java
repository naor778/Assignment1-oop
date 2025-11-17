import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {

    public static void main(String[] args) {

        GUI gui = new GUI("Multiple Balls", 400, 400);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        int width = 400;
        int height = 400;

        Ball[] balls = new Ball[args.length];

        for (int i = 0; i < args.length; i++) {

            int radius = Integer.parseInt(args[i]);

            int x = rand.nextInt(width - 2 * radius) + radius;
            int y = rand.nextInt(height - 2 * radius) + radius;


            Color randomColor = new Color(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));
            Ball ball = new Ball(new Point(x, y), radius, randomColor);

            double speed;
            if (radius > 50) {
                speed = 1;  // גדול מאוד → איטי
            } else {
                speed = 50.0 / radius;  // קטן יותר → מהיר יותר
            }

            double angle = rand.nextInt(360);

            Velocity v = Velocity.fromAngleAndSpeed(90, 2);
            ball.setVelocity(v);

            balls[i] = ball;
        }

        while (true) {
            DrawSurface d = gui.getDrawSurface();

            for (Ball ball : balls) {

                double x = ball.getX();
                double y = ball.getY();
                double dx = ball.getVelocity().getDx();
                double dy = ball.getVelocity().getDy();

                int r = ball.getSize();

                // גבולות המסך
                double leftBound   = 0 + r;
                double rightBound  = width - r;
                double topBound    = 0 + r;
                double bottomBound = height - r;

                double nextX = x + dx;
                double nextY = y + dy;

                // קפיצה משמאל / ימין
                if (nextX < leftBound || nextX > rightBound) {
                    dx = -dx;
                }

                // קפיצה מלמעלה / למטה
                if (nextY < topBound || nextY > bottomBound) {
                    dy = -dy;
                }

                // עדכון המהירות החדשה לכדור
                ball.setVelocity(dx, dy);

                // תנועה צעד אחד
                ball.moveOneStep();

                // ציור הכדור
                ball.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}