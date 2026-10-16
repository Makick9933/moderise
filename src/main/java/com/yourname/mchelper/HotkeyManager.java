package com.yourname.mchelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class HotkeyManager {
    public static final Map<Integer, String> ALT_HOTKEYS = new HashMap<>();

    static {
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_1, "#оск");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_2, "#мат");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_3, "#спам");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_4, "#оскрод");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_5, "#амор");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_6, "#фейк");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_7, "#реп");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_8, "#оскпр");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_9, "#рекл");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_0, "#читы");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_Q, "#диск");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_W, "#упом");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_E, "#выдадм");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_T, "#зазыв");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_Y, "#склон");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_U, "#комчит");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_I, "#твинк");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_O, "#дюп");
        ALT_HOTKEYS.put(GLFW.GLFW_KEY_P, "#багоюз");
    }

    public static boolean handle(int key, boolean alt, boolean ctrl) {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (key == GLFW.GLFW_KEY_GRAVE_ACCENT && !alt && !ctrl) {
            ModeManager.cycle();
            return true;
        }

        if (alt && key == GLFW.GLFW_KEY_H) {
            MemoHud.toggle();
            return true;
        }

        if (alt && key == GLFW.GLFW_KEY_R) {
            NickHistory.reset();
            ChatEditor.end("reset");
            return true;
        }

        if (alt && key == GLFW.GLFW_KEY_X) {
            ChatEditor.end("Alt+X");
            return true;
        }

        if (key == GLFW.GLFW_KEY_EQUAL && !alt && !ctrl) {
            NickHud.toggleVisible();
            return true;
        }

        if (key == GLFW.GLFW_KEY_LEFT && !alt && !ctrl) {
            NickHistory.move(-1);
            return true;
        }
        if (key == GLFW.GLFW_KEY_RIGHT && !alt && !ctrl) {
            NickHistory.move(1);
            return true;
        }

        if (alt && ALT_HOTKEYS.containsKey(key)) {
            String code = ALT_HOTKEYS.get(key);
            String nick = NickHistory.getSelected();
            if (nick.isEmpty()) return true;

            if (ChatEditor.append(code)) {
                if (mc.currentScreen instanceof ChatScreen) {
                    mc.setScreen(new ChatScreen(""));
                }
            } else {
                ChatEditor.start(nick, code);
                mc.setScreen(new ChatScreen(""));
            }
            return true;
        }

        return false;
    }
}
