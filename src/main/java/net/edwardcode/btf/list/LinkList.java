package net.edwardcode.btf.list;

public class LinkList {
    private LinkedListItem root = null;

    public LinkedListItem getRoot() {
        return root;
    }

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

    public void print() {
        if (root == null) {
            System.out.println("EMPTY");
            return;
        }
        LinkedListItem p = root;
        do {
            System.out.print(p.getValue());
            System.out.print(" ");
            p = p.getNext();
        } while (p != root);
        System.out.println();
    }

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

    public boolean hasMoreThanOneElement() {
        if (root == null) return false;
        return root.getNext() != root;
    }
}
