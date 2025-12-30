package animations;

import biuoop.DrawSurface;

public interface Animation {
    void doOneFrame(DrawSurface d);
    boolean shouldStop();
}
