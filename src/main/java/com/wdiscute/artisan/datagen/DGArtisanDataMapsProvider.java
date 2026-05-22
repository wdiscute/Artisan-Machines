package com.wdiscute.artisan.datagen;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import com.wdiscute.artisan.registry.ArtisanDataMaps;
import com.wdiscute.artisan.registry.ArtisanItems;
import com.wdiscute.artisan.upgrades.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

        //tiny gnome - looom
        itemUpgrades.add(ArtisanItems.TINY_GNOME,
                new MachineUpgrade(
                        ArtisanBlockEntities.LOOM.get(),
                        new AddRandomToResultUpgrade
                                (
                                        List.of(),
                                        List.of(ItemTags.BEDS.location()),
                                        0.25f
                                )
                ), false);

        //pink matter - cheese press
        itemUpgrades.add(ArtisanItems.PINK_MATTER,
                new MachineUpgrade(
                        ArtisanBlockEntities.CHEESE_PRESS.get(),
                        new TransmuteResultUpgrade(
                                new TransmuteResultUpgrade.TransmuteResult(
                                        Ingredient.of(Items.YELLOW_WOOL), new ItemStack(Items.YELLOW_GLAZED_TERRACOTTA), 1f
                                )
                        )
                ), false);

        //Gray Anatomy - wine keg
        itemUpgrades.add(ArtisanItems.GRAY_ANATOMY,
                new MachineUpgrade(
                        ArtisanBlockEntities.WINE_KEG.get(),
                        new DoubleResultUpgrade()
                ), false);

        //Broken Clock - Aging Cask
        itemUpgrades.add(ArtisanItems.BROKEN_CLOCK,
                new MachineUpgrade(
                        ArtisanBlockEntities.AGING_CASK.get(),
                        new ProcessingTimeMultiplierUpgrade(0.5f)
                ), false);

        //Inserter - Ancient Cask
        itemUpgrades.add(ArtisanItems.INSERTER,
                new MachineUpgrade(
                        ArtisanBlockEntities.ANCIENT_CASK.get(),
                        new EmptyUpgrade()
                ),
                false);

        //Black Opal - Crystalarium
        itemUpgrades.add(ArtisanItems.BLACK_OPAL,
                new MachineUpgrade(
                        ArtisanBlockEntities.CRYSTALARIUM.get(),
                        new TransmuteResultUpgrade(
                                new TransmuteResultUpgrade.TransmuteResult(Ingredient.of(Items.DIAMOND), Items.DIAMOND_BLOCK.getDefaultInstance(), 0.1f)
                        )
                ),
                false);

        //Infinity Worm - Deluxe Worm Farm
        itemUpgrades.add(ArtisanItems.INFINITY_WORM,
                new MachineUpgrade(
                        ArtisanBlockEntities.DELUXE_WORM_FARM.get(),
                        new InfiniteCraftUpgrade(
                                List.of(new ChancedStack(Items.SLIME_BALL.getDefaultInstance(), 1)),
                                4
                        )
                ),
                false);

        //Ancient Roe - Fish Smoker
        itemUpgrades.add(ArtisanItems.ANCIENT_ROE,
                new MachineUpgrade(
                        ArtisanBlockEntities.FISH_SMOKER.get(),
                        new DoubleResultUpgrade()
                ),
                false);

        //Cordycep - Dehydrator
        itemUpgrades.add(ArtisanItems.CORDYCEP,
                new MachineUpgrade(
                        ArtisanBlockEntities.DEHYDRATOR.get(),
                        new EmptyUpgrade()
                ),
                false);

        //Enkephalin - Mayonnaise Machine
        ItemStack mayo = Items.WHITE_CARPET.getDefaultInstance();
        mayo.set(DataComponents.CUSTOM_NAME, Component.literal("Supreme Mayo"));
        itemUpgrades.add(ArtisanItems.ENKEPHALIN,
                new MachineUpgrade(
                        ArtisanBlockEntities.MAYONNAISE_MACHINE.get(),
                        new AddAllToResultUpgrade(
                                List.of(mayo),
                                List.of(),
                                1)
                ),
                false);


        //Stone Hand - Preserves Jar
        itemUpgrades.add(ArtisanItems.ENKEPHALIN,
                new MachineUpgrade(
                        ArtisanBlockEntities.PRESERVES_JAR.get(),
                        new EmptyUpgrade()
                ),
                false);

        //Ancient Cog - Seed Maker
        itemUpgrades.add(ArtisanItems.ANCIENT_COG,
                new MachineUpgrade(
                        ArtisanBlockEntities.SEED_MAKER.get(),
                        new AddRandomToResultUpgrade
                                (
                                        List.of(),
                                        List.of(ItemTags.BEDS.location()),
                                        0.05f
                                )
                ), false);

        //Recycled Core = Recycling Machine
        itemUpgrades.add(ArtisanItems.RECYCLED_CORE,
                new MachineUpgrade(
                        ArtisanBlockEntities.RECYCLING_MACHINE.get(),
                        new DoubleResultUpgrade()
                ), false);


        var machineSettings = this.builder(ArtisanDataMaps.MACHINE_SETTINGS);

        ItemStack battery = Items.DIAMOND.getDefaultInstance();
        battery.set(DataComponents.CUSTOM_NAME, Component.literal("Battery"));
        machineSettings.add(ArtisanBlockEntities.CHARGING_ROD,

                new MachineSettings(
                        List.of(new StartRecipeUpgrade(List.of(new ChancedStack(battery, 1)), 8, 0.1f, true)),
                        1,
                        false
                ),
                false);

    }
}
