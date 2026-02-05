package menu;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

public class MenuAnimation<T> implements Menu<T> {
    private static class Selection<T> {
        String keys, message;
        T value;
        Selection(String keys, String message, T value) {
            this.keys = keys; this.message = message; this.value = value;
        }
    }

    private final String title;
    private final KeyboardSensor keyboard;
    private final List<Selection<T>> selections = new ArrayList<>();

    private Sprite background;
    private boolean stop = false;
    private T status = null;
    private boolean anyKeyWasPressed = true; // prevent immediate trigger

    public MenuAnimation(String title, KeyboardSensor keyboard) {
        this.title = title;
        this.keyboard = keyboard;
    }

    public void setBackground(Sprite background) {
        this.background = background;
    }

    @Override
    public void addSelection(String keys, String message, T returnVal) {
        // keys can be like: "s|S|ד"
        selections.add(new Selection<>(keys, message, returnVal));
    }

    @Override
    public T getStatus() {
        T ans = status;
        status = null;
        stop = false;
        anyKeyWasPressed = true;
        return ans;
    }

    private boolean isAnyKeyPressed(String keys) {
        for (String k : keys.split("\\|")) {
            if (!k.isEmpty() && keyboard.isPressed(k)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (background != null) {
            background.drawOn(d);
        }

        d.drawText(120, 120, title, 42);

        int y = 220;
        for (Selection<T> s : selections) {
            d.drawText(140, y, "(" + s.keys + ") " + s.message, 28);
            y += 40;
        }

        boolean pressedSomething = false;
        for (Selection<T> s : selections) {
            if (isAnyKeyPressed(s.keys)) {
                pressedSomething = true;
                if (!anyKeyWasPressed) {
                    status = s.value;
                    stop = true;
                }
            }
        }
        if (!pressedSomething) {
            anyKeyWasPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}
