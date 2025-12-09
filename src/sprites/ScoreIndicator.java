package sprites;



import biuoop.DrawSurface;
import game.Counter;
import game.Game;
import geometry.Rectangle;
import geometry.Point;
import java.awt.Color;

public class ScoreIndicator implements Sprite {
    private final Counter score;
    private final Rectangle rect;

    public ScoreIndicator(Rectangle rect, Counter score) {
        this.rect = rect;
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // אם בטעות משהו null – נצא בשקט ולא נזרוק חריגה
        if (this.rect == null || this.score == null) {
            return;
        }

        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int w = (int) this.rect.getWidth();
        int h = (int) this.rect.getHeight();

        // רקע
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(x, y, w, h);

        // טקסט הניקוד
        d.setColor(Color.BLACK);
        String text = "Score: " + this.score.getValue();
        d.drawText(x + w / 2 - 40, y + h - 5, text, 18);
    }

    @Override
    public void timePassed() {
        // אין לוגיקה – רק מציירים כל פריים
    }

    public void addToGame(Game game) {
        game.addSprite(this);
    }
}

