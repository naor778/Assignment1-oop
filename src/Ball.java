import java.awt.*;
import biuoop.DrawSurface;

public class Ball {
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
        if (this.velocity == null) {
            return;
        }
        this.center = this.velocity.applyToPoint(this.center);
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface){
        surface.setColor(this.color);
        surface.fillCircle(getX(),getY(),getSize());
    }
}