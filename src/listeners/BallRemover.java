package listeners;

import collidables.Block;
import game.Counter;
import game.GameLevel;
import sprites.Ball;

public class BallRemover implements HitListener {

    private GameLevel gameLevel;
    private Counter remainingBalls;

    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // מסירים את הכדור מהמשחק
        hitter.removeFromGame(this.gameLevel);

        // מעדכנים Counter
        this.remainingBalls.decrease(1);
    }
}