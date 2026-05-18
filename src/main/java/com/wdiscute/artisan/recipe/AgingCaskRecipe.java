package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class AgingCaskRecipe extends AbstractArtisanRecipe
{
    public AgingCaskRecipe(List<Ingredient> ingredients, ItemStack result, int processing_days)
    {
        super(ArtisanRecipeTypes.AGING_CASK.get(), ingredients, result, processing_days);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.AGING_CASK);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.AGING_CASK.get();
    }

}
