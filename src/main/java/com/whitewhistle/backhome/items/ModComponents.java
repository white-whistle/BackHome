package com.whitewhistle.backhome.items;

import com.whitewhistle.backhome.items.components.ItemStackComponent;
import com.whitewhistle.backhome.items.components.PlotComponent;
import com.whitewhistle.backhome.items.components.TurtleShellComponent;
import com.whitewhistle.backhome.utils.ModIdentifier;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModComponents {
    public static final ComponentType<TurtleShellComponent> TURTLE_SHELL_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            ModIdentifier.of("turtle_shell"),
            ComponentType.<TurtleShellComponent>builder().codec(TurtleShellComponent.CODEC).build()
    );

    public static final ComponentType<ItemStackComponent> BAIT_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            ModIdentifier.of("bait"),
            ComponentType.<ItemStackComponent>builder().codec(ItemStackComponent.CODEC).build()
    );

    public static final ComponentType<PlotComponent> PLOT_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            ModIdentifier.of("plot"),
            ComponentType.<PlotComponent>builder().codec(PlotComponent.CODEC).build()
    );

    public static final ComponentType<ItemStackComponent> DEED_STORAGE_TYPE = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            ModIdentifier.of("deed_storage"),
            ComponentType.<ItemStackComponent>builder().codec(ItemStackComponent.CODEC).build()
    );

    public static void init() {

    }
}
