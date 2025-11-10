package com.whitewhistle.backhome.utils;

import net.minecraft.util.Identifier;

import static com.whitewhistle.backhome.BackHome.MOD_ID;

public class ModIdentifier {
    public static Identifier of(String id) {
        return Identifier.of(MOD_ID, id);
    }
}
