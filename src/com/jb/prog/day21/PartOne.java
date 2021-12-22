package com.jb.prog.day21;

class Player {
    int pos, score;

    public Player(int pos) {
        this.pos = pos;
        score = 0;
    }

    public void move(int val) {
        pos += val % 10;
        if (pos > 10) pos %= 10;
        score += pos;
    }
}

class Dice {
    int val = 1;
    int rolled = 0;

    public int roll() {
        int roll = 0;
        for (int i = 0; i < 3; i++) {
            roll += val;
            val++;
            if (val > 100) val = 1;
        }
        rolled += 3;
        return roll;
    }
}

public class PartOne {
    public static void main(String[] args) {
        Player player1 = new Player(9);
        Player player2 = new Player(4);
        Player current = player1;
        Dice dice = new Dice();
        boolean done = false;
        while (!done) {
            current.move(dice.roll());
            if (current.score >= 1000) done = true;
            current = current == player1 ? player2 : player1;
        }
        System.out.println(current.score * dice.rolled);
    }
}
