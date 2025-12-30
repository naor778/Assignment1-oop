package game;

import animations.*;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.*;

import java.util.ArrayList;
import java.util.List;

public class Ass4Game {

    private static LevelInformation levelByNumber(int n) {
        return switch (n) {
            case 1 -> new DirectHit();
            case 2 -> new WideEasy();
            case 3 -> new Green3();
            case 4 -> new FinalFour();
            default -> null;
        };
    }

    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", 800, 600);
        AnimationRunner runner = new AnimationRunner(gui);
        KeyboardSensor keyboard = gui.getKeyboardSensor();

        Counter score = new Counter(0);
        GameFlow flow = new GameFlow(runner, keyboard, score);

        List<LevelInformation> levelsToRun = new ArrayList<>();

        if (args.length == 0) {
            levelsToRun = List.of(new DirectHit(), new WideEasy(), new Green3(), new FinalFour());
        } else {
            for (String s : args) {
                try {
                    int n = Integer.parseInt(s);
                    LevelInformation li = levelByNumber(n);
                    if (li != null) {
                        levelsToRun.add(li);
                    }
                } catch (NumberFormatException ignored) {
                    // מתעלמים
                }
            }

            // אם לא היה אף ארגומנט תקין -> מריצים הכל
            if (levelsToRun.isEmpty()) {
                levelsToRun = List.of(new DirectHit(), new WideEasy(), new Green3(), new FinalFour());
            }
        }

        boolean win = flow.runLevels(levelsToRun);

        Animation end = win ? new YouWinScreen(score) : new GameOverScreen(score);
        runner.run(new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY, end));

        gui.close();
    }
}
