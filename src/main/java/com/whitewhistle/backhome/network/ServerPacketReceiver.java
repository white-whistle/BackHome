package com.whitewhistle.backhome.network;

import com.whitewhistle.backhome.effect.ModStatusEffects;
import com.whitewhistle.backhome.network.payload.ActivateTurtleArmorPayload;
import com.whitewhistle.backhome.world.HomePlotSystem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;

public class ServerPacketReceiver {

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(ActivateTurtleArmorPayload.ID, (payload, ctx) -> {
            var player = ctx.player();

            // will transport player after this expires
            player.addStatusEffect(new StatusEffectInstance(ModStatusEffects.TRANSPORT, 20 + 20 + 6, 0, true, false));
        });
    }
}
