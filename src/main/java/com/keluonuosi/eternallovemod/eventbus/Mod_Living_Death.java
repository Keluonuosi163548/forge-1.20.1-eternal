package com.keluonuosi.eternallovemod.eventbus;


import com.keluonuosi.eternallovemod.EternalLoveMod;
import com.keluonuosi.eternallovemod.ModEventBus;
import com.keluonuosi.eternallovemod.item.ModCurios.LoveringItem;
import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.keluonuosi.eternallovemod.ModEventBus.LIT;

@Mod.EventBusSubscriber(modid = EternalLoveMod.MOD_ID) // 默认就是 Bus.FORGE
public class Mod_Living_Death {
    @SubscribeEvent
    public static void onLivingDeath_PlayerKillEntity(LivingDeathEvent event) {

        // 玩家触发的死亡事件
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            // 确认在服务端执行
            if (player.level().isClientSide()) return;

            // 玩家击杀了末影龙
            if (event.getEntity() instanceof EnderDragon) {
                List<ItemStack> eternalloverings =ModEventBus.getItemsInSlot(player,"eternal_love_ring", ModItems.LOVE_RING.get());
                for (ItemStack stack : eternalloverings) {
                    CompoundTag tag = stack.getOrCreateTag();
                    if (!tag.contains(LoveringItem.LRI_DRAGON_KILL)) {
                        tag.putBoolean(LoveringItem.LRI_DRAGON_KILL, true);

                        Advancement advancement = player.server.getAdvancements()
                                .getAdvancement(ResourceLocation.fromNamespaceAndPath(EternalLoveMod.MOD_ID, "kill_dragon"));
                        if (advancement != null) {
                            player.getAdvancements().award(advancement, "impossible");
                        }

                        player.sendSystemMessage(Component.translatable("death_message.love_ring.dragon_kill"));
                    }
                }
            }
            // 玩家击杀了凋零
            if (event.getEntity() instanceof WitherBoss) {
                List<ItemStack> eternalloverings =ModEventBus.getItemsInSlot(player,"eternal_love_ring", ModItems.LOVE_RING.get());
                for (ItemStack stack : eternalloverings) {
                    CompoundTag tag = stack.getOrCreateTag();
                    if (!tag.contains(LoveringItem.LRI_WITHER_KILL)) {
                        tag.putBoolean(LoveringItem.LRI_WITHER_KILL, true);

                        Advancement advancement = player.server.getAdvancements()
                                .getAdvancement(ResourceLocation.fromNamespaceAndPath(EternalLoveMod.MOD_ID, "kill_wither"));
                        if (advancement != null) {
                            player.getAdvancements().award(advancement, "impossible");
                        }

                        player.sendSystemMessage(Component.translatable("death_message.love_ring.wither_kill"));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            // 确认在服务端执行
            if (player.level().isClientSide()) return;

            //获取玩家持久化数据
            CompoundTag data = player.getPersistentData();
            if (!data.contains(EternalLoveMod.MOD_ID)) {
                data.put(EternalLoveMod.MOD_ID, new CompoundTag());
            }
            int ticks = data.getCompound(EternalLoveMod.MOD_ID).getInt(LIT);


            //检测玩家永恒之爱槽位是否有爱之戒
            List<ItemStack> love_rings = ModEventBus.getItemsInSlot(player, "eternal_love_ring", ModItems.LOVE_RING.get());
            // 检查是否有带凋零之证的爱之戒
            for (ItemStack stack : love_rings) {
                CompoundTag tag = stack.getTag();
                if (tag != null && tag.contains(LoveringItem.LRI_WITHER_KILL)) {
                    //判断是否处于冷却
                    //10s无敌持续时

                    if(!player.getCooldowns().isOnCooldown(ModItems.LOVE_RING.get())) {
                        // 免疫死亡
                        event.setCanceled(true);
                        player.setHealth(1.0f);
                        player.sendSystemMessage(Component.translatable("death_message.love_ring.playerdeath"));
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 4));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4));

                        player.level().playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);

                        // 冷却逻辑
                        player.getCooldowns().addCooldown(ModItems.LOVE_RING.get(), 600 * 20);

                        //10s无敌
                        if(ticks==0 || !data.getCompound(EternalLoveMod.MOD_ID).contains(LIT)){
                            data.getCompound(EternalLoveMod.MOD_ID).putInt(LIT, 200);
                        }
                    //判断是否处于10s无敌持续时间
                    } else if (ticks > 0) {
                        event.setCanceled(true);
                        player.setHealth(1.0f);
                    }
                    return;
                }
            }

            //检测玩家永恒之爱槽位是否有恒爱之戒
            List<ItemStack> eternal_love_rings = ModEventBus.getItemsInSlot(player, "eternal_love_ring", ModItems.ETERNAL_LOVE_RING.get());
            if(!eternal_love_rings.isEmpty()){
                //判断是否处于冷却

                if(!player.getCooldowns().isOnCooldown(ModItems.ETERNAL_LOVE_RING.get())) {
                    // 免疫死亡
                    event.setCanceled(true);
                    player.setHealth(1.0f);
                    player.sendSystemMessage(Component.translatable("death_message.eternal_love_ring.playerdeath"));
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 4));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4));

                    player.level().playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);

                    // 冷却逻辑
                    player.getCooldowns().addCooldown(ModItems.ETERNAL_LOVE_RING.get(), 600 * 20);

                    //10s无敌
                    if(ticks==0 || !data.getCompound(EternalLoveMod.MOD_ID).contains(LIT)){
                        data.getCompound(EternalLoveMod.MOD_ID).putInt(LIT, 200);
                    }
                //判断是否处于10s无敌持续时间
                }else if (ticks > 0) {
                    event.setCanceled(true);
                    player.setHealth(1.0f);
                }
            }
        }
    }
}
