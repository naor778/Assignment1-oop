package io;

import collidables.Block;
import geometry.Point;
import geometry.Rectangle;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BlocksDefinitionReader {

    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        Map<String, String> defaults = new HashMap<>();
        Map<String, Integer> spacerWidths = new HashMap<>();
        Map<String, BlockCreator> blockCreators = new HashMap<>();
        ColorsParser colors = new ColorsParser();

        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\s+");
                String type = parts[0];

                if (type.equals("default")) {
                    defaults.putAll(parseProps(parts, 1));
                } else if (type.equals("sdef")) {
                    Map<String, String> p = parseProps(parts, 1);
                    String sym = must1(p, "symbol");
                    int w = Integer.parseInt(must(p, "width"));
                    spacerWidths.put(sym, w);
                } else if (type.equals("bdef")) {
                    Map<String, String> p = parseProps(parts, 1);
                    // inherit defaults
                    for (Map.Entry<String, String> e : defaults.entrySet()) {
                        p.putIfAbsent(e.getKey(), e.getValue());
                    }

                    String sym = must1(p, "symbol");
                    int w = Integer.parseInt(must(p, "width"));
                    int h = Integer.parseInt(must(p, "height"));
                    String fill = must(p, "fill");
                    String stroke = p.get("stroke");

                    Color strokeColor = (stroke == null) ? null : colors.colorFromString(stroke);

                    BlockCreator creator = (x, y) -> {
                        Rectangle rect = new Rectangle(new Point(x, y), w, h);
                        if (fill.startsWith("color(")) {
                            return new Block(rect, colors.colorFromString(fill), strokeColor);
                        }
                        if (fill.startsWith("image(") && fill.endsWith(")")) {
                            String path = fill.substring("image(".length(), fill.length() - 1);
                            Image img = loadImageFromClasspath(path);
                            return new Block(rect, img, strokeColor);
                        }
                        throw new RuntimeException("Bad fill: " + fill);
                    };

                    blockCreators.put(sym, creator);
                } else {
                    throw new RuntimeException("Unknown line type: " + type);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    private static Map<String, String> parseProps(String[] parts, int start) {
        Map<String, String> map = new HashMap<>();
        for (int i = start; i < parts.length; i++) {
            int idx = parts[i].indexOf(':');
            if (idx <= 0) continue;
            map.put(parts[i].substring(0, idx), parts[i].substring(idx + 1));
        }
        return map;
    }

    private static String must(Map<String, String> p, String key) {
        String v = p.get(key);
        if (v == null) throw new RuntimeException("Missing property: " + key);
        return v;
    }

    private static String must1(Map<String, String> p, String key) {
        String v = must(p, key);
        if (v.length() != 1) throw new RuntimeException(key + " must be a single char, got: " + v);
        return v;
    }

    private static Image loadImageFromClasspath(String path) {
        try (InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new RuntimeException("Image not found on classpath: " + path);
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
