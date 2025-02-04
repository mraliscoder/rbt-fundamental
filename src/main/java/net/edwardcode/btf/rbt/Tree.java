package net.edwardcode.btf.rbt;

import net.edwardcode.btf.Key;
import net.edwardcode.btf.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of red-black tree<br />
 * Contains all needed functional to work with it
 */
public class Tree {
    /**
     * Root element of the tree
     */
    private TreeNode root = null;

    /**
     * Just initialize a tree, with no elements at all
     */
    public Tree() {}

    /**
     * Initialize tree with initial elements.
     * These elements will be added to tree automatically
     * @param initialLines array of initial elements
     */
    public Tree(List<Key> initialLines) {
        int i = 1;
        for (Key key : initialLines) {
            addElement(key, i);
            i++;
        }
    }

    /**
     * Perform pre-order of the tree
     * @return array list of keys in pre-order
     */
    public ArrayList<Key> preOrder() {
        if (root == null) {
            return null;
        }
        ArrayList<Key> result = new ArrayList<>();
        preOrder(root, result);
        return result;
    }

    /**
     * Internal recursive function to perform pre-order
     * @param root current element
     * @param stack already gathered elements
     */
    private void preOrder(TreeNode root, ArrayList<Key> stack) {
        if (root != null) {
            stack.add(root.getValue());
            preOrder(root.getLeft(), stack);
            preOrder(root.getRight(), stack);
        }
    }

    /**
     * Search for element in the tree
     * @param element element value we are looking for
     * @return {@link net.edwardcode.btf.rbt.TreeNode} if found, else null
     */
    public TreeNode searchElement(Key element) {
        if (root == null) return null;
        TreeNode current = root;
        while(true) {
            int compare = Key.compare(element, current.getValue());
            if (compare == 0) {
                return current;
            }
            if (compare < 0) {
                if (current.getLeft() == null) return null;
                current = current.getLeft();
            }
            if (compare > 0) {
                if (current.getRight() == null) return null;
                current = current.getRight();
            }
        }
    }

    /**
     * Add new element to the tree or add line number to the element if it already exists
     * @param element new value to add
     * @param lineNumber line number in the file that needs to be added to this element
     */
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

