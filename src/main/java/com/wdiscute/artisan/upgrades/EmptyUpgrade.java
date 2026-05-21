package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.MapCodec;
import com.wdiscute.artisan.registry.ArtisanUpgrades;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EmptyUpgrade extends AbstractUpgrade
{
    public static final MapCodec<AbstractUpgrade> CODEC = MapCodec.unit(EmptyUpgrade::new);

    @Override
    public MapCodec<? extends AbstractUpgrade> codec()
    {
        return CODEC;
    }

    @Override
    public DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolder()
    {
        return ArtisanUpgrades.EMPTY;
    }
}
