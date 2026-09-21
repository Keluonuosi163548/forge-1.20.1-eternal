package com.keluonuosi.eternallovemod.datagen;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

//方块标签
public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EternalLoveMod.MOD_ID, existingFileHelper);
    }

    //将指定方块添加到指定标签  使用tag获得标签，add方法添加
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
            ;


        tag(BlockTags.NEEDS_IRON_TOOL)
            ;

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
            ;
    }
}
