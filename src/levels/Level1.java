package levels;

import biuoop.DrawSurface;
import collidables.Block;
import geometry.Point;
import geometry.Rectangle;
import sprites.Sprite;
import sprites.Velocity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Level1 implements LevelInformation {

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int BORDER_THICKNESS = 20;

    private final List<Block> blocks;

    public Level1() {
        this.blocks = createBlocks();
    }

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> v = new ArrayList<>();
        v.add(Velocity.fromAngleAndSpeed(320, 5));
        v.add(Velocity.fromAngleAndSpeed(40, 5));
        return v;
    }

    @Override
    public int paddleSpeed() {
        return 7;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Level 1";
    }

    @Override
    public Sprite getBackground() {
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(Color.BLUE);
                d.fillRectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            }

            @Override
            public void timePassed() {
                // no-op
            }
        };
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks.size();
    }

    private List<Block> createBlocks() {
        List<Block> list = new ArrayList<>();

        int blockWidth = 50;
        int blockHeight = 20;
        int rows = 5;
        int blocksInRow = 12;
        int startX = SCREEN_WIDTH - BORDER_THICKNESS - blocksInRow * blockWidth;
        int startY = 100;

        Color[] rowColors = {
                Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN
        };

        for (int row = 0; row < rows; row++) {
            Color rowColor = rowColors[row];
            int y = startY + row * blockHeight;

            for (int i = 0; i < blocksInRow - row; i++) {
                int x = startX + i * blockWidth + row * (blockWidth / 2);

                Block b = new Block(
                        new Rectangle(new Point(x, y), blockWidth, blockHeight),
                        rowColor
                );

                list.add(b);
            }
        }

        return list;
    }
}
