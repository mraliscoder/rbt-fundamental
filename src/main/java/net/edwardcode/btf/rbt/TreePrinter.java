package net.edwardcode.btf.rbt;

import java.util.ArrayList;
import java.util.List;

public class TreePrinter {
    private static final String CH_DDIA = "┌";
    private static final String CH_RDDIA = "┐";
    private static final String CH_HOR = "-";
    private static final String CH_VER = "|";

    private static List<String> concat(List<String> a, List<String> b) {
        List<String> result = new ArrayList<>(a);
        result.addAll(b);
        return result;
    }

    private static void dump(TreeNode node, List<String> lpref, List<String> cpref, List<String> rpref, boolean root, boolean left, List<List<String>> lines) {
        if (node == null) return;
        if (lines == null && root) lines = new ArrayList<>();

        if (node.getLeft() != null) {
            dump(
                    node.getLeft(),
                    concat(lpref, List.of(" ")),
                    concat(lpref, List.of(CH_DDIA)),
                    concat(lpref, List.of(CH_HOR)),
                    false,
                    true,
                    lines
            );
        }

        String sval = node.getValue().toString();
        int sm = left || sval.isEmpty() ? sval.length() / 2 : (sval.length() + 1) / 2 - 1;

        for (int i = 0; i < sval.length(); i++) {
            lines.add(
                    concat(
                            i < sm ? lpref : i == sm ? cpref : rpref,
                            List.of(
                                    (node.getColor().isRed() ? "\u001B[31m" : "")
                                            + sval.charAt(i)
                                            + (node.getColor().isRed() ? "\u001B[0m" : "")
                            )
                    )
            );
        }

        if (node.getRight() != null) {
            dump(
                    node.getRight(),
                    concat(rpref, List.of(CH_HOR, " ")),
                    concat(rpref, List.of(CH_RDDIA, CH_VER)),
                    concat(rpref, List.of(" ", " ")),
                    false,
                    false,
                    lines
            );
        }

        if (root) {
            List<String> output = new ArrayList<>();

            for (int l = 0; ; l++) {
                boolean last = true;
                StringBuilder line = new StringBuilder();
                for (List<String> lineParts : lines) {
                    if (l < lineParts.size()) {
                        line.append(lineParts.get(l));
                        last = false;
                    } else {
                        line.append(" ");
                    }
                }

                if (last) break;
                output.add(line.toString());
            }

            for (String line : output) {
                System.out.println(line);
            }
        }
    }

    public static void printTreeVertically(TreeNode root) {
        dump(root, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), true, true, null);
    }
}
