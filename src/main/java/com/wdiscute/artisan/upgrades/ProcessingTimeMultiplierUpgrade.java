package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import com.wdiscute.artisan.registry.ArtisanUpgrades;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class ProcessingTimeMultiplierUpgrade extends AbstractUpgrade
{
    private final float multiplier;

    public static final MapCodec<ProcessingTimeMultiplierUpgrade> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("multiplier").forGetter(o -> o.multiplier)
            ).apply(instance, ProcessingTimeMultiplierUpgrade::new));

    public ProcessingTimeMultiplierUpgrade(float multiplier)
    {
        this.multiplier = multiplier;
    }

    public ProcessingTimeMultiplierUpgrade()
    {
        this.multiplier = 1;
    }

    @Override
    public void onRecipeStarted(AbstractArtisanRecipe recipe, AbstractMachineBlockEntity machine)
    {
        super.onRecipeStarted(recipe, machine);
        machine.hoursRemaining = (int) (machine.hoursRemaining * multiplier);
    }

    @Override
    public MapCodec<? extends AbstractUpgrade> codec()
    {
        return CODEC;
    }

    @Override
    public DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolder()
    {
        return ArtisanUpgrades.PROCESSING_TIME_MULTIPLIER;
    }
}
