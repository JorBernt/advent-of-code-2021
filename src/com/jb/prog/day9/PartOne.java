package com.jb.prog.day9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day9/input.txt"), StandardCharsets.UTF_8);
        int h = 0;
        while (in.hasNextLine()) {
            h++;
            in.nextLine();
        }
        in = new Scanner(new FileInputStream("data/day9/input.txt"), StandardCharsets.UTF_8);
        int[][] map = new int[h][];
        for (int i = 0; i < h; i++) {
            map[i] = Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
        }
        int ans = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (checkIfLowPoint(map, i, j)) {
                    ans += map[i][j] + 1;
                }
            }
        }
        System.out.println(ans);
    }

    static boolean checkIfLowPoint(int[][] map, int y, int x) {
        int n = map[y][x];
        if (y > 0 && map[y - 1][x] <= n) return false;
        if (y < map.length - 1 && map[y + 1][x] <= n) return false;
        if (x > 0 && map[y][x - 1] <= n) return false;
        if (x < map[0].length - 1 && map[y][x + 1] <= n) return false;
        return true;
    }
}
