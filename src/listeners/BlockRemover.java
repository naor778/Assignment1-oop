package listeners;

import collidables.Block;
import game.Game;
import game.Counter;

import sprites.Ball;

public class BlockRemover implements HitListener {

    private Game game;
    private Counter remainingBlocks;

    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("Removing block!");
        // מסירים בלוק מהמשחק
        beingHit.removeFromGame(this.game);

        // מקטינים Counter
        this.remainingBlocks.decrease(1);

        // מסירים את ה-listener מהבלוק
        beingHit.removeHitListener(this);
    }
}