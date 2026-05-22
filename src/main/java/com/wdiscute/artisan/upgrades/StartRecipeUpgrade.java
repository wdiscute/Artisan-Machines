package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.wdiscute.artisan.ChancedStack;
import com.wdiscute.artisan.machines.AbstractMachineBlock;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import com.wdiscute.artisan.registry.ArtisanUpgrades;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class StartRecipeUpgrade extends AbstractUpgrade
{
    private final List<ChancedStack> result;
    private final int hours;
    private final float chance;
    private final boolean stormOnly;

    public static final MapCodec<StartRecipeUpgrade> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ChancedStack.LIST_CODEC.fieldOf("result").forGetter(o -> o.result),
                    Codec.INT.fieldOf("processing_hours").forGetter(o -> o.hours),
                    Codec.FLOAT.fieldOf("chance_to_start").forGetter(o -> o.chance),
                    Codec.BOOL.fieldOf("during_storm_only").forGetter(o -> o.stormOnly)
            ).apply(instance, StartRecipeUpgrade::new));

    public StartRecipeUpgrade(List<ChancedStack> result, int hours, float chance, boolean stormOnly)
    {
        this.result = result;
        this.hours = hours;
        this.chance = chance;
        this.stormOnly = stormOnly;
    }

    public StartRecipeUpgrade()
    {
        this.result = List.of();
        this.hours = 99999;
        this.chance = 0;
        this.stormOnly = true;
    }

    @Override
    public void onTick(AbstractMachineBlockEntity machine)
    {
        super.onTick(machine);

        if(machine.getBlockState().getValue(AbstractMachineBlock.STATE).equals(AbstractMachineBlock.State.IDLE)
                && machine.getLevel().getRandom().nextFloat() < chance)
        {
            Level level = machine.getLevel();

            if(stormOnly && level.getThunderLevel(0) < 0.5) return;

            //summon lightning bolt
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
            Vec3 vec3 = Vec3.atLowerCornerWithOffset(machine.getBlockPos(), 0.5, 1.01, 0.5).offsetRandom(level.random, 0.7F);
            lightningBolt.setPos(vec3);
            level.addFreshEntity(lightningBolt);

            //set recipe
            machine.hoursRemaining = hours;
            machine.recipeResult = result;
            //update blockstate to working
            BlockState blockState = level.getBlockState(machine.getBlockPos());
            level.setBlockAndUpdate(machine.getBlockPos(),
                    blockState.setValue(AbstractMachineBlock.STATE, AbstractMachineBlock.State.WORKING));

            onRecipeStarted(null, machine);
        }
    }

    @Override
    public MapCodec<? extends AbstractUpgrade> codec()
    {
        return CODEC;
    }

    @Override
    public DeferredHolder<AbstractUpgrade, ? extends AbstractUpgrade> getRegistryHolder()
    {
        return ArtisanUpgrades.CHARGING_ROD;
    }
}
