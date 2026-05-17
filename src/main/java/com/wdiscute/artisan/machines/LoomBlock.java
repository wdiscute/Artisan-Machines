package com.wdiscute.artisan.machines;

import com.mojang.serialization.MapCodec;
import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LoomBlock extends AbstractDailyBlock
{
    public LoomBlock()
    {
        super(Properties.of()
                .destroyTime(2)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return ArtisanBlockEntities.LOOM.get().create(blockPos, blockState);
    }
}
