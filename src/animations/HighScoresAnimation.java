package animations;

import biuoop.DrawSurface;
import game.HighScoreFile;

public class HighScoresAnimation implements Animation {
    private final HighScoreFile highScoreFile;

    public HighScoresAnimation(HighScoreFile highScoreFile) {
        this.highScoreFile = highScoreFile;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(140, 220, highScoreFile.formatLine(), 32);
        d.drawText(140, 280, "Press space to continue", 24);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
