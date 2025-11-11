package com.whitewhistle.backhome.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.whitewhistle.backhome.world.HomePlotSystem;
import com.whitewhistle.backhome.world.ModDimensions;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

import java.util.Set;

public class ModCommands {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("tpTurtlePlot")
                    .then(CommandManager.argument("index", IntegerArgumentType.integer())
                            .executes((ctx -> {
                                int index = IntegerArgumentType.getInteger(ctx, "index");

                                var source = ctx.getSource();
                                var server = source.getServer();
                                var player = source.getPlayer();
                                if (player == null) return -1;

                                var targetWorld = server.getWorld(ModDimensions.HOUSE_WORLD_KEY);

                                var plotPos = HomePlotSystem.plotIndexToGridCoordinate(index);
                                var plotSpawn = HomePlotSystem.getPlotSpawnPoint(plotPos);

                                player.teleport(targetWorld, plotSpawn.getX(),plotSpawn.getY(),plotSpawn.getZ(), Set.of() ,0,0,false);

                                return 1; // command success
                            }))));
        });
    }
}
