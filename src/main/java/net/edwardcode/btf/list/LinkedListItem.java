package net.edwardcode.btf.list;

public class LinkedListItem {
    private int value;
    private LinkedListItem next;

    public LinkedListItem(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public LinkedListItem getNext() {
        return next;
    }
    public void setNext(LinkedListItem next) {
        this.next = next;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
