package com.wdiscute.artisan.machines;

import com.wdiscute.artisan.ArtisanConfig;
import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.recipe.ArtisanRecipeInput;
import com.wdiscute.artisan.registry.ArtisanDataMaps;
import com.wdiscute.artisan.upgrades.AbstractUpgrade;
import com.wdiscute.artisan.upgrades.EmptyUpgrade;
import com.wdiscute.artisan.upgrades.MachineUpgrade;
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
import net.minecraft.world.item.crafting.Ingredient;
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

public abstract class AbstractMachineBlockEntity extends BlockEntity
{
    public int tickOffset = -1;
    public long lastTickHour = -1;
    public int hoursRemaining = -1;
    public List<ChancedStack> recipeResult = List.of();
    public List<ItemStack> items = new ArrayList<>();
    public List<ItemStack> upgrades = new ArrayList<>();

    public AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState)
    {
        super(type, pos, blockState);
    }

    public int getHoursRemaining()
    {
        return hoursRemaining;
    }

    public boolean shouldTickNonWorking()
    {
        return false;
    }

    public void putItem(ItemStack itemStack)
    {
        items.add(itemStack.copyWithCount(1));
        if (!level.isClientSide)
            checkForRecipe();
        setChanged();
    }

    public void putUpgrade(ItemStack itemStack, MachineUpgrade machineUpgrade)
    {
        upgrades.add(itemStack.copyWithCount(1));
        machineUpgrade.upgrades().forEach(o -> o.onAdd(this));
        setChanged();
    }

    public List<AbstractUpgrade> getUpgrades()
    {
        //gets every upgrade from the items stored using the datamap
        List<List<AbstractUpgrade>> list = upgrades.stream().map(o -> ArtisanDataMaps.getOrDefault(o).upgrades()).toList();
        List<AbstractUpgrade> upgrades = new ArrayList<>();
        list.forEach(upgrades::addAll);
        return upgrades;
    }

    public List<ItemStack> getHarvestResults()
    {
        List<ItemStack> results = new ArrayList<>();

        for (ChancedStack chancedStack : recipeResult)
        {
            if(level.getRandom().nextFloat() < chancedStack.chance())
            {
                results.add(chancedStack.stack());
            }
        }

        for (AbstractUpgrade upgrade : getUpgrades())
        {
            results = upgrade.modifyHarvestResult(this, results);
        }
        return results;
    }

    public void harvest(Level level)
    {
        getUpgrades().forEach(o -> o.onHarvest(this));

        //spawn items
        getHarvestResults().forEach(o ->
        {
            Vec3 vec3 = Vec3.atLowerCornerWithOffset(worldPosition, 0.5, 1.01, 0.5).offsetRandom(level.random, 0.7F);
            ItemEntity itementity = new ItemEntity(level, vec3.x(), vec3.y(), vec3.z(), o);
            itementity.setDefaultPickUpDelay();
            level.addFreshEntity(itementity);
        });

        items.clear();
        recipeResult = List.of();

        hoursRemaining = -1;

        //set back to idle
        level.setBlockAndUpdate(getBlockPos(), level.getBlockState(worldPosition).setValue(AbstractMachineBlock.STATE, AbstractMachineBlock.State.IDLE));
        setChanged();
        getUpgrades().forEach(o -> o.onFinishedHarvest(this));
    }

    public void checkForRecipe()
    {
        ArtisanRecipeInput input = new ArtisanRecipeInput(items, upgrades);

        var recipeFor = level.getRecipeManager().getRecipeFor(getRecipeType(), input, level);
        if (recipeFor.isPresent())
        {
            AbstractArtisanRecipe recipe = recipeFor.get().value();
            recipeResult = recipe.getResult();
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(AbstractMachineBlock.STATE, AbstractMachineBlock.State.WORKING));
            //+1 as a recipe put 1 second before daily reset shouldn't be finished
            hoursRemaining = recipe.getHours() + 1;
            lastTickHour = -1;
            getUpgrades().forEach(o -> o.onRecipeStarted(recipe, this));
        }

        setChanged();
    }

    public List<ItemStack> getItems()
    {
        return items;
    }

    public List<ChancedStack> getResultItem()
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
        getUpgrades().forEach(o -> o.onHourlyTick(this, hour));

        if (lastTickHour == -1)
            lastTickHour = hour;
        else
            lastTickHour++;

        //if working
        if (state.getValue(AbstractMachineBlock.STATE).equals(AbstractMachineBlock.State.WORKING))
            hoursRemaining--;

        //if recipe finished
        if (hoursRemaining == 0)
        {
            System.out.println("Completed Recipe in " + this.getBlockPos() + " for an output of " + recipeResult);
            level.setBlockAndUpdate(worldPosition, state.setValue(AbstractMachineBlock.STATE, AbstractMachineBlock.State.HARVESTABLE));
        }

        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putLong("last_tick_hour", lastTickHour);
        tag.putInt("hours_remaining", hoursRemaining);

        //save items
        {
            ListTag itemsTag = new ListTag();
            for (ItemStack itemstack : this.items)
            {
                if (!itemstack.isEmpty())
                {
                    CompoundTag compoundtag = new CompoundTag();
                    itemsTag.add(itemstack.save(registries, compoundtag));
                }
            }
            tag.put("result", itemsTag);
        }

        //save result
        {
            ListTag resultTag = new ListTag();
            for (ChancedStack chancedStack : this.recipeResult)
            {
                if (!chancedStack.stack().isEmpty())
                {
                    CompoundTag compoundtag = new CompoundTag();
                    compoundtag.putFloat("chance", chancedStack.chance());
                    resultTag.add(chancedStack.stack().save(registries, compoundtag));
                }
            }
            tag.put("result", resultTag);
        }

        //save upgrades
        {
            ListTag upgrades = new ListTag();
            for (ItemStack itemstack : this.upgrades)
            {
                if (!itemstack.isEmpty())
                {
                    CompoundTag compoundtag = new CompoundTag();
                    upgrades.add(itemstack.save(registries, compoundtag));
                }
            }
            tag.put("upgrades", upgrades);
        }

    }


    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        lastTickHour = tag.getLong("last_tick_hour");
        hoursRemaining = tag.getInt("hours_remaining");

        //load items
        {
            items.clear();
            ListTag itemsTag = tag.getList("items", 10);
            for (int i = 0; i < itemsTag.size(); i++)
            {
                CompoundTag compoundtag = itemsTag.getCompound(i);
                this.items.add(ItemStack.parse(registries, compoundtag).orElse(ItemStack.EMPTY));
            }
        }

        //load result
        {
            recipeResult = new ArrayList<>();
            ListTag itemsTag = tag.getList("result", 10);
            for (int i = 0; i < itemsTag.size(); i++)
            {
                CompoundTag compoundtag = itemsTag.getCompound(i);
                float chance = compoundtag.getFloat("chance");
                this.recipeResult.add(new ChancedStack(ItemStack.parse(registries, compoundtag).orElse(ItemStack.EMPTY), chance));
            }
        }

        //load upgrades
        {
            upgrades.clear();
            ListTag upgradesTag = tag.getList("upgrades", 10);
            for (int i = 0; i < upgradesTag.size(); i++)
            {
                CompoundTag compoundtag = upgradesTag.getCompound(i);
                this.upgrades.add(ItemStack.parse(registries, compoundtag).orElse(ItemStack.EMPTY));
            }
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
        loadAdditional(pkt.getTag(), lookupProvider);
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

        return (level0, pos0, state0, blockEntity) ->
        {
            AbstractMachineBlockEntity adbe = ((AbstractMachineBlockEntity) blockEntity);

            //only tick for working machines
            if (state.getValue(AbstractMachineBlock.STATE).equals(AbstractMachineBlock.State.WORKING) || adbe.shouldTickNonWorking())
            {
                //tick with offset
                if (level.getGameTime() + adbe.getTickOffset(level) % ArtisanConfig.TICK_DELAY.get() == 0) adbe.tick();

                //daily tick
                long hour = (level.getGameTime() + adbe.getTickOffset(level)) / 1000;
                if (adbe.lastTickHour < hour)
                    adbe.hourlyTick(hour, state);
            }
        };
    }
}
