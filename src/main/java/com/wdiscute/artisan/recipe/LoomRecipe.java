package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class LoomRecipe extends AbstractArtisanRecipe
{
    public LoomRecipe(List<Ingredient> ingredients, List<ChancedStack> result, int processing_days, List<Ingredient> requiredUpgrades, List<Ingredient> blacklistedUpgrades)
    {
        super(ArtisanRecipeTypes.LOOM.get(), ingredients, result, processing_days, requiredUpgrades, blacklistedUpgrades);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.LOOM);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.LOOM.get();
    }

}
