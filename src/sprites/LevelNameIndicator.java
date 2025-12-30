package sprites;

import biuoop.DrawSurface;
import java.awt.Color;

public class LevelNameIndicator implements Sprite {
    private final String levelName;
    private final int y;

    public LevelNameIndicator(String levelName, int y) {
        this.levelName = levelName;
        this.y = y;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        int x = d.getWidth() - 220;
        d.drawText(x, y + 15, "Level Name: " + levelName, 16);
    }

    @Override
    public void timePassed() {
    }
}
