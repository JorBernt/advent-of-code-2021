package com.jb.prog;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> depths = new ArrayList<>();
        while (scanner.hasNextInt()) {
            depths.add(scanner.nextInt());
        }
        int prev = depths.get(0);
        int ans = 0;
        for(int n : depths) {
            if(n > prev) {
                ans++;
            }
            prev = n;
        }
        System.out.println(ans);
    }
}
