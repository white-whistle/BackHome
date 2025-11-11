package com.whitewhistle.backhome.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

public class HomePlotSystem {

    private static final int CHUNK_SIZE = 16;
    public static final int PLOT_SIZE = 8 * CHUNK_SIZE; // chunk size needs to be even for chunks to align on the grid
    public static final int MIN_HEIGHT = 0;
    public static final int MAX_HEIGHT = PLOT_SIZE;
    public static final int BARRIER_THICKNESS = 2;

    public static Vector2i worldToGridCoordinate(BlockPos blockPos) {
        return new Vector2i(
                blockPos.getX() / PLOT_SIZE,
                blockPos.getZ() / PLOT_SIZE
        );
    }

    public static Vector2i plotIndexToGridCoordinate(int index) {
        if (index == 0) return new Vector2i(0, 0);

        // Determine which "layer" (square ring) the index lies in
        int layer = (int) Math.ceil((Math.sqrt(index + 1) - 1) / 2);
        int legLen = 2 * layer;
        int maxVal = (2 * layer + 1) * (2 * layer + 1);
        int diff = maxVal - index;

        int side = diff / legLen;   // which side of the square we are on
        int offset = diff % legLen; // how far along that side we are

        int x = 0, y = 0;

        switch (side) {
            case 0: // right side
                x = -layer + offset;
                y = layer;
                break;
            case 1: // top side
                x = layer;
                y = layer - offset;
                break;
            case 2: // left side
                x = layer - offset;
                y = -layer;
                break;
            case 3: // bottom side
                x = -layer;
                y = -layer + offset;
                break;
        }


        return new Vector2i(x, y);
    }

    public static Vec3d getPlotSpawnPoint(Vector2i plotPos) {
        return new Vec3d(
                (plotPos.x * PLOT_SIZE) + (PLOT_SIZE / 2.0),
                MIN_HEIGHT + (PLOT_SIZE / 2.0),
                (plotPos.y * PLOT_SIZE) + (PLOT_SIZE / 2.0)
        );
    }
}
