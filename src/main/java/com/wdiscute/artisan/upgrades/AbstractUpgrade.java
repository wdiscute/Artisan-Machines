package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public abstract class AbstractUpgrade
{
    public static final Codec<AbstractUpgrade> UPGRADE_CODEC = ResourceLocation.CODEC
            .dispatch(mod -> mod.getRegistryHolderOrThrow().getId(),
                    rl -> Artisan.UPGRADE_REGISTRY.get(rl).getCodecOrThrow());

    public abstract MapCodec<? extends AbstractUpgrade> codec();

    public abstract DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolder();

    public DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolderOrThrow()
    {
        var holder = getRegistryHolder();
        if (holder == null)
        {
            throw new IllegalStateException("Modifier " + this + " does not have a registry holder!");
        }

        return holder;
    }

    public MapCodec<? extends AbstractUpgrade> getCodecOrThrow()
    {
        var codec = codec();
        if (codec == null)
        {
            throw new IllegalStateException("Modifier " + this + " does not have a codec!");
        }
        return codec;
    }

    public void onHarvest(AbstractMachineBlockEntity machine)
    {
    }

    public void onHourlyTick(AbstractMachineBlockEntity machine, long hour)
    {
    }

    public void onFinishedHarvest(AbstractMachineBlockEntity machine)
    {

    }

    public List<ItemStack> modifyHarvestResult(AbstractMachineBlockEntity machine, List<ItemStack> results)
    {
        return results;
    }
}
