package com.jb.prog.day15;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day15/input.txt"), StandardCharsets.UTF_8);
        int h = 500;
        int[][] map = new int[h][];
        int n = 0;
        int[][] tempMap = new int[h][];
        while (in.hasNextLine()) {
            map[n] = multiply(Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray(), true);

            n++;
        }
        int q = 0;
        while (n < h) {
            map[n] = multiply(map[q], false);
            n++;
            q++;
        }
        n = 0;
        for (int[] r : map) {
            tempMap[n] = Arrays.copyOf(r, r.length);
            n++;
        }
        int w = map[0].length;
        int ans = 0;
        List<Point> visited = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();
        boolean start = true;
        queue.add(new Point(0, 0));
        visited.add(queue.peek());
        while (!queue.isEmpty()) {
            Point p = queue.poll();
            List<Point> adjacent = getAdjacent(map, h, w, p);
            for (Point a : adjacent) {
                if (visited.contains(a)) continue;
                if (start) {
                    queue.add(a);
                    continue;
                }
                if (tempMap[a.y][a.x] == map[a.y][a.x]) {
                    queue.add(a);
                    tempMap[a.y][a.x] = tempMap[a.y][a.x] + tempMap[p.y][p.x];
                } else if (map[a.y][a.x] + tempMap[p.y][p.x] < tempMap[a.y][a.x]) {
                    tempMap[a.y][a.x] = map[a.y][a.x] + tempMap[p.y][p.x];
                    queue.add(a);
                }
            }
            if (start) start = !start;
            n = 0;

        }
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print("[" + tempMap[i][j] + "]");
            }
            System.out.println();
        }

    }

    static int[] multiply(int[] a, boolean w) {
        if (w) {
            int[] ret = new int[a.length * 5];
            for (int i = 0, j = 0, n = 0; i < ret.length; i++, j++) {
                if (j == a.length) {
                    j = 0;
                    n++;
                }
                ret[i] = a[j] + n;
                if (ret[i] == 10) ret[i] = 1;

            }
            return ret;
        }
        int[] ret = new int[a.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = a[i] + 1;
            if(ret[i] >= 10) ret[i] %= 9;
        }
        return ret;
    }


    static List<Point> getAdjacent(int[][] map, int h, int w, Point point) {
        List<Point> points = new ArrayList<>();
        if (point.x > 0) {
            points.add(new Point(point.x - 1, point.y));
        }
        if (point.x < w - 1) {
            points.add(new Point(point.x + 1, point.y));
        }
        if (point.y > 0) {
            points.add(new Point(point.x, point.y - 1));
        }
        if (point.y < h - 1) {
            points.add(new Point(point.x, point.y + 1));
        }
        return points;
    }
}
