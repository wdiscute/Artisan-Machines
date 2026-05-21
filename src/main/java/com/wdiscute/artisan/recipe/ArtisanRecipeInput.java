package com.wdiscute.artisan.recipe;

import com.wdiscute.artisan.upgrades.AbstractUpgrade;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public class ArtisanRecipeInput implements RecipeInput
{
    List<ItemStack> items;
    List<AbstractUpgrade> requiredUpgrades;

    public ArtisanRecipeInput(List<ItemStack> items, List<AbstractUpgrade> requiredUpgrades)
    {
        this.items = items;
        this.requiredUpgrades = requiredUpgrades;
    }

    public List<AbstractUpgrade> getRequiredUpgrades()
    {
        return requiredUpgrades;
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
