package com.wdiscute.artisan.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public class ArtisanRecipeInput implements RecipeInput
{
    List<ItemStack> items;
    List<ItemStack> upgrades;

    public ArtisanRecipeInput(List<ItemStack> items, List<ItemStack> requiredUpgrades)
    {
        this.items = items;
        this.upgrades = requiredUpgrades;
    }

    public List<ItemStack> getUpgrades()
    {
        return upgrades;
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
