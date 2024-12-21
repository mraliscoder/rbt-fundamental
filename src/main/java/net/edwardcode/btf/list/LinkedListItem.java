package net.edwardcode.btf.list;

/**
 * Item in the linked list
 */
public class LinkedListItem {
    /**
     * Value of the item
     */
    private int value;
    /**
     * Next element of the linked list
     */
    private LinkedListItem next;

    /**
     * Create new element of the linked list<br />
     * Linking is not done automatically, you need to do this manually
     * @param value value of the item
     */
    public LinkedListItem(int value) {
        this.value = value;
    }

    /**
     * Get value of the item
     * @return value of the item
     */
    public int getValue() {
        return value;
    }

    /**
     * Get next element of the linked list
     * @return next element of the linked list
     */
    public LinkedListItem getNext() {
        return next;
    }

    /**
     * Change next element of the linked list
     * @param next new next element
     */
    public void setNext(LinkedListItem next) {
        this.next = next;
    }

    /**
     * Change value of the element
     * @param value new value
     */
    public void setValue(int value) {
        this.value = value;
    }
}
