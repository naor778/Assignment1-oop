package io;

import collidables.Block;

public interface BlockCreator {
    Block create(int xpos, int ypos);
}
