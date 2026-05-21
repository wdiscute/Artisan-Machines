package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.MapCodec;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.registry.ArtisanUpgrades;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class DoubleResultUpgrade extends AbstractUpgrade
{
    public static final MapCodec<DoubleResultUpgrade> CODEC = MapCodec.unit(DoubleResultUpgrade::new);

    @Override
    public List<ItemStack> modifyHarvestResult(AbstractMachineBlockEntity machine, List<ItemStack> results)
    {
        results.addAll(new ArrayList<>(results));
        return results;
    }

    @Override
    public MapCodec<? extends AbstractUpgrade> codec()
    {
        return CODEC;
    }

    @Override
    public DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolder()
    {
        return ArtisanUpgrades.DOUBLE_RESULT;
    }
}
