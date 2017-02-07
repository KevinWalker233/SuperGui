package me.supergui.client;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import me.supergui.common.CommonProxy;
import me.supergui.common.event.LifeEvent;

public class ClientProxy extends CommonProxy {
	public static boolean open = false;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		new LifeEvent();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}
