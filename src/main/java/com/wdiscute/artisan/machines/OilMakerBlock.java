package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OilMakerBlock extends AbstractDailyBlock
{
    public OilMakerBlock()
    {
        super(Properties.of()
                .destroyTime(2)
        );
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return ArtisanBlockEntities.OIL_MAKER.get().create(blockPos, blockState);
    }
}
