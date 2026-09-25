package com.keluonuosi.eternallovemod.item.ModCurios;

import com.google.common.collect.Multimap;
import com.keluonuosi.eternallovemod.Tag.ModItemTags;
import com.keluonuosi.eternallovemod.data.ModSavedData;
import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import javax.annotation.Nullable;
import java.nio.file.Files;
import java.util.*;

import static com.keluonuosi.eternallovemod.ModEventBus.CONFIG_PATH;

public class EternalLoveringItem extends Item implements ICurioItem {

    public static final String ELRI_UUID_STRING ="0c061983-db38-48e0-8276-6c35cc9464ae";

    public EternalLoveringItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    // 每 tick 执行的逻辑
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        // 每 tick 执行的逻辑，例如给佩戴者施加效果
        // 判断实体是否为玩家
        if (!(slotContext.entity() instanceof Player player)) return;

        // 只在服务端执行
        if (player.level().isClientSide()) return;

        //如果玩家和物品的uuid不匹配，则执行完后返回
        if(isEternalUUID(player, stack)) return;

        //正常逻辑（属性、效果）
        if (!player.hasEffect(MobEffects.REGENERATION)) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 50, 1, false, false, true));
        }
        if (!player.hasEffect(MobEffects.JUMP)) {
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 50, 1, false, false, true));
        }

        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false, true));


    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        // 装备时的逻辑
        if (slotContext.entity() instanceof Player player) {
            if (isOwner(player)) {
                player.sendSystemMessage(Component.translatable("eternal_love_ring.owner_message0"));
            }
        }

    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        // 卸下时的逻辑
        if (slotContext.entity().getHealth() > slotContext.entity().getMaxHealth()) {
            slotContext.entity().setHealth(Math.min(slotContext.entity().getHealth(), slotContext.entity().getMaxHealth()));
        }
    }

    // 使用物品时的逻辑
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    // 使用物品右键方块时的逻辑
    @Override
    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.FAIL;
    }

    // 是否可以装备
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return "eternal_love_ring".equals(slotContext.identifier());
    }

    // 是否可以卸下
    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return true;
    }


    // 获取属性修改器
    //在计算玩家属性时会调用此方法 返回一个 Multimap<属性, 属性修改器>，即"哪些属性要被修改，怎么修改"。
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        // 获取一个初始的（通常是空的）Multimap
        Multimap<Attribute, AttributeModifier> modifiers = ICurioItem.super.getAttributeModifiers(slotContext, uuid, stack);
        // 添加属性修改器 修改属性：最大生命值
        modifiers.put(Attributes.MAX_HEALTH,
                //参数：UUID, 名称, 修改值, 操作方式
                new AttributeModifier(uuid, "eternal_love_ring_health", 16.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ATTACK_SPEED,//攻击速度
                //参数：UUID, 名称, 修改值, 操作方式
                new AttributeModifier(uuid, "eternal_love_ring_attack_speed", 1.1, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ATTACK_DAMAGE,//攻击伤害
                //参数：UUID, 名称, 修改值, 操作方式
                new AttributeModifier(uuid, "eternal_love_ring_attack_damage", 3.0, AttributeModifier.Operation.ADDITION));
       modifiers.put(Attributes.ARMOR,//盔甲值
                //参数：UUID, 名称, 修改值, 操作方式
                new AttributeModifier(uuid, "eternal_love_ring_armor", 5.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ARMOR_TOUGHNESS,//盔甲韧性
                //参数：UUID, 名称, 修改值, 操作方式
                new AttributeModifier(uuid, "eternal_love_ring_armor_toughness", 20.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.KNOCKBACK_RESISTANCE,//击退抗性
                //参数：UUID, 名称, 修改值, 操作方式
                new AttributeModifier(uuid, "eternal_love_ring_knockback_resistance", 1.1, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.MOVEMENT_SPEED,//移动速度
                //参数：UUID, 名称, 修改值, 操作方式
                new AttributeModifier(uuid, "eternal_love_ring_movement_speed", 0.22, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.FLYING_SPEED,//飞行速度
                //参数：UUID, 名称, 修改值, 操作方式
                new AttributeModifier(uuid, "eternal_love_ring_jump_strength", 1.1, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.LUCK,//幸运
                //参数：UUID, 名称, 修改值, 操作方式
                new AttributeModifier(uuid, "eternal_love_ring_luck", 48.0, AttributeModifier.Operation.ADDITION));

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (Screen.hasShiftDown()) {
            pTooltip.add(Component.translatable("tooltip.eternallovemod.eternal_love_ring_item.shift"));
            //加载启示录额外提示
            if ( ModList.get().isLoaded("goety_revelation") && Files.exists(CONFIG_PATH)){
                pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item.shift_goety_revelation" ));
            }
            //加载莱特兰-恶意额外提示
            if (ModList.get().isLoaded("l2hostility")) {
                pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item.shift_l2hostility" ));
            }
            //加载精妙背包额外提示
            if(ModList.get().isLoaded("sophisticatedbackpacks")){
                pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item.shift_sophisticatedbackpacks" ));
            }
        }else{
            pTooltip.add(Component.translatable("tooltip.eternallovemod.eternal_love_ring_item"));
        }
    }

    public boolean isEternalUUID(Player player, ItemStack stack){
        //判断是否在服务端
        if(player.level().isClientSide()) return false;
        //如果玩家和物品的uuid不匹配，则执行以下逻辑
        if (!isOwner(player)) {
            player.sendSystemMessage(Component.translatable("eternal_love_ring.owner_message1"));
            // 遍历所有槽位类型，找到实际装备的物品并移除
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                handler.getCurios().forEach((slotId, stacksHandler) -> {
                    var stacks = stacksHandler.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack inSlot = stacks.getStackInSlot(i);
                        if (inSlot == stack) {
                            stacksHandler.getStacks().setStackInSlot(i, ItemStack.EMPTY);
                            player.drop(inSlot, false);
                            // 在杀死玩家前，主动扫描背包保存受保护物品
                            ServerLevel serverLevel = (ServerLevel) player.level();
                            ModSavedData savedData = ModSavedData.get(serverLevel);
                            Map<UUID, List<ItemStack>> keepItemsByOwner = new HashMap<>();

                            // 扫描主背包和装备栏
                            for (int j = 0; j < player.getInventory().getContainerSize(); j++) {
                                ItemStack invStack = player.getInventory().getItem(j);
                                UUID ownerUUID = getBoundOwnerUUID(invStack, player);
                                if (ownerUUID != null) {
                                    keepItemsByOwner.computeIfAbsent(ownerUUID, k -> new ArrayList<>())
                                            .add(invStack.copy());
                                    player.getInventory().setItem(j, ItemStack.EMPTY);
                                }
                            }

                            // 扫描其他Curios槽位
                            handler.getCurios().forEach((sId, sHandler) -> {
                                var sStacks = sHandler.getStacks();
                                for (int k = 0; k < sStacks.getSlots(); k++) {
                                    ItemStack curioStack = sStacks.getStackInSlot(k);
                                    UUID ownerUUID = getBoundOwnerUUID(curioStack, player);
                                    if (ownerUUID != null) {
                                        keepItemsByOwner.computeIfAbsent(ownerUUID, k2 -> new ArrayList<>())
                                                .add(curioStack.copy());
                                        sStacks.setStackInSlot(k, ItemStack.EMPTY);
                                    }
                                }
                            });

                            if (!keepItemsByOwner.isEmpty()) {
                                for (Map.Entry<UUID, List<ItemStack>> entry : keepItemsByOwner.entrySet()) {
                                    savedData.getDeathKeepItems().put(entry.getKey(), entry.getValue());
                                }
                                savedData.setDirty();
                            }

                            player.setHealth(0.0F);
                            return;

                        }
                    }
                });
            });
            return true;
        }
        return false;
    }

    private boolean isOwner(Player player) {
        return player.getStringUUID().equals(ELRI_UUID_STRING);
    }

    private static UUID getBoundOwnerUUID(ItemStack stack, Player player) {
        if (stack.isEmpty()) return null;
        if (stack.is(ModItems.ETERNAL_LOVE_RING.get())) {
            return UUID.fromString(ELRI_UUID_STRING);
        }
        if (stack.is(ModItems.LOVE_RING.get())) {
            return LoveringItem.getOwnerUUID(stack);
        }
        if (stack.is(ModItemTags.ETERNAL_LOVE_SOUL_BOUND)) {
            return player.getUUID();
        }
        return null;
    }


}
