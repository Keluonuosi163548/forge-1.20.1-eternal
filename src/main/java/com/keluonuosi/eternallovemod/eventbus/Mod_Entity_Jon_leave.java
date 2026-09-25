package com.keluonuosi.eternallovemod.eventbus;

import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.keluonuosi.eternallovemod.Tag.ModItemTags;
import com.keluonuosi.eternallovemod.data.ModSavedData;
import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.keluonuosi.eternallovemod.item.ModCurios.EternalLoveringItem.ELRI_UUID_STRING;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class Mod_Entity_Jon_leave {

    //监听实体加入世界事件，使特定物品不可被破坏
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        //指定恒爱之戒 或 恒爱绑定标签物品 不可破坏
        if (event.getEntity() instanceof ItemEntity itemEntity) {
            if (itemEntity.getItem().is(ModItems.ETERNAL_LOVE_RING.get()) || itemEntity.getItem().is(ModItemTags.ETERNAL_LOVE_SOUL_BOUND)) {
                itemEntity.setInvulnerable(true);
                //打入防吸取标签
                event.getEntity().getPersistentData().putBoolean("PreventRemoteMovement", true);
                //强加载所在区块
                if (event.getLevel() instanceof ServerLevel serverLevel) {
                    int chunkX = itemEntity.getBlockX() >> 4;
                    int chunkZ = itemEntity.getBlockZ() >> 4;
                    ForgeChunkManager.forceChunk(serverLevel, EternalLoveMod.MOD_ID, itemEntity, chunkX, chunkZ, true, false);
                }
            }
        }



    }


    //监听实体离开世界事件，使特定物品归还给原主
    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;

        // 获取物品栈
        ItemStack stack = itemEntity.getItem();
        UUID ownerUUID = null;

        // 如果物品是恒爱之戒 或 恒爱绑定标签物品，则取消强制加载所在区块
        if (itemEntity.getItem().is(ModItems.ETERNAL_LOVE_RING.get()) || itemEntity.getItem().is(ModItemTags.ETERNAL_LOVE_SOUL_BOUND)) {
            int chunkX = itemEntity.getBlockX() >> 4;
            int chunkZ = itemEntity.getBlockZ() >> 4;
            ForgeChunkManager.forceChunk(serverLevel, EternalLoveMod.MOD_ID, itemEntity, chunkX, chunkZ, false, false);
        }

        if (stack.is(ModItems.ETERNAL_LOVE_RING.get())) {
            // 获取物品的原主 UUID
            ownerUUID = UUID.fromString(ELRI_UUID_STRING);
            //遍历方块实体容器，移除特定物品
            BlockPos pos = itemEntity.blockPosition();
            //遍历实体离开世界时周围的方块实体容器
            for (BlockPos check : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
                BlockEntity be = serverLevel.getBlockEntity(check);
                if (be instanceof RandomizableContainerBlockEntity container) {
                    for (int i = 0; i < container.getContainerSize(); i++) {
                        if (container.getItem(i).is(ModItems.ETERNAL_LOVE_RING.get())) {
                            container.setItem(i, ItemStack.EMPTY);
                        }
                    }
                }
            }
            // 遍历实体容器，移除特定物品
            AABB aabb = new AABB(pos).inflate(2);
            for (Entity entity : serverLevel.getEntities(null, aabb)) {
                if (entity instanceof Container container) {
                    for (int i = 0; i < container.getContainerSize(); i++) {
                        if (container.getItem(i).is(ModItems.ETERNAL_LOVE_RING.get())) {
                            container.setItem(i, ItemStack.EMPTY);
                        }
                    }
                }
            }
        }

        //如果ownerUUID为空，则跳过
        if (ownerUUID == null) return;
        //获取uuid对应的玩家
        Player owner = serverLevel.getPlayerByUUID(ownerUUID);
        //如果该玩家存在且存活
        if (owner != null && owner.isAlive()) {
            //将物品归还给该玩家
            owner.getInventory().placeItemBackInInventory(stack.copy());
        }
        //如果没有找到该玩家或该玩家已死亡
        else{
            //将物品添加到待归还列表
            ModSavedData savedData = ModSavedData.get(serverLevel);
            List<ItemStack> ownerkeepItems = savedData.getOwnerKeepItems().computeIfAbsent(ownerUUID, k -> new ArrayList<>());

            ownerkeepItems.add(stack.copy());
            savedData.setDirty();
        }
    }
}
