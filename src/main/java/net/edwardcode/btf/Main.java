package net.edwardcode.btf;

import net.edwardcode.btf.rbt.Tree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InvalidKeyException {
        File inputFile = new File("input.txt");

        if (!inputFile.exists()) {
            System.err.println("Sorry, file input.txt not exists. Please create it first!");
            System.exit(1);
            return;
        }

        List<String> fileLines;
        try {
            fileLines = Files.readAllLines(inputFile.toPath());
        } catch (IOException e) {
            System.err.println("Failed to read file:");
            e.printStackTrace();
            System.exit(1);
            return;
        }

        List<Key> initialKeys = new LinkedList<>();
        int lineNumber = 0;
        for (String line : fileLines) {
            line = line.toUpperCase();
            lineNumber++;
            Key key;
            try {
                key = Key.parseKey(line);
            } catch (InvalidKeyException e) {
                System.err.printf("WARN: In line %d: value is not a valid key: %s\n", lineNumber, line);
                continue;
            }
            initialKeys.add(key);
        }

        if (initialKeys.isEmpty()) {
            System.err.println("WARN: Continuing with empty initial items");
        }

        Tree tree = new Tree(initialKeys);
        tree.deleteAllTree();
    }
}
