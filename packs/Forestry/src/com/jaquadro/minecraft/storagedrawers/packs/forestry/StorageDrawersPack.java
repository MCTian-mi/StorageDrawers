package com.jaquadro.minecraft.storagedrawers.packs.forestry;

import com.jaquadro.minecraft.storagedrawers.api.IStorageDrawersApi;
import com.jaquadro.minecraft.storagedrawers.api.StorageDrawersApi;
import com.jaquadro.minecraft.storagedrawers.api.pack.IExtendedDataResolver;
import com.jaquadro.minecraft.storagedrawers.packs.forestry.core.DataResolver;
import com.jaquadro.minecraft.storagedrawers.packs.forestry.core.ModBlocks;
import com.jaquadro.minecraft.storagedrawers.packs.forestry.core.RefinedRelocation;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = StorageDrawersPack.MOD_ID, name = StorageDrawersPack.MOD_NAME, version = StorageDrawersPack.MOD_VERSION, dependencies = "required-after:StorageDrawers;")
public class StorageDrawersPack
{
    public static final String MOD_ID = "StorageDrawersForestry";
    public static final String MOD_NAME = "Storage Drawers: Forestry Pack";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String SOURCE_PATH = "com.jaquadro.minecraft.storagedrawers.packs.forestry.";

    public DataResolver[] resolvers = new DataResolver[] {
        new DataResolver(MOD_ID, 0),
        new DataResolver(MOD_ID, 1)
    };

    public ModBlocks blocks = new ModBlocks();

    @Mod.Instance(MOD_ID)
    public static StorageDrawersPack instance;

    @SidedProxy(clientSide = SOURCE_PATH + "CommonProxy", serverSide = SOURCE_PATH + "CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        blocks.init();
    }

    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {
        RefinedRelocation.init();
        for (DataResolver resolver : resolvers)
            resolver.init();
    }

    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        IStorageDrawersApi api = StorageDrawersApi.instance();
        if (api != null) {
            for (IExtendedDataResolver resolver : resolvers) {
                api.registerStandardPackRecipes(resolver);
                api.packFactory().registerResolver(resolver);
            }
        }
    }
}
