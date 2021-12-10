package com.jb.prog.day10;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PartTwo {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day10/input.txt"), StandardCharsets.UTF_8);
        var scoreTable = Map.of(
                ')', 1,
                ']', 2,
                '}', 3,
                '>', 4);
        var pair = Map.of(
                '(', ')',
                '{', '}',
                '[', ']',
                '<', '>'
        );
        List<Long> scores = new ArrayList<>();
        while (in.hasNextLine()) {
            String input = in.nextLine();
            if (isCorrupt(input)) continue;
            scores.add(getScore(input, scoreTable, pair));
        }
        scores.sort(Comparator.naturalOrder());
        System.out.println(scores.get(scores.size() / 2));
    }


    static boolean isCorrupt(String input) {
        Stack<Character> stack = new Stack<>();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '(':
                case '{':
                case '[':
                case '<': {
                    stack.add(c);
                    continue;
                }
            }
            if (stack.isEmpty()) {
                return true;
            }
            char v = stack.pop();
            if (c == ')' && v != '(') return true;
            else if (c == '}' && v != '{') return true;
            else if (c == ']' && v != '[') return true;
            else if (c == '>' && v != '<') return true;
        }
        return false;
    }


    static long getScore(String input, Map<Character, Integer> scoreTable, Map<Character, Character> pair) {
        Stack<Character> stack = new Stack<>();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '(':
                case '{':
                case '[':
                case '<': {
                    stack.add(c);
                    continue;
                }
            }
            stack.pop();
        }
        long score = 0;
        while (!stack.isEmpty()) {
            score *= 5;
            score += scoreTable.get(pair.get(stack.pop()));
        }
        return score;
    }


}
