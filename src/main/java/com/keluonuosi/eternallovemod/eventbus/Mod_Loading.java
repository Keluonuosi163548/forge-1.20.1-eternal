package com.keluonuosi.eternallovemod.eventbus;


import com.keluonuosi.eternallovemod.EternalLoveMod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static com.keluonuosi.eternallovemod.ModEventBus.CONFIG_PATH;
import static com.keluonuosi.eternallovemod.ModEventBus.ETERNAL_LOVE_MOD_SAVE;

public class Mod_Loading {



    //模组加载时调用
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        // 启示录联动：
        if(ModList.get().isLoaded("goety_revelation")) {

        }
    }

    // 确保配置文件的白名单中包含指定物品
    private static void ensureWhitelist() throws IOException {

    }


}
