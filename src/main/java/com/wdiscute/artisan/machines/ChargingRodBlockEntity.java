package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class ChargingRodBlockEntity extends AbstractMachineBlockEntity
{
    public ChargingRodBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ArtisanBlockEntities.CHARGING_ROD.get(), blockPos, blockState);
    }

    @Override
    public RecipeType<? extends AbstractArtisanRecipe> getRecipeType()
    {
        return ArtisanRecipeTypes.CHARGING_ROD.get();
    }
}
