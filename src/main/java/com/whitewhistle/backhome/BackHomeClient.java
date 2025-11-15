package com.whitewhistle.backhome;

import com.whitewhistle.backhome.client.ModKeyBindings;
import com.whitewhistle.backhome.client.render.TurtleShellArmorModel;
import com.whitewhistle.backhome.items.GlobalItemTooltipHandler;
import com.whitewhistle.backhome.items.custom.TurtleShellArmorItem;
import net.fabricmc.api.ClientModInitializer;
import software.bernie.geckolib.event.armor.GeoArmorPreRenderEvent;

public class BackHomeClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeyBindings.init();

        GeoArmorPreRenderEvent.EVENT.register((armorPreRenderEvent) -> {
            if (armorPreRenderEvent.getRenderer() instanceof TurtleShellArmorModel.Renderer<?>) {

                // only render turtle shell when not inside the house dimension
                return !((Boolean) armorPreRenderEvent.<Boolean>getRenderData(TurtleShellArmorItem.IS_IN_HOUSE));
            }

            return false;
        });

        GlobalItemTooltipHandler.init();
    }
}
