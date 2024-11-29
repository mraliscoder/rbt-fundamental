package net.edwardcode.btf;

import java.util.regex.Pattern;

/**
 * Some useful utilities
 */
public class Utils {
    /**
     * Pattern to describe group data<br/>
     * Б - Бакалавриат<br/>
     * С - Специалитет<br/>
     * М - Магистратура
     */
    private static final Pattern GROUP_PATTERN = Pattern.compile("([БСМ])([0-9]{4})");

    /**
     * Validate if group name is correctly typed
     * @param groupName group name
     * @return true if valid and false if invalid
     */
    public static boolean validateName(String groupName) {
        return GROUP_PATTERN.matcher(groupName).matches();
    }
}
