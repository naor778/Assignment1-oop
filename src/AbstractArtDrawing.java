import biuoop.GUI;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.Random;



public class AbstractArtDrawing {
    private static final int Wide = 500;
    private static final int Height = 600;
    private static final int NUM_LINES = 10;
    private final GUI gui;
    private final Random rand = new Random();


    public AbstractArtDrawing() {
        this.gui = new GUI("Abstract Art",Wide, Height);
    }

    private Line generateRandomLine() {
        int x1 = rand.nextInt(Wide);
        int y1 = rand.nextInt(Height);
        int x2 = rand.nextInt(Wide);
        int y2 = rand.nextInt(Height);
        return new Line(x1, y1, x2, y2);
    }

    private void drawPoint(DrawSurface d, Point p, Color color, int radius) {
        d.setColor(color);
        d.fillCircle((int) Math.round(p.getX()),
                (int) Math.round(p.getY()),
                radius);
    }

    private void drawLine(DrawSurface d, Line l, Color color) {
        d.setColor(color);
        d.drawLine((int) Math.round(l.start().getX()),
                (int) Math.round(l.start().getY()),
                (int) Math.round(l.end().getX()),
                (int) Math.round(l.end().getY()));
    }

    public void draw() {
        DrawSurface d = gui.getDrawSurface();
        Line[] lines = new Line[NUM_LINES];

        for (int i = 0; i < NUM_LINES; i=i+1) {
            lines[i] = generateRandomLine();
            drawLine(d, lines[i], Color.BLACK);
        }

        // 2) מציירים נקודות אמצע בכחול (רדיוס 3)
        for (int i = 0; i < NUM_LINES; i=i+1) {
            drawPoint(d, lines[i].middle(), Color.BLUE, 3);
        }

        // 3) מחשבים חיתוכים בין כל זוג קווים, ומסמנים באדום (רדיוס 3)
        for (int i = 0; i < NUM_LINES; i++) {
            for (int j = i + 1; j < NUM_LINES; j++) {
                Point p = lines[i].intersectionWith(lines[j]);
                if (p != null) {
                    drawPoint(d, p, Color.RED, 3);
                }
            }
        }

        gui.show(d);   // הצגה אחת בסוף
    }

    public static void main(String[] args) {
        new AbstractArtDrawing().draw();
    }
}


