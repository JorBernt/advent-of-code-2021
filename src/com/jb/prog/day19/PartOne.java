package com.jb.prog.day19;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

record Pos(int x, int y, int z) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return x == pos.x && y == pos.y && z == pos.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public Pos add(Pos b) {
        return new Pos(x + b.x(), y + b.y(), z + b.z());
    }

    public Pos sub(Pos b) {
        return new Pos(x - b.x(), y - b.y(), z - b.z());
    }
}

class ProbeScanner {
    int id;
    Set<Pos> beacons;
    List<List<Pos>> combinations;

    public ProbeScanner(int id) {
        this.id = id;
        beacons = new HashSet<>();
        combinations = new ArrayList<>();
    }

    public void addPos(String s) {
        int[] p = Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray();
        beacons.add(new Pos(p[0], p[1], p[2]));
    }
}

public class PartOne {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day19/input.txt"), StandardCharsets.UTF_8);
        List<ProbeScanner> scanners = getScanners(in);
        ProbeScanner scanner0 = scanners.get(0);
        scanners.remove(0);
        for (ProbeScanner scanner : scanners) {
            List<List<Pos>> combs = new ArrayList<>();
            for (Pos p : scanner.beacons) combs.add(getCombinations(p.x(), p.y(), p.z()));
            for (List<Pos> l : combs) {
                int n = 0;
                for (Pos p : l) {
                    if (scanner.combinations.size() == n) scanner.combinations.add(new ArrayList<>());
                    scanner.combinations.get(n).add(p);
                    n++;
                }
            }
        }
        for (ProbeScanner scanner : scanners) {
            match(scanner0, scanner);
        }
        
        System.out.println(scanner0.beacons.size());
    }

    static void match(ProbeScanner a, ProbeScanner b) {
        for (Pos p : a.beacons) {
            for (List<Pos> list : b.combinations) {
                for (Pos t : list) {
                    List<Pos> moved = getMoved(p, t, list);
                    int n = 0;
                    for (Pos m : moved) {
                        if (a.beacons.contains(m)) n++;
                    }
                    if (n > 11) {
                        System.out.println(n);
                        a.beacons.addAll(moved);
                        return;
                    }
                }
            }
        }
    }

    static List<Pos> getMoved(Pos p, Pos a, List<Pos> list) {
        Pos m = p.sub(a);
        List<Pos> ret = new ArrayList<>();
        for (var d : list) {
            ret.add(d.add(m));
        }
        return ret;
    }


    static List<Pos> getCombinations(int x, int y, int z) {
        List<Pos> pos = new ArrayList<>();
        pos.add(new Pos(x, y, z));
        pos.add(new Pos(x, -z, y));
        pos.add(new Pos(x, -y, -z));
        pos.add(new Pos(x, z, -y));
        pos.add(new Pos(z, y, -x));
        pos.add(new Pos(z, x, y));
        pos.add(new Pos(z, -y, x));
        pos.add(new Pos(z, x, -y));
        pos.add(new Pos(y, x, z));
        pos.add(new Pos(y, -z, x));
        pos.add(new Pos(y, -x, -z));
        pos.add(new Pos(y, z, -x));
        pos.add(new Pos(-x, y, -z));
        pos.add(new Pos(-x, z, y));
        pos.add(new Pos(-x, -y, z));
        pos.add(new Pos(-x, -z, -y));
        pos.add(new Pos(-z, y, x));
        pos.add(new Pos(-z, -x, y));
        pos.add(new Pos(-z, -y, -x));
        pos.add(new Pos(-z, -x, -y));
        pos.add(new Pos(-y, x, -z));
        pos.add(new Pos(-y, z, x));
        pos.add(new Pos(-y, -x, z));
        pos.add(new Pos(-y, -z, -x));
        return pos;
    }


    static List<ProbeScanner> getScanners(Scanner in) {
        List<ProbeScanner> scanners = new ArrayList<>();
        ProbeScanner ps = null;
        int id = 0;
        while (in.hasNextLine()) {
            String str = in.nextLine();
            if (str.isEmpty()) continue;
            if (str.contains("scanner")) {
                ps = new ProbeScanner(id++);
                scanners.add(ps);
            } else ps.addPos(str);
        }
        return scanners;
    }
}
