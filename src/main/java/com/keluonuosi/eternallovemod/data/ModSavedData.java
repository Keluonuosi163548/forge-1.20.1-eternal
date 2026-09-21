package com.keluonuosi.eternallovemod.data;

import com.keluonuosi.eternallovemod.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class ModSavedData extends SavedData {

    private static final String DATA_NAME = "eternal_love_mod_data";

    private final Map<UUID, List<ItemStack>> ownerKeepItems = new HashMap<>();
    private final Map<UUID, List<ItemStack>> deathKeepItems = new HashMap<>();

    public ModSavedData() {
    }

    // 获取数据实例
    public static ModSavedData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(
                tag -> load(serverLevel, tag),//读取磁盘 如果有的话
                ModSavedData::new,//创建数据文件 如果没有的话
                DATA_NAME//文件名
        );
    }

    private static ModSavedData load(ServerLevel serverLevel, CompoundTag tag) {
        ModSavedData data = new ModSavedData();

        if (tag.contains("ownerKeepItems")) {
            ListTag list = tag.getList("ownerKeepItems", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag entry = list.getCompound(i);
                UUID uuid = entry.getUUID("uuid");
                ListTag items = entry.getList("items", Tag.TAG_COMPOUND);
                List<ItemStack> stacks = new ArrayList<>();
                for (int j = 0; j < items.size(); j++) {
                    stacks.add(ItemStack.of(items.getCompound(j)));
                }
                data.ownerKeepItems.put(uuid, stacks);
            }
        }

        if (tag.contains("deathKeepItems")) {
            ListTag list = tag.getList("deathKeepItems", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag entry = list.getCompound(i);
                UUID uuid = entry.getUUID("uuid");
                ListTag items = entry.getList("items", Tag.TAG_COMPOUND);
                List<ItemStack> stacks = new ArrayList<>();
                for (int j = 0; j < items.size(); j++) {
                    stacks.add(ItemStack.of(items.getCompound(j)));
                }
                data.deathKeepItems.put(uuid, stacks);
            }
        }

        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag ownerList = new ListTag();
        for (Map.Entry<UUID, List<ItemStack>> entry : ownerKeepItems.entrySet()) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putUUID("uuid", entry.getKey());
            ListTag items = new ListTag();
            for (ItemStack stack : entry.getValue()) {
                items.add(stack.save(new CompoundTag()));
            }
            entryTag.put("items", items);
            ownerList.add(entryTag);
        }
        tag.put("ownerKeepItems", ownerList);

        ListTag deathList = new ListTag();
        for (Map.Entry<UUID, List<ItemStack>> entry : deathKeepItems.entrySet()) {
            CompoundTag entryTag = new CompoundTag();
            entryTag.putUUID("uuid", entry.getKey());
            ListTag items = new ListTag();
            for (ItemStack stack : entry.getValue()) {
                items.add(stack.save(new CompoundTag()));
            }
            entryTag.put("items", items);
            deathList.add(entryTag);
        }
        tag.put("deathKeepItems", deathList);

        return tag;
    }

    public Map<UUID, List<ItemStack>> getOwnerKeepItems() {
        return ownerKeepItems;
    }

    public Map<UUID, List<ItemStack>> getDeathKeepItems() {
        return deathKeepItems;
    }
}
