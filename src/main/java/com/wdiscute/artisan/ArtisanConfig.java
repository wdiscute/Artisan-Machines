package com.wdiscute.artisan;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ArtisanConfig
{
    private static final ModConfigSpec.Builder BUILDER_CLIENT = new ModConfigSpec.Builder();

    static final ModConfigSpec SPEC = BUILDER_CLIENT.build();


    private static final ModConfigSpec.Builder BUILDER_SERVER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue TICK_DELAY = BUILDER_SERVER
            .comment("Controls how many ticks each machine waits before running ticking logic, higher means better performance")
            .translation("artisan_machines.configuration.tick_delay")
            .defineInRange("tick_delay", 20, 1, 999);

    static final ModConfigSpec SPEC_SERVER = BUILDER_SERVER.build();

}
