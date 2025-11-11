package com.whitewhistle.backhome.network.payload;

import com.whitewhistle.backhome.utils.ModIdentifier;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public class ActivateTurtleArmorPayload implements CustomPayload {
    public static final Id<ActivateTurtleArmorPayload> ID = new CustomPayload.Id<>(ModIdentifier.of("activate_turtle_armor"));

    public static final PacketCodec<ByteBuf, ActivateTurtleArmorPayload> CODEC = CustomPayload.codecOf(
            // serialize into buf
            (payload, buf) -> {},
            // deserialize from buf
            (payload) -> new ActivateTurtleArmorPayload()
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
