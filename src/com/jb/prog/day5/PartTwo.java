package com.jb.prog.day5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day5/input.txt"), StandardCharsets.UTF_8);
        List<Line> lines = new ArrayList<>();
        while (in.hasNextLine()) {
            lines.add(new Line(in.nextLine()));
        }
        int x = 0, y = 0;
        for (Line line : lines) {
            x = Math.max(x, Math.max(line.a.x, line.b.x));
            y = Math.max(y, Math.max(line.a.y, line.b.y));
        }
        int[][] map = new int[y + 1][x + y];
        for (Line line : lines) {
            travel(line, map);
        }
        int ans = 0;
        for (int[] r : map) ans += Arrays.stream(r).filter(a -> a > 1).count();
        System.out.println(ans);
    }

    static void travel(Line line, int[][] map) {
        while (!line.a.equals(line.b)) {
            map[line.a.y][line.a.x]++;
            if (line.a.y < line.b.y) line.a.y++;
            else if (line.a.y > line.b.y) line.a.y--;
            if (line.a.x < line.b.x) line.a.x++;
            else if (line.a.x > line.b.x) line.a.x--;
        }
        map[line.a.y][line.a.x]++;
    }
}
