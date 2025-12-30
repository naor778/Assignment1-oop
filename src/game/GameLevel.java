package game;

import animations.Animation;
import animations.KeyPressStoppableAnimation;
import animations.PauseScreen;

import biuoop.DrawSurface;
import collidables.Block;
import collidables.Collidable;
import collidables.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import animations.AnimationRunner;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.PrintingHitListener;
import listeners.ScoreTrackingListener;
import sprites.*;
import levels.LevelInformation;
import biuoop.KeyboardSensor;

public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;

    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private AnimationRunner runner;
    private boolean running;
    private int width = 800;
    private int height = 600;
    private biuoop.KeyboardSensor keyboard;
    private LevelInformation levelInfo;

    public GameLevel(LevelInformation levelInfo, AnimationRunner runner, KeyboardSensor keyboard, Counter score) {
        this.levelInfo = levelInfo;
        this.runner = runner;
        this.keyboard = keyboard;
        this.score = score;

        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }


    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }



    // פונקציה שעושה את כל בניית העולם – בלוקים, גבולות, כדור וכו'
    public void initialize() {
        int borderThickness = 20;


        // ========= גבולות המסך =========
        Block top = new Block(
                new Rectangle(new Point(0, 0), width, borderThickness),
                java.awt.Color.GRAY);
        Block bottom = new Block(
                new Rectangle(new Point(0, height - borderThickness), width, borderThickness),
                java.awt.Color.GRAY);
        Block left = new Block(
                new Rectangle(new Point(0, borderThickness),
                        borderThickness, height - 2 * borderThickness),
                java.awt.Color.GRAY);
        Block right = new Block(
                new Rectangle(new Point(width - borderThickness, borderThickness),
                        borderThickness, height - 2 * borderThickness),
                java.awt.Color.GRAY);

        top.addToGame(this);
        bottom.addToGame(this);
        left.addToGame(this);
        right.addToGame(this);


        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(0);
// score משותף לכל הרמות — לא מאפסים פה!

        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        PrintingHitListener printer = new PrintingHitListener();
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);
        bottom.addHitListener(ballRemover);


        Rectangle scoreRect = new Rectangle(
                new Point(0, borderThickness),  // מתחת ל-border העליון
                width,
                borderThickness);
        this.addSprite(this.levelInfo.getBackground());
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreRect, this.score);
        scoreIndicator.addToGame(this);
        LevelNameIndicator levelNameIndicator =
                new LevelNameIndicator(this.levelInfo.levelName(), borderThickness);
        this.addSprite(levelNameIndicator);

        for (Block b : this.levelInfo.blocks()) {
            b.addToGame(this);

            b.addHitListener(blockRemover);
            b.addHitListener(scoreTracker);
            b.addHitListener(printer); // אפשר להשאיר לדיבוג

            this.remainingBlocks.increase(1);
        }

        // ========= פאדל =========
        int paddleWidth = this.levelInfo.paddleWidth();

        Rectangle paddleRect = new Rectangle(
                new Point(width / 2.0 - paddleWidth / 2.0, height - 40), // באמצע למטה
                paddleWidth,
                20);

        Paddle paddle = new Paddle(
                paddleRect,
                java.awt.Color.ORANGE,
                this.keyboard,
                this.levelInfo.paddleSpeed(),  // מהירות לפי הרמה
                this.width,
                borderThickness);

        paddle.addToGame(this);

        java.util.List<Velocity> velocities = this.levelInfo.initialBallVelocities();

        for (int i = 0; i < velocities.size(); i++) {
            Point start = new Point(400 + i * 20, 300);

            Ball ball = new Ball(start, 7, java.awt.Color.WHITE);
            ball.setVelocity(velocities.get(i));
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);

            this.remainingBalls.increase(1);
        }
    }

        // לופ האנימציה
        public void run() {
            // Countdown: 3..2..1 במשך 2 שניות (מסך קפוא)
            this.runner.run(new animations.CountdownAnimation(2, 3, this.sprites));

            this.running = true;
            this.runner.run(this);

            if (this.remainingBlocks.getValue() == 0) {
                this.score.increase(100);
            }
        }



    @Override
    public void doOneFrame(DrawSurface d) {

        // draw (כולל רקע שהוספת ל-sprites)
        this.sprites.drawAllOn(d);

        // pause לפני update
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("P") || this.keyboard.isPressed("פ")) {
            this.runner.run(
                    new KeyPressStoppableAnimation(this.keyboard,
                            biuoop.KeyboardSensor.SPACE_KEY,
                            new PauseScreen())
            );
            return;
        }

        // update
        this.sprites.notifyAllTimePassed();

        // stop conditions
        if (this.remainingBalls.getValue() == 0) {
            this.running = false;
        }
        if (this.remainingBlocks.getValue() == 0) {
            this.running = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

public boolean isPlayerDead() {
    return this.remainingBalls.getValue() == 0;
}
    }