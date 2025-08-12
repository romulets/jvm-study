package datastructures.tree;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AVLTreePrint {

    /**
     * O(n) not optimized at all, and ugly. Just aids development
     */
    static public <T> String treeDiagram(AVLTree<T> tree) {
        if (tree == null) {
            return "";
        }

        String thisDiagram = tree.toString();
        if (tree.isLeaf()) {
            return thisDiagram; // early return if there are no children
        }

        String leftDiagram = treeDiagram(tree.left());
        leftDiagram = Arrays.stream(leftDiagram.split("\\n"))
                .map(l -> {
                    if (!l.isEmpty() && l.charAt(0) == '<') {
                        return "├─ " + l + " # left";
                    }
                    return "├─ " + l;
                })
                .collect(Collectors.joining("\n"));

        String rightDiagram = treeDiagram(tree.right());
        rightDiagram = Arrays.stream(rightDiagram.split("\\n"))
                .map(l -> {
                    if (!l.isEmpty() && l.charAt(0) == '<') {
                        return "├─ " + l + " # right";
                    }
                    return "├─ " + l;
                })
                .collect(Collectors.joining("\n"));

        StringBuilder builder = new StringBuilder(
                tree.size() + (leftDiagram.length()) + (rightDiagram.length())
        );

        builder.append(thisDiagram);
        builder.append("\n");
        if (tree.left() != null) {
            builder.append(leftDiagram);
            builder.append("\n");
        }
        if (tree.right() != null) {
            builder.append(rightDiagram);
            builder.append("\n");
        }

        return builder.toString();
    }

    static public <T> void printTree(AVLTree<T> tree) {
        System.out.println(treeDiagram(tree));
    }
}
