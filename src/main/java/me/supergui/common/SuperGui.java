package me.supergui.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SuperGui.MODID, name = SuperGui.NAME, version = SuperGui.VERSION)
public class SuperGui {
	public static final String MODID = "supergui";
	public static final String NAME = "supergui";
	public static final String VERSION = "1.0.0";
	@Instance(SuperGui.MODID)
	public static SuperGui instance;

	@SidedProxy(clientSide = "me.supergui.client.ClientProxy", serverSide = "me.supergui.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
