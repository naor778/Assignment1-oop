package listeners;

import collidables.Block;
import game.GameLevel;
import game.Counter;

import sprites.Ball;

public class BlockRemover implements HitListener {

    private GameLevel gameLevel;
    private Counter remainingBlocks;

    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("Removing block!");
        // מסירים בלוק מהמשחק
        beingHit.removeFromGame(this.gameLevel);

        // מקטינים Counter
        this.remainingBlocks.decrease(1);

        // מסירים את ה-listener מהבלוק
        beingHit.removeHitListener(this);
    }
}