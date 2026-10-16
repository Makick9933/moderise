package com.yourname.mchelper;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class ToastHud {
    private static String text = "";
    private static String sub = "";
    private static long showUntil = 0;
    private static int color = 0xFFFFFF;

    public static void show(String main, String subText, int colorHex) {
        text = main;
        sub = subText;
        color = colorHex;
        showUntil = System.currentTimeMillis() + 1200;
    }

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            if (System.currentTimeMillis() > showUntil) return;

            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return;

            int cx = context.getScaledWindowWidth() / 2;
            int y = 60;

            int boxW = mc.textRenderer.getWidth(text) + 40;
            if (mc.textRenderer.getWidth(sub) + 40 > boxW) boxW = mc.textRenderer.getWidth(sub) + 40;

            context.fill(cx - boxW / 2, y - 4, cx + boxW / 2, y + 32, 0xEE0D0D0D);
            context.drawBorder(cx - boxW / 2, y - 4, boxW, 36, color);
            context.drawCenteredTextWithShadow(mc.textRenderer, text, cx, y, 0xFFFFFF);
            context.drawCenteredTextWithShadow(mc.textRenderer, sub, cx, y + 16, 0xAAAAAA);
        });
    }
}
