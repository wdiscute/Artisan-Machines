package com.wdiscute.artisan.upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record MachineSettings
        (
                List<AbstractUpgrade> baseUpgrades,
                int upgradeSlots,
                boolean canUseRepeatedUpgrades
        )
{

    public static final MachineSettings DEFAULT = new MachineSettings(List.of(), 1, false);

    public static final Codec<MachineSettings> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    AbstractUpgrade.UPGRADE_CODEC.listOf().fieldOf("upgrades").forGetter(MachineSettings::baseUpgrades),
                    Codec.INT.fieldOf("upgrade_slots").forGetter(MachineSettings::upgradeSlots),
                    Codec.BOOL.fieldOf("can_use_repeated_upgrades").forGetter(MachineSettings::canUseRepeatedUpgrades)
            ).apply(instance, MachineSettings::new));

}
