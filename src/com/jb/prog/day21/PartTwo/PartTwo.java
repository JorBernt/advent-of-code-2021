package com.jb.prog.day21.PartTwo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Player {
    int pos, score;
    int id;

    public Player(int pos, int id) {
        this.pos = pos;
        this.id = id;
        score = 0;
    }

    private Player(int pos, int score, int id) {
        this.pos = pos;
        this.score = score;
        this.id = id;
    }

    public void move(int val) {
        pos += val % 10;
        if (pos > 10) pos %= 10;
        score += pos;
    }

    public Player copy() {
        return new Player(pos, score, id);
    }

    public String toString() {
        return id + "-" + pos + "-" + score;
    }
}

class Dice {
    int val = 1;
    int rolled = 0;

    public Dice() {
    }

    private Dice(int val, int rolled) {
        this.val = val;
        this.rolled = rolled;
    }

    public int roll() {
        int roll = 0;
        for (int i = 0; i < 3; i++) {
            roll += val;
            val++;
            if (val > 3) val = 1;
        }
        rolled += 3;
        return roll;
    }

    public Dice copy() {
        return new Dice(val, rolled);
    }

}

public class PartTwo {
    static long p1 = 0;
    static long p2 = 0;
    static Map<String, long[]> memo = new HashMap<>();

    public static void main(String[] args) {
        Player player1 = new Player(9, 1);
        Player player2 = new Player(4, 2);
        long w[] = play(2, player1, player2, 0, 0);
        System.out.println(Math.max(w[0], w[1]));
    }

    static long[] play(int currentID, Player player1, Player player2, int n, int m) {
        String in = currentID + "-" + player1.toString() + "-" + player2.toString() + "-" + n + "-" + m;
        if (memo.containsKey(in)) {
            return memo.get(in);
        }
        Player current = currentID == 1 ? player1 : player2;
        if (n == 3) {
            current.move(m);
            n = 0;
            m = 0;
        }
        if (current.score >= 21) {
            if (current.id == player1.id) {
                return new long[]{1, 0};
            } else {
                return new long[]{0, 1};
            }
        }
        long[] w = new long[2];
        for (int i = 1; i <= 3; i++) {
            Player a = currentID == 1 ? current.copy() : player1.copy();
            Player b = currentID == 2 ? current.copy() : player2.copy();
            long[] d = play(currentID == 1 ? 2 : 1, a, b, n + 1, m + i);
            for (int j = 0; j < 2; j++) w[j] += d[j];

        }
        memo.put(in, w);
        return w;
    }
}
