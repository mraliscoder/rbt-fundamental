package net.edwardcode.btf;

/**
 * Exception thrown when entered key is not a valid group key
 */
public class InvalidKeyException extends Exception {
    /**
     * Throwing exception
     * @param s error description
     */
    public InvalidKeyException(String s) {
        super(s);
    }
}
