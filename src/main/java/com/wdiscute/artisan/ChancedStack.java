package com.wdiscute.artisan;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public record ChancedStack(ItemStack stack, float chance)
{
    public static final Codec<ChancedStack> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStack.CODEC.fieldOf("stack").forGetter(ChancedStack::stack),
                    Codec.FLOAT.fieldOf("chance").forGetter(ChancedStack::chance)
            ).apply(instance, ChancedStack::new));

    public static final Codec<List<ChancedStack>> LIST_CODEC = CODEC.listOf();


    public static final StreamCodec<RegistryFriendlyByteBuf, ChancedStack> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, o -> o.stack,
            ByteBufCodecs.FLOAT, o -> o.chance,
            ChancedStack::new
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, List<ChancedStack>> LIST_STREAM_CODEC =
            STREAM_CODEC.apply(ByteBufCodecs.list());
}
