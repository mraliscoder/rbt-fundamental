package net.edwardcode.btf.rbt;

import net.edwardcode.btf.Key;

/**
 * Exception thrown when element already exists in the tree
 */
public class ElementExistsException extends Exception {
    /**
     * Element that caused this exception
     */
    private final Key element;

    public ElementExistsException(Key element) {
        this.element = element;
    }

    public Key getElement() {
        return element;
    }
}
