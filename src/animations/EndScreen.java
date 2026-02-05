package animations;

import biuoop.DrawSurface;
import game.Counter;

public class EndScreen implements Animation {
    private final Counter score;
    private final boolean win;

    public EndScreen(Counter score, boolean win) {
        this.score = score;
        this.win = win;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        String msg = win ? "You Win! Your score is " : "Game Over. Your score is ";
        d.drawText(140, 250, msg + score.getValue(), 32);
        d.drawText(140, 310, "Press space to continue", 24);
    }

    @Override
    public boolean shouldStop() {
        return false; // נעצור מבחוץ עם KeyPressStoppableAnimation
    }
}
