package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class OilMakerRecipe extends AbstractArtisanRecipe
{
    public OilMakerRecipe(List<Ingredient> ingredients, ItemStack result, int processing_hours)
    {
        super(ArtisanRecipeTypes.OIL_MAKER.get(), ingredients, result, processing_hours);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.OIL_MAKER);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.OIL_MAKER.get();
    }

}
