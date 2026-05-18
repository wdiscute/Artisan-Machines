package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.machines.*;
import com.wdiscute.artisan.recipe.OilMakerRecipe;
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
    DeferredBlock<Block> DELUXE_WORM_FARM = register("deluxe_worm_farm", DeluxeWormFarmBlock::new);
    DeferredBlock<Block> FISH_SMOKER = register("fish_smoker", FishSmokerBlock::new);
    DeferredBlock<Block> DEHYDRATOR = register("dehydrator", DehydratorBlock::new);
    DeferredBlock<Block> MAYONNAISE_MACHINE = register("mayonnaise_machine", MayonnaiseMachineBlock::new);
    DeferredBlock<Block> PRESERVES_JAR = register("preserves_jar", PreservesJarBlock::new);
    DeferredBlock<Block> SEED_MAKER = register("seed_maker", SeedMakerBlock::new);
    DeferredBlock<Block> RECYCLING_MACHINE = register("recycling_machine", RecyclingMachineBlock::new);
    DeferredBlock<Block> BAIT_MAKER = register("bait_maker", BaitMakerBlock::new);
    DeferredBlock<Block> OIL_MAKER = register("oil_maker", OilMakerBlock::new);


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