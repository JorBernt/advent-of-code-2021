package com.jb.prog.day9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class PartTwo {
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
        int basinCount = -1;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 9 && map[i][j] > 0) {
                    grow(map, i, j, basinCount);
                    basinCount--;
                }
            }
        }
        int[] count = new int[basinCount * -1];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] < 0) {
                    count[map[i][j]*-1]++;
                }
            }
        }
        Arrays.sort(count);
        System.out.println(count[count.length - 1] * count[count.length - 2] * count[count.length - 3]);
    }

    static void grow(int[][] map, int y, int x, int b) {
        if (y < 0 || x < 0 || y == map.length || x == map[0].length || map[y][x] == 9 || map[y][x] < 0) return;
        map[y][x] = b;
        grow(map, y - 1, x, b);
        grow(map, y + 1, x, b);
        grow(map, y, x - 1, b);
        grow(map, y, x + 1, b);
    }


}
