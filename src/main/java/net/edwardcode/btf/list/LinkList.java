package net.edwardcode.btf.list;

/**
 * Class that represents linked circular list and defines
 * functions to work with it
 */
public class LinkList {
    /**
     * First (root) element of the linked list
     */
    private LinkedListItem root = null;

    /**
     * Add new element to the linking list
     * @param value new element
     */
    public void insertElement(int value) {
        LinkedListItem newNode = new LinkedListItem(value);

        // Oh, that was easy.
        if (root == null) {
            root = newNode;
            newNode.setNext(newNode);
            return;
        }

        // But now....
        LinkedListItem p = root;
        LinkedListItem prev = null;
        do {
            if (p.getValue() < value) {
                break;
            }
            prev = p;
            p = p.getNext();
        } while (p != root);

        if (prev == null) {
            newNode.setNext(root.getNext());
            root.setNext(newNode);
            newNode.setValue(root.getValue());
            root.setValue(value);
        } else {
            prev.setNext(newNode);
            newNode.setNext(p);
        }
    }

    /**
     * Search for element to delete and delete it
     * @param value value of the element to delete
     */
    public void deleteElement(int value) {
        if (root == null) return;

        boolean headDeleted = false;

        LinkedListItem oldHead = root;
        LinkedListItem current = root;
        LinkedListItem prev = null;
        do {
            if (current.getValue() == value) {
                if (current.getNext() == current) {
                    root = null;
                    return;
                }
                if (prev != null) {
                    prev.setNext(current.getNext());
                }
                if (current == root) {
                    headDeleted = true;
                    root = current.getNext();
                }

            } else {
                prev = current;
            }
            current = current.getNext();
        } while (current != oldHead);

        if (headDeleted && prev != null) {
            prev.setNext(root);
        }
    }

    /**
     * Check if linked list contains specified element
     * @param value element value to check
     * @return true if in list, else false
     */
    public boolean findElement(int value) {
        LinkedListItem p = root;
        do {
            if (p.getValue() == value) {
                return true;
            }
            p = p.getNext();
        } while (p != root);
        return false;
    }

    /**
     * Check if there's more than one element in the linked list
     * @return true if more than one element in the linked list, else false
     */
    public boolean hasMoreThanOneElement() {
        if (root == null) return false;
        return root.getNext() != root;
    }
}
