package com.keluonuosi.eternallovemod.datagen;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.keluonuosi.eternallovemod.Tag.ModItemTags;
import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

//物品标签
public class ModItemTagsProvider extends ItemTagsProvider {


    public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, EternalLoveMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModItemTags.ETERNAL_LOVE_SOUL_BOUND)
                .add(ModItems.ETERNAL_LOVE_RING.get())
                .add(ModItems.LOVE_RING.get());
    }

}
