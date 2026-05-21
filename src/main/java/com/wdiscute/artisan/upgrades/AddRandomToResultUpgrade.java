package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.machines.AbstractMachineBlock;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.registry.ArtisanUpgrades;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddRandomToResultUpgrade extends AbstractUpgrade
{
    private final List<ItemStack> stacksToAdd;
    private final List<ResourceLocation> tagsToAdd;
    private final float chance;

    public static final MapCodec<AddRandomToResultUpgrade> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ItemStack.CODEC.listOf().fieldOf("stacks").forGetter(o -> o.stacksToAdd),
                    ResourceLocation.CODEC.listOf().fieldOf("tags").forGetter(o -> o.tagsToAdd),
                    Codec.FLOAT.fieldOf("chance").forGetter(o -> o.chance)
            ).apply(instance, AddRandomToResultUpgrade::new));

    public AddRandomToResultUpgrade(List<ItemStack> result, List<ResourceLocation> tagsToAdd, float chance)
    {
        this.stacksToAdd = result;
        this.tagsToAdd = tagsToAdd;
        this.chance = chance;
    }

    public AddRandomToResultUpgrade()
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

        for (ResourceLocation rl : tagsToAdd)
        {
            TagKey<Item> tk = TagKey.create(Registries.ITEM, rl);
            Optional<HolderSet.Named<Item>> tag = BuiltInRegistries.ITEM.getTag(tk);

            //if tag is present add all items of the tag to the list
            tag.ifPresent(holders -> allItems.addAll(holders.stream().map(o -> o.value().getDefaultInstance()).toList()));
        }

        if (random.nextFloat() < chance)
            results.add(allItems.get(random.nextInt(allItems.size())));

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
