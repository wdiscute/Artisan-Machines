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

public class WineKegRecipe extends AbstractArtisanRecipe
{
    public WineKegRecipe(List<Ingredient> ingredients, List<ChancedStack> result, int processing_hours, List<ResourceLocation> requiredUpgrades)
    {
        super(ArtisanRecipeTypes.WINE_KEG.get(), ingredients, result, processing_hours, requiredUpgrades);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.WINE_KEG);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.WINE_KEG.get();
    }

}
