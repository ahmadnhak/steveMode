
package net.mcreator.stevefabric.client.gui.screen;

import net.minecraft.world.World;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.MinecraftClient;

import net.mcreator.stevefabric.screen.SteveGuiGui;
import net.mcreator.stevefabric.StevefabricMod;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

@Environment(EnvType.CLIENT)
public class SteveGuiGuiWindow extends HandledScreen<SteveGuiGui.GuiContainerMod> {
	private World world;
	private int positionX, positionY, positionZ;
	private PlayerEntity entity;
	private final static HashMap guistate = SteveGuiGui.guistate;
	public SteveGuiGuiWindow(SteveGuiGui.GuiContainerMod container, PlayerInventory inventory, Text text) {
		super(container, inventory, text);
		this.world = container.world;
		this.positionX = container.x;
		this.positionY = container.y;
		this.positionZ = container.z;
		this.entity = container.entity;
		this.backgroundWidth = 176;
		this.backgroundHeight = 166;
	}
	private static final Identifier texture = new Identifier("stevefabric:textures/steve_gui.png");
	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.drawMouseoverTooltip(ms, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(MatrixStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.color4f(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
		int k = (this.width - this.backgroundWidth) / 2;
		int l = (this.height - backgroundHeight) / 2;
		drawTexture(ms, k, l, 0, 0, this.backgroundWidth, backgroundHeight, this.backgroundWidth, backgroundHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.client.player.closeScreen();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	protected void drawForeground(MatrixStack ms, int mouseX, int mouseY) {
	}

	@Override
	public void onClose() {
		super.onClose();
		MinecraftClient.getInstance().keyboard.setRepeatEvents(false);
	}

	@Override
	public void init(MinecraftClient client, int width, int height) {
		super.init(client, width, height);
		client.keyboard.setRepeatEvents(true);
	}

	public static void screenInit() {
		ServerPlayNetworking.registerGlobalReceiver(StevefabricMod.id("stevegui_slot_0"), SteveGuiGui.GUISlotChangedMessage::apply);
	}
}
