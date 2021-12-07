package com.jb.prog.day7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        int[] crabs = Arrays.stream(new Scanner(new FileInputStream("data/day7/input.txt"), StandardCharsets.UTF_8)
                .nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int min = Arrays.stream(crabs).min().getAsInt();
        int max = Arrays.stream(crabs).max().getAsInt();
        int ans = Integer.MAX_VALUE;
        for(int i = 0; i <= max; i++) {
            int sum = 0;
            for(int n : crabs) {
                sum += Math.abs(i-n);
                if(sum > ans) break;
            }
            ans = Math.min(ans, sum);
        }
        System.out.println(ans);

    }
}
