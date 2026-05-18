package com.wdiscute.artisan.datagen;

import com.wdiscute.artisan.Artisan;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Artisan.MOD_ID)
public class ArtisanDataGenerators
{

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
//        event.createDatapackRegistryObjects(
//                new RegistrySetBuilder()
//                        .add(Starcatcher.FISH_REGISTRY_KEY, DGSCFishProperties::bootstrap)
//        );

        DataGenerator gen = event.getGenerator();

        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput output = gen.getPackOutput();

        //fish properties
//        gen.addProvider(
//                event.includeServer(),
//                new DGSCFishProperties(output, lookupProvider)
//        );

        //item models
        //ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        //gen.addProvider(event.includeServer(), new DGSCItemModelProvider(output, existingFileHelper));

        //block tags
        //BlockTagsProvider btp = new DGSCBlocksTagsProvider(output, lookupProvider, existingFileHelper);
        //gen.addProvider(event.includeServer(), btp);

        //item tags
        //ItemTagsProvider itp = new DGSCItemsTagsProvider(output, lookupProvider, btp.contentsGetter(), existingFileHelper);
        //gen.addProvider(event.includeServer(), itp);

        //advancements
        //gen.addProvider(event.includeServer(), new DGSCAdvancementProvider(output, lookupProvider, existingFileHelper));

        //loot modifiers
        //gen.addProvider(event.includeServer(), new DGSCLootModifiers(output, lookupProvider));

        //loot table
        //gen.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(),
        //        List.of(new LootTableProvider.SubProviderEntry(DGSCBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));

        //recipes
        gen.addProvider(event.includeServer(), new DGArtisanRecipeProvider(output, lookupProvider));

        //data maps
        //gen.addProvider(event.includeServer(), new DGSCDataMapsProvider(output, lookupProvider));

    }
}
