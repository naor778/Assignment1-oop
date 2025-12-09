package listeners;


import collidables.Block;
import game.Counter;
import sprites.Ball;

public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // כל פגיעה בבלוק = 5 נקודות
        System.out.println("+5 score add");
        this.currentScore.increase(5);
    }
}