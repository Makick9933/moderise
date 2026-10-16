package com.yourname.mchelper.mixin;

import com.yourname.mchelper.HotkeyManager;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (action != GLFW.GLFW_PRESS) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen != null) return;

        boolean alt = (modifiers & GLFW.GLFW_MOD_ALT) != 0;
        boolean ctrl = (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;

        if (HotkeyManager.handle(key, alt, ctrl)) {
            ci.cancel();
        }
    }
}
