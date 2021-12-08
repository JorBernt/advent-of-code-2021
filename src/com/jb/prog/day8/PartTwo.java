package com.jb.prog.day8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PartTwo {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day8/input.txt"), StandardCharsets.UTF_8);
        int ans = 0;
        boolean falg = true;
        while (in.hasNextLine()) {
            String[] input = in.nextLine().split(" \\| ");
            List<List<String>> digit = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                digit.add(new ArrayList<>(Arrays.stream("abcdefg".split("")).toList()));
            }
            for (String s : input[0].split(" ")) {
                remove(digit, s);
            }
            for (int i = 0; i < digit.size(); i++) {
                List<String> a = digit.get(i);
                if (a.size() == 3) {
                    for (List<String> b : digit) {
                        if (b.size() == 2) {
                            List<String> temp = new ArrayList<>();
                            for (String c : a) {
                                if (!b.contains(c)) temp.add(c);
                            }
                            if (temp.size() == 1) {
                                digit.get(i).clear();
                                digit.get(i).addAll(temp);
                                for (List<String> d : digit) {
                                    if (d.size() == 1) continue;
                                    for (String c : temp) d.remove(c);
                                }
                            }
                        }
                    }

                }
                if (a.size() == 2) {
                    if (digit.stream().filter(q -> q.equals(a)).count() != 2) break;
                    for (List<String> d : digit) {
                        if (d.size() == 2) continue;
                        for (String c : a) d.remove(c);
                    }
                    if (falg) {
                        i = 0;
                        falg = !falg;
                    }
                }
            }
            List<String> possible = new ArrayList<>();
            getPossible(digit, possible, 0, "");
            out:for (String pos : possible) {
                Map<String, Integer> val = new HashMap<>();
                val.put(helper(new int[]{0, 1, 2, 4, 5, 6}, pos), 0);
                val.put(helper(new int[]{2, 5}, pos), 1);
                val.put(helper(new int[]{0, 2, 3, 4, 6}, pos), 2);
                val.put(helper(new int[]{0, 2, 3, 5, 6}, pos), 3);
                val.put(helper(new int[]{1, 2, 3, 5}, pos), 4);
                val.put(helper(new int[]{0, 1, 3, 5, 6}, pos), 5);
                val.put(helper(new int[]{0, 1, 3, 4, 5, 6}, pos), 6);
                val.put(helper(new int[]{0, 2, 5}, pos), 7);
                val.put(helper(new int[]{0, 1, 2, 3, 4, 5, 6}, pos), 8);
                val.put(helper(new int[]{0, 1, 2, 3, 5, 6}, pos), 9);
                for (String s : input[0].split(" ")) {
                    StringBuilder sb = new StringBuilder();
                    char[] ca = s.toCharArray();
                    Arrays.sort(ca);
                    for (char c : ca) sb.append(c);
                    if (!val.containsKey(sb.toString())) {
                        continue out;
                    }
                }
                StringBuilder k = new StringBuilder();
                for (String s : input[1].split(" ")) {
                    StringBuilder sb = new StringBuilder();
                    char[] ca = s.toCharArray();
                    Arrays.sort(ca);
                    for (char c : ca) sb.append(c);
                    k.append(val.get(sb.toString()));
                }
                ans += Integer.parseInt(k.toString());

            }
        }
        System.out.println(ans);


    }

    private static String helper(int[] p, String s) {
        List<Character> characters = new ArrayList<>();
        for (int n : p) {
            characters.add(s.charAt(n));
        }
        characters.sort(Comparator.naturalOrder());
        StringBuilder sb = new StringBuilder();
        for (char c : characters) sb.append(c);
        return sb.toString();
    }

    private static void getPossible(List<List<String>> digit, List<String> possible, int index, String t) {
        if (index == digit.size()) {
            possible.add(t);
            return;
        }
        for (String s : digit.get(index)) {
            if (t.contains(s)) continue;
            getPossible(digit, possible, index + 1, t + s);
        }

    }

    private static void remove(List<List<String>> digit, String s) {
        switch (s.length()) {
            case 2 -> {
                for (int i = 0; i < 7; i++) {
                    if (i == 2 || i == 5) continue;
                    for (String c : s.split("")) {
                        digit.get(i).remove(c);
                    }
                }
            }
            case 4 -> {
                for (int i = 0; i < 7; i++) {
                    if (i == 1 || i == 2 || i == 3 || i == 5) continue;
                    for (String c : s.split("")) {
                        digit.get(i).remove(c);
                    }
                }
            }
            case 3 -> {
                for (int i = 0; i < 7; i++) {
                    if (i == 0 || i == 2 | i == 5) continue;
                    for (String c : s.split("")) {
                        digit.get(i).remove(c);
                    }
                }
            }
        }
    }
}
