package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CheesePressBlock extends AbstractMachineBlock
{
    public CheesePressBlock()
    {
        super(Properties.of()
                .destroyTime(2)
        );
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return ArtisanBlockEntities.CHEESE_PRESS.get().create(blockPos, blockState);
    }
}
