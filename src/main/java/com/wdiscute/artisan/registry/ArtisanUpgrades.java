package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.upgrades.*;
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
    DeferredHolder<AbstractUpgrade, AbstractUpgrade> PRESERVE_QUALITY = register("preserve_quality", PreserveQualityUpgrade::new);
    DeferredHolder<AbstractUpgrade, AbstractUpgrade> ADD_ALL_TO_RESULT = register("add_all_to_result", AddAllToResultUpgrade::new);
    DeferredHolder<AbstractUpgrade, AbstractUpgrade> ADD_RANDOM_TO_RESULT = register("add_random_to_result", AddRandomToResultUpgrade::new);
    DeferredHolder<AbstractUpgrade, AbstractUpgrade> TRANSMUTE_RESULT = register("transmute_result", TransmuteResultUpgrade::new);
    DeferredHolder<AbstractUpgrade, AbstractUpgrade> PROCESSING_TIME_MULTIPLIER = register("processing_time_multiplier", ProcessingTimeMultiplierUpgrade::new);
    DeferredHolder<AbstractUpgrade, AbstractUpgrade> DOUBLE_RESULT = register("double_result", DoubleResultUpgrade::new);
    DeferredHolder<AbstractUpgrade, AbstractUpgrade> CHARGING_ROD = register("start_recipe", StartRecipeUpgrade::new);

    static DeferredHolder<AbstractUpgrade, AbstractUpgrade> register(String name, Supplier<AbstractUpgrade> supplier)
    {
        return UPGRADES_REGISTRY.register(name, supplier);
    }

    static void register(IEventBus eventBus)
    {
        UPGRADES_REGISTRY.register(eventBus);
    }

}
