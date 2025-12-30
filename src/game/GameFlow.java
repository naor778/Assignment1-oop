package game;

import animations.AnimationRunner;
import biuoop.KeyboardSensor;
import levels.LevelInformation;

import java.util.List;

public class GameFlow {
    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final Counter score;

    public GameFlow(AnimationRunner runner, KeyboardSensor keyboard, Counter score) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.score = score;
    }

    public boolean runLevels(List<LevelInformation> levels) {
        for (LevelInformation info : levels) {
            GameLevel level = new GameLevel(info, runner, keyboard, score);
            level.initialize();
            level.run();

            if (level.isPlayerDead()) {
                return false; // הפסד
            }
        }
        return true; // ניצחון
    }


}
