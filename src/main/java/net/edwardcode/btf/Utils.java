package net.edwardcode.btf;

import net.edwardcode.btf.rbt.TreeNode;

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


    // TEMPORARY, DELETE AFTER WORK FINISHING todo
    public static String returnTextWithColor(TreeNode node) {
        if (node == null || node.getValue() == null) return " null";
        if (node.getColor().isRed()) return "\u001B[31m" + node.getValue() + "\u001B[0m";
        return node.getValue().toString();
    }
    public static void printElement(String prefix, TreeNode node, boolean isLeft) {
        if (node != null) {
            printElement(prefix + "|   ", node.getRight(), false);
            System.out.print(prefix);
            System.out.print(isLeft ? "|--" : "+--");
            System.out.println(returnTextWithColor(node));

            printElement(prefix + "|   ", node.getLeft(), true);
        }
    }
}
