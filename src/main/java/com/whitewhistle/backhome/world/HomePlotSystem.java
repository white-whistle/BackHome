package com.whitewhistle.backhome.world;

import com.whitewhistle.backhome.items.ModComponents;
import com.whitewhistle.backhome.items.ModItems;
import com.whitewhistle.backhome.items.components.PlotComponent;
import com.whitewhistle.backhome.items.components.TurtleShellComponent;
import com.whitewhistle.backhome.items.custom.TurtleShellArmorItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

import java.util.Set;

public class HomePlotSystem {

    private static final int CHUNK_SIZE = 16;
    public static final int AIR_POCKET_SIZE = 10;
    public static final int PLOT_SIZE = 8 * CHUNK_SIZE; // chunk size needs to be even for chunks to align on the grid
    public static final int MIN_HEIGHT = 0;
    public static final int MAX_HEIGHT = PLOT_SIZE;
    public static final int BARRIER_THICKNESS = 2;

    private static final float HALF_PLOT_SIZE = PLOT_SIZE / 2f;

    public static ItemStack createNextPlotDeed(MinecraftServer server) {
        var serverState = ModPersistentState.getServerState(server);

        var stack = createPlotDeed(serverState.lastPlotIndex);

        serverState.lastPlotIndex++;

        return stack;
    }

    public static ItemStack createPlotDeed(int index) {
        var stack = ModItems.TURTLE_DEED.getDefaultStack();
        stack.set(ModComponents.PLOT_TYPE, new PlotComponent(index));

        return stack;
    }

    public static void handleArmorTrigger(MinecraftServer server, ServerPlayerEntity player) {
        var turtleArmorStack = player.getEquippedStack(EquipmentSlot.CHEST);
        if (turtleArmorStack.isEmpty()) return;

        var cooldownManager = player.getItemCooldownManager();
        if (cooldownManager.isCoolingDown(turtleArmorStack)) return;

        // cooldown for 1 second, to avoid miss-fire
        cooldownManager.set(turtleArmorStack, 20);

        var shellData = turtleArmorStack.get(ModComponents.TURTLE_SHELL_TYPE);

        var world = player.getEntityWorld();

        var isAtHome = world.getRegistryKey().equals(ModDimensions.HOUSE_WORLD_KEY);

        if (isAtHome) {
            boolean noShellData = shellData == null || shellData.worldKey() == null || shellData.worldPos() == null;
            if (noShellData) return;

            movePlayerOutOfPlot(player, turtleArmorStack);
        } else {
            shellData = new TurtleShellComponent(new Vec3d(player.getX(), player.getY(), player.getZ()), world.getRegistryKey());
            turtleArmorStack.set(ModComponents.TURTLE_SHELL_TYPE, shellData);

            movePlayerToPlot(player, turtleArmorStack);
        }
    }

    public static Vector2i worldToGridCoordinate(BlockPos blockPos) {
        return new Vector2i(
                Math.round((blockPos.getX() - HALF_PLOT_SIZE) / (float) PLOT_SIZE),
                Math.round((blockPos.getZ() - HALF_PLOT_SIZE) / (float) PLOT_SIZE)
        );
    }

    public static boolean isPlayerInHouseDim(PlayerEntity player) {
        var world = player.getEntityWorld();
        return world.getRegistryKey().equals(ModDimensions.HOUSE_WORLD_KEY);
    }

    public static int getArmorPlotIndex(ItemStack stack) {
        var deedStack = TurtleShellArmorItem.getDeed(stack);
        if (deedStack.isEmpty()) return 0;

        var plotData = deedStack.get(ModComponents.PLOT_TYPE);
        if (plotData == null) return 0;

        return plotData.plot();
    }

    public static void movePlayerToPlot(PlayerEntity player, ItemStack armorStack) {
        var idx = getArmorPlotIndex(armorStack);

        var world = player.getEntityWorld();
        if (!(world instanceof ServerWorld serverWorld)) return;

        var server = serverWorld.getServer();

        var plotPos = HomePlotSystem.plotIndexToGridCoordinate(idx);
        var targetWorld = server.getWorld(ModDimensions.HOUSE_WORLD_KEY);
        var targetPos = HomePlotSystem.getPlotSpawnPoint(plotPos);

        player.teleport(targetWorld, targetPos.getX(), targetPos.getY(), targetPos.getZ(), Set.of(), player.getYaw(), player.getPitch(), false);
    }

    public static void movePlayerOutOfPlot(PlayerEntity player, ItemStack armorStack) {
        var world = player.getEntityWorld();
        if (!(world instanceof ServerWorld serverWorld)) return;

        var shellData = armorStack.get(ModComponents.TURTLE_SHELL_TYPE);
        if (shellData == null) return;

        var server = serverWorld.getServer();
        var targetWorld = server.getWorld(shellData.worldKey());
        var targetPos = shellData.worldPos();

        player.teleport(targetWorld, targetPos.getX(), targetPos.getY(), targetPos.getZ(), Set.of(), player.getYaw(), player.getPitch(), false);
    }

    public static Vector2i plotIndexToGridCoordinate(int index) {
        if (index == 0) return new Vector2i(0, 0);

        // ring (layer) containing index (0-based indices)
        int layer = (int) Math.ceil((Math.sqrt(index + 1) - 1) / 2.0);

        int legLen = 2 * layer;                         // length of each side on this ring
        int maxIndexInLayer = (2 * layer + 1) * (2 * layer + 1) - 1; // largest index in this layer
        int diff = maxIndexInLayer - index;             // how far back from the layer's end

        int side = diff / legLen;   // which side (0..3)
        int offset = diff % legLen; // position along that side

        int x = 0, y = 0;

        // Mapping that yields the spiral: 1 -> (1,0), 2 -> (1,1), 3 -> (0,1), ...
        switch (side) {
            case 0: // rightmost column, going bottom -> top
                x = layer - offset;
                y = -layer;
                break;
            case 1: // leftwards along bottom -> left column
                x = -layer;
                y = -layer + offset;
                break;
            case 2: // top row, left -> right
                x = -layer + offset;
                y = layer;
                break;
            case 3: // right column, top -> bottom
                x = layer;
                y = layer - offset;
                break;
        }

        return new Vector2i(x, y);
    }

    public static int gridCoordinateToPlotIndex(int x, int y) {
        if (x == 0 && y == 0) return 0;

        int layer = Math.max(Math.abs(x), Math.abs(y));
        int legLen = 2 * layer;
        int maxIndexInLayer = (2 * layer + 1) * (2 * layer + 1) - 1;

        int diff = 0;

        if (y == -layer) {
            // bottom side, right→left
            diff = (layer - x);
        } else if (x == -layer) {
            // left side, bottom→top
            diff = legLen + (y + layer);
        } else if (y == layer) {
            // top side, left→right
            diff = 2 * legLen + (x + layer);
        } else if (x == layer) {
            // right side, top→bottom
            diff = 3 * legLen + (layer - y);
        }

        return maxIndexInLayer - diff;
    }

    public static Vec3d getPlotSpawnPoint(Vector2i plotPos) {
        return new Vec3d(
                (plotPos.x * PLOT_SIZE) + (PLOT_SIZE / 2.0),
                MIN_HEIGHT + (PLOT_SIZE / 2.0) - (AIR_POCKET_SIZE / 2.0),
                (plotPos.y * PLOT_SIZE) + (PLOT_SIZE / 2.0)
        );
    }
}
