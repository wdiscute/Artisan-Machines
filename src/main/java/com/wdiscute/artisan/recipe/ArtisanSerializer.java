package com.wdiscute.artisan.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class ArtisanSerializer<T extends AbstractArtisanRecipe> implements RecipeSerializer<T>
{
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public ArtisanSerializer(AbstractArtisanRecipe.Factory<T> factory)
    {
        this.codec = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Codec.INT.fieldOf("processing_hours").forGetter(recipe -> recipe.processing_hours)
                        )
                        .apply(instance, factory::create)
        );

        this.streamCodec = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), o -> o.ingredients,
                ItemStack.STREAM_CODEC, o -> o.result,
                ByteBufCodecs.INT, o -> o.processing_hours,
                factory::create
        );
    }

    @Override
    public MapCodec<T> codec()
    {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec()
    {
        return this.streamCodec;
    }
}
