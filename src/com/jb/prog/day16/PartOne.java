package com.jb.prog.day16;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Literal {
    long val;
    int length;

    public Literal(long val, int length) {
        this.val = val;
        this.length = length;
    }

}

class Packet {
    int version;
    int typeId;
    long literal;
    String binary;

    List<Packet> subPackets;

    public Packet(int version, int typeId, String binary) {
        this.version = version;
        this.typeId = typeId;
        this.binary = binary;
        subPackets = new ArrayList<>();
    }
}

public class PartOne {
    static String binary;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("data/day16/input.txt"), StandardCharsets.UTF_8);
        String input = in.nextLine();
        StringBuilder sb = new StringBuilder();
        for (String c : input.split("")) {
            sb.append(Integer.toBinaryString(1 << 4 | (Integer.parseInt(c, 16))).substring(1));
        }
        binary = sb.toString();
        Packet packet = readPacket();
        calculatePacketSum(packet);
        System.out.println(packet.literal);
    }

    static long calculatePacketSum(Packet packet) {
        if (packet.typeId == 4) return packet.literal;
        switch (packet.typeId) {
            case 0 -> {
                long n = 0;
                for(Packet p : packet.subPackets) n += calculatePacketSum(p);
                packet.literal = n;
            }
            case 1 -> {
                long n = 1;
                for(Packet p : packet.subPackets) n*= calculatePacketSum(p);
                packet.literal = n;
            }
            case 2 -> {
                long n = Long.MAX_VALUE;
                for (Packet p : packet.subPackets) n = Math.min(n, calculatePacketSum(p));
                packet.literal = n;
            }
            case 3 -> {
                long n = 0;
                for (Packet p : packet.subPackets) n = Math.max(n, calculatePacketSum(p));
                packet.literal = n;
            }
            case 5 -> {
                long a = calculatePacketSum(packet.subPackets.get(0));
                long b = calculatePacketSum(packet.subPackets.get(1));
                packet.literal = a > b ? 1 : 0;
            }
            case 6 -> {
                long a = calculatePacketSum(packet.subPackets.get(0));
                long b = calculatePacketSum(packet.subPackets.get(1));
                packet.literal = a < b ? 1 : 0;
            }
            case 7 -> {
                long a = calculatePacketSum(packet.subPackets.get(0));
                long b = calculatePacketSum(packet.subPackets.get(1));
                packet.literal = a == b ? 1 : 0;
            }

        }
        return packet.literal;
    }


    static List<Packet> readPacket(int lengthTypeId, int length) {
        List<Packet> subPackets = new ArrayList<>();
        int l = binary.length() - length;
        if (lengthTypeId == 0) {
            while (binary.length() != l) {
                Packet packet = readPacket();
                length -= packet.binary.length();
                subPackets.add(packet);
            }

        } else {
            for (int i = 0; i < length; i++) {
                Packet packet = readPacket();
                subPackets.add(packet);
            }
        }
        return subPackets;
    }

    static Packet readPacket() {
        int packetVersion = Integer.parseInt(binary.substring(0, 3), 2);
        int packetTypeID = Integer.parseInt(binary.substring(3, 6), 2);
        Packet packet = new Packet(packetVersion, packetTypeID, binary.substring(0, 6));
        binary = binary.substring(6);
        switch (packetTypeID) {
            case 4 -> {
                Literal literal = readLiteralValue(binary);
                packet.binary += binary.substring(0, literal.length);
                binary = binary.substring(literal.length);
                packet.literal = literal.val;
            }
            default -> {
                char c = binary.charAt(0);
                packet.binary += c;
                binary = binary.substring(1);
                if (c == '0') {
                    int bitsInSubPackets = Integer.parseInt(binary.substring(0, 15), 2);
                    packet.binary += binary.substring(0, 15);
                    binary = binary.substring(15);
                    packet.subPackets.addAll(readPacket(0, bitsInSubPackets));
                } else {
                    int numberOfSubPackets = Integer.parseInt(binary.substring(0, 11), 2);
                    packet.binary += binary.substring(0, 11);
                    binary = binary.substring(11);
                    packet.subPackets.addAll(readPacket(1, numberOfSubPackets));
                }
            }
        }
        return packet;
    }

    static Literal readLiteralValue(String input) {
        int l = 0;
        StringBuilder sb = new StringBuilder();
        while (true) {
            l+=5;
            sb.append(input, 1, 5);
            if (input.charAt(0) == '0') {
                break;
            }
            input = input.substring(5);
        }
        long k = Long.parseLong(sb.toString(), 2);
        return new Literal(k, l);
    }
}
