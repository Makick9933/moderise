package com.yourname.mchelper;

import java.util.ArrayList;
import java.util.List;

public class ChatEditor {
    public static boolean active = false;
    public static String nick = "";
    public static ModeManager.Mode mode = ModeManager.Mode.KICK;
    public static final List<String> codes = new ArrayList<>();
    public static long lastTs = 0;
    public static final long TIMEOUT_MS = 8000;

    public static void start(String nickName, String code) {
        active = true;
        nick = nickName;
        mode = ModeManager.get();
        codes.clear();
        codes.add(code);
        lastTs = System.currentTimeMillis();
    }

    public static boolean append(String code) {
        if (!active) return false;
        if (System.currentTimeMillis() - lastTs > TIMEOUT_MS) {
            end("timeout");
            return false;
        }
        if (codes.contains(code)) codes.remove(code);
        else codes.add(code);
        lastTs = System.currentTimeMillis();
        return true;
    }

    public static void end(String reason) {
        active = false;
        nick = "";
        codes.clear();
        lastTs = 0;
    }

    public static String buildCommand() {
        if (nick.isEmpty() || codes.isEmpty()) return "";
        String joined = String.join(" + ", codes);

        switch (mode) {
            case KICK:
                return "/kick " + nick + " #предмут + " + joined;
            case MUTE:
                String dur = Rules.durationForCombo(codes);
                if (dur.equals("permanent")) dur = "24h";
                return "/tempmute " + nick + " " + dur + " " + joined;
            case BAN:
                boolean anyPerm = false;
                for (String c : codes) {
                    if (Rules.durationSeconds(c) == 0) { anyPerm = true; break; }
                }
                if (anyPerm) return "/ban " + nick + " " + joined;
                else return "/tempban " + nick + " " + Rules.durationForCombo(codes) + " " + joined;
        }
        return "";
    }
}
