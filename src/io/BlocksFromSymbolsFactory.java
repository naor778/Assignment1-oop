package io;

import collidables.Block;
import java.util.Map;

public class BlocksFromSymbolsFactory {
    private final Map<String, Integer> spacerWidths;
    private final Map<String, BlockCreator> blockCreators;

    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }

    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }

    public Block getBlock(String s, int xpos, int ypos) {
        return blockCreators.get(s).create(xpos, ypos);
    }

    public int getSpaceWidth(String s) {
        return spacerWidths.get(s);
    }
}
