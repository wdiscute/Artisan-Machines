package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.wdiscute.artisan.machines.AbstractMachineBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public record MachineUpgrade
        (
                List<BlockEntityType<?>> machines,
                List<AbstractUpgrade> upgrades
        )
{
    public static final MachineUpgrade EMPTY = new MachineUpgrade(List.of(), List.of());

    public static final Codec<MachineUpgrade> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE.byNameCodec().listOf().fieldOf("machines").forGetter(MachineUpgrade::machines),
                    AbstractUpgrade.UPGRADE_CODEC.listOf().fieldOf("upgrades").forGetter(MachineUpgrade::upgrades)
            ).apply(instance, MachineUpgrade::new));


    public MachineUpgrade(BlockEntityType<?> machine, AbstractUpgrade upgrade)
    {
        this(List.of(machine), List.of(upgrade));
    }

    public MachineUpgrade(List<BlockEntityType<?>> machines, AbstractUpgrade upgrade)
    {
        this(machines, List.of(upgrade));
    }

    public MachineUpgrade(BlockEntityType<?> machine, List<AbstractUpgrade> upgrades)
    {
        this(List.of(machine), upgrades);
    }
}
