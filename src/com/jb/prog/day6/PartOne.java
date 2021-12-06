package com.jb.prog.day6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day6/input.txt"), StandardCharsets.UTF_8);
        String input = in.nextLine();
        long[] fishes = new long[9];
        for(int n : Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray()) fishes[n]++;
        for (int i = 0; i < 256; i++) {
            long[] temp = new long[9];
            temp[6] = fishes[0];
            temp[8] = fishes[0];
            for(int j=1;j<fishes.length;j++) {
                temp[j-1] += fishes[j];
            }
            fishes = temp;
        }
        long total = Arrays.stream(fishes).sum();
        System.out.println(total);
    }
}
