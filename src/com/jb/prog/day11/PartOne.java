package com.jb.prog.day11;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Octopus {
    int x, y, energy;
    List<Octopus> adjacent;
    boolean flashed;

    public Octopus(int x, int y, int energy) {
        this.x = x;
        this.y = y;
        this.energy = energy;
        adjacent = new ArrayList<>();
        flashed = false;
    }

    public void addAdjacent(List<Octopus> octopi) {
        for(Octopus o : octopi) {
            if(o.x == x && o.y == y + 1) adjacent.add(o);
            if(o.x == x && o.y == y - 1) adjacent.add(o);
            if(o.x == x - 1 && o.y == y) adjacent.add(o);
            if(o.x == x + 1 && o.y == y) adjacent.add(o);
            if(o.x == x + 1 && o.y == y + 1) adjacent.add(o);
            if(o.x == x - 1 && o.y == y - 1) adjacent.add(o);
            if(o.x == x - 1 && o.y == y + 1) adjacent.add(o);
            if(o.x == x + 1 && o.y == y - 1) adjacent.add(o);
        }
    }
}

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day11/input.txt"), StandardCharsets.UTF_8);
        int h = 0;
        List<Octopus> octopi = new ArrayList<>();
        while (in.hasNextLine()) {
            int[] row = Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
            for(int w = 0; w < row.length; w++) {
                octopi.add(new Octopus(h, w, row[w]));
            }
            h++;
        }
        boolean partTwo = true;
        for(Octopus o : octopi) o.addAdjacent(octopi);
        int ans = 0;
        int simultations = 0;
        int g = partTwo ? 1000 : 200;
        for(int i = 0; i < g; i++) {
            for(Octopus o : octopi) {
                o.energy++;
                if(o.energy == 10) {
                    ans += flash(o);
                }
            }
            int c = 0;
            for (Octopus o : octopi) {
                if(o.flashed) {
                    o.energy = 0;
                    o.flashed = false;
                    c++;
                }
            }
            if(partTwo) {
                if(c == octopi.size()) {
                    simultations = i;
                    break;
                }
            }
        }
        System.out.println(partTwo ? simultations : ans);
    }

    static int flash(Octopus o) {
        o.flashed = true;
        int flashes = 0;

        for(Octopus a : o.adjacent) {
            a.energy++;
            if(a.energy == 10 && !a.flashed) {
                flashes += flash(a);
            }
        }
        return flashes + 1;
    }
}
