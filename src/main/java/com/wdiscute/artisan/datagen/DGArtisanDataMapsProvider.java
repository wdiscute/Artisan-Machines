package com.wdiscute.artisan.datagen;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import com.wdiscute.artisan.registry.ArtisanDataMaps;
import com.wdiscute.artisan.registry.ArtisanItems;
import com.wdiscute.artisan.upgrades.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DGArtisanDataMapsProvider extends DataMapProvider
{
    protected DGArtisanDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider)
    {
        var itemUpgrades = this.builder(ArtisanDataMaps.ARTISAN_UPGRADES);
        var machineSettings = this.builder(ArtisanDataMaps.MACHINE_SETTINGS);

        //bla.add(Items.SLIME_BALL.builtInRegistryHolder(), Items.BUCKET, false);

        itemUpgrades.add(Items.TRIAL_KEY.builtInRegistryHolder(),
                new MachineUpgrade(
                        ArtisanBlockEntities.DELUXE_WORM_FARM.get(),
                        new InfiniteCraftUpgrade(List.of(new ChancedStack(Items.DIRT.getDefaultInstance(), 1)), 8)
        ), false);

        itemUpgrades.add(ArtisanItems.TINY_GNOME,
                new MachineUpgrade(
                        ArtisanBlockEntities.LOOM.get(),
                        new AddRandomToResultUpgrade
                                (
                                        List.of(),
                                        List.of(ItemTags.LOGS.location()),
                                        0.25f
                                )
                ), false);

        itemUpgrades.add(ArtisanItems.PINK_MATTER,
                new MachineUpgrade(
                        ArtisanBlockEntities.CHEESE_PRESS.get(),
                        new TransmuteResultUpgrade(
                                List.of(
                                        new TransmuteResultUpgrade.TransmuteResult(
                                                Items.GOLD_INGOT, new ItemStack(Items.DIAMOND)
                                        )
                                )
                        )
                ), false);

        itemUpgrades.add(ArtisanItems.BROKEN_CLOCK,
                new MachineUpgrade(
                        ArtisanBlockEntities.AGING_CASK.get(),
                        new ProcessingTimeMultiplierUpgrade(0.5f)
                ), false);

        itemUpgrades.add(ArtisanItems.ANCIENT_ROE,
                new MachineUpgrade(
                        ArtisanBlockEntities.FISH_SMOKER.get(),
                        new DoubleResultUpgrade()
                ), false);

        itemUpgrades.add(ArtisanItems.ANCIENT_ROE,
                new MachineUpgrade(
                        ArtisanBlockEntities.FISH_SMOKER.get(),
                        new DoubleResultUpgrade()
                ), false);



    }
}
