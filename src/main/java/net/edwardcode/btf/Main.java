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

        //// 1. Initialization ////
        // 1.1. Initialization with initial elements
        Tree tree = new Tree(initialKeys);
        // 1.2. Initialization without initial elements (empty tree)
        //Tree tree = new Tree();

        //// 2. New element creation
        tree.addElement(Key.parseKey("Б9123"), 1234);

        //// 3. Delete specified element
        tree.deleteElement(Key.parseKey("Б9123"), 1234);

        //// 4. Search for specified element
        System.out.println(tree.searchElement(Key.parseKey("Б9123")));

        //// 5. Print tree
        tree.printTree();

        //// 6. Pre-order tree
        System.out.println(tree.preOrder());

        //// 7. Remove entire tree
        tree.deleteAllTree();
    }
}
