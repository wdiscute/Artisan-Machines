package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class LoomRecipe extends AbstractArtisanRecipe
{
    public LoomRecipe(List<Ingredient> ingredients, ItemStack result, int processing_days)
    {
        super(ArtisanRecipeTypes.LOOM.get(), ingredients, result, processing_days);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(Blocks.LOOM);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.LOOM.get();
    }

}
