package com.jb.prog.day17;

record Area(int x1, int x2, int y1, int y2) { }

class Probe {
    int x, y, velX, velY;

    public Probe(int x, int y, int velX, int velY) {
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
    }
}

class PartOne {
    public static void main(String[] args) {
        Area area = new Area(217, 240, -126, -69);
        Probe probe = new Probe(0, 0, 24, 0);
        int x = 0;
        while (true) {
            x = probe.velX;
            while (true) {
                if (!findX(probe, area)) {
                    probe.x = 0;
                    probe.velX = --x;
                    probe.velY = 0;
                    probe.y = 0;
                } else break;
            }
            break;
        }
        probe.velY = 10000;
        probe.velX = x;
        probe.y = 0;
        probe.x = 0;
        int y = 10000;
        while (true) {
            while (!fire(probe, area)) {
                if (probe.y < area.y2()) {
                    probe.velX = probe.x > area.x2() ? --x : x;
                    probe.x = 0;
                    probe.velY = --y;
                    probe.y = 0;
                }
            }
            break;
        }
    }


    static boolean findX(Probe probe, Area area) {
        while (true) {
            probe.x += probe.velX;
            if (probe.velX > 0)
                probe.velX--;
            else return probe.x > area.x1() && probe.x < area.x2();
        }

    }

    static boolean fire(Probe probe, Area area) {
        int max = 0;
        while (probe.y > area.y2()) {
            probe.x += probe.velX;
            probe.y += probe.velY;
            if (probe.velX > 0)
                probe.velX--;
            probe.velY--;
            if (probe.y > max) {
                max = probe.y;
            }
            if (probe.x >= area.x1() && probe.x <= area.x2() &&
                    probe.y >= area.y1() && probe.y <= area.y2()) {
                System.out.println("Highest point: \n" + max);
                return true;
            }
        }
        return false;
    }
}
