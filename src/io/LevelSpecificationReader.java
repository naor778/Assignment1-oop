package io;

import levels.LevelFromFile;
import levels.LevelInformation;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelSpecificationReader {
    public List<LevelInformation> fromReader(Reader reader) {
        List<String> lines = readUsefulLines(reader);
        List<LevelInformation> levels = new ArrayList<>();

        int i = 0;
        while (i < lines.size()) {
            if (!lines.get(i).equals("START_LEVEL")) { i++; continue; }

            List<String> one = new ArrayList<>();
            i++; // after START_LEVEL
            while (i < lines.size() && !lines.get(i).equals("END_LEVEL")) {
                one.add(lines.get(i));
                i++;
            }
            if (i >= lines.size()) throw new RuntimeException("Missing END_LEVEL");
            i++; // consume END_LEVEL

            levels.add(LevelFromFile.parse(one));
        }
        return levels;
    }

    private List<String> readUsefulLines(Reader reader) {
        try (BufferedReader br = new BufferedReader(reader)) {
            List<String> out = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // ignore comments/blanks :contentReference[oaicite:9]{index=9}
                out.add(line);
            }
            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
