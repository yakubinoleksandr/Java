package com.example;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class RedBlackTreeApp {

    enum Color {
        RED, BLACK
    }

    static class Node {
        int data;
        Node left, right, parent;
        Color color;

        Node(int data) {
            this.data = data;
            this.color = Color.RED;
        }
    }

    static class RedBlackTree {
        private Node root;

        private void rotateLeft(Node node) {
            Node rightChild = node.right;
            node.right = rightChild.left;

            if (rightChild.left != null) {
                rightChild.left.parent = node;
            }

            rightChild.parent = node.parent;

            if (node.parent == null) {
                root = rightChild;
            } else if (node == node.parent.left) {
                node.parent.left = rightChild;
            } else {
                node.parent.right = rightChild;
            }

            rightChild.left = node;
            node.parent = rightChild;
        }

        private void rotateRight(Node node) {
            Node leftChild = node.left;
            node.left = leftChild.right;

            if (leftChild.right != null) {
                leftChild.right.parent = node;
            }

            leftChild.parent = node.parent;

            if (node.parent == null) {
                root = leftChild;
            } else if (node == node.parent.right) {
                node.parent.right = leftChild;
            } else {
                node.parent.left = leftChild;
            }

            leftChild.right = node;
            node.parent = leftChild;
        }

        public void insert(int data) {
            Node node = new Node(data);
            if (root == null) {
                node.color = Color.BLACK;
                root = node;
                return;
            }

            Node temp = root;
            Node parent = null;

            while (temp != null) {
                parent = temp;
                if (node.data < temp.data) {
                    temp = temp.left;
                } else if (node.data > temp.data) {
                    temp = temp.right;
                } else {
                    return;
                }
            }

            node.parent = parent;
            if (node.data < parent.data) {
                parent.left = node;
            } else {
                parent.right = node;
            }

            fixInsert(node);
        }

        private void fixInsert(Node node) {
            Node parent;
            Node grandParent;

            while (node != root && node.color == Color.RED && node.parent.color == Color.RED) {
                parent = node.parent;
                grandParent = parent.parent;

                if (parent == grandParent.left) {
                    Node uncle = grandParent.right;

                    if (uncle != null && uncle.color == Color.RED) {
                        grandParent.color = Color.RED;
                        parent.color = Color.BLACK;
                        uncle.color = Color.BLACK;
                        node = grandParent;
                    } else {
                        if (node == parent.right) {
                            rotateLeft(parent);
                            node = parent;
                            parent = node.parent;
                        }
                        rotateRight(grandParent);
                        Color tempColor = parent.color;
                        parent.color = grandParent.color;
                        grandParent.color = tempColor;
                        node = parent;
                    }
                } else {
                    Node uncle = grandParent.left;

                    if (uncle != null && uncle.color == Color.RED) {
                        grandParent.color = Color.RED;
                        parent.color = Color.BLACK;
                        uncle.color = Color.BLACK;
                        node = grandParent;
                    } else {
                        if (node == parent.left) {
                            rotateRight(parent);
                            node = parent;
                            parent = node.parent;
                        }
                        rotateLeft(grandParent);
                        Color tempColor = parent.color;
                        parent.color = grandParent.color;
                        grandParent.color = tempColor;
                        node = parent;
                    }
                }
            }
            root.color = Color.BLACK;
        }

        public void inOrder() {
            inOrderHelper(root);
            System.out.println();
        }

        private void inOrderHelper(Node node) {
            if (node != null) {
                inOrderHelper(node.left);
                System.out.print(node.data + "(" + (node.color == Color.RED ? "R" : "B") + ") ");
                inOrderHelper(node.right);
            }
        }

        public void printTree() {
            printTreeHelper(root, "", true);
        }

        private void printTreeHelper(Node node, String indent, boolean last) {
            if (node != null) {
                System.out.print(indent);
                if (last) {
                    System.out.print("R---- ");
                    indent += "   ";
                } else {
                    System.out.print("L---- ");
                    indent += "|  ";
                }
                String colorStr = node.color == Color.RED ? "RED" : "BLACK";
                System.out.println(node.data + " (" + colorStr + ")");
                printTreeHelper(node.left, indent, false);
                printTreeHelper(node.right, indent, true);
            }
        }

        public void clear() {
            root = null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RedBlackTree tree = new RedBlackTree();
        Random random = new Random();

        while (true) {
            System.out.println("\n=== ДЕМОНСТРАЦІЯ ЧЕРВОНО-ЧОРНОГО ДЕРЕВА ===");
            System.out.println("1. Заповнити випадковими числами (довільний порядок)");
            System.out.println("2. Заповнити впорядкованими числами (по зростанню)");
            System.out.println("3. Ввести числа вручну з клавіатури");
            System.out.println("4. Вивести центрований обхід (In-order)");
            System.out.println("5. Відобразити структуру дерева");
            System.out.println("0. Вихід");
            System.out.print("Оберіть опцію: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    tree.clear();
                    System.out.print("Введіть кількість елементів: ");
                    int countRandom = scanner.nextInt();
                    int[] randomArray = new int[countRandom];
                    for (int i = 0; i < countRandom; i++) {
                        randomArray[i] = random.nextInt(100);
                        tree.insert(randomArray[i]);
                    }
                    System.out.println("Порядок додавання елементів (довільний масив): " + Arrays.toString(randomArray));
                    System.out.println("Дерево успішно побудовано.");
                    break;

                case 2:
                    tree.clear();
                    System.out.print("Введіть кількість елементів: ");
                    int countSorted = scanner.nextInt();
                    int[] sortedArray = new int[countSorted];
                    for (int i = 0; i < countSorted; i++) {
                        sortedArray[i] = random.nextInt(100);
                    }
                    Arrays.sort(sortedArray);
                    System.out.println("Порядок додавання елементів (впорядкований масив): " + Arrays.toString(sortedArray));
                    for (int val : sortedArray) {
                        tree.insert(val);
                    }
                    System.out.println("Дерево успішно побудовано.");
                    break;

                case 3:
                    System.out.print("Введіть число для додавання в дерево: ");
                    int val = scanner.nextInt();
                    tree.insert(val);
                    System.out.println("Елемент " + val + " додано.");
                    break;

                case 4:
                    System.out.print("Центрований обхід дерева [Значення(Колір)]: ");
                    tree.inOrder();
                    break;

                case 5:
                    System.out.println("\nСтруктура Червоно-Чорного Дерева:");
                    tree.printTree();
                    break;

                case 0:
                    System.out.println("Завершення роботи.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
            }
        }
    }
}