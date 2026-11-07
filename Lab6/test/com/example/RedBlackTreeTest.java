package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    private RedBlackTreeApp.RedBlackTree tree;

    @BeforeEach
    void setUp() {
        tree = new RedBlackTreeApp.RedBlackTree();
    }

    private RedBlackTreeApp.Node getRoot(RedBlackTreeApp.RedBlackTree targetTree) throws Exception {
        Field rootField = RedBlackTreeApp.RedBlackTree.class.getDeclaredField("root");
        rootField.setAccessible(true);
        return (RedBlackTreeApp.Node) rootField.get(targetTree);
    }

    @Test
    @DisplayName("Перевірка додавання елементів у довільному порядку")
    void testInsertRandomOrder() throws Exception {
        int[] elements = {10, 85, 15, 70, 20, 60, 30};
        for (int elem : elements) {
            tree.insert(elem);
        }

        RedBlackTreeApp.Node root = getRoot(tree);
        assertNotNull(root, "Корінь дерева не повинен бути null");

        assertEquals(RedBlackTreeApp.Color.BLACK, root.color, "Корінь дерева має бути ЧОРНИМ");
    }

    @Test
    @DisplayName("Перевірка додавання елементів у впорядкованому порядку (за зростанням)")
    void testInsertSortedOrder() throws Exception {
        int[] elements = {10, 20, 30, 40, 50, 60, 70, 80};
        for (int elem : elements) {
            tree.insert(elem);
        }

        RedBlackTreeApp.Node root = getRoot(tree);
        assertNotNull(root, "Корінь не повинен бути null");
        assertEquals(RedBlackTreeApp.Color.BLACK, root.color, "Корінь має залишатися ЧОРНИМ");

        assertNotEquals(10, root.data, "Дерево повинно збалансуватися (10 не має залишатися коренем)");
    }

    @Test
    @DisplayName("Перевірка центрованого обходу (In-Order traversal)")
    void testInOrderTraversal() {
        int[] elements = {40, 20, 60, 10, 30, 50, 70};
        for (int elem : elements) {
            tree.insert(elem);
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        tree.inOrder();

        System.setOut(originalOut);

        String output = outContent.toString().trim();

        assertTrue(output.startsWith("10("), "Першим елементом при центрованому обході має бути 10");
        assertTrue(output.contains("20("));
        assertTrue(output.contains("30("));
        assertTrue(output.contains("40("));
        assertTrue(output.contains("50("));
        assertTrue(output.contains("60("));
        assertTrue(output.endsWith(")"), "Вивід повинен закінчуватися останнім вузлом");
    }

    @Test
    @DisplayName("Перевірка ігнорування дублікатів")
    void testDuplicateInsertion() throws Exception {
        tree.insert(15);
        tree.insert(15);
        tree.insert(15);

        RedBlackTreeApp.Node root = getRoot(tree);
        assertNotNull(root);
        assertNull(root.left, "У вузла не повинно бути лівого нащадка при вставці дубліката");
        assertNull(root.right, "У вузла не повинно бути правого нащадка при вставці дубліката");
    }

    @Test
    @DisplayName("Перевірка очищення дерева")
    void testClearTree() throws Exception {
        tree.insert(10);
        tree.insert(20);
        tree.clear();

        RedBlackTreeApp.Node root = getRoot(tree);
        assertNull(root, "Після очищення корінь повинен бути null");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 50, 100})
    @DisplayName("Перевірка властивості RBT: у червоного вузла лише чорні нащадки")
    void testNoTwoConsecutiveRedNodes(int count) throws Exception {
        for (int i = 1; i <= count; i++) {
            tree.insert(i);
        }

        RedBlackTreeApp.Node root = getRoot(tree);
        validateRedNodeChildren(root);
    }

    private void validateRedNodeChildren(RedBlackTreeApp.Node node) {
        if (node == null) return;

        if (node.color == RedBlackTreeApp.Color.RED) {
            if (node.left != null) {
                assertEquals(RedBlackTreeApp.Color.BLACK, node.left.color,
                        "Лівий нащадок червоного вузла (" + node.data + ") повинен бути ЧОРНИМ");
            }
            if (node.right != null) {
                assertEquals(RedBlackTreeApp.Color.BLACK, node.right.color,
                        "Правий нащадок червоного вузла (" + node.data + ") повинен бути ЧОРНИМ");
            }
        }

        validateRedNodeChildren(node.left);
        validateRedNodeChildren(node.right);
    }
}