package com.wdiscute.artisan.datagen;

import com.wdiscute.artisan.Artisan;
import com.wdiscute.artisan.registry.ArtisanBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class DGArtisanItemModelProvider extends ItemModelProvider
{
    public DGArtisanItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, Artisan.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        //make item model that just links to base block model
        ArtisanBlocks.BLOCKS.getEntries().forEach(
                o -> withExistingParent(BuiltInRegistries.BLOCK.getKey(o.get()).toString(),
                        Artisan.rl("block/" + BuiltInRegistries.BLOCK.getKey(o.get()).getPath() + "_working")

                ));
    }

    private ItemModelBuilder simpleItem(DeferredItem<? extends Item> item)
    {
        return withExistingParent(item.getId().getPath(), mcLoc("item/generated")).texture("layer0", modLoc("item/" + item.getId().getPath()));
    }
}
