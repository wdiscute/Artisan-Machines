package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class CrystalariumBlockEntity extends AbstractDailyBlockEntity
{
    public CrystalariumBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ArtisanBlockEntities.CRYSTALARIUM.get(), blockPos, blockState);
    }

    @Override
    public RecipeType<? extends AbstractArtisanRecipe> getRecipeType()
    {
        return ArtisanRecipeTypes.CRYSTALARIUM.get();
    }
}
