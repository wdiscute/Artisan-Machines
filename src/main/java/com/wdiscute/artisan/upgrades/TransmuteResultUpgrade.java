package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.registry.ArtisanUpgrades;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class TransmuteResultUpgrade extends AbstractUpgrade
{
    private final List<TransmuteResult> transmutations;

    public static final MapCodec<TransmuteResultUpgrade> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    TransmuteResult.CODEC.listOf().fieldOf("transmutations").forGetter(o -> o.transmutations)
            ).apply(instance, TransmuteResultUpgrade::new));

    public TransmuteResultUpgrade(List<TransmuteResult> transmutations)
    {
        this.transmutations = transmutations;
    }

    public TransmuteResultUpgrade(TransmuteResult transmutations)
    {
        this.transmutations = List.of(transmutations);
    }

    public TransmuteResultUpgrade()
    {
        this.transmutations = List.of();
    }

    @Override
    public List<ItemStack> modifyHarvestResult(AbstractMachineBlockEntity machine, List<ItemStack> results)
    {

        for (TransmuteResult transmutation : transmutations)
        {
            transmutation.transmute(results, machine.getLevel().getRandom());
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
        return ArtisanUpgrades.TRANSMUTE_RESULT;
    }

    public record TransmuteResult(Ingredient original, ItemStack transmuted, float chance)
    {
        public static final Codec<TransmuteResult> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Ingredient.CODEC.fieldOf("original").forGetter(TransmuteResult::original),
                        ItemStack.CODEC.fieldOf("transmuted").forGetter(TransmuteResult::transmuted),
                        Codec.FLOAT.fieldOf("chance").forGetter(TransmuteResult::chance)
                ).apply(instance, TransmuteResult::new));

        public void transmute(List<ItemStack> results, RandomSource randomSource)
        {
            //return if chance not good
            if(randomSource.nextFloat() > chance) return;

            List<ItemStack> stacksToRemove = new ArrayList<>();
            for (ItemStack originalStack : results)
            {
                if(original.test(originalStack))
                {
                    stacksToRemove.add(originalStack);
                    results.add(transmuted);
                }
            }

            results.removeAll(stacksToRemove);
        }
    }

}
