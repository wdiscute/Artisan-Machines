package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class AncientCaskRecipe extends AbstractArtisanRecipe
{
    public AncientCaskRecipe(List<Ingredient> ingredients, ItemStack result, int processing_days)
    {
        super(ArtisanRecipeTypes.ANCIENT_CASK.get(), ingredients, result, processing_days);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.ANCIENT_CASK);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.ANCIENT_CASK.get();
    }

}
