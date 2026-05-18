package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.machines.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ArtisanBlockEntities
{
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Artisan.MOD_ID);

    Supplier<BlockEntityType<?>> LOOM = register("loom", ArtisanBlocks.LOOM, LoomBlockEntity::new);
    Supplier<BlockEntityType<?>> CHEESE_PRESS = register("cheese_press", ArtisanBlocks.CHEESE_PRESS, CheesePressBlockEntity::new);
    Supplier<BlockEntityType<?>> WINE_KEG = register("wine_keg", ArtisanBlocks.WINE_KEG, WineKegBlockEntity::new);
    Supplier<BlockEntityType<?>> AGING_CASK = register("aging_cask", ArtisanBlocks.AGING_CASK, AgingCaskBlockEntity::new);
    Supplier<BlockEntityType<?>> ANCIENT_CASK = register("ancient_cask", ArtisanBlocks.ANCIENT_CASK, AncientCaskBlockEntity::new);
    Supplier<BlockEntityType<?>> CRYSTALARIUM = register("crystalarium", ArtisanBlocks.CRYSTALARIUM, CrystalariumBlockEntity::new);


    static <T extends BlockEntity> Supplier<BlockEntityType<?>> register(String name, DeferredBlock<Block> block, BlockEntityType.BlockEntitySupplier<? extends T> factory)
    {
        return BLOCK_ENTITIES.register(name,
                () -> BlockEntityType.Builder.of(factory, block.get()).build(null));
    }

    static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
