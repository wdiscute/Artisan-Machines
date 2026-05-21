package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.machines.AbstractMachineBlock;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.registry.ArtisanUpgrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class InfiniteCraftUpgrade extends AbstractUpgrade
{
    private final List<ChancedStack> result;
    private final int hours;

    public static final MapCodec<InfiniteCraftUpgrade> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ChancedStack.LIST_CODEC.fieldOf("chanced_stack").forGetter(o -> o.result),
                    Codec.INT.fieldOf("processing_hours").forGetter(o -> o.hours)
            ).apply(instance, InfiniteCraftUpgrade::new));

    public InfiniteCraftUpgrade(List<ChancedStack> result, int hours)
    {
        this.result = result;
        this.hours = hours;
    }

    public InfiniteCraftUpgrade()
    {
        this.result = List.of();
        this.hours = 99999;
    }

    @Override
    public void onAdd(AbstractMachineBlockEntity machine)
    {
        super.onAdd(machine);
        //set "recipe"
        machine.hoursRemaining = hours;
        machine.recipeResult = result;
        //update blockstate to working
        Level level = machine.getLevel();
        BlockState blockState = level.getBlockState(machine.getBlockPos());
        level.setBlockAndUpdate(machine.getBlockPos(),
                blockState.setValue(AbstractMachineBlock.STATE, AbstractMachineBlock.State.WORKING));
    }

    @Override
    public void onFinishedHarvest(AbstractMachineBlockEntity machine)
    {
        super.onFinishedHarvest(machine);
        machine.hoursRemaining = hours;
        machine.recipeResult = result;
        //update blockstate to working
        Level level = machine.getLevel();
        BlockState blockState = level.getBlockState(machine.getBlockPos());
        level.setBlockAndUpdate(machine.getBlockPos(),
                blockState.setValue(AbstractMachineBlock.STATE, AbstractMachineBlock.State.WORKING));
    }

    @Override
    public MapCodec<? extends AbstractUpgrade> codec()
    {
        return CODEC;
    }

    @Override
    public DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolder()
    {
        return ArtisanUpgrades.INFINITE_CRAFT;
    }
}
