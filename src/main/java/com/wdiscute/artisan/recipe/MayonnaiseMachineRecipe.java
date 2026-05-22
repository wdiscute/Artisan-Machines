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

public class MayonnaiseMachineRecipe extends AbstractArtisanRecipe
{
    public MayonnaiseMachineRecipe(List<Ingredient> ingredients, List<ChancedStack> result, int processing_hours, List<Ingredient> requiredUpgrades, List<Ingredient> blacklistedUpgrades)
    {
        super(ArtisanRecipeTypes.MAYONNAISE_MACHINE.get(), ingredients, result, processing_hours, requiredUpgrades, blacklistedUpgrades);
    }

    @Override
    public ItemStack getToastSymbol()
    {
        return new ItemStack(ArtisanBlocks.MAYONNAISE_MACHINE);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ArtisanRecipeSerializers.MAYONNAISE_MACHINE.get();
    }

}
