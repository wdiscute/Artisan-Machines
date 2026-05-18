package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class WineKegBlockEntity extends AbstractDailyBlockEntity
{
    public WineKegBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ArtisanBlockEntities.WINE_KEG.get(), blockPos, blockState);
    }

    @Override
    public void tick()
    {
        super.tick();
    }

    @Override
    public RecipeType<? extends AbstractArtisanRecipe> getRecipeType()
    {
        return ArtisanRecipeTypes.WINE_KEG.get();
    }

    @Override
    public void hourlyTick(long hour)
    {
        super.hourlyTick(hour);
    }
}
