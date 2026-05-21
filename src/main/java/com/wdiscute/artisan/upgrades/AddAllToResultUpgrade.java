package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.registry.ArtisanUpgrades;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class AddAllToResultUpgrade extends AbstractUpgrade
{
    private final List<ItemStack> stacksToAdd;
    private final List<HolderSet<Item>> tagsToAdd;
    private final float chance;

    public static final MapCodec<AddAllToResultUpgrade> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ItemStack.CODEC.listOf().fieldOf("stacks").forGetter(o -> o.stacksToAdd),
                    RegistryCodecs.homogeneousList(Registries.ITEM).listOf().fieldOf("tags").forGetter(o -> o.tagsToAdd),
                    Codec.FLOAT.fieldOf("chance").forGetter(o -> o.chance)
            ).apply(instance, AddAllToResultUpgrade::new));

    public AddAllToResultUpgrade(List<ItemStack> result, List<HolderSet<Item>> tagsToAdd, float chance)
    {
        this.stacksToAdd = result;
        this.tagsToAdd = tagsToAdd;
        this.chance = chance;
    }

    public AddAllToResultUpgrade()
    {
        this.stacksToAdd = List.of();
        this.tagsToAdd = List.of();
        this.chance = 0;
    }

    @Override
    public List<ItemStack> modifyHarvestResult(AbstractMachineBlockEntity machine, List<ItemStack> results)
    {
        RandomSource random = machine.getLevel().getRandom();

        List<ItemStack> allItems = new ArrayList<>(stacksToAdd);

        tagsToAdd.forEach(tag -> allItems.addAll(tag.stream().map(o -> o.value().getDefaultInstance()).toList()));

        if (random.nextFloat() < chance)
            results.addAll(allItems);

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
        return ArtisanUpgrades.ADD_RANDOM_TO_RESULT;
    }
}
