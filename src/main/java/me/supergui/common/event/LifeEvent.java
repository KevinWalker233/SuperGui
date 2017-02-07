package me.supergui.common.event;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import me.supergui.client.ClientProxy;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

public class LifeEvent {
	boolean trigger = false;

	public LifeEvent() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void playerGui(RenderGameOverlayEvent.Pre event) {
		if (ClientProxy.open == false) {

		} else {
			if (event.type == ElementType.HEALTH || event.type == ElementType.FOOD
					|| event.type == ElementType.HOTBAR) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void test(RenderGameOverlayEvent.Pre event) {
		if (event.type == ElementType.CHAT) {
			event.setCanceled(true);

		}
	}

	@SubscribeEvent
	public void keyListener(KeyInputEvent event) {
		if (Keyboard.getEventKey() == Keyboard.KEY_K) {
			if (!trigger) {
				ClientProxy.open = !ClientProxy.open;
			}
			trigger = !trigger;
		}
	}
}
