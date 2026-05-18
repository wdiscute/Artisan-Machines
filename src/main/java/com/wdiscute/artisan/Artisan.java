package com.wdiscute.artisan;

import com.mojang.logging.LogUtils;
import com.wdiscute.artisan.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

import java.util.Random;

@Mod(Artisan.MOD_ID)
public class Artisan
{
    public static final Random r = new Random();
    public static final String MOD_ID = "artisan_machines";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation rl(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public Artisan(IEventBus modEventBus, ModContainer modContainer)
    {
        ArtisanItems.register(modEventBus);
        ArtisanBlocks.register(modEventBus);
        ArtisanBlockEntities.register(modEventBus);
        ArtisanCreativeModeTabs.register(modEventBus);
        ArtisanRecipeTypes.register(modEventBus);
        ArtisanRecipeSerializers.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.CLIENT, ArtisanConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, ArtisanConfig.SPEC_SERVER);
    }

    @Mod(value = Artisan.MOD_ID, dist = Dist.CLIENT)
    public static class Client
    {
        public Client(ModContainer modContainer)
        {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
}
