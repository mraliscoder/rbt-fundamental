package net.edwardcode.btf.rbt;

import net.edwardcode.btf.Key;

/**
 * Describes tree node element
 */
public class TreeNode {
    private TreeNode left = null, right = null, parent;
    private TreeColor color = TreeColor.RED;
    private final Key value;

    public TreeNode(Key value, TreeNode parent) {
        this.value = value;
        this.parent = parent;
    }

    /**
     * Set node color
     * @param color red or black
     */
    public void setColor(TreeColor color) {
        this.color = color;
    }

    /**
     * Get color of node
     * @return red or black
     */
    public TreeColor getColor() {
        return this.color;
    }

    /**
     * Get value of this element
     * @return value
     */
    public Key getValue() {
        return value;
    }

    public TreeNode getLeft() {
        return this.left;
    }
    public TreeNode getRight() {
        return this.right;
    }
    public TreeNode getParent() {
        return this.parent;
    }
    public void setLeft(TreeNode left) {
        this.left = left;
    }
    public void setRight(TreeNode right) {
        this.right = right;
    }
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getUncle() {
        if (this.parent != null && this.parent.parent != null) {
            TreeNode grand = this.parent.parent;
            if (grand.left == this.parent) {
                return grand.right;
            } else {
                return grand.left;
            }
        }
        return null;
    }
    public TreeNode getBrother() {
        if (this.parent != null) {
            if (this.parent.left == this) {
                return this.parent.right;
            } else {
                return this.parent.left;
            }
        }
        return null;
    }
    public TreeNode getGrand() {
        if (this.parent == null) return null;
        return this.parent.parent;
    }

    public void inverseColor() {
        if (this.color == TreeColor.RED) {
            this.color = TreeColor.BLACK;
        } else {
            this.color = TreeColor.RED;
        }
    }

    /**
     * It's easier to explain using this img:
     * <br />
     * <img alt="No child?" src="https://dpud.net/img/memes-rbt/no-child.jpg" />
     * @return true if left and right children are null
     */
    public boolean noChild() {
        return left == null && right == null;
    }
}
