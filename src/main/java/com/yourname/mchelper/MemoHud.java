package com.yourname.mchelper;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

public class MemoHud {
    private static boolean visible = false;

    public static void toggle() {
        visible = !visible;
    }

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            if (!visible) return;

            MinecraftClient mc = MinecraftClient.getInstance();
            int x = 10;
            int y = 10;

            context.fill(x - 4, y - 4, x + 220, y + 320, 0xDD000000);
            context.drawTextWithShadow(mc.textRenderer, "ХОТКЕИ", x, y, 0xFFCC00);
            y += 16;

            context.drawTextWithShadow(mc.textRenderer, "~  - режим", x, y, 0x66FF66); y += 12;
            context.drawTextWithShadow(mc.textRenderer, "=  - скрыть", x, y, 0x66FF66); y += 12;
            context.drawTextWithShadow(mc.textRenderer, "<- -> ники", x, y, 0x888888); y += 12;
            context.drawTextWithShadow(mc.textRenderer, "Alt+X - сброс", x, y, 0x66FF66); y += 20;
            context.drawTextWithShadow(mc.textRenderer, "ПРИЧИНЫ", x, y, 0xFFCC00); y += 16;

            for (var rule : Rules.ALL) {
                int c = rule.isBan ? 0xFF6666 : 0x4A90E2;
                context.drawTextWithShadow(mc.textRenderer, rule.code + " " + rule.name, x, y, c);
                y += 12;
                if (y > 300) break;
            }
        });
    }
}
