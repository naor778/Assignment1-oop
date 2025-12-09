package listeners;

import collidables.Block;
import game.Counter;
import game.Game;
import sprites.Ball;

public class BallRemover implements HitListener {

    private Game game;
    private Counter remainingBalls;

    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // מסירים את הכדור מהמשחק
        hitter.removeFromGame(this.game);

        // מעדכנים Counter
        this.remainingBalls.decrease(1);
    }
}