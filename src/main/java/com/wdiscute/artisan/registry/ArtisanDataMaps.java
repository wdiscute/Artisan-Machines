package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.upgrades.AbstractUpgrade;
import com.wdiscute.artisan.upgrades.MachineUpgrade;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public interface ArtisanDataMaps
{
    DataMapType<Item, MachineUpgrade> ARTISAN_UPGRADES = DataMapType.builder(
            Artisan.rl("artisan_upgrades"), Registries.ITEM, MachineUpgrade.CODEC
    ).synced(MachineUpgrade.CODEC, true).build();


    static <T> T getOrDefault(ItemStack stack, DataMapType<Item, T> dataMap, T d)
    {
        T data = stack.getItemHolder().getData(dataMap);
        if (data == null) return d;
        return data;
    }

}
