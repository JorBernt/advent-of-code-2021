package com.jb.prog.day20;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class Square {
    int x1, x2, y1, y2;

    public Square(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
}

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day20/input.txt"), StandardCharsets.UTF_8);
        char[] algo = in.nextLine().toCharArray();
        in.nextLine();
        char[][] map = new char[1000][1000];
        char[][] temp = new char[1000][1000];
        int y = 400;
        int X = 0;
        while (in.hasNextLine()) {
            char[] ca = in.nextLine().toCharArray();
            X = 400 + ca.length;
            for(int i = 400, j = 0; j < ca.length; i++, j++) {
                map[y][i] = ca[j];
                temp[y][i] = ca[j];
            }
            y++;
        }
        Square square = new Square(10, 950, 10, 950);
        for(int i = 0; i < 50; i++) {
            for(y = square.y1; y < square.y2; y++) {
                for(int x = square.x1; x < square.x2; x++) {
                    temp[y][x] = enhance(map, y, x, algo);
                }
            }
            for(y = 0; y < map.length; y++) {
                for(int x = 0; x < map[0].length; x++) {
                    map[y][x] = temp[y][x];
                }
            }

        }
        int lit = 0;
        for(int i = 350; i < 700; i++) {
            for(int j = 350; j < 700; j++) {
                System.out.print(map[i][j]+"");
                if(map[i][j] == '#') lit++;
            }
            System.out.println();

        }
        System.out.println(lit);
    }

    static char enhance(char[][] map, int Y, int X, char[] algo) {
        StringBuilder sb = new StringBuilder();
        for(int y = Y - 1; y <= Y + 1; y++) {
            for(int x = X - 1; x <= X + 1; x++) {
                sb.append(map[y][x] == '#' ? 1 : 0);
            }
        }
        int n = Integer.parseInt(sb.toString(), 2);
        return algo[n];
    }
}
