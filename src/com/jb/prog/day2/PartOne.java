package com.jb.prog.day2;

import java.util.Scanner;

public class PartOne {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int depth = 0;
        int pos = 0;
        while (scanner.hasNextLine()) {
            String input = scanner.next();
            if(input.equals("end")) break;
            int x = scanner.nextInt();
            switch (input) {
                case "forward":
                    pos += x;
                    break;
                case "down":
                    depth += x;
                    break;
                case "up":
                    depth -= x;
                    break;
            }
        }
        System.out.println(depth * pos);
    }
}
