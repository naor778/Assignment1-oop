package tasks;

import animations.Animation;
import animations.AnimationRunner;

public class ShowHiScoresTask implements Task<Void> {
    private final AnimationRunner runner;
    private final Animation highScoresAnimation;

    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    @Override
    public Void run() {
        runner.run(highScoresAnimation);
        return null;
    }
}

