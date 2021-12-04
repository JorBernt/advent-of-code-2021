package com.jb.prog.day4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("data/day4/input.txt"), StandardCharsets.UTF_8);
        int[] drawn = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        List<int[][]> boards = new ArrayList<>();
        int row = 0;
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                boards.add(new int[5][5]);
                row = 0;
                continue;
            }
            boards.get(boards.size() - 1)[row] = Arrays
                    .stream(input.split(" "))
                    .filter(a -> !a.isEmpty())
                    .mapToInt(Integer::parseInt)
                    .toArray();
            row++;
        }

        for(int n : drawn) {
            for(int[][] board : boards) {
                checkNumber(board, n);
                if(winner(board)) {
                    calcWinner(board, n);
                    return;
                }
            }
        }
    }

    static void calcWinner(int[][] board, int n) {
        int sum = 0;
        for(int[] r : board)
            sum += Arrays.stream(r).filter(a -> a >= 0).sum();
        System.out.println(n * sum);
    }

    static void checkNumber(int[][] board, int n) {
        for(int[] r : board) {
            for(int i = 0; i < r.length; i++) {
                if(r[i] == n) {
                    r[i] = -1;
                    return;
                }
            }
        }
    }

    static boolean winner(int[][] board) {
        for(int i = 0; i < board.length; i++) {
            int row = 0, col = 0;
            for(int j = 0; j < board[i].length; j++) {
                row += board[i][j];
                col += board[j][i];
            }
            if(row == -5 || col == -5) {
                return true;
            }
        }
        return false;
    }
}
