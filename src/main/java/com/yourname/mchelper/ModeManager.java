package com.yourname.mchelper;

public class ModeManager {
    public enum Mode { KICK, MUTE, BAN }

    private static Mode current = Mode.KICK;

    public static Mode get() { return current; }

    public static void cycle() {
        switch (current) {
            case KICK -> current = Mode.MUTE;
            case MUTE -> current = Mode.BAN;
            case BAN -> current = Mode.KICK;
        }
    }

    public static String label() {
        switch (current) {
            case KICK: return "KICK";
            case MUTE: return "MUTE";
            case BAN: return "BAN";
        }
        return "?";
    }

    public static int color() {
        switch (current) {
            case KICK: return 0xFFCC00;
            case MUTE: return 0x4A90E2;
            case BAN: return 0xFF4444;
        }
        return 0xFFFFFF;
    }
}
