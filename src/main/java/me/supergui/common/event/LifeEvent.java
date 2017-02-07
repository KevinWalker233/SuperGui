package me.supergui.common.event;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import me.supergui.client.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
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
		final Minecraft mc = Minecraft.getMinecraft();
		final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		float playerhealth = mc.thePlayer.getHealth();
		float hunger = player.getFoodStats().getFoodLevel();
		final int width = event.resolution.getScaledWidth();
		final int height = event.resolution.getScaledHeight();
		if (ClientProxy.open == false) {

		} else {
			if (playerhealth < 15) {
				render(1);
			}
			if (playerhealth < 5) {
				render(2);
			}
			if (event.type == ElementType.HEALTH || event.type == ElementType.FOOD
					|| event.type == ElementType.CROSSHAIRS) {
				event.setCanceled(true);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/head.png"));
				Gui.func_146110_a(2, 2, 50, 50, 50, 50, 50, 50);
				mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/health.png"));
				Gui.func_146110_a(54, 3, playerhealth * 5, 25, (int) (playerhealth * 5), 25, playerhealth * 5, 25);
				mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/hungry.png"));
				Gui.func_146110_a(54, 27, hunger * 5, 25, (int) (hunger * 5), 25, hunger * 5, 25);
				// mc.renderEngine.bindTexture(new ResourceLocation("supergui",
				// "textures/CROSSHAIRS.png"));
				// Gui.func_146110_a(width / 2 - 15, height / 2 - 14, 120 / 4,
				// 120 / 4, 120 / 4, 120 / 4, 120 / 4,
				// 122 / 4);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				String hp = String.format("生命值: %d/%d", MathHelper.ceiling_float_int(playerhealth),
						MathHelper.ceiling_double_int(mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth)
								.getAttributeValue()));
				String hg = String.format("饥饿度: %d/20", MathHelper.ceiling_float_int(hunger));
				FontRenderer fontRenderer = mc.fontRenderer;
				fontRenderer.drawStringWithShadow(hp, 57, 10, 0xFFFFFF);
				fontRenderer.drawStringWithShadow(hg, 57, 34, 0xFFFFFF);
				mc.renderEngine.bindTexture(Gui.icons);
				if (!player.isSwingInProgress && player.isCollidedVertically && !player.isEating()) {
					GL11.glEnable(GL11.GL_ALPHA_TEST);
					mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/CROSSHAIRS.png"));
					Gui.func_146110_a(width / 2 - 15, height / 2 - 14, 120 / 4, 120 / 4, 120 / 4, 120 / 4, 120 / 4,
							120 / 4);
					GL11.glDisable(GL11.GL_ALPHA_TEST);
				} else {
					GL11.glEnable(GL11.GL_ALPHA_TEST);
					mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/CROSSHAIRS.png"));
					Gui.func_146110_a(width / 2 - 30, height / 2 - 28, 120 / 2, 120 / 2, 120 / 2, 120 / 2, 120 / 2,
							120 / 2);
					GL11.glDisable(GL11.GL_ALPHA_TEST);
				}
			}
		}
	}

	final ResourceLocation exp_empty = new ResourceLocation("supergui", "textures/exp_empty.png");
	final ResourceLocation exp_full = new ResourceLocation("supergui", "textures/exp_full.png");

	@SubscribeEvent
	public void onHubChanged(final RenderGameOverlayEvent.Pre event) {
		if (ClientProxy.open == false) {

		} else {
			if (event.type == ElementType.EXPERIENCE) {
				event.setCanceled(true); // 取消掉事件来阻止原图标的绘制
				final int width = event.resolution.getScaledWidth();
				final int height = event.resolution.getScaledHeight();
				final Minecraft mc = Minecraft.getMinecraft();
				mc.renderEngine.bindTexture(exp_empty);// 237,22
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				Gui.func_152125_a(105, 210, 0, 0, 242, 28, width / 2, 8, 242, 28);
				mc.renderEngine.bindTexture(exp_full);
				EntityPlayer ep = Minecraft.getMinecraft().thePlayer;
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				Gui.func_152125_a(106, 211, 0, 0, 237, 22, (int) ((width / 2 - 4) * ep.experience), 6, 237, 22);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				mc.renderEngine.bindTexture(Gui.icons);
			}
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

	public int getExpToLevel(int level) {
		return level >= 15 ? 17 + (level - 15) * 3 : level >= 30 ? 62 + (level - 30) * 7 : 17;
	}

	public void render(int num) {
		final Minecraft mc = Minecraft.getMinecraft();
		if (num == 1) {
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/1.png"));
			Gui.func_146110_a(-1, 50, 300 / 2, 300 / 2, 300 / 2, 300 / 2, 300 / 2, 300 / 2);
			mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/4.png"));
			Gui.func_146110_a(350, 170, 300 / 5, 300 / 5, 300 / 5, 300 / 5, 300 / 5, 300 / 5);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
		} else if (num == 2) {
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/1.png"));
			Gui.func_146110_a(-1, 50, 300 / 2, 300 / 2, 300 / 2, 300 / 2, 300 / 2, 300 / 2);
			mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/2.png"));
			Gui.func_146110_a(-1, 130, 300 / 2, 300 / 2, 300 / 2, 300 / 2, 300 / 2, 300 / 2);
			mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/3.png"));
			Gui.func_146110_a(330, 80, 300 / 3, 300 / 3, 300 / 3, 300 / 3, 300 / 3, 300 / 3);
			mc.renderEngine.bindTexture(new ResourceLocation("supergui", "textures/4.png"));
			Gui.func_146110_a(350, 170, 300 / 5, 300 / 5, 300 / 5, 300 / 5, 300 / 5, 300 / 5);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
		}
	}
}
