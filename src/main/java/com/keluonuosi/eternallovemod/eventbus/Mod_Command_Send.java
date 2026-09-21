package com.keluonuosi.eternallovemod.eventbus;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.keluonuosi.eternallovemod.item.ModCurios.EternalLoveringItem.ELRI_UUID_STRING;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class Mod_Command_Send {


    //监听命令事件，防止恒爱之戒被clear清除
    @SubscribeEvent
    public static void onCommand(CommandEvent event) {
        ParseResults<CommandSourceStack> parseResults = event.getParseResults();

        String input = parseResults.getReader().getString();
        if (input.startsWith("clear") || input.startsWith("minecraft:clear")) {

            // 获取命令源
            CommandSourceStack source = (CommandSourceStack) parseResults.getContext().getSource();

            // 没指定目标 → 目标是执行者自己
            String args = input.replaceFirst("^(minecraft:)?clear\\s*", "").trim();

            if (args.isEmpty()) {
                // 目标是执行者，检查执行者是否是绑定玩家
                if (source.getEntity() instanceof ServerPlayer player) {
                    if (player.getStringUUID().equals(ELRI_UUID_STRING)) {
                        event.setCanceled(true);
                        source.sendFailure(Component.translatable("eternal_love_ring.command_clear"));
                    }
                }
            } else {
                // 指定了目标，检查参数字符串是否包含绑定玩家的 UUID 或名称
                ServerPlayer targetPlayer = source.getLevel().getServer()
                        .getPlayerList().getPlayerByName(args);
                if (targetPlayer != null && targetPlayer.getStringUUID().equals(ELRI_UUID_STRING)) {
                    event.setCanceled(true);
                    source.sendFailure(Component.translatable("eternal_love_ring.command_clear"));
                }
                // 如果是选择器 @a/@e 等，检查 UUID 字符串是否在命令中
                if (input.contains(ELRI_UUID_STRING.toLowerCase())) {
                    event.setCanceled(true);
                    source.sendFailure(Component.translatable("eternal_love_ring.command_clear"));
                }
            }
        }
    }
}
