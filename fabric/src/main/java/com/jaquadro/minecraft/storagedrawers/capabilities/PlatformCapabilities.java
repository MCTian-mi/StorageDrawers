package com.jaquadro.minecraft.storagedrawers.capabilities;

import com.jaquadro.minecraft.storagedrawers.api.capabilities.IItemHandler;
import com.jaquadro.minecraft.storagedrawers.api.capabilities.IItemRepository;
import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawerAttributes;
import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawerGroup;
import com.jaquadro.minecraft.storagedrawers.block.tile.BlockEntityController;
import com.jaquadro.minecraft.storagedrawers.block.tile.BlockEntityControllerIO;
import com.jaquadro.minecraft.storagedrawers.block.tile.BlockEntityDrawers;
import com.jaquadro.minecraft.storagedrawers.core.ModBlockEntities;
import com.texelsaurus.minecraft.chameleon.capabilities.ChameleonCapability;
import com.texelsaurus.minecraft.chameleon.capabilities.FabricCapability;

public class PlatformCapabilities
{
    public final static FabricCapability<IDrawerAttributes> DRAWER_ATTRIBUTES = new FabricCapability<>();
    public final static FabricCapability<IDrawerGroup> DRAWER_GROUP = new FabricCapability<>();
    public final static FabricCapability<IItemRepository> ITEM_REPOSITORY = new FabricCapability<>();
    public final static FabricCapability<IItemHandler> ITEM_HANDLER = new FabricCapability<>();

    static <T> FabricCapability<T> cast(ChameleonCapability<T> cap) {
        return (FabricCapability<T>) cap;
    }

    public static void initHandlers() {
        ModBlockEntities.getDrawerTypes().forEach((entity) -> {
            cast(Capabilities.DRAWER_ATTRIBUTES).register(entity, e -> BlockEntityDrawers.getDrawerAttributes(e));
            cast(Capabilities.DRAWER_GROUP).register(entity, e -> BlockEntityDrawers.getGroup(e));
            cast(Capabilities.ITEM_REPOSITORY).register(entity, DrawerItemRepository::new);
            cast(Capabilities.ITEM_HANDLER).register(entity, DrawerItemHandler::new);
        });

        cast(Capabilities.DRAWER_GROUP).register(ModBlockEntities.CONTROLLER.get(), e -> e);
        cast(Capabilities.ITEM_REPOSITORY).register(ModBlockEntities.CONTROLLER.get(), BlockEntityController::getItemRepository);
        cast(Capabilities.ITEM_HANDLER).register(ModBlockEntities.CONTROLLER.get(), DrawerItemHandler::new);

        cast(Capabilities.DRAWER_GROUP).register(ModBlockEntities.CONTROLLER_IO.get(), e -> e);
        cast(Capabilities.ITEM_REPOSITORY).register(ModBlockEntities.CONTROLLER_IO.get(), BlockEntityControllerIO::getItemRepository);
        cast(Capabilities.ITEM_HANDLER).register(ModBlockEntities.CONTROLLER_IO.get(), DrawerItemHandler::new);
    }
}
