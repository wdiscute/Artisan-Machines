package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.ArtisanConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDailyBlockEntity extends BlockEntity
{
    private int tickOffset = -1;
    private long lastTickDay = -1;
    private List<ItemStack> items = new ArrayList<>();

    public AbstractDailyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState)
    {
        super(type, pos, blockState);
    }

    public void putItem(ItemStack itemStack)
    {
        items.add(itemStack.copyWithCount(1));
        if (itemStack.getCount() > 1)
            itemStack.shrink(1);
    }

    public List<ItemStack> getItems()
    {
        return items;
    }

    public void setItems(List<ItemStack> list)
    {
        items = list;
    }

    public abstract RecipeType<?> getRecipeType();

    /**
     * ticks with an offset based on the tick delay config - does NOT tick every tick
     */
    public void tick()
    {

    }

    /**
     * ticks daily, adds one to last tick day so if the machine was unloaded all days are ticked
     */
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
        saveAllItems(tag, registries);
    }

    public CompoundTag saveAllItems(CompoundTag tag, HolderLookup.Provider levelRegistry)
    {
        ListTag listtag = new ListTag();

        for (int i = 0; i < items.size(); i++)
        {
            ItemStack itemstack = items.get(i);
            if (!itemstack.isEmpty())
            {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte) i);
                listtag.add(itemstack.save(levelRegistry, compoundtag));
            }
        }

        tag.put("Items", listtag);
        return tag;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        lastTickDay = tag.getLong("last_tick_day");
        loadAllItems(tag, registries);
    }

    public void loadAllItems(CompoundTag tag, HolderLookup.Provider levelRegistry)
    {
        ListTag listtag = tag.getList("Items", 10);

        for (int i = 0; i < listtag.size(); i++)
        {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < items.size())
            {
                items.set(j, ItemStack.parse(levelRegistry, compoundtag).orElse(ItemStack.EMPTY));
            }
        }
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
            long day = (level.getGameTime() + dbe.getTickOffset(level)) / 24000;
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
