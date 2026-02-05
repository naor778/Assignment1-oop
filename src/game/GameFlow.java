package game;

import animations.AnimationRunner;
import biuoop.KeyboardSensor;
import levels.LevelInformation;

import java.util.List;

public class GameFlow {
    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final Counter score;

    private boolean didWin = true;

    public GameFlow(AnimationRunner runner, KeyboardSensor keyboard, Counter score) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.score = score;
    }

    public boolean didWin() {
        return didWin;
    }

    public void runLevels(List<LevelInformation> levels) {
        didWin = true;

        for (LevelInformation levelInfo : levels) {
            // אצלך זה החתימה של הקונסטרקטור: (levelInfo, runner, keyboard, score)
            GameLevel level = new GameLevel(levelInfo, runner, keyboard, score);

            level.initialize();
            level.run();

            // אם השחקן מת (נגמרו הכדורים) => הפסד, לא ממשיכים לרמות הבאות
            if (level.isPlayerDead()) {
                didWin = false;
                break;
            }
        }
    }
}
