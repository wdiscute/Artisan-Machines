package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.machines.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ArtisanBlocks
{
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Artisan.MOD_ID);

    DeferredBlock<Block> LOOM = register("loom", LoomBlock::new);
    DeferredBlock<Block> CHEESE_PRESS = register("cheese_press", CheesePressBlock::new);
    DeferredBlock<Block> WINE_KEG = register("wine_keg", WineKegBlock::new);
    DeferredBlock<Block> AGING_CASK = register("aging_cask", AgingCaskBlock::new);
    DeferredBlock<Block> ANCIENT_CASK = register("ancient_cask", AncientCaskBlock::new);
    DeferredBlock<Block> CRYSTALARIUM = register("crystalarium", CrystalariumBlock::new);


    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block)
    {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        ArtisanItems.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}