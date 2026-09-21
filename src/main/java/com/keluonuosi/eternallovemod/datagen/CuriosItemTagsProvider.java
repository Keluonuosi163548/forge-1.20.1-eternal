
package com.keluonuosi.eternallovemod.datagen;

import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

import static com.keluonuosi.eternallovemod.Tag.ModCuriosItemTags.*;


public class CuriosItemTagsProvider extends ItemTagsProvider {


    public CuriosItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                                  CompletableFuture<TagLookup<Block>> pBlockTags,
                                  @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, "curios", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // 将饰品物品添加到 necklace 槽位标签
        //项链
        tag(NECKLACE);
        //戒指
         tag(RING);
        //腰带
         tag(BELT);
        //头饰
         tag(HEAD);
        //背部
         tag(BACK);
        //身体
         tag(BODY);
        //饰品
         tag(CURIO);

         //
        tag(ETERNAL_LOVE_RING)
                .add(ModItems.ETERNAL_LOVE_RING.get())
                .add(ModItems.LOVE_RING.get());
    }
}