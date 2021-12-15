package com.jb.prog.day13;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Point {
    int x, y;

    public Point(int[] p) {
        this.x = p[0];
        this.y = p[1];
    }
}

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day13/input.txt"), StandardCharsets.UTF_8);
        List<Point> points = new ArrayList<>();
        List<String> folds = new ArrayList<>();
        int maxY = 0;
        int maxX = 0;
        while (in.hasNextLine()) {
            String input = in.nextLine();
            if (input.isEmpty()) break;
            Point p = new Point(Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray());
            maxY = Math.max(maxY, p.y);
            maxX = Math.max(maxX, p.x);
            points.add(p);
        }
        while (in.hasNextLine()) {
            folds.add(in.nextLine().split(" ")[2]);
        }
        boolean[][] map = new boolean[maxY + 1][maxX + 1];
        for (Point p : points) {
            map[p.y][p.x] = true;
        }
        for (String s : folds) {
            String[] k = s.split("=");
            if (k[0].equals("x")) {
                int x = Integer.parseInt(k[1]);
                int n = 1;
                while (x - n >= 0 && x + n <= map[0].length) {
                    for (int i = 0; i < map.length; i++) {
                        map[i][x - n] = map[i][x - n] | map[i][x + n];
                        map[i][x + n] = false;
                    }
                    n++;
                }
            } else {
                int y = Integer.parseInt(k[1]);
                int n = 1;
                while (y - n >= 0 && y + n <= map.length) {
                    for (int i = 0; i < map[0].length; i++) {
                        map[y - n][i] = map[y - n][i] | map[y + n][i];
                        map[y + n][i] = false;
                    }
                    n++;
                }
            }

        }
        int ans = 0;
        for (boolean[] r : map) {
            for (boolean b : r) if (b) ans++;
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 50; j++) {
                if (map[i][j]) System.out.print("O");
                else System.out.print(".");
            }
            System.out.println();
        }

        System.out.println(ans);


    }
}
