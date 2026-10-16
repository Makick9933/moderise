package com.yourname.mchelper;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

import java.util.List;

public class NickHud {
    private static boolean visible = true;

    public static void toggleVisible() {
        visible = !visible;
    }

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            if (!visible) return;
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return;

            List<String> nicks = NickHistory.snapshot();
            if (nicks.isEmpty()) return;

            int x = context.getScaledWindowWidth() - 140;
            int y = 20;

            context.fill(x - 4, y - 4, x + 132, y + 20 + nicks.size() * 12, 0xCC000000);
            context.drawTextWithShadow(mc.textRenderer, ModeManager.label(), x, y, ModeManager.color());
            y += 16;

            for (int i = 0; i < nicks.size(); i++) {
                int color = (i == NickHistory.getSelectedIndex()) ? 0xFFFFFF00 : 0xFFAAAAAA;
                context.drawTextWithShadow(mc.textRenderer, nicks.get(i), x, y, color);
                y += 12;
            }
        });
    }
}
