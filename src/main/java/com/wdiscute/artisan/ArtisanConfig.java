package com.wdiscute.artisan;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ArtisanConfig
{
    private static final ModConfigSpec.Builder BUILDER_CLIENT = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue MINIGAME_RENDER_SCALE = BUILDER_CLIENT
            .push("minigame_window")
            .translation("artisan_machines.configuration.tick_delay")
            .defineInRange("minigame_scale", 1.5, 0.1, 6);

    static final ModConfigSpec SPEC = BUILDER_CLIENT.build();


    private static final ModConfigSpec.Builder BUILDER_SERVER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue TICK_DELAY = BUILDER_SERVER
            .comment("Controls how many ticks each working machine waits before checking for a new hour")
            .translation("artisan_machines.configuration.tick_delay")
            .defineInRange("tick_delay", 999, 1, 999);

    static final ModConfigSpec SPEC_SERVER = BUILDER_SERVER.build();

}