    /**
     * Delete element if it exists and has specified line number
     * @param element value to delete
     * @param lineNumber line number to delete
     * @return true if successfully deleted, false otherwise
     */
    public boolean deleteElement(Key element, int lineNumber) {
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

    /**
     * Internal function to insert element at needed position in the tree on element addition and balance it
     * @param element new element to add
     * @param parent parent of the new element
     * @param left is we adding new element to the left of the parent or not
     * @param lineNumber line number of the new element
     */
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
                    if (current == oldParent.getRight() && oldParent == oldParent.getParent().getLeft()) {
                        // (2) when we're right child of parent
                        oldParent.getParent().setLeft(current);
                        oldParent.setRight(null);
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
                        oldParent.setLeft(null);
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

    /**
     * Internal function to check element deletion:<br />
     * a. Is element has no child?<br />
     * b. Is element has one child?<br />
     * c. Is element has both children?
     * @param element already found element that we will delete
     * @param lineNumber line number of deleting element
     * @return true if element successfully deleted, false otherwise
     */
    private boolean deleteElement(TreeNode element, int lineNumber) {
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
            element.setValue(null);
            if (!color.isRed()) {
                balanceDeletion(element, true);
            } else {
                if (element.getParent().getLeft() == element) {
                    element.getParent().setLeft(null);
                } else {
                    element.getParent().setRight(null);
                }
                element.setParent(null);
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
            if (element == root) {
                root = child;
            }
        } else {
            TreeNode minimal = element.getRight();
            while (minimal.getLeft() != null) {
                minimal = minimal.getLeft();
            }
            element.setValue(minimal.getValue());
            element.setLines(minimal.getLines());
            minimal.setValue(null);

            deleteElement(minimal, -1);
        }
        return true;
    }

    /**
     * Balance tree after element deletion
     * @param element element we're standing on before deletion
     * @param deleteAfterBalancing true if we need to delete element in the element param after processing balance
     */
    private void balanceDeletion(TreeNode element, boolean deleteAfterBalancing) {
        TreeNode current = element;
        while (!current.getColor().isRed() && current != root) {
            if (current.getBrother() != null && current.getBrother().getColor().isRed()) {
                // (1)
                current.getBrother().inverseColor();
                current.getParent().inverseColor();

                TreeNode brother = current.getBrother();
                TreeNode parent = current.getParent();
                TreeNode grand = current.getGrand();

                // Fix grandparent
                if (parent == root) {
                    root = current.getBrother();
                    brother.setParent(null);
                } else {
                    brother.setParent(grand);
                    if (grand.getLeft() == parent) {
                        grand.setLeft(brother);
                    } else {
                        grand.setRight(brother);
                    }
                }
                parent.setParent(brother);

                if (parent.getLeft() == current) {
                    if (brother.getLeft() != null) {
                        parent.setRight(brother.getLeft());
                        brother.getLeft().setParent(parent);
                    } else {
                        parent.setRight(null);
                    }
                    brother.setLeft(parent);
                } else {
                    if (brother.getRight() != null) {
                        parent.setLeft(brother.getRight());
                        brother.getRight().setParent(parent);
                    } else {
                        parent.setRight(null);
                    }
                    brother.setRight(parent);
                }

            }
            if (current.getBrother() != null && current.getBrother().hasOnlyBlackChild()) {
                // (2)
                current.getBrother().inverseColor();
                current = current.getParent();
            } else {
                if ((current.getParent().getLeft() == current && current.getBrother() != null && (current.getBrother().getRight() == null || !current.getBrother().getRight().getColor().isRed()))
                    || (current.getParent().getRight() == current && current.getBrother() != null && (current.getBrother().getLeft() == null || !current.getBrother().getLeft().getColor().isRed()))) {
                    // (3)
                    current.getBrother().inverseColor();
                    if (current.getParent().getLeft() == current) {
                        current.getBrother().getLeft().inverseColor();
                    } else {
                        current.getBrother().getRight().inverseColor();
                    }

                    TreeNode brother = current.getBrother();
                    TreeNode parent = current.getParent();
                    TreeNode brotherInnerNode = (current.getParent().getLeft() == current)
                            ? brother.getLeft()
                            : brother.getRight();
                    TreeNode brotherInnerInnerNode = (current.getParent().getLeft() == current)
                            ? brotherInnerNode.getRight()
                            : brotherInnerNode.getLeft();

                    // If we have inner-inner-child of the brother, we need to fix relationships
                    if (brotherInnerInnerNode != null) {
                        brotherInnerInnerNode.setParent(brother);
                        if (brotherInnerInnerNode == brotherInnerNode.getRight()) {
                            brother.setLeft(brotherInnerInnerNode);
                            brotherInnerNode.setRight(null);
                        } else {
                            brother.setRight(brotherInnerInnerNode);
                            brotherInnerNode.setLeft(null);
                        }
                    } else {
                        if (brother == parent.getRight()) {
                            brother.setLeft(null);
                        } else {
                            brother.setRight(null);
                        }
                    }
                    brotherInnerNode.setParent(null);

                    // Now perform rotation
                    brother.setParent(brotherInnerNode);
                    if (current == parent.getLeft()) {
                        parent.setRight(brotherInnerNode);
                        brotherInnerNode.setRight(brother);
                    } else {
                        parent.setLeft(brotherInnerNode);
                        brotherInnerNode.setLeft(brother);
                    }
                }

                // (4)
                TreeNode brother = current.getBrother();
                TreeNode parent = current.getParent();
                TreeNode outerBrotherChild = (parent.getLeft() == current)
                        ? brother.getRight()
                        : brother.getLeft();
                TreeNode innerBrotherChild = (parent.getLeft() == current)
                        ? brother.getLeft()
                        : brother.getRight();

                boolean weWasLeft = parent.getLeft() == current;

                TreeColor oldBrotherColor = current.getBrother().getColor();
                brother.setColor(parent.getColor());
                parent.setColor(oldBrotherColor);
                outerBrotherChild.setColor(oldBrotherColor);

                // And now the funniest part that I hate
                if (parent == root) {
                    root = brother;
                    brother.setParent(null);
                } else {
                    brother.setParent(parent.getParent());
                    if (parent.getParent().getLeft() == parent) {
                        parent.getParent().setLeft(brother);
                    } else {
                        parent.getParent().setRight(brother);
                    }
                }
                parent.setParent(brother);

                if (innerBrotherChild != null) {
                    innerBrotherChild.setParent(parent);
                    if (weWasLeft) {
                        parent.setRight(innerBrotherChild);
                    } else {
                        parent.setLeft(innerBrotherChild);
                    }
                } else {
                    if (weWasLeft) {
                        parent.setRight(null);
                    } else {
                        parent.setLeft(null);
                    }
                }

                if (weWasLeft) {
                    brother.setLeft(parent);
                } else {
                    brother.setRight(parent);
                }
                current = root;
            }
        }
        current.setColor(TreeColor.BLACK);

        if (deleteAfterBalancing && element != root) {
            if (element.getParent().getLeft() == element) {
                element.getParent().setLeft(null);
            } else {
                element.getParent().setRight(null);
            }
            element.setParent(null);
        }
        if (element == root) {
            root = null;
        }
    }

    /**
     * Print tree in easy-to-read format
     */
    public void printTree() {
        TreePrinter.printTreeVertically(root);
    }

    /**
     * Clear memory (delete all elements of the tree)
     */
    public void deleteAllTree() {//
        deleteAllTree(root);
    }

    /**
     * Internal function to delete all tree in post-order
     * @param root current element we're standing on
     */
    private void deleteAllTree(TreeNode root) {
        if (root == null) return;
        deleteAllTree(root.getLeft());
        deleteAllTree(root.getRight());

        if (root.getParent() != null) {
            if (root.getParent().getLeft() == root) {
                root.getParent().setLeft(null);
            } else {
                root.getParent().setRight(null);
            }
            root.setParent(null);
        }
        root.setLeft(null);
        root.setRight(null);

        if (root == this.root) {
            this.root = null;
        }
    }
}
