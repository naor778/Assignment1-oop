package levels;

import biuoop.DrawSurface;
import collidables.Block;
import io.BlocksDefinitionReader;
import io.BlocksFromSymbolsFactory;
import io.ColorsParser;
import io.LevelSpecificationReader;
import sprites.Sprite;
import sprites.Velocity;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class LevelFromFile implements LevelInformation {

    private final String name;
    private final List<Velocity> velocities;
    private final Sprite background;
    private final int paddleSpeed;
    private final int paddleWidth;
    private final int blocksStartX;
    private final int blocksStartY;
    private final int rowHeight;
    private final int numBlocksToRemove;
    private final List<String> layoutLines;
    private final BlocksFromSymbolsFactory factory;

    private LevelFromFile(String name,
                          List<Velocity> velocities,
                          Sprite background,
                          int paddleSpeed,
                          int paddleWidth,
                          int blocksStartX,
                          int blocksStartY,
                          int rowHeight,
                          int numBlocksToRemove,
                          List<String> layoutLines,
                          BlocksFromSymbolsFactory factory) {
        this.name = name;
        this.velocities = velocities;
        this.background = background;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.blocksStartX = blocksStartX;
        this.blocksStartY = blocksStartY;
        this.rowHeight = rowHeight;
        this.numBlocksToRemove = numBlocksToRemove;
        this.layoutLines = layoutLines;
        this.factory = factory;
    }

    public static LevelFromFile parse(List<String> levelLines) {
        Map<String, String> fields = new HashMap<>();
        List<String> blocksLayout = new ArrayList<>();

        int i = 0;
        while (i < levelLines.size()) {
            String line = levelLines.get(i);
            if (line.equals("START_BLOCKS")) {
                i++;
                while (i < levelLines.size() && !levelLines.get(i).equals("END_BLOCKS")) {
                    blocksLayout.add(levelLines.get(i));
                    i++;
                }
                if (i >= levelLines.size()) throw new RuntimeException("Missing END_BLOCKS");
                i++; // consume END_BLOCKS
                continue;
            }

            int idx = line.indexOf(':');
            if (idx < 0) throw new RuntimeException("Bad field line: " + line);
            fields.put(line.substring(0, idx), line.substring(idx + 1));
            i++;
        }

        String levelName = must(fields, "level_name");
        List<Velocity> vels = parseVelocities(must(fields, "ball_velocities"));
        Sprite bg = parseBackground(must(fields, "background"));

        int paddleSpeed = Integer.parseInt(must(fields, "paddle_speed"));
        int paddleWidth = Integer.parseInt(must(fields, "paddle_width"));

        String blockDefsPath = must(fields, "block_definitions");
        BlocksFromSymbolsFactory factory = loadBlocksFactory(blockDefsPath);

        int startX = Integer.parseInt(must(fields, "blocks_start_x"));
        int startY = Integer.parseInt(must(fields, "blocks_start_y"));
        int rowHeight = Integer.parseInt(must(fields, "row_height"));
        int numBlocks = Integer.parseInt(must(fields, "num_blocks"));

        return new LevelFromFile(levelName, vels, bg, paddleSpeed, paddleWidth,
                startX, startY, rowHeight, numBlocks, blocksLayout, factory);
    }

    private static List<Velocity> parseVelocities(String s) {
        // space-separated items a,s :contentReference[oaicite:10]{index=10}
        List<Velocity> out = new ArrayList<>();
        for (String item : s.split("\\s+")) {
            String[] parts = item.split(",");
            double angle = Double.parseDouble(parts[0].trim());
            double speed = Double.parseDouble(parts[1].trim());
            out.add(Velocity.fromAngleAndSpeed(angle, speed));
        }
        return out;
    }

    private static Sprite parseBackground(String s) {
        // same format as fill: color(...) or image(...) :contentReference[oaicite:11]{index=11}
        ColorsParser cp = new ColorsParser();
        if (s.startsWith("color(")) {
            Color c = cp.colorFromString(s);
            return new ColorBackground(c);
        }
        if (s.startsWith("image(") && s.endsWith(")")) {
            String path = s.substring("image(".length(), s.length() - 1);
            Image img = loadImageFromClasspath(path);
            return new ImageBackground(img);
        }
        throw new RuntimeException("Bad background: " + s);
    }

    private static BlocksFromSymbolsFactory loadBlocksFactory(String path) {
        // relative to classpath :contentReference[oaicite:12]{index=12}
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        if (is == null) throw new RuntimeException("Block definitions not found on classpath: " + path);
        return BlocksDefinitionReader.fromReader(new InputStreamReader(is));
    }

    private static Image loadImageFromClasspath(String path) {
        try (InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new RuntimeException("Image not found on classpath: " + path);
            return ImageIO.read(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String must(Map<String, String> m, String k) {
        String v = m.get(k);
        if (v == null) throw new RuntimeException("Missing field: " + k);
        return v;
    }

    @Override
    public int numberOfBalls() { return velocities.size(); }

    @Override
    public List<Velocity> initialBallVelocities() { return velocities; }

    @Override
    public int paddleSpeed() { return paddleSpeed; }

    @Override
    public int paddleWidth() { return paddleWidth; }

    @Override
    public String levelName() { return name; }

    @Override
    public Sprite getBackground() { return background; }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        int y = blocksStartY;

        for (String row : layoutLines) {
            int x = blocksStartX;
            for (int i = 0; i < row.length(); i++) {
                String sym = String.valueOf(row.charAt(i));

                if (factory.isSpaceSymbol(sym)) {
                    x += factory.getSpaceWidth(sym);
                } else if (factory.isBlockSymbol(sym)) {
                    Block b = factory.getBlock(sym, x, y);
                    blocks.add(b);
                    x += (int) b.getCollisionRectangle().getWidth();
                } else {
                    throw new RuntimeException("Unknown symbol in blocks layout: " + sym);
                }
            }
            y += rowHeight;
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() { return numBlocksToRemove; }

    // ----- simple background sprites -----
    private static class ColorBackground implements Sprite {
        private final Color c;
        ColorBackground(Color c) { this.c = c; }
        public void drawOn(DrawSurface d) {
            d.setColor(c);
            d.fillRectangle(0, 0, 800, 600);
        }
        public void timePassed() {}
    }

    private static class ImageBackground implements Sprite {
        private final Image img;
        ImageBackground(Image img) { this.img = img; }
        public void drawOn(DrawSurface d) {
            d.drawImage(0, 0, img);
        }
        public void timePassed() {}
    }
}
