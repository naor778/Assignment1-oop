package collidables;

import biuoop.DrawSurface;
import game.GameLevel;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;
import sprites.Ball;
import sprites.Sprite;
import sprites.Velocity;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Block implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners = new ArrayList<>();
    private Rectangle rectangle;

    // NEW: fill can be either color or image
    private Color fillColor;
    private Image fillImage;

    // NEW: optional stroke (border). null => no border
    private Color strokeColor;

    // Old constructor (kept for backwards compatibility)
    public Block(Rectangle rectangle, Color color) {
        this(rectangle, color, Color.BLACK); // same as your previous behavior
    }

    // NEW: Color fill + optional stroke
    public Block(Rectangle rectangle, Color fillColor, Color strokeColor) {
        this.rectangle = rectangle;
        this.fillColor = fillColor;
        this.fillImage = null;
        this.strokeColor = strokeColor;
    }

    // NEW: Image fill + optional stroke
    public Block(Rectangle rectangle, Image fillImage, Color strokeColor) {
        this.rectangle = rectangle;
        this.fillImage = fillImage;
        this.fillColor = null;
        this.strokeColor = strokeColor;
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
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();

        double left = this.rectangle.getUpperLeft().getX();
        double right = left + this.rectangle.getWidth();
        double top = this.rectangle.getUpperLeft().getY();
        double bottom = top + this.rectangle.getHeight();

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        double eps = 0.0001;

        boolean hitLeftSide = Math.abs(x - left) <= eps;
        boolean hitRightSide = Math.abs(x - right) <= eps;

        boolean hitTop = Math.abs(y - top) <= eps;
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

    // kept for compatibility (if someone used it)
    public Color getColor() {
        if (this.fillColor != null) {
            return this.fillColor;
        }
        // if the block is image-filled, return some default
        return Color.WHITE;
    }

    @Override
    public void drawOn(DrawSurface d) {
        Rectangle r = this.rectangle;

        int x = (int) r.getUpperLeft().getX();
        int y = (int) r.getUpperLeft().getY();
        int w = (int) r.getWidth();
        int h = (int) r.getHeight();

        // Fill
        if (fillImage != null) {
            d.drawImage(x, y, fillImage);
        } else {
            // if no image, use color (fallback to white)
            Color c = (fillColor != null) ? fillColor : Color.WHITE;
            d.setColor(c);
            d.fillRectangle(x, y, w, h);
        }

        // Stroke (optional)
        if (strokeColor != null) {
            d.setColor(strokeColor);
            d.drawRectangle(x, y, w, h);
        }
    }

    @Override
    public void timePassed() {
        // no-op
    }

    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }
}
