package game;

import biuoop.GUI;
import biuoop.DrawSurface;
import collidables.Block;
import collidables.Collidable;
import collidables.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;

import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.PrintingHitListener;
import listeners.ScoreTrackingListener;
import sprites.*;


public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;


    private int width = 800;
    private int height = 600;

    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("My game.Game", width, height);
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
        this.score = new Counter(0);
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        PrintingHitListener printer = new PrintingHitListener();
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);
        bottom.addHitListener(ballRemover);



        Rectangle scoreRect = new Rectangle(
                new Point(0, borderThickness),  // מתחת ל-border העליון
                width,
                borderThickness);

        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreRect, this.score);
        scoreIndicator.addToGame(this);

        // ========= בלוקים למעלה (pattern) =========
        int blockWidth = 50;
        int blockHeight = 20;
        int rows = 5;  // מספר שורות
        int blocksInRow = 12; // כמה בלוקים בכל שורה
        int startX = width - borderThickness - blocksInRow * blockWidth; // מיישר לשמאל קצת
        int startY = 100;

        java.awt.Color[] rowColors = {
                java.awt.Color.GRAY,
                java.awt.Color.RED,
                java.awt.Color.YELLOW,
                java.awt.Color.BLUE,
                java.awt.Color.GREEN
        };

        for (int row = 0; row < rows; row++) {
            java.awt.Color rowColor = rowColors[row];
            int y = startY + row * blockHeight;

            for (int i = 0; i < blocksInRow - row; i++) { // כל שורה קצת קצרה יותר
                int x = startX + i * blockWidth + row * (blockWidth / 2); // הזחה קטנה לכל שורה
                Block b = new Block(
                        new Rectangle(new Point(x, y), blockWidth, blockHeight),
                        rowColor);
                b.addToGame(this);

                b.addHitListener(blockRemover);
                b.addHitListener(scoreTracker);
                b.addHitListener(printer);       // אופציונלי רק לדיבוג
                this.remainingBlocks.increase(1);
            }
        }

        // ========= פאדל =========
        biuoop.KeyboardSensor keyboard = this.gui.getKeyboardSensor();
        Rectangle paddleRect = new Rectangle(
                new Point(width / 2.0 - 50, height - 40), // בערך באמצע למטה
                100,
                20);

        Paddle paddle = new Paddle(
                paddleRect,
                java.awt.Color.ORANGE,
                keyboard,
                7,                 // מהירות פאדל
                this.width,
                borderThickness);

        paddle.addToGame(this);

        // ========= שני כדורים =========
        // כדור ראשון
        Ball ball1 = new Ball(new Point(350, 300), 7, java.awt.Color.WHITE);
        ball1.setVelocity(Velocity.fromAngleAndSpeed(320, 5));
        ball1.setGameEnvironment(this.environment);
        ball1.addToGame(this);
        this.remainingBalls.increase(1);

        // כדור שני
        Ball ball2 = new Ball(new Point(450, 300), 7, java.awt.Color.PINK);
        ball2.setVelocity(Velocity.fromAngleAndSpeed(40, 5));
        ball2.setGameEnvironment(this.environment);
        ball2.addToGame(this);
        this.remainingBalls.increase(1);
    }

    // לופ האנימציה
    public void run() {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (this.remainingBlocks.getValue() > 0
                && this.remainingBalls.getValue() > 0) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = gui.getDrawSurface();

            // רקע

            d.setColor(java.awt.Color.BLUE);
            d.fillRectangle(0, 0, width, height);

            // ספרייטים
            this.sprites.drawAllOn(d);
            gui.show(d);

            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }

        }
        this.gui.close();
    }

    }
