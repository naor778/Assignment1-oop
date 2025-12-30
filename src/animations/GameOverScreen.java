package animations;

import biuoop.DrawSurface;
import game.Counter;

public class GameOverScreen implements Animation {
    private final Counter score;

    public GameOverScreen(Counter score) {
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(100, d.getHeight() / 2, "Game Over. Your score is " + score.getValue(), 32);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
