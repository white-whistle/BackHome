package com.whitewhistle.backhome.network;

import com.whitewhistle.backhome.network.payload.ActivateTurtleArmorPayload;
import com.whitewhistle.backhome.world.HomePlotSystem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ServerPacketReceiver {

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(ActivateTurtleArmorPayload.ID, (payload, ctx) -> {
            var player = ctx.player();
            var server = ctx.server();

            HomePlotSystem.handleArmorTrigger(server, player);
        });
    }
}
