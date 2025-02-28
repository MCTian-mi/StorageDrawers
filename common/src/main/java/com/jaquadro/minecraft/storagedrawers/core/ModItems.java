package com.jaquadro.minecraft.storagedrawers.core;

import com.jaquadro.minecraft.storagedrawers.ModConstants;
import com.jaquadro.minecraft.storagedrawers.block.BlockDrawers;
import com.jaquadro.minecraft.storagedrawers.block.BlockTrim;
import com.jaquadro.minecraft.storagedrawers.block.meta.BlockMeta;
import com.jaquadro.minecraft.storagedrawers.item.*;
import com.texelsaurus.minecraft.chameleon.ChameleonServices;
import com.texelsaurus.minecraft.chameleon.registry.ChameleonRegistry;
import com.texelsaurus.minecraft.chameleon.registry.RegistryEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class ModItems
{
    public static final ChameleonRegistry<Item> ITEMS = ChameleonServices.REGISTRY.create(BuiltInRegistries.ITEM, ModConstants.MOD_ID);

    public static final List<RegistryEntry<? extends Item>> EXCLUDE_ITEMS_CREATIVE_TAB = new ArrayList<>();

    public static final RegistryEntry<? extends Item>
        OBSIDIAN_STORAGE_UPGRADE = ITEMS.register("obsidian_storage_upgrade", () -> new ItemUpgradeStorage(EnumUpgradeStorage.OBSIDIAN, new Item.Properties())),
        IRON_STORAGE_UPGRADE = ITEMS.register("iron_storage_upgrade", () -> new ItemUpgradeStorage(EnumUpgradeStorage.IRON, new Item.Properties())),
        GOLD_STORAGE_UPGRADE = ITEMS.register("gold_storage_upgrade", () -> new ItemUpgradeStorage(EnumUpgradeStorage.GOLD, new Item.Properties())),
        DIAMOND_STORAGE_UPGRADE = ITEMS.register("diamond_storage_upgrade", () -> new ItemUpgradeStorage(EnumUpgradeStorage.DIAMOND, new Item.Properties())),
        EMERALD_STORAGE_UPGRADE = ITEMS.register("emerald_storage_upgrade", () -> new ItemUpgradeStorage(EnumUpgradeStorage.EMERALD, new Item.Properties())),
        ONE_STACK_UPGRADE = ITEMS.register("one_stack_upgrade", () -> new ItemUpgrade(new Item.Properties())),
        VOID_UPGRADE = ITEMS.register("void_upgrade", () -> new ItemUpgrade(new Item.Properties())),
        CREATIVE_STORAGE_UPGRADE = ITEMS.register("creative_storage_upgrade", () -> new ItemUpgrade(new Item.Properties())),
        CREATIVE_VENDING_UPGRADE = ITEMS.register("creative_vending_upgrade", () -> new ItemUpgrade(new Item.Properties())),
        //CONVERSION_UPGRADE = ITEMS.register("conversion_upgrade", () -> new ItemUpgrade(new Item.Properties())),
        REDSTONE_UPGRADE = ITEMS.register("redstone_upgrade", () -> new ItemUpgradeRedstone(EnumUpgradeRedstone.COMBINED, new Item.Properties())),
        MIN_REDSTONE_UPGRADE = ITEMS.register("min_redstone_upgrade", () -> new ItemUpgradeRedstone(EnumUpgradeRedstone.MIN, new Item.Properties())),
        MAX_REDSTONE_UPGRADE = ITEMS.register("max_redstone_upgrade", () -> new ItemUpgradeRedstone(EnumUpgradeRedstone.MAX, new Item.Properties())),
        ILLUMINATION_UPGRADE = ITEMS.register("illumination_upgrade", () -> new ItemUpgrade(new Item.Properties())),
        FILL_LEVEL_UPGRADE = ITEMS.register("fill_level_upgrade", () -> new ItemUpgrade(new Item.Properties())),
        UPGRADE_TEMPLATE = ITEMS.register("upgrade_template", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<? extends ItemKey>
        DRAWER_KEY = ITEMS.register("drawer_key", () -> new ItemDrawerKey(new Item.Properties())),
        QUANTIFY_KEY = ITEMS.register("quantify_key", () -> new ItemQuantifyKey(new Item.Properties())),
        SHROUD_KEY = ITEMS.register("shroud_key", () -> new ItemShroudKey(new Item.Properties()));

    public static final RegistryEntry<? extends ItemKeyring>
        KEYRING = ITEMS.register("keyring", () -> new ItemKeyring(null, new Item.Properties().stacksTo(1))),
        KEYRING_DRAWER = ITEMS.register("keyring_drawer", () -> new ItemKeyring(DRAWER_KEY, new Item.Properties().stacksTo(1))),
        KEYRING_QUANTIFY = ITEMS.register("keyring_quantify", () -> new ItemKeyring(QUANTIFY_KEY, new Item.Properties().stacksTo(1))),
        KEYRING_SHROUD = ITEMS.register("keyring_shroud", () -> new ItemKeyring(SHROUD_KEY, new Item.Properties().stacksTo(1)));

    private ModItems() { }

    public static void init() {
        EXCLUDE_ITEMS_CREATIVE_TAB.add(KEYRING_DRAWER);
        EXCLUDE_ITEMS_CREATIVE_TAB.add(KEYRING_QUANTIFY);
        EXCLUDE_ITEMS_CREATIVE_TAB.add(KEYRING_SHROUD);

        for (RegistryEntry<Block> ro : ModBlocks.BLOCKS.getEntries()) {
            if (ModBlocks.EXCLUDE_ITEMS.contains(ro.getId().getPath()))
                continue;

            registerBlock(ITEMS, ro);
        }
        ITEMS.init();
    }

    static void registerBlock(ChameleonRegistry<Item> register, RegistryEntry<? extends Block> blockHolder) {
        if (blockHolder == null)
            return;

        register.register(blockHolder.getId().getPath(), () -> {
            Block block = blockHolder.get();
            if (block instanceof BlockMeta)
                return null;
            if (block instanceof BlockDrawers) {
                return new ItemDrawers(block, new Item.Properties());
            } else if (block instanceof BlockTrim) {
                return new ItemTrim(block, new Item.Properties());
            } else {
                return new BlockItem(block, new Item.Properties());
            }
        });
    }

    private static <B extends Item> Stream<B> getItemsOfType(Class<B> itemClass) {
        return BuiltInRegistries.ITEM.stream().filter(itemClass::isInstance).map(itemClass::cast);
    }

    public static Stream<ItemKey> getKeys() {
        return getItemsOfType(ItemKey.class);
    }

    public static Stream<ItemKeyring> getKeyrings() {
        return getItemsOfType(ItemKeyring.class);
    }
}
