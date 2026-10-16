package com.yourname.mchelper;

import net.fabricmc.api.ClientModInitializer;

public class ChatHelperClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ChatInterceptor.register();
        NickHud.register();
        ToastHud.register();
        MemoHud.register();
    }
}
