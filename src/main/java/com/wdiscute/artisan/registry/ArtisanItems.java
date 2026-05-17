package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ArtisanItems
{
    DeferredRegister.Items ITEMS = DeferredRegister.createItems(Artisan.MOD_ID);




    static void register(IEventBus modEventBus)
    {
        ITEMS.register(modEventBus);
    }
}
