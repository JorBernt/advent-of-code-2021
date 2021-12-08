package com.jb.prog.day8;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day8/input.txt"), StandardCharsets.UTF_8);
        int ans = 0;
        while (in.hasNextLine()) {
            String[] input = in.nextLine().split(" \\| ");
            for(String s : input[1].split(" ")) {
                switch (s.length()) {
                    case 2:
                    case 3:
                    case 4:
                    case 7: ans++;
                }
            }

        }
        System.out.println(ans);

    }
}
