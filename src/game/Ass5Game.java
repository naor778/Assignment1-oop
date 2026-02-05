package game;

import animations.Animation;
import animations.AnimationRunner;
import animations.EndScreen;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import io.LevelSpecificationReader;
import levels.LevelInformation;
import menu.Menu;
import menu.MenuAnimation;
import tasks.QuitTask;
import tasks.ShowHiScoresTask;
import tasks.Task;

import java.awt.ContainerOrderFocusTraversalPolicy;
import java.awt.Window;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.List;

public class Ass5Game {

    private static final String DEFAULT_LEVELS_FILE = "levels/level_definitions.txt";
    private static final String HIGHSCORE_FILE = "highscores.txt";

    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final HighScoreFile highScoreFile;
    private final String levelsPath;

    public Ass5Game(AnimationRunner runner, KeyboardSensor keyboard, String levelsPath) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.levelsPath = levelsPath;
        this.highScoreFile = new HighScoreFile(HIGHSCORE_FILE);
    }

    public void run() {
        while (true) {
            Menu<Task<Void>> menu = new MenuAnimation<>("Arkanoid", keyboard);

            menu.addSelection("s", "Start game", new Task<Void>() {
                @Override
                public Void run() {
                    List<LevelInformation> levels = loadLevels(levelsPath);

                    Counter score = new Counter(0);

                    GameFlow flow = new GameFlow(runner, keyboard, score);
                    flow.runLevels(levels);

                    boolean win = flow.didWin();

                    highScoreFile.updateIfHigher(score.getValue());

                    runner.run(new KeyPressStoppableAnimation(
                            keyboard,
                            KeyboardSensor.SPACE_KEY,
                            new EndScreen(score, win)
                    ));
                    return null;
                }
            });

            Animation hs = new KeyPressStoppableAnimation(
                    keyboard,
                    KeyboardSensor.SPACE_KEY,
                    new HighScoresAnimation(highScoreFile)
            );
            menu.addSelection("h", "See highest score", new ShowHiScoresTask(runner, hs));

            menu.addSelection("q", "Quit", new QuitTask());

            runner.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }

    private static List<LevelInformation> loadLevels(String path) {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("Levels file not found on classpath: " + path);
        }
        LevelSpecificationReader r = new LevelSpecificationReader();
        return r.fromReader(new InputStreamReader(is));
    }

    // ---- JDK 23 Swing workaround (biuoop GUI focus traversal crash) ----
    private static void applyJdk23SwingWorkaround(GUI gui) {
        try {
            // biuoop.GUI doesn't expose the JFrame/Window, so we find it via reflection
            for (Field f : gui.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object v = f.get(gui);
                if (v instanceof Window w) {
                    w.setFocusTraversalPolicy(new ContainerOrderFocusTraversalPolicy());
                    return;
                }
            }
        } catch (Exception ignored) {
            // If it fails, we simply don't apply the workaround
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", 800, 600);

        // apply workaround ASAP (before the EDT tries to pick initial focus)
        applyJdk23SwingWorkaround(gui);

        AnimationRunner runner = new AnimationRunner(gui);
        KeyboardSensor keyboard = gui.getKeyboardSensor();

        String levelsPath = (args.length > 0) ? args[0] : DEFAULT_LEVELS_FILE;

        new Ass5Game(runner, keyboard, levelsPath).run();
    }
}
