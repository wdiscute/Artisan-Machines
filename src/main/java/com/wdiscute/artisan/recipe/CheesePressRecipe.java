package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class CheesePressRecipe extends AbstractArtisanRecipe
{
    public CheesePressRecipe(List<Ingredient> ingredients, List<ChancedStack> result, int processing_days, List<ResourceLocation> requiredUpgrades)
    {
        super(ArtisanRecipeTypes.CHEESE_PRESS.get(), ingredients, result, processing_days, requiredUpgrades);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.CHEESE_PRESS);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.CHEESE_PRESS.get();
    }

}
