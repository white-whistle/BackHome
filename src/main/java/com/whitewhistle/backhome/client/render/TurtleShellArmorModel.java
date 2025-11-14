package com.whitewhistle.backhome.client.render;

import com.whitewhistle.backhome.items.custom.TurtleShellArmorItem;
import com.whitewhistle.backhome.utils.ModIdentifier;
import com.whitewhistle.backhome.world.ModDimensions;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class TurtleShellArmorModel extends GeoModel<TurtleShellArmorItem> {
    @Override
    public Identifier getModelResource(GeoRenderState geoRenderState) {
        return ModIdentifier.of("turtle_shell_armor");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState geoRenderState) {
        return ModIdentifier.of("textures/armor/turtle_shell_armor.png");
    }

    @Override
    public Identifier getAnimationResource(TurtleShellArmorItem turtleShellArmorItem) {
        return ModIdentifier.of("turtle_shell_armor");
    }

    public static class Renderer<R extends BipedEntityRenderState & GeoRenderState> extends GeoArmorRenderer<TurtleShellArmorItem, R> {
        public Renderer() {
            super(new TurtleShellArmorModel());
        }

        @Override
        public void addRenderData(TurtleShellArmorItem animatable, RenderData relatedObject, R renderState, float partialTick) {
            var isInHouse = false;

            var entity = relatedObject.entity();
            if (entity != null) {
                var world = entity.getEntityWorld();

                isInHouse = world.getRegistryKey().equals(ModDimensions.HOUSE_WORLD_KEY);
            }

            renderState.addGeckolibData(TurtleShellArmorItem.IS_IN_HOUSE, isInHouse);
        }
    }
}
