package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.registry.ArtisanBlocks;
import com.wdiscute.artisan.registry.ArtisanRecipeSerializers;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public class RecyclingMachineRecipe extends AbstractArtisanRecipe
{
    public RecyclingMachineRecipe(List<Ingredient> ingredients, ItemStack result, int processing_hours)
    {
        super(ArtisanRecipeTypes.RECYCLING_MACHINE.get(), ingredients, result, processing_hours);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.RECYCLING_MACHINE);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.RECYCLING_MACHINE.get();
    }

}
