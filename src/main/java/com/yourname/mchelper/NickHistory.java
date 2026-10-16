package com.yourname.mchelper;

import java.util.ArrayList;
import java.util.List;

public class NickHistory {
    private static final int MAX = 11;

    public static class Entry {
        public final String nick;
        public final long ts;
        public Entry(String nick, long ts) {
            this.nick = nick;
            this.ts = ts;
        }
    }

    private static final List<Entry> HISTORY = new ArrayList<>();
    private static int selected = -1;
    private static boolean userSelected = false;

    public static synchronized void add(String nick) {
        HISTORY.removeIf(e -> e.nick.equalsIgnoreCase(nick));
        HISTORY.add(new Entry(nick, System.currentTimeMillis()));

        while (HISTORY.size() > MAX) {
            HISTORY.remove(0);
            if (selected > 0) selected--;
        }

        if (!userSelected || selected < 0 || selected >= HISTORY.size()) {
            selected = HISTORY.size() - 1;
        }
    }

    public static synchronized List<String> snapshot() {
        List<String> out = new ArrayList<>();
        for (Entry e : HISTORY) out.add(e.nick);
        return out;
    }

    public static synchronized String getSelected() {
        if (HISTORY.isEmpty()) return "";
        if (selected < 0 || selected >= HISTORY.size()) selected = HISTORY.size() - 1;
        return HISTORY.get(selected).nick;
    }

    public static synchronized int getSelectedIndex() {
        return selected;
    }

    public static synchronized void move(int delta) {
        if (HISTORY.isEmpty()) return;
        int n = selected + delta;
        if (n < 0) n = 0;
        if (n >= HISTORY.size()) n = HISTORY.size() - 1;
        selected = n;
        userSelected = true;
    }

    public static synchronized void reset() {
        HISTORY.clear();
        selected = -1;
        userSelected = false;
    }
}
