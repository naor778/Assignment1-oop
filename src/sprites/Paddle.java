package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collidables.Collidable;
import game.Game;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

public class Paddle implements Sprite, Collidable {
    private Rectangle rect;
    private Color color;
    private KeyboardSensor keyboard;
    private double speed;
    private int screenWidth;
    private int borderThickness;

    public Paddle(Rectangle rect, Color color,
                  KeyboardSensor keyboard, double speed,
                  int screenWidth, int borderThickness) {
        this.rect = rect;
        this.color = color;
        this.keyboard = keyboard;
        this.speed = speed;
        this.screenWidth = screenWidth;
        this.borderThickness = borderThickness;
    }

    // להזיז שמאלה
    public void moveLeft() {
        double newX = rect.getUpperLeft().getX() - speed;
        // לא לצאת מגבול שמאל
        if (newX < borderThickness) {
            newX = borderThickness;
        }
        this.rect = new Rectangle(
                new Point(newX, rect.getUpperLeft().getY()),
                rect.getWidth(), rect.getHeight());
    }

    // להזיז ימינה
    public void moveRight() {
        double newX = rect.getUpperLeft().getX() + speed;
        double maxX = screenWidth - borderThickness - rect.getWidth();
        if (newX > maxX) {
            newX = maxX;
        }
        this.rect = new Rectangle(
                new Point(newX, rect.getUpperLeft().getY()),
                rect.getWidth(), rect.getHeight());
    }

    // sprites.Sprite: כל טיק זמן – לבדוק מקשים
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    // sprites.Sprite: ציור
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        int x = (int) rect.getUpperLeft().getX();
        int y = (int) rect.getUpperLeft().getY();
        int w = (int) rect.getWidth();
        int h = (int) rect.getHeight();

        d.fillRectangle(x, y, w, h);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, w, h);
    }

    // collidables.Collidable
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    @Override
    public Velocity hit( Ball hitter,Point collisionPoint, Velocity currentVelocity) {
        System.out.println("Paddle.hit at (" + collisionPoint.getX() + "," + collisionPoint.getY() + ")");
        double hitX = collisionPoint.getX();

        double paddleX = this.rect.getUpperLeft().getX();
        double paddleWidth = this.rect.getWidth();
        double regionWidth = paddleWidth / 5.0;

        // מחשבים באיזה אזור פגענו (1..5)
        double relativeX = hitX - paddleX;
        int region;

        if (relativeX < regionWidth) {
            region = 1;
        } else if (relativeX < 2 * regionWidth) {
            region = 2;
        } else if (relativeX < 3 * regionWidth) {
            region = 3;
        } else if (relativeX < 4 * regionWidth) {
            region = 4;
        } else {
            region = 5;
        }

        // המהירות הנוכחית – מחשבים את הגודל (speed)
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double speed = Math.sqrt(dx * dx + dy * dy);

        double angle;

        switch (region) {
            case 1:
                angle = 300; // הרבה שמאלה למעלה (-60)
                break;
            case 2:
                angle = 330; // קצת שמאלה למעלה (-30)
                break;
            case 3:
                // כמו בלוק: שומרים dx ומחזירים את dy למעלה
                return new Velocity(dx, -Math.abs(dy));
            case 4:
                angle = 30;  // קצת ימינה למעלה
                break;
            case 5:
            default:
                angle = 60;  // הרבה ימינה למעלה
                break;
        }

        // בונים מהירות חדשה לפי הזווית והגודל

        return Velocity.fromAngleAndSpeed(angle, speed);
    }


    // נוח למטלה: להוסיף את הפאדאל למשחק
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
