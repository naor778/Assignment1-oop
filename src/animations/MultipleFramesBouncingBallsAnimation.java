package animations;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import geometry.Point;
import sprites.Ball;
import sprites.Velocity;

import java.awt.Color;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    private static final int FRAME1_LEFT = 50;
    private static final int FRAME1_TOP = 50;
    private static final int FRAME1_RIGHT = 500;
    private static final int FRAME1_BOTTOM = 500;

    private static final int FRAME2_LEFT = 450;
    private static final int FRAME2_TOP = 450;
    private static final int FRAME2_RIGHT = 600;
    private static final int FRAME2_BOTTOM = 600;

    public static void main(String[] args) {

        if (args.length == 0) {
            return;
        }

        int n = args.length;

        int[] radii = new int[n];
        for (int i = 0; i < n; i++) {
            radii[i] = Integer.parseInt(args[i]);
        }

        GUI gui = new GUI("Multiple Frames Bouncing Balls", WIDTH, HEIGHT);
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        Ball[] balls = new Ball[n];
        boolean[] inFirstFrame = new boolean[n]; // true = אפור, false = צהוב

        for (int i = 0; i < n; i++) {
            int radius = radii[i];

            Color randomColor = new Color(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));

            double speed;
            if (radius > 50) {
                speed = 1;
            } else {
                speed = 50.0 / radius;
            }

            double angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);

            int x;
            int y;

            if (i < n / 2) {
                inFirstFrame[i] = true;
                int minX = FRAME1_LEFT + radius;
                int maxX = FRAME1_RIGHT - radius;
                int minY = FRAME1_TOP + radius;
                int maxY = FRAME1_BOTTOM - radius;

                x = rand.nextInt(maxX - minX + 1) + minX;
                y = rand.nextInt(maxY - minY + 1) + minY;
            } else {
                inFirstFrame[i] = false;
                int minX = FRAME2_LEFT + radius;
                int maxX = FRAME2_RIGHT - radius;
                int minY = FRAME2_TOP + radius;
                int maxY = FRAME2_BOTTOM - radius;

                x = rand.nextInt(maxX - minX + 1) + minX;
                y = rand.nextInt(maxY - minY + 1) + minY;
            }

            Ball ball = new Ball(new Point(x, y), radius, randomColor);
            ball.setVelocity(v);
            balls[i] = ball;
        }

        while (true) {
            DrawSurface d = gui.getDrawSurface();

            drawFrames(d);

            for (int i = 0; i < n; i++) {
                if (inFirstFrame[i]) {
                    moveBallInFrame(balls[i],
                            FRAME1_LEFT, FRAME1_TOP, FRAME1_RIGHT, FRAME1_BOTTOM);
                } else {
                    moveBallInFrame(balls[i],
                            FRAME2_LEFT, FRAME2_TOP, FRAME2_RIGHT, FRAME2_BOTTOM);
                }
                balls[i].drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }

    private static void drawFrames(DrawSurface d) {
        d.setColor(Color.GRAY);
        d.drawRectangle(FRAME1_LEFT,
                FRAME1_TOP,
                FRAME1_RIGHT - FRAME1_LEFT,
                FRAME1_BOTTOM - FRAME1_TOP);

        d.setColor(Color.YELLOW);
        d.drawRectangle(FRAME2_LEFT,
                FRAME2_TOP,
                FRAME2_RIGHT - FRAME2_LEFT,
                FRAME2_BOTTOM - FRAME2_TOP);
    }

    private static void moveBallInFrame(Ball ball,
                                        int left, int top, int right, int bottom) {
        if (ball.getVelocity() == null) {
            return;
        }

        int r = ball.getSize();

        double leftBound = left + r;
        double rightBound = right - r;
        double topBound = top + r;
        double bottomBound = bottom - r;

        double x = ball.getX();
        double y = ball.getY();

        double dx = ball.getVelocity().getDx();
        double dy = ball.getVelocity().getDy();

        double nextX = x + dx;
        double nextY = y + dy;

        if (nextX < leftBound || nextX > rightBound) {
            dx = -dx;
        }


        if (nextY < topBound || nextY > bottomBound) {
            dy = -dy;
        }

        ball.setVelocity(dx, dy);
        ball.moveOneStep();
    }
}
