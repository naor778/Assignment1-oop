package sprites;


import biuoop.DrawSurface;
import collidables.Block;
import collidables.Collidable;
import collidables.CollisionInfo;
import collidables.GameEnvironment;
import game.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import java.awt.Color;

public class Ball implements Sprite {

    private static final int SCREEN_WIDTH =800 ;
    private static final int SCREEN_HEIGHT = 600;

    private GameEnvironment gameEnvironment;
    private Point center;
    private int r;
    private Color color;
    private Velocity velocity;
    // constructor
    public Ball(Point center, int r, java.awt.Color color){
        this.center=center;
        this.r=r;
        this.color=color;
    }


    public void removeFromGame(Game game) { game.removeSprite(this); }

    // accessors
    public int getX(){
        return  (int) Math.round(center.getX());
    }
    public int getY() {
        return  (int) Math.round(center.getY());
    }

    public int getSize(){
        return r;
    }
    public java.awt.Color getColor(){
        return color;
    }
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    public Velocity getVelocity() {

        return this.velocity;
    }



    public void moveOneStep() {
        if (this.gameEnvironment == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
            // גם במקרה שאין סביבה – עדיין נשמור על המסך
            keepInScreen();
            return;
        }

        // 1. מסלול מהנקודה הנוכחית לנקודה הבאה
        Point current = this.center;
        Point next = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(current, next);

        // 2. בדיקה האם יש התנגשות בדרך
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);

        if (info == null) {
            // אין התנגשות – פשוט זזים
            this.center = next;
        } else {
            // יש עצם מתנגש
            Point collisionPoint = info.collisionPoint();
            Collidable object = info.collisionObject();

            // להזיז את הכדור טיפה לפני נקודת ההתנגשות
            double epsilon = 0.001;
            double newX = collisionPoint.getX() - this.velocity.getDx() * epsilon;
            double newY = collisionPoint.getY() - this.velocity.getDy() * epsilon;
            this.center = new Point(newX, newY);

            // לחשב מהירות חדשה דרך hit של האובייקט
            this.velocity = object.hit(this, collisionPoint, this.velocity);
        }

        // *** תיקונים נגד "להיכנס לבלוק" ו"בריחה מהמסך" ***
       fixIfInsideObjects();
        keepInScreen();
    }




    @Override
    public void timePassed() {
        this.moveOneStep();
    }


//
    // draw the ball on the given DrawSurface
    @Override
    public void drawOn(DrawSurface surface){
        surface.setColor(this.color);
        surface.fillCircle(getX(),getY(),getSize());
    }

    public void setVelocity(double dx, double dy){
        this.velocity=new Velocity(dx,dy);
    }

    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }
    public void addToGame(Game g) {
        g.addSprite(this);
    }
    // אם הכדור בטעות נכנס לתוך בלוק – מוציאים אותו החוצה
    // אם הכדור בטעות נכנס לתוך בלוק – מוציאים אותו החוצה ומתייחסים לזה כמו hit
    // אם הכדור בטעות נכנס לתוך פאדל או בלוק – מתייחסים לזה כהתנגשות
    private void fixIfInsideObjects() {
        if (this.gameEnvironment == null) {
            return;
        }

        for (Collidable c : this.gameEnvironment.getCollidables()) {
            if (c == null || c.getCollisionRectangle() == null) {
                continue;
            }

            geometry.Rectangle rect = c.getCollisionRectangle();
            double left   = rect.getUpperLeft().getX();
            double top    = rect.getUpperLeft().getY();
            double right  = left + rect.getWidth();
            double bottom = top + rect.getHeight();

            double x = this.center.getX();
            double y = this.center.getY();

            // האם מרכז הכדור *בתוך* המלבן?
            if (x <= left || x >= right || y <= top || y >= bottom) {
                continue;
            }

            // מכאן – הכדור נמצא *בתוך* האובייקט הזה

            double distLeft   = x - left;
            double distRight  = right - x;
            double distTop    = y - top;
            double distBottom = bottom - y;

            double min = Math.min(Math.min(distLeft, distRight), Math.min(distTop, distBottom));
            double eps = 0.1;
            Point collisionPoint;

            if (min == distLeft) {
                collisionPoint = new Point(left, y);
                this.center = new Point(left - this.r - eps, y);
            } else if (min == distRight) {
                collisionPoint = new Point(right, y);
                this.center = new Point(right + this.r + eps, y);
            } else if (min == distTop) {
                collisionPoint = new Point(x, top);
                this.center = new Point(x, top - this.r - eps);
            } else { // bottom
                collisionPoint = new Point(x, bottom);
                this.center = new Point(x, bottom + this.r + eps);
            }


            this.velocity = c.hit(this, collisionPoint, this.velocity);


            break;



        }
    }


    private void keepInScreen() {
        double x = this.center.getX();
        double y = this.center.getY();
        boolean changed = false;

        if (x - this.r < 0) {
            x = this.r;
            changed = true;
        }
        if (x + this.r > SCREEN_WIDTH) {
            x = SCREEN_WIDTH - this.r;
            changed = true;
        }
        if (y - this.r < 0) {
            y = this.r;
            changed = true;
        }
        if (y + this.r > SCREEN_HEIGHT) {
            y = SCREEN_HEIGHT - this.r;
            changed = true;
        }

        if (changed) {
            this.center = new Point(x, y);
            // לא נוגעים ב-velocity!
        }
    }



}