package com.whitewhistle.backhome.world;

import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

import static com.whitewhistle.backhome.BackHome.MOD_ID;

public class ModPersistentState extends PersistentState {

    public int lastPlotIndex = 0;

    private ModPersistentState() {
    }

    private ModPersistentState(int lastPlotIndex) {
        this.lastPlotIndex = lastPlotIndex;
    }

    private int getTotalDirtBlocksBroken() {
        return lastPlotIndex;
    }

    private static final Codec<ModPersistentState> CODEC = Codec.INT.fieldOf("lastPlotIndex").codec().xmap(
            ModPersistentState::new, // create a new 'ModPersistentState' from the stored number
            ModPersistentState::getTotalDirtBlocksBroken // return the number from the 'ModPersistentState' to be saved
    );

    private static final PersistentStateType<ModPersistentState> type = new PersistentStateType<>(
            MOD_ID,
            ModPersistentState::new, // If there's no 'ModPersistentState' yet create one and refresh variables
            CODEC, // If there is a 'ModPersistentState' NBT, parse it with 'CODEC'
            null // Supposed to be an 'DataFixTypes' enum, but we can just pass null
    );

    public static ModPersistentState getServerState(MinecraftServer server) {
        // (Note: arbitrary choice to use 'World.OVERWORLD' instead of 'World.END' or 'World.NETHER'.  Any work)
        ServerWorld serverWorld = server.getWorld(World.OVERWORLD);
        assert serverWorld != null;

        // The first time the following 'getOrCreate' function is called, it creates a brand new 'ModPersistentState' and
        // stores it inside the 'PersistentStateManager'. The subsequent calls to 'getOrCreate' pass in the saved
        // 'ModPersistentState' NBT on disk to the codec in our type, using the codec to decode the nbt into our state
        ModPersistentState state = serverWorld.getPersistentStateManager().getOrCreate(type);

        // If state is not marked dirty, nothing will be saved when Minecraft closes.
        // Technically it's 'cleaner' if you only mark state as dirty when there was actually a change, but the vast majority
        // of mod writers are just going to be confused when their data isn't being saved, and so it's best just to 'markDirty' for them.
        // Besides, it's literally just setting a bool to true, and the only time there's a 'cost' is when the file is written to disk when
        // there were no actual change to any of the mods state (INCREDIBLY RARE).
        state.markDirty();

        return state;
    }

}
