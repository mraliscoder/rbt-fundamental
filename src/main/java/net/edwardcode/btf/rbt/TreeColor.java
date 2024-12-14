package net.edwardcode.btf.rbt;

public enum TreeColor {
    /**
     * This node is red
     */
    RED(true),
    /**
     * This node is black
     */
    BLACK(false); // NEGRO (Spanish you know :)?)

    /**
     * If the node is red
     */
    private final boolean isRed;

    /**
     * Initialize object
     * @param i is node is red
     */
    TreeColor(boolean i) {
        this.isRed = i;
    }

    /**
     * Get if color is red
     * @return true if red
     */
    public boolean isRed() {
        return this.isRed;
    }

    /**
     * Convert 'is-red' format into TreeColor object
     * @param isRed is red?
     * @return color
     */
    public static TreeColor getTreeColor(boolean isRed) {
        return isRed ? RED : BLACK;
    }
}
