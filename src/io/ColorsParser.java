package io;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorsParser {
    private static final Map<String, Color> NAMED = new HashMap<>();
    static {
        NAMED.put("black", Color.BLACK);
        NAMED.put("blue", Color.BLUE);
        NAMED.put("cyan", Color.CYAN);
        NAMED.put("gray", Color.GRAY);
        NAMED.put("lightGray", Color.LIGHT_GRAY);
        NAMED.put("green", Color.GREEN);
        NAMED.put("orange", Color.ORANGE);
        NAMED.put("pink", Color.PINK);
        NAMED.put("red", Color.RED);
        NAMED.put("white", Color.WHITE);
        NAMED.put("yellow", Color.YELLOW);
    }

    public Color colorFromString(String s) {
        // s like: color(red) OR color(RGB(10,20,30))
        if (!s.startsWith("color(") || !s.endsWith(")")) {
            throw new IllegalArgumentException("Bad color: " + s);
        }
        String inner = s.substring("color(".length(), s.length() - 1);

        if (inner.startsWith("RGB(") && inner.endsWith(")")) {
            String rgb = inner.substring("RGB(".length(), inner.length() - 1);
            String[] parts = rgb.split(",");
            int r = Integer.parseInt(parts[0].trim());
            int g = Integer.parseInt(parts[1].trim());
            int b = Integer.parseInt(parts[2].trim());
            return new Color(r, g, b);
        }

        Color c = NAMED.get(inner);
        if (c == null) throw new IllegalArgumentException("Unknown color name: " + inner);
        return c;
    }
}
