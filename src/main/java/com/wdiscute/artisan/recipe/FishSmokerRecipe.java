package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class FishSmokerRecipe extends AbstractArtisanRecipe
{
    public FishSmokerRecipe(List<Ingredient> ingredients, List<ChancedStack> result, int processing_hours, List<ResourceLocation> requiredUpgrades)
    {
        super(ArtisanRecipeTypes.FISH_SMOKER.get(), ingredients, result, processing_hours, requiredUpgrades);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.FISH_SMOKER);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.FISH_SMOKER.get();
    }

}
