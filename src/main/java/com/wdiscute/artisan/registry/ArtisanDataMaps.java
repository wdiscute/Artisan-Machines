package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.upgrades.AbstractUpgrade;
import com.wdiscute.artisan.upgrades.MachineSettings;
import com.wdiscute.artisan.upgrades.MachineUpgrade;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public interface ArtisanDataMaps
{
    DataMapType<Item, MachineUpgrade> ARTISAN_UPGRADES = DataMapType.builder(
            Artisan.rl("artisan_upgrades"), Registries.ITEM, MachineUpgrade.CODEC
    ).synced(MachineUpgrade.CODEC, true).build();

    DataMapType<BlockEntityType<?>, MachineSettings> MACHINE_SETTINGS = DataMapType.builder(
            Artisan.rl("machine_settings"), Registries.BLOCK_ENTITY_TYPE, MachineSettings.CODEC
    ).synced(MachineSettings.CODEC, true).build();

    static MachineUpgrade getOrDefault(ItemStack stack)
    {
        MachineUpgrade data = stack.getItemHolder().getData(ARTISAN_UPGRADES);
        if (data == null) return MachineUpgrade.EMPTY;
        return data;
    }

    static MachineSettings getOrDefault(BlockEntity be)
    {
        MachineSettings data = be.getType().builtInRegistryHolder().getData(MACHINE_SETTINGS);
        if (data == null) return MachineSettings.DEFAULT;
        return data;
    }


}
