package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class LoomBlockEntity extends AbstractDailyBlockEntity
{
    public LoomBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(ArtisanBlockEntities.LOOM.get(), blockPos, blockState);
    }



}
