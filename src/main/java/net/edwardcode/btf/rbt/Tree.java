package net.edwardcode.btf.rbt;

import net.edwardcode.btf.Key;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    /**
     * Root element of the tree
     */
    private TreeNode root = null;

    /**
     * Initialize tree with initial elements.
     * These elements will be added to tree automatically
     * @param initialLines array of initial elements
     */
    public Tree(List<Key> initialLines) throws ElementExistsException {
        for (Key key : initialLines) {
            addElement(key);
        }

        System.out.println(preOrder());
    }

    public ArrayList<Key> preOrder() {
        if (root == null) {
            return null;
        }
        ArrayList<Key> result = new ArrayList<>();
        preOrder(root, result);
        return result;
    }

    private void preOrder(TreeNode root, ArrayList<Key> stack) {
        if (root != null) {
            preOrder(root.getLeft(), stack);
            stack.add(root.getValue()); //todo change to preorder from inorder
            preOrder(root.getRight(), stack);
        }
    }

    public void addElement(Key element) throws ElementExistsException {
        if (root == null) {
            root = new TreeNode(element, null);
            root.setColor(TreeColor.BLACK);
            return;
        }
        TreeNode current = root;
        while (true) {
            int compareValue = Key.compare(element, current.getValue());
            if (compareValue == 0) {
                throw new ElementExistsException(element);
            }
            if (compareValue < 0) {
                if (current.getLeft() == null) {
                    insertElement(element, current, true);
                    break;
                }
                current = current.getLeft();
            } else {
                if (current.getRight() == null) {
                    insertElement(element, current, false);
                    break;
                }
                current = current.getRight();
            }
        }
    }
    private void insertElement(Key element, TreeNode parent, boolean left) {
        TreeNode newNode = new TreeNode(element, parent);
        if (left) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        // Start balancing!
        TreeNode current = newNode;
        while (current.getParent().getColor().isRed()) {
            if (current.getUncle() != null && current.getUncle().getColor() == TreeColor.RED) {
                // (1)
                current.getParent().inverseColor();
                current.getGrand().inverseColor();
                current.getUncle().inverseColor();
                current = current.getGrand();
            } else {
                if ((current == current.getParent().getRight() && current.getParent() == current.getGrand().getLeft())
                    || (current == current.getParent().getLeft() && current.getParent() == current.getGrand().getRight())) {

                    TreeNode oldParent = current.getParent();
                    current.setParent(current.getGrand());
                    if (current == current.getParent().getRight() && current.getParent() == current.getGrand().getLeft()) {
                        // (2) when we're right child of parent
                        oldParent.getParent().setLeft(current);
                        if (current.getLeft() != null) {
                            current.getLeft().setParent(oldParent);
                            oldParent.setRight(current.getLeft());
                        }
                        oldParent.setParent(current);
                        current.setLeft(oldParent);
                        current = current.getLeft();
                    } else {
                        // (2) when we're left child of parent
                        oldParent.getParent().setRight(current);
                        if (current.getRight() != null) {
                            current.getRight().setParent(oldParent);
                            oldParent.setLeft(current.getRight());
                        }
                        oldParent.setParent(current);
                        current.setRight(oldParent);
                        current = current.getRight();
                    }
                }

                // (3)
                current.getParent().inverseColor();
                current.getGrand().inverseColor();
                if (current.getGrand() == root) {
                    root = current.getParent();
                    current.getParent().setParent(null);
                } else {
                    // We have a grand-grand-parent
                    if (current.getGrand().getParent().getLeft() == current.getGrand()) {
                        current.getGrand().getParent().setLeft(current.getParent());
                    } else {
                        current.getParent().getParent().getParent().setRight(current.getParent());
                    }
                    current.getParent().setParent(current.getParent().getParent().getParent());
                }
                current.getParent().getParent().setParent(current.getParent());
                if (current == current.getParent().getLeft()) {
                    // (3) We're left child
                } else {
                    // (3) We're right child
                }
            }
        }
    }
}
