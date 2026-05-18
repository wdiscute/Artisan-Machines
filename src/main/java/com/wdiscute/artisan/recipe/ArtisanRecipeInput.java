package com.wdiscute.artisan.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public class ArtisanRecipeInput implements RecipeInput
{
    List<ItemStack> items;

    public ArtisanRecipeInput(List<ItemStack> items)
    {
        this.items = items;
    }

    @Override
    public ItemStack getItem(int index)
    {
        return items.get(index);
    }

    @Override
    public int size()
    {
        return items.size();
    }
}
