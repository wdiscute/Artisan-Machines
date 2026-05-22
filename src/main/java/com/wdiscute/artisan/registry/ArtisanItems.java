package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ArtisanItems
{
    DeferredRegister.Items ITEMS = DeferredRegister.createItems(Artisan.MOD_ID);
    DeferredRegister.Items UPGRADES = DeferredRegister.createItems(Artisan.MOD_ID);

    DeferredItem<Item> STONE_HAND = register("stone_hand");
    DeferredItem<Item> TINY_GNOME = register("tiny_gnome");
    DeferredItem<Item> PINK_MATTER = register("pink_matter");
    DeferredItem<Item> ANCIENT_COG = register("ancient_cog");
    DeferredItem<Item> BROKEN_CLOCK = register("broken_clock");
    DeferredItem<Item> BLACK_OPAL = register("black_opal");
    DeferredItem<Item> ANCIENT_ROE = register("ancient_roe");
    DeferredItem<Item> INFINITY_WORM = register("infinity_worm");
    DeferredItem<Item> INSERTER = register("inserter");
    DeferredItem<Item> CORDYCEP = register("cordycep");
    DeferredItem<Item> ENKEPHALIN = register("enkephalin");
    DeferredItem<Item> GRAY_ANATOMY = register("gray_anatomy");
    DeferredItem<Item> RECYCLED_CORE = register("recycled_core");


    static DeferredItem<Item> register(String name)
    {
        return UPGRADES.register(name, () -> new Item(new Item.Properties()));
    }

    static void register(IEventBus modEventBus)
    {
        ITEMS.register(modEventBus);
        UPGRADES.register(modEventBus);
    }
}
