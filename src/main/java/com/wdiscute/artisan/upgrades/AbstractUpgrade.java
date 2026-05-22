package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.recipe.AbstractArtisanRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractUpgrade
{
    public static final Codec<AbstractUpgrade> UPGRADE_CODEC = ResourceLocation.CODEC
            .dispatch(mod -> mod.getRegistryHolderOrThrow().getId(),
                    rl -> Artisan.UPGRADE_REGISTRY.get(rl).getCodecOrThrow());

    public abstract MapCodec<? extends AbstractUpgrade> codec();

    public abstract DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolder();

    public DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolderOrThrow()
    {
        var holder = getRegistryHolder();
        if (holder == null)
        {
            throw new IllegalStateException("Modifier " + this + " does not have a registry holder!");
        }

        return holder;
    }

    public MapCodec<? extends AbstractUpgrade> getCodecOrThrow()
    {
        var codec = codec();
        if (codec == null)
        {
            throw new IllegalStateException("Modifier " + this + " does not have a codec!");
        }
        return codec;
    }

    public void onHarvest(AbstractMachineBlockEntity machine)
    {
    }

    /**
     * ticks hourly when working, or when blockEntity overrides shouldTickNonWorking()
     */
    public void onHourlyTick(AbstractMachineBlockEntity machine, long hour)
    {
    }

    /**
     * ticks with an offset based on the tick delay config - does NOT tick every tick
     */
    public void onTick(AbstractMachineBlockEntity machine)
    {
    }

    public void onFinishedHarvest(AbstractMachineBlockEntity machine)
    {
    }

    public List<ItemStack> modifyHarvestResult(AbstractMachineBlockEntity machine, List<ItemStack> results)
    {
        return results;
    }

    public void onAdd(AbstractMachineBlockEntity abstractMachineBlockEntity)
    {

    }

    public void onRecipeStarted(@Nullable AbstractArtisanRecipe recipe, AbstractMachineBlockEntity machine)
    {

    }
}
