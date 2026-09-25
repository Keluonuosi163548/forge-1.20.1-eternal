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
            // 配置文件不存在则跳过
            if (!Files.exists(CONFIG_PATH)) return;

            try {
                ensureWhitelist();
            }catch (RuntimeException e){
                throw e;
            }
            catch (Exception e) {
                EternalLoveMod.LOGGER.error("Failed to update revelation config whitelist", e);
            }
        }
    }

    // 确保配置文件的白名单中包含指定物品
    private static void ensureWhitelist() throws IOException {
        String content = Files.readString(CONFIG_PATH);
        boolean modified = false;

        for (String ringId : ETERNAL_LOVE_MOD_SAVE) {
            if (!content.contains(ringId)) {
                content = insertIntoWhitelist(content, ringId);
                modified = true;
            }
        }

        if (modified) {
            Files.writeString(CONFIG_PATH, content);
            EternalLoveMod.LOGGER.info("Added eternal love rings to revelation whitelist");
            throw new RuntimeException(
                    "\n\n[永恒之爱 / Eternal Love Mod]" +
                    "\n[检测到诡厄巫法·启示录 - 已自动加载联动配置]" +
                    "\n[Weird Goety Revelation detected - linked settings loaded automatically]" +
                    "\n请重启游戏使配置生效 / Please restart the game" +
                    "\n"
            );
        }
    }

    private static String insertIntoWhitelist(String content, String itemId) {
        // 找到 whitelistItems
        String key = "whitelistItems";
        int keyIndex = content.indexOf(key);
        if (keyIndex == -1) return content;

        // 找到 [ 的位置
        int bracketStart = content.indexOf('[', keyIndex);
        if (bracketStart == -1) return content;

        // 找到 ] 的位置
        int bracketEnd = content.indexOf(']', bracketStart);
        if (bracketEnd == -1) return content;

        // 在 ] 前插入指定物品 ID
        String listContent = content.substring(bracketStart + 1, bracketEnd).trim();
        String newEntry = listContent.isEmpty()
                ? "\"" + itemId + "\""
                : ", \"" + itemId + "\"";

        return content.substring(0, bracketEnd) + newEntry + content.substring(bracketEnd);
    }
}
