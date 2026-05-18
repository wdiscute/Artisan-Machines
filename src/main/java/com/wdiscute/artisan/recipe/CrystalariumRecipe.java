package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class CrystalariumRecipe extends AbstractArtisanRecipe
{
    public CrystalariumRecipe(List<Ingredient> ingredients, ItemStack result, int processing_hours)
    {
        super(ArtisanRecipeTypes.CRYSTALARIUM.get(), ingredients, result, processing_hours);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.CRYSTALARIUM);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.CRYSTALARIUM.get();
    }

}
