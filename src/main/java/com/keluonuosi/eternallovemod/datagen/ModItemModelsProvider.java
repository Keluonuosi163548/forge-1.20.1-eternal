package com.keluonuosi.eternallovemod.datagen;

import com.keluonuosi.eternallovemod.EternalLoveMod;

import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

//物品模型
public class ModItemModelsProvider extends ItemModelProvider {

    public ModItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EternalLoveMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //basicItem为默认物品模型

        //注册普通物品
        basicItem(ModItems.ETERNAL_LOVE_RING.get());
        basicItem(ModItems.LOVE_RING.get());

    }



    //特殊物品物品模型方法（物品实例 素体块）
    private <T extends Block> void buttonItem(RegistryObject<T> block,RegistryObject<Block> bese) {
        this.withExistingParent(block.getId().getPath(),mcLoc("block/button_inventory"))
            .texture("texture",ResourceLocation.fromNamespaceAndPath(EternalLoveMod.MOD_ID, "block/" + bese.getId().getPath()));
    }

    private <T extends Block> void fenceItem(RegistryObject<T> block,RegistryObject<Block> bese) {
        this.withExistingParent(block.getId().getPath(),mcLoc("block/fence_inventory"))
                .texture("texture",ResourceLocation.fromNamespaceAndPath(EternalLoveMod.MOD_ID, "block/" + bese.getId().getPath()));
    }

    private <T extends Block> void wallItem(RegistryObject<T> block,RegistryObject<Block> bese) {
        this.withExistingParent(block.getId().getPath(),mcLoc("block/wall_inventory"))
                .texture("wall",ResourceLocation.fromNamespaceAndPath(EternalLoveMod.MOD_ID, "block/" + bese.getId().getPath()));
    }

    //手持物品模型注册方法
    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                mcLoc("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(EternalLoveMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
