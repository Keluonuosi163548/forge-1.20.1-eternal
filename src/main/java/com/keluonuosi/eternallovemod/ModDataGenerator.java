package com.keluonuosi.eternallovemod;

import com.keluonuosi.eternallovemod.Tag.ModCuriosItemTags;
import com.keluonuosi.eternallovemod.datagen.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

//调用数据生成
@Mod.EventBusSubscriber(modid=EternalLoveMod.MOD_ID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        //服务端文件
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        //构建合成表
        generator.addProvider(event.includeServer(),new ModRecipesProvider(packOutput));

        //构建战利品表
        generator.addProvider(event.includeServer(),new LootTableProvider(packOutput, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTablesProvider::new, LootContextParamSets.BLOCK)
        )));

        //构建方块标签
        BlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(),
                new ModBlockTagsProvider(packOutput,lookupProvider,existingFileHelper));

        //构建物品标签
        generator.addProvider(event.includeServer(),new ModItemTagsProvider(packOutput,lookupProvider,blockTagsProvider.contentsGetter(),existingFileHelper));

        //构建_curios_物品标签
        generator.addProvider(event.includeServer(), new CuriosItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));


        //客户端文件
        //构建方块状态表
        generator.addProvider(event.includeClient(),new ModBlockStateProvider(packOutput,existingFileHelper));
        //构建物品模型表
        generator.addProvider(event.includeClient(),new ModItemModelsProvider(packOutput,existingFileHelper));
        //构建语言文件
        generator.addProvider(event.includeClient(),new ModEnUsLangProvider(packOutput));
        generator.addProvider(event.includeClient(),new ModZhCnLangProvider(packOutput));

    }
}
