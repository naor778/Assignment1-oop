package game;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighScoreFile {
    private static final String PREFIX = "The highest score so far is: ";
    private final File file;

    public HighScoreFile(String filename) {
        this.file = new File(filename); // working directory (folder game was run from)
    }

    public int getHighScore() {
        if (!file.exists()) {
            return 0;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = br.readLine();
            if (line == null) return 0;

            Matcher m = Pattern.compile("(\\d+)").matcher(line);
            return m.find() ? Integer.parseInt(m.group(1)) : 0;
        } catch (IOException e) {
            return 0;
        }
    }

    public void updateIfHigher(int score) {
        int best = getHighScore();
        if (!file.exists() || score > best) {
            write(score);
        }
    }

    private void write(int score) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            bw.write(PREFIX + score);
            bw.newLine();
        } catch (IOException ignored) {
        }
    }

    public String formatLine() {
        return PREFIX + getHighScore();
    }
}
