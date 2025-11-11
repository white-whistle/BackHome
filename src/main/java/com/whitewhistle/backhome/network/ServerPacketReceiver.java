package com.whitewhistle.backhome.network;

import com.whitewhistle.backhome.network.payload.ActivateTurtleArmorPayload;
import com.whitewhistle.backhome.world.ModDimensions;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.World;

import java.util.Set;

public class ServerPacketReceiver {

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(ActivateTurtleArmorPayload.ID, (payload, ctx) -> {
            var player = ctx.player();
            var world = player.getEntityWorld();
            var server = ctx.server();

            var isAtHome = world.getRegistryKey().equals(ModDimensions.HOUSE_WORLD_KEY);

            // TODO: get restore dimension stored on the armor item's DataComponent
            var targetWorld = isAtHome ? server.getWorld(World.OVERWORLD) : server.getWorld(ModDimensions.HOUSE_WORLD_KEY);

            player.teleport(targetWorld, 0,0,0, Set.of() ,0,0,false);
        });
    }
}
