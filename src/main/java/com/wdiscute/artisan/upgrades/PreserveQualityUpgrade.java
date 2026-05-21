package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.wdiscute.artisan.compat.QualityFoodCompat;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.registry.ArtisanUpgrades;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class PreserveQualityUpgrade extends AbstractUpgrade
{

    HolderSet<Item> excludedInputs;
    HolderSet<Item> excludedOutputs;

    public static final MapCodec<PreserveQualityUpgrade> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("excluded_inputs").forGetter(o -> o.excludedInputs),
                    RegistryCodecs.homogeneousList(Registries.ITEM).fieldOf("excluded_outputs").forGetter(o -> o.excludedOutputs)
            ).apply(instance, PreserveQualityUpgrade::new));

    public PreserveQualityUpgrade()
    {
        excludedInputs = HolderSet.empty();
        excludedOutputs = HolderSet.empty();
    }

    public PreserveQualityUpgrade(HolderSet<Item> inputs, HolderSet<Item> outputs)
    {
        this.excludedInputs = inputs;
        this.excludedOutputs = outputs;
    }

    @Override
    public List<ItemStack> modifyHarvestResult(AbstractMachineBlockEntity machine, List<ItemStack> results)
    {
        if(ModList.get().isLoaded("quality_foods"))
        {
            List<ItemStack> inputs = new ArrayList<>(machine.items);

            //remove excluded outputs from items in machine
            inputs.removeIf(o -> excludedOutputs.contains(o.getItemHolder()));

            //modify results lists to set the quality of each result item stack
            QualityFoodCompat.applyLowestQualityToItem(inputs, results, excludedOutputs);
        }

        return results;
    }

    @Override
    public MapCodec<? extends AbstractUpgrade> codec()
    {
        return CODEC;
    }

    @Override
    public DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolder()
    {
        return ArtisanUpgrades.PRESERVE_QUALITY;
    }
}
