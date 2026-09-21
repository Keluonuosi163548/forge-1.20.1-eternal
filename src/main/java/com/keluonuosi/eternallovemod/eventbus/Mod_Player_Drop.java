package com.keluonuosi.eternallovemod.eventbus;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.keluonuosi.eternallovemod.Tag.ModItemTags;
import com.keluonuosi.eternallovemod.data.ModSavedData;
import com.keluonuosi.eternallovemod.item.ModCurios.LoveringItem;
import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

import static com.keluonuosi.eternallovemod.item.ModCurios.EternalLoveringItem.ELRI_UUID_STRING;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class Mod_Player_Drop {


    //监听玩家死亡战利品事件，保留特定物品
    @SubscribeEvent
    public static void onPlayerDrops(LivingDropsEvent event) {
        //判断死亡实体是否为玩家，执行时是否为服务端
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide()) return;

        // 创建一个列表用于存放保留的物品
        Map<UUID, List<ItemStack>> keepItemsByOwner = new HashMap<>();
        Iterator<ItemEntity> it = event.getDrops().iterator();

        // 遍历死亡战利品列表
        while (it.hasNext()) {
            ItemEntity itemEntity = it.next();
            ItemStack stack = itemEntity.getItem();

            UUID ownerUUID = null;
            //判断物品是否为特定物品
            if (stack.is(ModItems.ETERNAL_LOVE_RING.get())) {
                ownerUUID = UUID.fromString(ELRI_UUID_STRING);
            }else if(stack.is(ModItems.LOVE_RING.get())) {
                // 读取爱之戒NBT中绑定的UUID
                ownerUUID = LoveringItem.getOwnerUUID(stack);
            }else if(stack.is(ModItemTags.ETERNAL_LOVE_SOUL_BOUND)){
                ownerUUID = player.getUUID();
            }

            if (ownerUUID != null) {
                keepItemsByOwner.computeIfAbsent(ownerUUID, k -> new ArrayList<>())
                        .add(stack.copy());
                it.remove();
            }


        }
        // 如果保留列表不为空，则将其放入死亡保留物品Map中
        if (!keepItemsByOwner.isEmpty()) {
            // 获取保存数据
            ServerLevel serverLevel = (ServerLevel) player.level();
            ModSavedData savedData = ModSavedData.get(serverLevel);
            // 将该uuid玩家死亡时保留的物品写入数据
            for (Map.Entry<UUID, List<ItemStack>> entry : keepItemsByOwner.entrySet()) {
                savedData.getDeathKeepItems().put(entry.getKey(), entry.getValue());
            }
            savedData.setDirty();
        }
    }
}
