import java.awt.*;
import biuoop.DrawSurface;

public class Ball implements Sprite{
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
        if (this.gameEnvironment == null || this.velocity == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
            return;
        }

        // 1. מסלול מהנקודה הנוכחית לנקודה הבאה
        Point start = this.center;
        Point end = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(start, end);

        // 2. בדיקה האם יש התנגשות בדרך
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);

        if (info == null) {
            // אין התנגשות – זזים כרגיל
            this.center = end;
            return;
        }

        // 3. יש התנגשות
        Point collisionPoint = info.collisionPoint();
        Collidable object = info.collisionObject();

        // קודם מחשבים מהירות חדשה לפי האובייקט שפגענו בו
        Velocity newVelocity = object.hit(collisionPoint, this.velocity);

        // 4. מזיזים את הכדור "חוצה" מהבלוק לפי ה־*מהירות החדשה*
        double eps = 0.01; // אתה יכול לשחק עם הערך: 0.01–1
        double newX = collisionPoint.getX() + newVelocity.getDx() * eps;
        double newY = collisionPoint.getY() + newVelocity.getDy() * eps;

        this.center = new Point(newX, newY);
        this.velocity = newVelocity;
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

}