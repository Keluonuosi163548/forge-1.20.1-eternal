package com.keluonuosi.eternallovemod.datagen;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

//方块模型
public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EternalLoveMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {


    }


    //方块项模型数据生成方法
    private <T extends Block> void blockItem(RegistryObject<T> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(EternalLoveMod.MOD_ID + ":block/" + block.getId().getPath()));
    }
    //方块项模型数据生成方法（带后缀）
    private <T extends Block> void blockItem(RegistryObject<T> block, String append) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(EternalLoveMod.MOD_ID + ":block/" + block.getId().getPath() + append));
    }


    //作物模型生成方法
    public void crop(CropBlock block, String name, IntegerProperty property) {
        Function<BlockState, ConfiguredModel[]> function = state ->
                cropStates(state, name, property);

        getVariantBuilder(block).forAllStates(function);
    }

    //
    private ConfiguredModel[] cropStates(BlockState state, String modelName, IntegerProperty property) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(property),
                ResourceLocation.fromNamespaceAndPath(EternalLoveMod.MOD_ID, "block/" + modelName + state.getValue(property))).renderType("cutout"));

        return models;
    }

}
