package net.edwardcode.btf.rbt;

import net.edwardcode.btf.Key;
import net.edwardcode.btf.Utils;
import net.edwardcode.btf.list.LinkList;

/**
 * Describes tree node element
 */
public class TreeNode {
    /**
     * Left child of this element
     */
    private TreeNode left = null;
    /**
     * Right child of this element
     */
    private TreeNode right = null;
    /**
     * Parent of this element
     */
    private TreeNode parent = null;
    /**
     * Color of this element
     */
    private TreeColor color = TreeColor.RED;
    /**
     * Value of this element
     */
    private Key value;
    /**
     * Line numbers in the file with specified value
     */
    private LinkList lines = new LinkList();

    /**
     * Create new node
     * @param value value of this node
     * @param parent parent of this node
     */
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

    /**
     * Get left child of the element
     * @return left child (may produce null)
     */
    public TreeNode getLeft() {
        return this.left;
    }

    /**
     * Get right child of the element
     * @return right child (may produce null)
     */
    public TreeNode getRight() {
        return this.right;
    }

    /**
     * Get parent of the element
     * @return parent (will be null if root)
     */
    public TreeNode getParent() {
        return this.parent;
    }

    /**
     * Change left child of the element
     * @param left new left child
     */
    public void setLeft(TreeNode left) {
        this.left = left;
    }

    /**
     * Change right child of the element
     * @param right new right child
     */
    public void setRight(TreeNode right) {
        this.right = right;
    }

    /**
     * Change parent of the element
     * @param parent new parent
     */
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    /**
     * Get uncle of specified element if exists
     * @return uncle if exists, otherwise null
     */
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

    /**
     * Get brother of specified element if exists
     * @return brother if exists, otherwise null
     */
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

    /**
     * Get grandparent of the element if it exists
     * @return grandparent if exists, otherwise null
     */
    public TreeNode getGrand() {
        if (this.parent == null) return null;
        return this.parent.parent;
    }

    /**
     * Get stored line numbers
     * @return stored line numbers
     */
    public LinkList getLines() {
        return this.lines;
    }

    /**
     * Set line numbers
     * @param lines new line numbers
     */
    public void setLines(LinkList lines) {
        this.lines = lines;
    }

    /**
     * Inverse node color. If it was red - it will become black and vise-versa
     */
    public void inverseColor() {
        if (this.color == TreeColor.RED) {
            this.color = TreeColor.BLACK;
        } else {
            this.color = TreeColor.RED;
        }
    }

    /**
     * Add new line number to the line numbers list
     * @param value line number
     */
    public void addLineRow(int value) {
        lines.insertElement(value);
    }

    /**
     * Remove line number from the line numbers list
     * @param value line number
     */
    public void deleteLineRow(int value) {
        lines.deleteElement(value);
    }

    /**
     * Check if line numbers list contains specified line number
     * @param lineRow line number
     * @return true if exists, false otherwise
     */
    public boolean hasLine(int lineRow) {
        return lines.findElement(lineRow);
    }

    /**
     * Check if line numbers list contains more than one element
     * @return true if contains more than one element, false otherwise
     */
    public boolean hasMoreThanOneLine() {
        return lines.hasMoreThanOneElement();
    }

    /**
     * Change value of the node
     * @param value new value
     */
    public void setValue(Key value) {
        this.value = value;
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

    /**
     * Check if element has only one child
     * @return true if it has only one child, else otherwise
     */
    public boolean onlyOneChild() {
        return (left == null && right != null) || (left != null && right == null);
    }

    /**
     * Check if element has only negro children
     * @return true if only negro children, else otherwise
     */
    public boolean hasOnlyBlackChild() {
        return (left == null || !left.getColor().isRed()) && (right == null || !right.getColor().isRed());
    }

    @Override
    public String toString() {
        return value != null ? Utils.returnTextWithColor(this) : "--NULL--";
    }
}
