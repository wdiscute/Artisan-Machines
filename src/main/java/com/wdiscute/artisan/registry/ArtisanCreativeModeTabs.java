package com.wdiscute.artisan.registry;

import com.wdiscute.artisan.Artisan;
import net.mcexpanded.fancytabsections.FancyTabSections;
import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.mcexpanded.fancytabsections.creativetab.SectionColored;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ArtisanCreativeModeTabs
{
    DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Artisan.MOD_ID);

    static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
        addItems();
    }

    static void addItems()
    {
        //Must Have
        FancyTabSections.addSection(Artisan.rl("artisan_machines"),
                new SectionColored(
                        Artisan.rl("machines"),
                        Component.translatable("creativetab.artisan_machines.artisan_machines.machines"),
                        0xff344545,
                        0xffffffff,
                        ConglomerateOfItems.create()
                                .add(ArtisanItems.ITEMS)
                )
        );


        Supplier<CreativeModeTab> ARTISAN_MACHINES = CREATIVE_MODE_TABS.register(
                "artisan_machines", () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.LOOM))
                        .title(Component.translatable("creativetab.artisan_machines.artisan_machines"))
                        .displayItems((itemDisplayParameters, output) ->
                        {
                            //empty as it's added through Fancy Tab Sections instead
                        })
                        .build()
        );
    }
}
