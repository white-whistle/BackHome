package com.whitewhistle.backhome;

import com.whitewhistle.backhome.client.ModKeyBindings;
import net.fabricmc.api.ClientModInitializer;

public class BackHomeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeyBindings.init();
    }
}
