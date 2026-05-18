package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.ArtisanConfig;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.recipe.ArtisanRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDailyBlockEntity extends BlockEntity
{
    private int tickOffset = -1;
    private long lastTickHour = -1;
    private int hoursRemaining = -1;
    private ItemStack recipeResult = ItemStack.EMPTY;
    private List<ItemStack> items = new ArrayList<>();

    public AbstractDailyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState)
    {
        super(type, pos, blockState);
    }

    public int getHoursRemaining()
    {
        return hoursRemaining;
    }

    public void putItem(ItemStack itemStack)
    {
        items.add(itemStack.copyWithCount(1));
        if (!level.isClientSide)
            checkForRecipe();
        setChanged();
    }

    public void harvest()
    {
        //spawn result item
        Vec3 vec3 = Vec3.atLowerCornerWithOffset(worldPosition, 0.5, 1.01, 0.5).offsetRandom(level.random, 0.7F);
        ItemEntity itementity = new ItemEntity(level, vec3.x(), vec3.y(), vec3.z(), getResultItem());
        itementity.setDefaultPickUpDelay();
        level.addFreshEntity(itementity);

        items.clear();
        recipeResult = ItemStack.EMPTY;

        hoursRemaining = -1;

        //set back to idle
        level.setBlockAndUpdate(getBlockPos(), level.getBlockState(worldPosition).setValue(AbstractDailyBlock.STATE, AbstractDailyBlock.State.IDLE));
        setChanged();
    }

    private void checkForRecipe()
    {
        ArtisanRecipeInput input = new ArtisanRecipeInput(items);

        var recipeFor = level.getRecipeManager().getRecipeFor(getRecipeType(), input, level);
        if (recipeFor.isPresent())
        {
            AbstractArtisanRecipe recipe = recipeFor.get().value();
            recipeResult = recipe.getResultItem(level.registryAccess());
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(AbstractDailyBlock.STATE, AbstractDailyBlock.State.WORKING));
            //+1 as a recipe put 1 second before daily reset shouldn't be finished
            hoursRemaining = recipe.getHours() + 1;
            lastTickHour = -1;
        }
        setChanged();
    }

    public List<ItemStack> getItems()
    {
        return items;
    }

    public ItemStack getResultItem()
    {
        return recipeResult;
    }

    public abstract RecipeType<? extends AbstractArtisanRecipe> getRecipeType();

    /**
     * ticks with an offset based on the tick delay config - does NOT tick every tick
     */
    public void tick()
    {
        System.out.println("tick");
    }

    /**
     * ticks daily, adds one to last tick hour so if the machine was unloaded all days are ticked
     */
    public void hourlyTick(long hour, BlockState state)
    {
        if (lastTickHour == -1)
            lastTickHour = hour;
        else
            lastTickHour++;

        System.out.println("Hourly tick on hour " + lastTickHour + " for machine " + this);

        //if working
        if (state.getValue(AbstractDailyBlock.STATE).equals(AbstractDailyBlock.State.WORKING))
            hoursRemaining--;

        //if recipe finished
        if (hoursRemaining == 0)
        {
            System.out.println("Completed Recipe in " + this.getBlockPos() + " for an output of " + recipeResult);
            level.setBlockAndUpdate(worldPosition, state.setValue(AbstractDailyBlock.STATE, AbstractDailyBlock.State.HARVESTABLE));
        }

        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putLong("last_tick_hour", lastTickHour);
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

        //save recipe result
        if (!recipeResult.isEmpty())
        {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.putByte("Slot", (byte) 99);
            listtag.add(recipeResult.save(levelRegistry, compoundtag));
        }

        tag.put("Items", listtag);
        return tag;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        lastTickHour = tag.getLong("last_tick_hour");
        loadAllItems(tag, registries);
    }

    public void loadAllItems(CompoundTag tag, HolderLookup.Provider levelRegistry)
    {
        items.clear();
        ListTag listtag = tag.getList("Items", 10);

        for (int i = 0; i < listtag.size(); i++)
        {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j == (byte) 99)
                recipeResult = ItemStack.parse(levelRegistry, compoundtag).orElse(ItemStack.EMPTY);
            else
                items.add(ItemStack.parse(levelRegistry, compoundtag).orElse(ItemStack.EMPTY));
        }
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider)
    {
        super.onDataPacket(net, pkt, lookupProvider);
        loadAllItems(pkt.getTag(), lookupProvider);
    }

    private int getTickOffset(Level level)
    {
        if (tickOffset != -1) return tickOffset;
        tickOffset = level.getRandom().nextInt(ArtisanConfig.TICK_DELAY.get());
        setChanged();
        return tickOffset;
    }

    public static <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state)
    {
        //don't tick on click
        if (level.isClientSide) return null;

        //only tick for working machines
        if (!state.getValue(AbstractDailyBlock.STATE).equals(AbstractDailyBlock.State.WORKING)) return null;

        return (level0, pos0, state0, blockEntity) ->
        {
            AbstractDailyBlockEntity dbe = ((AbstractDailyBlockEntity) blockEntity);

            //tick with offset
            if (level.getGameTime() + dbe.getTickOffset(level) % ArtisanConfig.TICK_DELAY.get() == 0) dbe.tick();

            //daily tick
            long hour = (level.getGameTime() + dbe.getTickOffset(level)) / 1000;
            if (dbe.lastTickHour < hour) dbe.hourlyTick(hour, state);
        };
    }
}
