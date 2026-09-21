package com.keluonuosi.eternallovemod.item;

import com.keluonuosi.eternallovemod.EternalLoveMod;

import com.keluonuosi.eternallovemod.item.ModCurios.EternalLoveringItem;
import com.keluonuosi.eternallovemod.item.ModCurios.LoveringItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    //创建物品注册表ITEMS
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EternalLoveMod.MOD_ID);

    //注册物品
    //注册名可用a-z A-Z 0-9 _ /       不可用 - 空格 及其他字符
    //ITEMS.register中注册名建议为方法名小写，可以使用斜杠指定路径，该路径用于分类资源文件，不会影响物品注册名
    //物品属性资源文件路径为resources/assets/ <MOD_ID> /models/item/ <注册名>.json
    //物品纹理资源文件路径为resources/assets/ <MOD_ID> /textures/items/ <注册名>.png
    //如果使用斜杠指定路径，在语言文件中需将 / 转义为 .      并且在资源文件路径下新建对应路径文件夹
    //例如属性资源文件路径为 resources/assets/ <MOD_ID> /models/item/<路径名>/ <注册名>.json  纹理路径同理

    //物品属性方法
    //stacksTo(int)
    //设置最大堆叠数量（1~64）
    //durability(int)    从0计数 既255耐久可用256次
    //设置最大耐久值（自动将堆叠数设为 1）
    //craftRemainder(Item)
    //设置合成后留在合成格中的物品（如桶合成后留下桶）
    //rarity(Rarity)
    //设置物品稀有度（影响物品名称颜色：白色/黄色/蓝色/紫色） COMMON UNCOMMON RARE EPIC
    //food(FoodProperties)
    //设置食物的食物属性（饥饿值、饱和度、效果等）
    //fireResistant()
    //使物品免疫火焰/岩浆销毁


    public static final RegistryObject<Item> ETERNAL_LOVE_RING =
            ITEMS.register("eternal_love", () -> new EternalLoveringItem(
                    new Item.Properties().fireResistant().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> LOVE_RING =
            ITEMS.register("love_ring", () -> new LoveringItem(
                    new Item.Properties().fireResistant().rarity(Rarity.EPIC)));



    //将物品注册表注册事件注入总线
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}