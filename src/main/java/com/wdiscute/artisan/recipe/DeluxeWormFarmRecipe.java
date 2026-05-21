package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class DeluxeWormFarmRecipe extends AbstractArtisanRecipe
{
    public DeluxeWormFarmRecipe(List<Ingredient> ingredients, List<ChancedStack> result, int processing_hours)
    {
        super(ArtisanRecipeTypes.DELUXE_WORM_FARM.get(), ingredients, result, processing_hours);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.DELUXE_WORM_FARM);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.DELUXE_WORM_FARM.get();
    }

}
