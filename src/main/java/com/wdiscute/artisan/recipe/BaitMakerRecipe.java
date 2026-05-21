package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class BaitMakerRecipe extends AbstractArtisanRecipe
{
    public BaitMakerRecipe(List<Ingredient> ingredients, List<ChancedStack> result, int processing_hours)
    {
        super(ArtisanRecipeTypes.BAIT_MAKER.get(), ingredients, result, processing_hours);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.BAIT_MAKER);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.BAIT_MAKER.get();
    }

}
