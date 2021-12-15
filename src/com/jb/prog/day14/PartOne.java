package com.jb.prog.day14;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class PartOne {
    static Map<String, long[]> memo = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day14/input.txt"), StandardCharsets.UTF_8);
        String start = in.nextLine();
        Map<String, List<String>> rules = new HashMap<>();
        in.nextLine();
        while (in.hasNextLine()) {
            String[] rule = in.nextLine().split(" -> ");
            rules.put(rule[0], new ArrayList<>());
            rules.get(rule[0]).add(rule[0].charAt(0) + rule[1]);
            rules.get(rule[0]).add(rule[1] + rule[0].charAt(1));
        }
        long[] count = new long[26];
        for (int i = 0; i < start.length() - 1; i++) {
            long[] t = rec(start.substring(i, i + 2), rules, 0, new long[26]);
            for (int j = 0; j < t.length; j++) count[j] += t[j];
        }
        for(char c : start.toCharArray()) count[c-'A']++;
        long max = Arrays.stream(count).max().getAsLong();
        long min = Arrays.stream(count).filter(a -> a > 0).min().getAsLong();
        System.out.println(max - min);

    }

    static long[] rec(String pair, Map<String, List<String>> rules, int depth, long[] ar) {
        if (memo.containsKey(pair + depth)) {
            return memo.get(pair + depth);
        }
        ar[rules.get(pair).get(0).charAt(1)-'A']++;
        if(depth == 39) {
            memo.put(pair+depth, Arrays.copyOf(ar, ar.length));
            return ar;
        }
        for (String s : rules.get(pair)) {
            long[] d = rec(s, rules, depth + 1, new long[26]);
            for (int i = 0; i < d.length; i++) ar[i] += d[i];
        }
        memo.put(pair + depth, Arrays.copyOf(ar, ar.length));
        return ar;
    }

}
