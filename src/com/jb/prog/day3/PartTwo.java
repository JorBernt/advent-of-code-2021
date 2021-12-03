package com.jb.prog.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> bits = new ArrayList<>();
        while (true) {
            String input = in.next();
            if (input.equals("end")) break;
            bits.add(input);
        }
        int oxygen = oxygenRating(new ArrayList<>(bits));
        int co2 = co2Rating(bits);
        System.out.println(oxygen * co2);
    }

    static char mostCommon(List<String> bits, int index) {
        int z = 0, o = 0;
        for (String s : bits) {
            if (s.charAt(index) == '1') o++;
            else z++;
        }
        return o >= z ? '1' : '0';
    }

    static int oxygenRating(List<String> bits) {
        for (int i = 0; i < bits.get(0).length(); i++) {
            char common = mostCommon(bits, i);
            final int n = i;
            bits = bits.stream().filter(a -> a.charAt(n) == common).toList();
            if (bits.size() == 1) break;
        }
        return Integer.parseInt(bits.get(0), 2);
    }

    static int co2Rating(List<String> bits) {
        for (int i = 0; i < bits.get(0).length(); i++) {
            char uncommon = mostCommon(bits, i) == '1' ? '0' : '1';
            final int n = i;
            bits = bits.stream().filter(a -> a.charAt(n) == uncommon).toList();
            if (bits.size() == 1) break;
        }
        return Integer.parseInt(bits.get(0), 2);
    }

}
