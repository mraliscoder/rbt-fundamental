package net.edwardcode.btf.rbt;

import net.edwardcode.btf.ElementExistsException;
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
        int i = 1;
        for (Key key : initialLines) {
            addElement(key, i);
            i++;
        }

        preOrder();
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
            stack.add(root.getValue()); //todo change to preorder from inorder
            System.out.print(root.getValue() + " | ");
            root.printLineRows();
            preOrder(root.getLeft(), stack);
            preOrder(root.getRight(), stack);
        }
    }

    public void addElement(Key element, int lineNumber) {
        if (root == null) {
            root = new TreeNode(element, null);
            root.addLineRow(lineNumber);
            root.setColor(TreeColor.BLACK);
            return;
        }
        TreeNode current = root;
        while (true) {
            int compareValue = Key.compare(element, current.getValue());
            if (compareValue == 0) {
                current.addLineRow(lineNumber);
                return;
            } else if (compareValue < 0) {
                if (current.getLeft() == null) {
                    insertElement(element, current, true, lineNumber);
                    break;
                }
                current = current.getLeft();
            } else {
                if (current.getRight() == null) {
                    insertElement(element, current, false, lineNumber);
                    break;
                }
                current = current.getRight();
            }
        }
    }
    public boolean deleteElement(Key element, int lineNumber) {
        System.out.println("Deleting " + element + " on row " + lineNumber);
        TreeNode current = root;
        while (true) {
            if (Key.compare(element, current.getValue()) == 0) {
                // ok we have found it, deleting
                return deleteElement(current, lineNumber);
            } else if (current.noChild()) {
                // we have not found anything.
                return false;
            } else if (Key.compare(element, current.getValue()) < 0) {
                if (current.getLeft() == null) {
                    return false;
                }
                current = current.getLeft();
            } else {
                if (current.getRight() == null) {
                    return false;
                }
                current = current.getRight();
            }
        }
    }

    private void insertElement(Key element, TreeNode parent, boolean left, int lineNumber) {
        TreeNode newNode = new TreeNode(element, parent);
        newNode.addLineRow(lineNumber);
        if (left) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        // Start balancing!
        TreeNode current = newNode;
        while (current.getParent() != null && current.getParent().getColor().isRed()) {
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
                        oldParent.setRight(null);
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
                        oldParent.setLeft(null);
                        current.setRight(oldParent);
                        current = current.getRight();
                    }
                }

                // (3)
                current.getParent().inverseColor();
                current.getGrand().inverseColor();

                TreeNode grandParent = current.getGrand();

                if (grandParent == root) {
                    root = current.getParent();
                    current.getParent().setParent(null); // verified here
                } else {
                    // We have a grand-grand-parent
                    if (grandParent.getParent().getLeft() == grandParent) {
                        grandParent.getParent().setLeft(current.getParent());
                    } else {
                        grandParent.getParent().setRight(current.getParent());
                    }
                    current.getParent().setParent(grandParent.getParent());
                    grandParent.setParent(null);
                }
                if (grandParent.getLeft() == current.getParent()) {
                    grandParent.setLeft(null);
                } else {
                    grandParent.setRight(null);
                }
                grandParent.setParent(current.getParent());

                TreeNode brother = current.getBrother();
                if (brother != null) {
                    brother.setParent(grandParent);
                }

                if (current == current.getParent().getLeft()) {
                    // (3) We're left child
                    current.getParent().setRight(grandParent);
                    if (brother != null) {
                        grandParent.setLeft(brother);
                    }
                } else {
                    // (3) We're right child
                    current.getParent().setLeft(grandParent);
                    if (brother != null) {
                        grandParent.setRight(brother);
                    }
                }
            }
        }
        root.setColor(TreeColor.BLACK);
    }

    private boolean deleteElement(TreeNode element, int lineNumber) {
        System.out.println("Start element " + element + " deletion on row " + lineNumber + ", parent is " + element.getParent());

        if (lineNumber != -1) {
            if (!element.hasLine(lineNumber))
                return false;

            if (element.hasMoreThanOneLine()) {
                element.deleteLineRow(lineNumber);
                return true;
            }
        }
        // Now we go deeper
        if (element.noChild()) {
            TreeColor color = element.getColor();
            TreeNode deletedElement = new TreeNode(null, element.getParent());
            if (element.getParent() != null) {
                if (element.getParent().getLeft() == element) {
                    element.getParent().setLeft(deletedElement);
                } else {
                    element.getParent().setRight(deletedElement);
                }
            }
            element.setParent(null);

            if (!color.isRed()) {
                balanceDeletion(deletedElement, true);
            }
            return true;
        } else if (element.onlyOneChild()) {
            TreeColor deletionColor = element.getColor();
            TreeNode child;
            if (element.getLeft() != null) {
                child = element.getLeft();
                child.setParent(element.getParent());

                element.getLeft().setParent(element.getParent());
            } else {
                child = element.getRight();
                child.setParent(element.getParent());

                element.getRight().setParent(element.getParent());
            }
            if (element.getParent() != null) {
                if (element.getParent().getLeft() == element) {
                    element.getParent().setLeft(child);
                } else {
                    element.getParent().setRight(child);
                }
            }
            element.setParent(null);
            TreeColor replacementColor = child.getColor();
            child.setColor(deletionColor);
            if (!replacementColor.isRed()) {
                balanceDeletion(child, false);
            }
        } else {
            TreeNode minimal = element.getRight();
            while (minimal.getLeft() != null) {
                minimal = minimal.getLeft();
            }
            element.setValue(minimal.getValue());
            minimal.setValue(null);

            deleteElement(minimal, -1);
        }
        return true;
    }
    private void balanceDeletion(TreeNode element, boolean deleteAfterBalancing) {
        System.out.println("Balancing on element which has parent " + element.getParent() + ", after balancing I will " + (deleteAfterBalancing ? "" : "NOT ") + "delete this element");

        TreeNode current = element;
        while (!current.getColor().isRed() && current != root) {
            if (current.getBrother() != null && current.getBrother().getColor().isRed()) {
                // (1)
            }
            if (current.getBrother() != null && current.getBrother().hasOnlyBlackChild()) {
                // (2)
            } else {
                if ((current.getParent().getLeft() == current && current.getBrother() != null && (current.getBrother().getRight() == null || !current.getBrother().getRight().getColor().isRed()))
                    || (current.getParent().getRight() == current && current.getBrother() != null && (current.getBrother().getLeft() == null || !current.getBrother().getLeft().getColor().isRed()))) {
                    // (3)
                }
                // (4)
            }
        }
    }
}
