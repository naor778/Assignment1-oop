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

            Ball ball = new Ball(new Point(x, y), radius, Color.BLACK);

            double speed;
            if (radius > 50) {
                speed = 1;  // גדול מאוד → איטי
            } else {
                speed = 50.0 / radius;  // קטן יותר → מהיר יותר
            }

            double angle = rand.nextInt(360);

            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            ball.setVelocity(v);

            balls[i] = ball;
        }

        while (true) {
            DrawSurface d = gui.getDrawSurface();

            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}