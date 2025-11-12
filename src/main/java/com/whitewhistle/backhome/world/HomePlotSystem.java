package com.whitewhistle.backhome.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

public class HomePlotSystem {

    private static final int CHUNK_SIZE = 16;
    public static final int PLOT_SIZE = 8 * CHUNK_SIZE; // chunk size needs to be even for chunks to align on the grid
    public static final int MIN_HEIGHT = 0;
    public static final int MAX_HEIGHT = PLOT_SIZE;
    public static final int BARRIER_THICKNESS = 2;

    private static final float HALF_PLOT_SIZE = PLOT_SIZE / 2f;

    public static Vector2i worldToGridCoordinate(BlockPos blockPos) {
        return new Vector2i(
                Math.round((blockPos.getX() - HALF_PLOT_SIZE) / (float)PLOT_SIZE),
                Math.round((blockPos.getZ() - HALF_PLOT_SIZE) / (float)PLOT_SIZE)
        );
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
                MIN_HEIGHT + (PLOT_SIZE / 2.0),
                (plotPos.y * PLOT_SIZE) + (PLOT_SIZE / 2.0)
        );
    }
}
