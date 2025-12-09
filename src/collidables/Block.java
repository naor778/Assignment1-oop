package collidables;

import biuoop.DrawSurface;
import game.Game;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;
import sprites.Ball;
import sprites.Sprite;
import sprites.Velocity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Block implements Collidable, Sprite , HitNotifier {
    private List<HitListener> hitListeners = new ArrayList<>();
    private Rectangle rectangle;
    private Color color;

    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    private void notifyHit(Ball hitter) {

        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    @Override
    public Velocity hit(Ball hitter,Point collisionPoint, Velocity currentVelocity) {
        System.out.println("Block.hit called at (" + collisionPoint.getX() + ", " + collisionPoint.getY() + ")");
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();

        double left   = this.rectangle.getUpperLeft().getX();
        double right  = left + this.rectangle.getWidth();
        double top    = this.rectangle.getUpperLeft().getY();
        double bottom = top + this.rectangle.getHeight();

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        double eps = 0.0001;

        // האם פגענו בצד שמאל או ימין
        boolean hitLeftSide  = Math.abs(x - left)  <= eps;
        boolean hitRightSide = Math.abs(x - right) <= eps;

        // האם פגענו בחלק עליון או תחתון
        boolean hitTop    = Math.abs(y - top)    <= eps;
        boolean hitBottom = Math.abs(y - bottom) <= eps;

        if (hitLeftSide || hitRightSide) {
            dx = -dx;
        }
        if (hitTop || hitBottom) {
            dy = -dy;
        }
      this.notifyHit(hitter);
        return new Velocity(dx, dy);
    }



    public Color getColor() {
        return this.color;
    }
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Rectangle r = this.rectangle;

        int x = (int) r.getUpperLeft().getX();
        int y = (int) r.getUpperLeft().getY();
        int w = (int) r.getWidth();
        int h = (int) r.getHeight();

        d.fillRectangle(x, y, w, h);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, w, h);
    }

    @Override
    public void timePassed() {

    }
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }


}