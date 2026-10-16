package com.yourname.mchelper;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatInterceptor {
    private static final Pattern NICK_PATTERN = Pattern.compile("^[A-Za-z0-9_]{3,16}$");
    private static final Pattern AUTHOR_1 = Pattern.compile("\\)\\s*([A-Za-z0-9_]{3,16})\\s*:");
    private static final Pattern AUTHOR_2 = Pattern.compile("\\[([^\\]]+?)\\s*->\\s*[^\\]]+\\]");
    private static final Pattern AUTHOR_3 = Pattern.compile("\\(от\\s+([^\\)]+)\\)");

    public static void register() {
        ClientReceiveMessageEvents.CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
            String raw = message.getString();
            String nick = extractAuthor(raw);
            if (nick != null && NICK_PATTERN.matcher(nick).matches()) {
                NickHistory.add(nick);
            }
        });
    }

    private static String extractAuthor(String body) {
        Matcher m = AUTHOR_1.matcher(body);
        if (m.find()) return m.group(1).trim();
        m = AUTHOR_2.matcher(body);
        if (m.find()) return m.group(1).trim();
        m = AUTHOR_3.matcher(body);
        if (m.find()) return m.group(1).trim();
        return null;
    }
}
