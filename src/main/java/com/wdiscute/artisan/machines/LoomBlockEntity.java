package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class LoomBlockEntity extends AbstractDailyBlockEntity
{
    public LoomBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ArtisanBlockEntities.LOOM.get(), blockPos, blockState);
    }

    @Override
    public void tick()
    {
        super.tick();
        System.out.println("tick");
    }

    @Override
    public RecipeType<?> getRecipeType()
    {
        return ArtisanRecipeTypes.LOOM.get();
    }

    @Override
    public void dailyTick(long day)
    {
        super.dailyTick(day);
        System.out.println("daily");
    }
}
