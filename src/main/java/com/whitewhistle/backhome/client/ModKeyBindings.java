package com.whitewhistle.backhome.client;

import com.whitewhistle.backhome.items.ModItems;
import com.whitewhistle.backhome.network.payload.ActivateTurtleArmorPayload;
import com.whitewhistle.backhome.utils.ModIdentifier;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import org.lwjgl.glfw.GLFW;

import static com.whitewhistle.backhome.BackHome.MOD_ID;

public class ModKeyBindings {
    public static final String KEY_CATEGORY = "key-binds";

    public static void init() {
        var modCategory = KeyBinding.Category.create(ModIdentifier.of(KEY_CATEGORY));

        var activateTurtleArmorKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key." + MOD_ID + ".enter_home",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_H,
                        modCategory
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (activateTurtleArmorKey.isPressed()) {
                var player = client.player;
                if (player == null) return;

                if (!player.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.TURTLE_ARMOR)) return;

                ClientPlayNetworking.send(new ActivateTurtleArmorPayload());
            }
        });
    }
}
