package com.yourname.mchelper.mixin;

import com.yourname.mchelper.ChatEditor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Shadow protected TextFieldWidget chatField;

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        if (ChatEditor.active) {
            String cmd = ChatEditor.buildCommand();
            if (!cmd.isEmpty()) {
                chatField.setText(cmd);
                chatField.setCursorToEnd();
            }
        }
    }
}
