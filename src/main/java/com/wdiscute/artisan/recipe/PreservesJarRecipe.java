package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class PreservesJarRecipe extends AbstractArtisanRecipe
{
    public PreservesJarRecipe(List<Ingredient> ingredients, List<ChancedStack> result, int processing_hours)
    {
        super(ArtisanRecipeTypes.PRESERVES_JAR.get(), ingredients, result, processing_hours);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.PRESERVES_JAR);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.PRESERVES_JAR.get();
    }

}
