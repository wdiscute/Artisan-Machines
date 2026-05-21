package com.wdiscute.artisan;

import com.wdiscute.artisan.registry.ArtisanDataMaps;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = Artisan.MOD_ID)
public class ArtisanEvents
{
    @SubscribeEvent
    public static void addRegistry(NewRegistryEvent event)
    {
        event.register(Artisan.UPGRADE_REGISTRY);
    }

    @SubscribeEvent
    public static void addDataMap(RegisterDataMapTypesEvent event)
    {
        event.register(ArtisanDataMaps.ARTISAN_UPGRADES);
        event.register(ArtisanDataMaps.MACHINE_SETTINGS);
    }
}
