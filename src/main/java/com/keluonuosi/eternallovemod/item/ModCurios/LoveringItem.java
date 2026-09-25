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
import static com.keluonuosi.eternallovemod.item.ModCurios.EternalLoveringItem.ELRI_UUID_STRING;

public class LoveringItem extends Item implements ICurioItem {

    private static final String LRI_TIER = "lovering_tier";
    private static final String LRI_NAME = "lovering_name";

    public static final String LRI_UUID = "lovering_uuid";
    public static final String LRI_DRAGON_KILL = "lovering_dragon_kill";
    public static final String LRI_WITHER_KILL = "lovering_wither_kill";

    public LoveringItem(Properties properties) {
        super(properties.stacksTo(1));
    }



    //物品在_curio槽每tick触发
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        // 判断实体是否为玩家
        if (!(slotContext.entity() instanceof Player player)) return;
        // 只在服务端执行
        if (player.level().isClientSide()) return;
        //如果玩家和物品的uuid不匹配，则执行完后返回
        CompoundTag tag = stack.getOrCreateTag();

        //随机生成初始属性
        if (!tag.contains(LRI_TIER)) {
            tag.putInt(LRI_TIER, new java.util.Random().nextInt(9) + 1);
        }
        //绑定玩家uuid和名称
        if (!tag.contains(LRI_UUID)) {
            tag.putUUID(LRI_UUID, player.getUUID());
            tag.putString(LRI_NAME, player.getName().getString());
        }else{
            //检查物品的uuid是否和玩家匹配
            isEternalUUID(player, stack);
        }
    }

    //物品被装备时触发
    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        // 判断实体是否为玩家
        if(slotContext.entity() instanceof Player player) {

        }

    }

    //物品被卸载时触发
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {

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


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {

        Multimap<Attribute, AttributeModifier> modifiers = ICurioItem.super.getAttributeModifiers(slotContext, uuid, stack);

        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(LRI_TIER)) return modifiers;
        int tier = tag.getInt(LRI_TIER);

        if(tag.contains(LRI_DRAGON_KILL)){
            modifiers.put(Attributes.MAX_HEALTH,//最大生命值
                    //参数：UUID, 名称, 修改值, 操作方式
                    new AttributeModifier(uuid, "eternal_love_ring_health", 8.0, AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.ATTACK_SPEED,//攻击速度
                    //参数：UUID, 名称, 修改值, 操作方式
                    new AttributeModifier(uuid, "eternal_love_ring_attack_speed", 0.2, AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.ATTACK_DAMAGE,//攻击伤害
                    //参数：UUID, 名称, 修改值, 操作方式
                    new AttributeModifier(uuid, "eternal_love_ring_attack_damage", 1.5, AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.ARMOR,//盔甲值
                    //参数：UUID, 名称, 修改值, 操作方式
                    new AttributeModifier(uuid, "eternal_love_ring_armor", 4.0, AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.ARMOR_TOUGHNESS,//盔甲韧性
                    //参数：UUID, 名称, 修改值, 操作方式
                    new AttributeModifier(uuid, "eternal_love_ring_armor_toughness", 3.0, AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.KNOCKBACK_RESISTANCE,//击退抗性
                    //参数：UUID, 名称, 修改值, 操作方式
                    new AttributeModifier(uuid, "eternal_love_ring_knockback_resistance", 0.3, AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.MOVEMENT_SPEED,//移动速度
                    //参数：UUID, 名称, 修改值, 操作方式
                    new AttributeModifier(uuid, "eternal_love_ring_movement_speed", 0.13, AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.FLYING_SPEED,//飞行速度
                    //参数：UUID, 名称, 修改值, 操作方式
                    new AttributeModifier(uuid, "eternal_love_ring_flying_speed", 0.25, AttributeModifier.Operation.ADDITION));
            modifiers.put(Attributes.LUCK,//幸运
                    //参数：UUID, 名称, 修改值, 操作方式
                    new AttributeModifier(uuid, "eternal_love_ring_luck", 3.0, AttributeModifier.Operation.ADDITION));
        }else {
            switch (tier) {
                case 1:
                    modifiers.put(Attributes.MAX_HEALTH,//最大生命值
                            //参数：UUID, 名称, 修改值, 操作方式
                            new AttributeModifier(uuid, "eternal_love_ring_health", 8.0, AttributeModifier.Operation.ADDITION));
                    break;
                case 2:
                    modifiers.put(Attributes.ATTACK_SPEED,//攻击速度
                            //参数：UUID, 名称, 修改值, 操作方式
                            new AttributeModifier(uuid, "eternal_love_ring_attack_speed", 0.2, AttributeModifier.Operation.ADDITION));
                    break;
                case 3:
                    modifiers.put(Attributes.ATTACK_DAMAGE,//攻击伤害
                            //参数：UUID, 名称, 修改值, 操作方式
                            new AttributeModifier(uuid, "eternal_love_ring_attack_damage", 1.5, AttributeModifier.Operation.ADDITION));
                    break;
                case 4:
                    modifiers.put(Attributes.ARMOR,//盔甲值
                            //参数：UUID, 名称, 修改值, 操作方式
                            new AttributeModifier(uuid, "eternal_love_ring_armor", 4.0, AttributeModifier.Operation.ADDITION));
                    break;
                case 5:
                    modifiers.put(Attributes.ARMOR_TOUGHNESS,//盔甲韧性
                            //参数：UUID, 名称, 修改值, 操作方式
                            new AttributeModifier(uuid, "eternal_love_ring_armor_toughness", 3.0, AttributeModifier.Operation.ADDITION));
                    break;
                case 6:
                    modifiers.put(Attributes.KNOCKBACK_RESISTANCE,//击退抗性
                            //参数：UUID, 名称, 修改值, 操作方式
                            new AttributeModifier(uuid, "eternal_love_ring_knockback_resistance", 0.3, AttributeModifier.Operation.ADDITION));
                    break;
                case 7:
                    modifiers.put(Attributes.MOVEMENT_SPEED,//移动速度
                            //参数：UUID, 名称, 修改值, 操作方式
                            new AttributeModifier(uuid, "eternal_love_ring_movement_speed", 0.13, AttributeModifier.Operation.ADDITION));
                    break;
                case 8:
                    modifiers.put(Attributes.FLYING_SPEED,//飞行速度
                            //参数：UUID, 名称, 修改值, 操作方式
                            new AttributeModifier(uuid, "eternal_love_ring_flying_speed", 0.25, AttributeModifier.Operation.ADDITION));
                    break;
                case 9:
                    modifiers.put(Attributes.LUCK,//幸运
                            //参数：UUID, 名称, 修改值, 操作方式
                            new AttributeModifier(uuid, "eternal_love_ring_luck", 3.0, AttributeModifier.Operation.ADDITION));
                    break;
                default:
                    break;
            }
        }

        return modifiers;

    }

    //物品提示
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (Screen.hasShiftDown()) {
            CompoundTag tag = pStack.getTag();
            if (tag == null || !tag.contains(LRI_UUID) || !tag.contains(LRI_NAME)){
                pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item.shift" ));
            }else {
                pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item.shift1")
                        .append(tag.getString(LRI_NAME)));
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


                //剧情标签判断
                if(!tag.contains(LRI_DRAGON_KILL) && !tag.contains(LRI_WITHER_KILL)){
                    pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item.shift2"));
                }else{
                    pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item.shift3"));
                    if(tag.contains(LRI_WITHER_KILL)){
                        pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item.shift4"));
                    }
                    if(tag.contains(LRI_DRAGON_KILL)){
                        pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item.shift5"));
                    }
                }

            }

        }else{
            pTooltip.add(Component.translatable("tooltip.eternallovemod.love_ring_item"));
        }
    }


    //只能装备在_curio槽
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return "eternal_love_ring".equals(slotContext.identifier());
    }

    //可以卸载
    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return true;
    }


    public boolean isEternalUUID(Player player, ItemStack stack){
        //判断是否在服务端
        if(player.level().isClientSide()) return false;
        //如果玩家和物品的uuid不匹配，则执行以下逻辑
        if (!isOwner(player, stack)) {
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

                            player.setHealth(0);
                            return;

                        }
                    }
                });
            });
            return true;
        }
        return false;
    }
    private boolean isOwner(Player player, ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(LRI_UUID) && player.getUUID().equals(tag.getUUID(LRI_UUID));
    }

    public static UUID getOwnerUUID(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(LRI_UUID)) {
            return tag.getUUID(LRI_UUID);
        }
        return null;
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
