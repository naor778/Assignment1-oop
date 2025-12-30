package animations;

import biuoop.DrawSurface;
import sprites.SpriteCollection;

import java.awt.Color;

public class CountdownAnimation implements Animation {
    private final double numOfSeconds;
    private final int countFrom;
    private final SpriteCollection gameScreen;

    private long startTimeMillis = -1;
    private boolean stop = false;

    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (startTimeMillis < 0) {
            startTimeMillis = System.currentTimeMillis();
        }

        // מציירים את המסך "קפוא"
        gameScreen.drawAllOn(d);

        double elapsedSec = (System.currentTimeMillis() - startTimeMillis) / 1000.0;
        if (elapsedSec >= numOfSeconds) {
            stop = true;
            return;
        }

        int shown = countFrom - (int) Math.floor(elapsedSec * countFrom / numOfSeconds);
        if (shown < 1) {
            shown = 1;
        }

        // רקע לבן *בלי שקיפות*
        d.setColor(Color.WHITE);
        d.fillCircle(d.getWidth() / 2, d.getHeight() / 2, 60);

        // טקסט שחור
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 10, d.getHeight() / 2 + 10, String.valueOf(shown), 60);
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}
