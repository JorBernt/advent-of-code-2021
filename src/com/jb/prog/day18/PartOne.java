package com.jb.prog.day18;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Node {
    Node l, r, parent;
    int val;

    public Node() {
        val = -1;
    }

    public Node(int val) {
        this.val = val;
    }

    public Node(Node left, Node right) {
        l = left;
        r = right;
    }


}


class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day18/input.txt"), StandardCharsets.UTF_8);
        Queue<Node> nodes = new LinkedList<>();
        while (in.hasNextLine()) {
            nodes.add(parseNumber(new Node(), in.nextLine()));
        }
        Node root = nodes.poll();
        print(root);
        System.out.println();
        while (!nodes.isEmpty()) {
            root = add(root, nodes.poll());
            reduce(root);
        }
        print(root);
        System.out.println();
        System.out.println(magnitude(root));
    }

    static long magnitude(Node root) {
        if(root == null) return 0;
        if(root.l == null && root.r == null) {
            return root.val;
        }
        return (magnitude(root.l) * 3) + (magnitude(root.r)  * 2);
    }

    static void print(Node root) {
        if(root == null) {
            return;
        }
        if(root.l != null) {
            System.out.print("[");
            print(root.l);
        }
        if(root.l == null && root.r == null) {
            System.out.print(root.val);
        }
        if(root.r != null) {
            System.out.print(",");
            print(root.r);
            System.out.print("]");
        }

    }

    static void reduce(Node root) {
        while (explode(root, 1)) {}
        boolean b = split(root);
        if(!b) return;
        reduce(root);
    }

    static boolean split(Node root) {
        if (root == null) return false;
        if (root.val >= 10) {
            root.l = new Node(root.val / 2);
            root.r = new Node((int) Math.ceil(root.val / 2d));
            root.l.parent = root;
            root.r.parent = root;
            root.val = -1;
            return true;
        }
        return split(root.l) || split(root.r);
    }

    static boolean explode(Node root, int depth) {
        if (root == null) return false;
        if (depth > 4 && root.l != null && root.r != null && root.l.val != -1 && root.r.val != -1) {
            if(root.parent.r == root) {
                Node node = root.parent.l;
                while (node.r != null) node = node.r;
                node.val += root.l.val;
                Node par = root.parent;
                Node cur = root;
                while (par != null) {
                    if(par.l == cur) {
                        cur = par.r;
                        while (cur.l != null) cur = cur.l;
                        cur.val += root.r.val;
                        break;
                    }
                    cur = par;
                    par = par.parent;
                }
            }
            else {
                Node node = root.parent.r;
                while (node.l != null) node = node.l;
                node.val += root.r.val;
                Node par = root.parent;
                Node cur = root;
                while (par != null) {
                    if(par.r == cur) {
                        cur = par.l;
                        while (cur.r != null) cur = cur.r;
                        cur.val += root.l.val;
                        break;
                    }
                    cur = par;
                    par = par.parent;
                }
            }
            root.l = null;
            root.r = null;
            root.val = 0;
            return true;
        }
        return explode(root.l, depth + 1) || explode(root.r, depth + 1);
    }

    static Node add(Node left, Node right) {
        Node root = new Node(left, right);
        left.parent = root;
        right.parent = root;
        return root;
    }

    static Node parseNumber(Node node, String str) {
        if (str.charAt(0) == '[')
            str = str.substring(1, str.length() - 1);
        if (str.charAt(0) == '[') {
            int n = 0;
            boolean b = true;
            int m = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '[') n++;
                else if (str.charAt(i) == ']') n--;
                if (n == 0) {
                    if (b) {
                        node.l = parseNumber(new Node(), str.substring(0, i + 1));
                        node.l.parent = node;
                        str = str.substring(i + 2);
                        i = -1;
                        b = false;
                    } else {
                        node.r = parseNumber(new Node(), str.substring(m));
                        node.r.parent = node;
                    }
                }
            }
        } else if (str.contains(",")) {
            node.l = parseNumber(new Node(), str.substring(0, str.indexOf(",")));
            node.r = parseNumber(new Node(), str.substring(str.indexOf(",") + 1));
            node.l.parent = node;
            node.r.parent = node;
        } else node.val = Integer.parseInt(str);
        return node;
    }
}