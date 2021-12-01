
package com.jb.prog.day1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PartTwo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> depths = new ArrayList<>();
        while (scanner.hasNextInt()) {
            depths.add(scanner.nextInt());
        }
        List<int[]> windows = new ArrayList<>();
        windows.add(new int[2]);
        for(int n : depths) {
            windows.add(new int[2]);
            for(int[] a : windows) {
                if(a[1] < 3) {
                    a[0] += n;
                    a[1]++;
                }
            }
        }
        int prev = windows.get(0)[0];
        int ans = 0;
        for(int[] a : windows) {
            if(a[0] > prev) {
                ans++;
            }
            prev = a[0];
        }
        System.out.println(ans);
    }
}
