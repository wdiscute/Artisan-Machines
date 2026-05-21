package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.upgrades.AbstractUpgrade;
import com.wdiscute.artisan.upgrades.EmptyUpgrade;
import com.wdiscute.artisan.upgrades.InfiniteCraftUpgrade;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ArtisanUpgrades
{
    DeferredRegister<AbstractUpgrade> UPGRADES_REGISTRY =
            DeferredRegister.create(Artisan.UPGRADE_REGISTRY, Artisan.MOD_ID);

    DeferredHolder<AbstractUpgrade, AbstractUpgrade> EMPTY = register("empty", EmptyUpgrade::new);
    DeferredHolder<AbstractUpgrade, AbstractUpgrade> INFINITE_CRAFT = register("infinite_craft", InfiniteCraftUpgrade::new);

    static DeferredHolder<AbstractUpgrade, AbstractUpgrade> register(String name, Supplier<AbstractUpgrade> supplier)
    {
        return UPGRADES_REGISTRY.register(name, supplier);
    }

    static void register(IEventBus eventBus)
    {
        UPGRADES_REGISTRY.register(eventBus);
    }

}
