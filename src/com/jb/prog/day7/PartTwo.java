package com.jb.prog.day7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class PartTwo {
    public static void main(String[] args) throws FileNotFoundException {
        int[] crabs = Arrays.stream(new Scanner(new FileInputStream("data/day7/input.txt"), StandardCharsets.UTF_8)
                .nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int min = Arrays.stream(crabs).min().orElse(-1);
        int max = Arrays.stream(crabs).max().orElse(-1);
        int ans = Integer.MAX_VALUE;
        for(int i = 0; i <= max; i++) {
            int sum = 0;
            for(int n : crabs) {
                int m = Math.abs(i-n);
                int t = 1;
                for(int j = 0; j < m; j++) {
                    sum += t;
                    t++;
                    if(sum > ans) break;
                }
                if(sum > ans) break;
            }
            ans = Math.min(ans, sum);
        }
        System.out.println(ans);

    }
}
