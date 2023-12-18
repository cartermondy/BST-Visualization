/*
 * Carter Mondy
 * CS 5040
 * Fall 2023
 * Instructor: Umana Tasmin
 */

// Import Scanner
import java.util.InputMismatchException;
import java.util.Scanner;

// Node class
class Node {
    int val;
    Node left;
    Node right;

    public Node(int val) {
        this.val = val;
    }
}

public class BST {
    Node root;

    // Insert value in BST
    public void insert(int val) {
        Node newNode = new Node(val);

        if (root == null) {
            root = newNode;
        } else {
            Node focusNode = root;
            Node parent;
            while (true) {
                parent = focusNode;
                if (val < focusNode.val) {
                    focusNode = focusNode.left;
                    if (focusNode == null) {
                        parent.left = newNode;
                        return;
                    }
                } else {
                    focusNode = focusNode.right;
                    if (focusNode == null) {
                        parent.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    // Find a value as well as the path in the BST
    public boolean find(int val) {
        if (root == null) {
            System.out.println("Tree is empty");
            return false;
        }
        Node current = root;
        StringBuilder path = new StringBuilder();
        while (current != null) {
            path.append(current.val);
            if (val == current.val) {
                System.out.println(path.toString().trim());
                return true;
            } else if (val < current.val) {
                path.append(" -> ");
                current = current.left;
            } else {
                current = current.right;
                path.append(" -> ");
            }
        }
        System.out.println("Value not found in tree");
        return false; // Value not found
    }

    // Recursive function to remove duplicates
    public Node removeDuplicates(Node root) {
        if (root == null)
            return null;
        root.left = removeDuplicates(root.left);
        root.right = removeDuplicates(root.right);
        removeDuplicatesHelper(root);
        return root;
    }

    // Helper function to remove duplicates
    public Node removeDuplicatesHelper(Node root) {
        if (root == null)
            return null;
        root.left = removeDuplicatesHelper(root.left);
        root.right = removeDuplicatesHelper(root.right);
        // Check and remove duplicates on the left side
        if (root.left != null && root.val == root.left.val) {
            root.left = findMinAndRemove(root.left);
        }
        // Check and remove duplicates on the right side
        if (root.right != null && root.val == root.right.val) {
            root.right = findMinAndRemove(root.right);
        }
        return root;
    }

    // Helper function to find minimum node in subtree and remove it
    private Node findMinAndRemove(Node root) {
        if (root.left == null) {
            return root.right;
        }
        root.left = findMinAndRemove(root.left);
        return root;
    }

    // List the values (In Order Traversal)
    public void traverseInOrder(Node root) {
        if (root != null) {
            traverseInOrder(root.left);
            System.out.println(root.val + " ");
            traverseInOrder(root.right);
        }
    }

    // In order to visualize tree in console
    public void visualizeTree(String prefix, Node root, boolean isLeft) {
        if (root != null) {
            visualizeTree(prefix + "    ", root.right, false);
            System.out.println(prefix + ("|-- ") + root.val);
            visualizeTree(prefix + "    ", root.left, true);
        }
    }

    public static void main(String[] ags) {
        BST bst = new BST();
        Scanner scanner = new Scanner(System.in);

        // Build tree from user input
        System.out.println("Enter values to insert into the Binary Search Tree (enter a non-integer to finish): ");
        while (scanner.hasNextInt()) {
            int val = scanner.nextInt();
            bst.insert(val);
        }
        // Visualize the tree input
        System.out.println();
        System.out.println("Tree Visualization: ");
        bst.visualizeTree("", bst.root, false);
        System.out.println();

        // User can use methods
        while (true) {
            System.out.println("-- Main Menu --");
            System.out.println("1: Insert a New Value");
            System.out.println("2: Find a Value");
            System.out.println("3: Traverse Tree In-Order");
            System.out.println("4: Remove Duplicates");
            System.out.println("5: Visualize Tree");
            System.out.println("6: Exit");

            System.out.println("Enter choice: ");
            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Enter a value to insert into the BST: ");
                        int newVal = scanner.nextInt();
                        bst.insert(newVal);
                        break;
                    case 2:
                        System.out.println("Enter a value to find: ");
                        int findVal = scanner.nextInt();
                        bst.find(findVal);
                        break;
                    case 3:
                        bst.traverseInOrder(bst.root);
                        break;
                    case 4:
                        System.out.println("Tree with duplicates");
                        bst.traverseInOrder(bst.root);
                        bst.removeDuplicates(bst.root);
                        System.out.println("Tree without Duplicates");
                        // bst.visualizeTree("", bst.root, false);
                        bst.traverseInOrder(bst.root);
                        break;
                    case 5:
                        System.out.println("Tree Visualization");
                        bst.visualizeTree("", bst.root, false);
                        break;
                    case 6:
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice enter an option (1-5): ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }
}
