package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.ArtisanConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AbstractDailyBlockEntity extends BlockEntity
{
    private int tickOffset = -1;
    private long lastTickDay = -1;

    public AbstractDailyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState)
    {
        super(type, pos, blockState);
    }

    public void tick()
    {

    }

    //ticks daily, adds one to last tick day so if the machine was unloaded all days are ticked
    public void dailyTick(long day)
    {
        if (lastTickDay == -1)
            lastTickDay = day;
        else
            lastTickDay++;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putLong("last_tick_day", lastTickDay);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        lastTickDay = tag.getLong("last_tick_day");
    }

    public static <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level)
    {
        if (level.isClientSide) return null;

        return (level0, pos0, state0, blockEntity) ->
        {
            AbstractDailyBlockEntity dbe = ((AbstractDailyBlockEntity) blockEntity);

            //tick with offset
            if (level.getGameTime() + dbe.getTickOffset(level) % ArtisanConfig.TICK_DELAY.get() == 0) dbe.tick();

            //daily tick
            long day = level.getGameTime() + dbe.getTickOffset(level) % 24000;
            if (dbe.lastTickDay < day) dbe.dailyTick(day);
        };
    }

    private int getTickOffset(Level level)
    {
        if (tickOffset != -1) return tickOffset;
        tickOffset = level.getRandom().nextInt(ArtisanConfig.TICK_DELAY.get());
        return tickOffset;
    }

}
