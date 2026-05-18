package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.ArtisanConfig;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.recipe.ArtisanRecipeInput;
import com.wdiscute.artisan.registry.ArtisanRecipeTypes;
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
    private long lastTickDay = -1;
    private int daysRemaining = -1;
    private ItemStack recipeResult = ItemStack.EMPTY;
    private List<ItemStack> items = new ArrayList<>();

    public AbstractDailyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState)
    {
        super(type, pos, blockState);
    }

    public void putItem(ItemStack itemStack)
    {
        items.add(itemStack.copyWithCount(1));
        setChanged();
        if (!level.isClientSide)
            checkForRecipe();
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

        daysRemaining = -1;

        //set back to idle
        level.setBlockAndUpdate(getBlockPos(), level.getBlockState(worldPosition).setValue(AbstractDailyBlock.STATE, AbstractDailyBlock.State.IDLE));
    }

    private void checkForRecipe()
    {
        ArtisanRecipeInput input = new ArtisanRecipeInput(items);

        var recipeFor = level.getRecipeManager().getRecipeFor(ArtisanRecipeTypes.LOOM.get(), input, level);
        if (recipeFor.isPresent())
        {
            AbstractArtisanRecipe recipe = recipeFor.get().value();
            recipeResult = recipe.getResultItem(level.registryAccess());
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(AbstractDailyBlock.STATE, AbstractDailyBlock.State.WORKING));
            //+1 as a recipe put 1 second before daily reset shouldn't be finished
            daysRemaining = recipe.getDays() + 1;
        }
    }

    public List<ItemStack> getItems()
    {
        return items;
    }

    public ItemStack getResultItem()
    {
        return recipeResult;
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
        System.out.println("tick");
    }

    /**
     * ticks daily, adds one to last tick day so if the machine was unloaded all days are ticked
     */
    public void dailyTick(long day)
    {
        System.out.println("daily tick on day " + lastTickDay);
        if (lastTickDay == -1)
            lastTickDay = day;
        else
            lastTickDay++;

        if(level.getBlockState(worldPosition).getValue(AbstractDailyBlock.STATE).equals(AbstractDailyBlock.State.WORKING))
            daysRemaining--;

        if(daysRemaining == 0)
        {
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(AbstractDailyBlock.STATE, AbstractDailyBlock.State.HARVESTABLE));
        }
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
        lastTickDay = tag.getLong("last_tick_day");
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
