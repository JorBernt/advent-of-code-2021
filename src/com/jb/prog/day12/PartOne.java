package com.jb.prog.day12;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PartOne {
    static int totalPaths = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day12/input.txt"), StandardCharsets.UTF_8);
        Map<String, List<String>> caves = new HashMap<>();
         while (in.hasNextLine()) {
             String[] input = in.nextLine().split("-");
             if(!caves.containsKey(input[0])) caves.put(input[0], new ArrayList<>());
             caves.get(input[0]).add(input[1]);
             if(!caves.containsKey(input[1])) caves.put(input[1], new ArrayList<>());
             caves.get(input[1]).add(input[0]);
         }
         findPath(caves, new ArrayList<>(), "start", false);
        System.out.println(totalPaths);
    }

    static void findPath(Map<String, List<String>> caves, List<String> path, String cave, boolean twoSmallCavesVisited) {
        if(!twoSmallCavesVisited && Character.isLowerCase(cave.charAt(0)) && path.contains(cave)) twoSmallCavesVisited = true;
        path.add(cave);
        if(cave.equals("end")) {
            totalPaths++;
            return;
        }
        for(String s : caves.get(cave)) {
            if(s.equals("start") || (Character.isLowerCase(s.charAt(0)) && path.contains(s) && twoSmallCavesVisited)) continue;
            findPath(caves, new ArrayList<>(path), s, twoSmallCavesVisited);
        }
    }

}
