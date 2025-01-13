package net.edwardcode.btf;

/**
 * Class that represents group type
 */
public enum GroupEducationType {
    /**
     * Бакалавриат
     */
    BACHELOR('Б'),
    /**
     * Специалитет
     */
    SPECIALISATION('С'),
    /**
     * Магистратура
     */
    MAGISTRACY('М');

    /**
     * Char representation of group type
     */
    private final char type;

    /**
     * Initialize a Group education type object
     * @param type obj
     */
    GroupEducationType(char type) {
        this.type = type;
    }

    /**
     * Get group type in char format
     * @return char format of group type
     */
    public char getType() {
        return this.type;
    }

    /**
     * Convert char-type of group type into enum
     * @param type char-type
     * @return Group education type object
     */
    public static GroupEducationType getType(char type) {
        return switch (type) {
            case 'Б' -> BACHELOR;
            case 'С' -> SPECIALISATION;
            case 'М' -> MAGISTRACY;
            default -> null;
        };
    }
}
