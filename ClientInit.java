/*
*    MCreator note:
*
*    If you lock base mod element files, you can edit this file and the proxy files
*    and they won't get overwritten. If you change your mod package or modid, you
*    need to apply these changes to this file MANUALLY.
*
*
*    If you do not lock base mod element files in Workspace settings, this file
*    will be REGENERATED on each build.
*
*/
package net.mcreator.stevefabric;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.mcreator.stevefabric.client.*;
import net.mcreator.stevefabric.screen.SteveGuiGui;
import net.mcreator.stevefabric.server.BreakBlockCommand;
import net.mcreator.stevefabric.server.MovetoCommandCommand;
import net.mcreator.stevefabric.server.PlayAnimationCommandCommand;
import net.minecraft.client.option.KeyBinding;

import net.mcreator.stevefabric.entity.render.SteveEntityRenderer;
import net.mcreator.stevefabric.client.gui.screen.SteveGuiGuiWindow;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {
	public static final KeyBinding Change_KEY = KeyBindingHelper.registerKeyBinding(new ChangeKeyBinding());
	public static final KeyBinding Plus_KEY = KeyBindingHelper.registerKeyBinding(new PlusKeyBinding());
	public static final KeyBinding Minus_KEY = KeyBindingHelper.registerKeyBinding(new MinusKeyBinding());
	public static final KeyBinding ReloadAnimations_KEY = KeyBindingHelper.registerKeyBinding(new ReloadAnimationsKeyBinding());
	public static final KeyBinding Play_KEY = KeyBindingHelper.registerKeyBinding(new PlayKeyBinding());
	public static final KeyBinding RePlay_KEY = KeyBindingHelper.registerKeyBinding(new RePlayKeyBinding());

	@Override
	public void onInitializeClient() {
		SteveEntityRenderer.clientInit();
		HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
		});
		PlayAnimationCommandCommand.clientCommand();
		BreakBlockCommand.hit();

		ScreenRegistry.register(StevefabricMod.SteveGuiScreenType, SteveGuiGuiWindow::new);

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			client.execute(()->{
				ManegerCustomCode.clientManeger();
			});
		});
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (((ChangeKeyBinding) Change_KEY).isPressed() && !((ChangeKeyBinding) Change_KEY).wasPressed()) {
					((ChangeKeyBinding) Change_KEY).keyPressed(client.player);
			}
			if (!((ChangeKeyBinding) Change_KEY).isPressed() && ((ChangeKeyBinding) Change_KEY).wasPressed()) {
				((ChangeKeyBinding) Change_KEY).keyReleased(client.player);
			} ;
			if (((PlusKeyBinding) Plus_KEY).isPressed() && !((PlusKeyBinding) Plus_KEY).wasPressed()) {
				((PlusKeyBinding) Plus_KEY).keyPressed(client.player);
			}
			if (!((PlusKeyBinding) Plus_KEY).isPressed() && ((PlusKeyBinding) Plus_KEY).wasPressed()) {
				((PlusKeyBinding) Plus_KEY).keyReleased(client.player);
			} ;
			if (((MinusKeyBinding) Minus_KEY).isPressed() && !((MinusKeyBinding) Minus_KEY).wasPressed()) {
				((MinusKeyBinding) Minus_KEY).keyPressed(client.player);
			}
			if (!((MinusKeyBinding) Minus_KEY).isPressed() && ((MinusKeyBinding) Minus_KEY).wasPressed()) {
				((MinusKeyBinding) Minus_KEY).keyReleased(client.player);
			} ;
			if (((ReloadAnimationsKeyBinding) ReloadAnimations_KEY).isPressed() && !((ReloadAnimationsKeyBinding) ReloadAnimations_KEY).wasPressed()) {
				((ReloadAnimationsKeyBinding) ReloadAnimations_KEY).keyPressed(client.player);
			}
			if (!((ReloadAnimationsKeyBinding) ReloadAnimations_KEY).isPressed() && ((ReloadAnimationsKeyBinding) ReloadAnimations_KEY).wasPressed()) {
				((ReloadAnimationsKeyBinding) ReloadAnimations_KEY).keyReleased(client.player);
			} ;
			if (((PlayKeyBinding) Play_KEY).isPressed() && !((PlayKeyBinding) Play_KEY).wasPressed()) {
				((PlayKeyBinding) Play_KEY).keyPressed(client.player);
			}
			if (!((PlayKeyBinding) Play_KEY).isPressed() && ((PlayKeyBinding) Play_KEY).wasPressed()) {
				((PlayKeyBinding) Play_KEY).keyReleased(client.player);
			} ;
			if (((RePlayKeyBinding) RePlay_KEY).isPressed() && !((RePlayKeyBinding) RePlay_KEY).wasPressed()) {
				((RePlayKeyBinding) RePlay_KEY).keyPressed(client.player);
			}
			if (!((RePlayKeyBinding) RePlay_KEY).isPressed() && ((RePlayKeyBinding) RePlay_KEY).wasPressed()) {
				((RePlayKeyBinding) RePlay_KEY).keyReleased(client.player);
			} ;
		});
	}




}
