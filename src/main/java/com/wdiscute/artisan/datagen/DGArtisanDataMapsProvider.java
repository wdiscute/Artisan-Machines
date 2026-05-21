package com.wdiscute.artisan.datagen;

import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.registry.ArtisanBlockEntities;
import com.wdiscute.artisan.registry.ArtisanDataMaps;
import com.wdiscute.artisan.upgrades.InfiniteCraftUpgrade;
import com.wdiscute.artisan.upgrades.MachineUpgrade;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
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

        itemUpgrades.add(Items.TRIAL_KEY.builtInRegistryHolder(),
                new MachineUpgrade(
                        ArtisanBlockEntities.DELUXE_WORM_FARM.get(),
                        new InfiniteCraftUpgrade(List.of(new ChancedStack(Items.DIRT.getDefaultInstance(), 1)), 8)
        ), false);
    }



}
