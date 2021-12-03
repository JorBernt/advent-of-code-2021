package com.jb.prog.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> bits = new ArrayList<>();
        while (true) {
            String input = in.next();
            if(input.equals("end")) break;
            bits.add(input);
        }
        int mid = bits.size() / 2;
        StringBuilder gammaRate = new StringBuilder();
        for(int i = 0; i < bits.get(0).length(); i++) {
            int c = 0;
            for(String s : bits) {
                if(s.charAt(i) == '1') c++;
            }
            gammaRate.append(c >= mid ? 1 : 0);
        }
        int g = Integer.parseInt(gammaRate.toString(), 2);
        int e = flipBits(g, gammaRate.length());

        System.out.println(g*e);
    }

    static int flipBits(int n, int k) {
        int mask = 1;
        for (int i = 1; i < k; ++i)
            mask |= mask << 1;
        return ~n & mask;
    }
}
