import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;

public class BallAnimation {
    private static final String[] DEFAULT_ARGS = {"100", "100", "2", "3"};

    public static void main(String[] args) {
        if (args.length == 0) {
            args = DEFAULT_ARGS;
        }
        int x  = Integer.parseInt(args[0]);
        int y  = Integer.parseInt(args[1]);
        double dx = Double.parseDouble(args[2]);
        double dy = Double.parseDouble(args[3]);

        Point start = new Point(x, y);

        drawAnimation(start, dx, dy);

    }

    static private void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("title",200,200);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start, 30, Color.BLACK);


        Velocity v = Velocity.fromAngleAndSpeed(90, 2);
        ball.setVelocity(v);
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);  // wait for 50 milliseconds.
        }
    }
}
