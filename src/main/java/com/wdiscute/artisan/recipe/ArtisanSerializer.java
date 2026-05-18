package com.wdiscute.artisan.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.List;

public class ArtisanSerializer<T extends AbstractArtisanRecipe> implements RecipeSerializer<T>
{
    private final AbstractArtisanRecipe.Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public ArtisanSerializer(AbstractArtisanRecipe.Factory<T> factory)
    {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                                Codec.INT.fieldOf("processing_days").forGetter(recipe -> recipe.processing_days)
                        )
                        .apply(instance, factory::create)
        );

        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    private T fromNetwork(RegistryFriendlyByteBuf buffer)
    {
        List<Ingredient> ingredient = Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()).decode(buffer);
        ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
        int days = buffer.readVarInt();
        return this.factory.create(ingredient, itemstack, days);
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, T recipe)
    {
        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()).encode(buffer, recipe.ingredients);
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        buffer.writeFloat(recipe.processing_days);
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
