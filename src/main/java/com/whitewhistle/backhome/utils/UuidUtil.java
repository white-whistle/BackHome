package com.whitewhistle.backhome.utils;

import java.util.UUID;

import static com.whitewhistle.backhome.BackHome.MOD_ID;

public class UuidUtil {
    public static UUID from(String name) {
        return UUID.nameUUIDFromBytes((MOD_ID + ":" + name).getBytes());
    }
}