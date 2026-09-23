package com.keluonuosi.eternallovemod;

import com.keluonuosi.eternallovemod.block.ModBlocks;
import com.keluonuosi.eternallovemod.eventbus.Mod_Loading;
import com.keluonuosi.eternallovemod.item.ModCreativeModeTabs;
import com.keluonuosi.eternallovemod.item.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import java.util.*;

/* SPDX-License-Identifier: LGPL-3.0-only */

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EternalLoveMod.MOD_ID)
public class EternalLoveMod
{
    public static final String MOD_ID = "eternal_love_mod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EternalLoveMod(FMLJavaModLoadingContext context)
    {


        //modEventBus为模组事件总线（模组加载时进行）
        //MinecraftForge.EVENT_BUS为游戏事件总线（游戏运行时进行）
        //获取模组事件总线
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        //注册游戏事件监听到mod事件总线
        modEventBus.addListener(this::commonSetup);
        //注册模组加载监听到模组事件总线
        modEventBus.addListener(Mod_Loading::onCommonSetup);



        // Register ourselves for server and other game events we are interested in
        //注册初始化模组事件到mod事件总线
        MinecraftForge.EVENT_BUS.register(this);
        ModItems.register(modEventBus);//注册物品
        ModCreativeModeTabs.register(modEventBus);//注册创造模式物品选项卡
        ModBlocks.register(modEventBus);//注册方块  //同时注册方块物品 //有关战利品列表的主要注释在该类中

        // Register the item to a creative tab
        //注册创造模式物品选项卡
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        //注册配置文件
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        //将物品添加到原版创造模式物品栏INGREDIENTS(原材料)选项卡
        //使用类名（方法名）
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
