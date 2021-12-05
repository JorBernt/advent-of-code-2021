package com.jb.prog.day5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Line {
    Point a, b;

    public Line(String line) {
        String[] input = line.split(" -> ");
        a = createPoint(input[0]);
        b = createPoint(input[1]);
    }

    private Point createPoint(String s) {
        String[] S = s.split(",");
        int x = Integer.parseInt(S[0]);
        int y = Integer.parseInt(S[1]);
        return new Point(x, y);
    }

    public boolean isStraight() {
        return (a.x == b.x) || (a.y == b.y);
    }

    class Point {
        public int x, y;

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
}

public class PartOne {
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
            if (!line.isStraight()) continue;
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
