package animations;

import biuoop.DrawSurface;
import game.Counter;

public class YouWinScreen implements Animation {
    private final Counter score;

    public YouWinScreen(Counter score) {
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(100, d.getHeight() / 2, "You Win! Your score is " + score.getValue(), 32);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
