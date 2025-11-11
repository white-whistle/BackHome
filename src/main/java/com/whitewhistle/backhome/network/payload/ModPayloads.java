package com.whitewhistle.backhome.network.payload;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModPayloads {
    public static void init() {
        PayloadTypeRegistry.playC2S().register(ActivateTurtleArmorPayload.ID, ActivateTurtleArmorPayload.CODEC);
    }
}
