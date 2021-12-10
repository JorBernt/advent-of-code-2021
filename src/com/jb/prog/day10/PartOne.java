package com.jb.prog.day10;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day10/input.txt"), StandardCharsets.UTF_8);
        Map<Character, Integer> scoreTable = Map.of(
                ')', 3,
                ']', 57,
                '}', 1197,
                '>', 25137);
        int ans = 0;
        while (in.hasNextLine()) {
            String input = in.nextLine();
            ans += countIllegal(input, scoreTable);
        }
        System.out.println(ans);
    }

    static int countIllegal(String input, Map<Character, Integer> scoreTable) {
        Stack<Character> stack = new Stack<>();
        int score = 0;
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
            if(stack.isEmpty()) {
                score += scoreTable.get(c);
                continue;
            }
            char v = stack.pop();
            if(c == ')' && v != '(') score += scoreTable.get(c);
            else if(c == '}' && v != '{') score += scoreTable.get(c);
            else if(c == ']' && v != '[') score += scoreTable.get(c);
            else if(c == '>' && v != '<') score += scoreTable.get(c);
        }
        return score;
    }
}
