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

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.mcreator.stevefabric.server.BreakBlockCommand;
import net.mcreator.stevefabric.server.ManegerWriterCommand;
import net.mcreator.stevefabric.server.MovetoCommandCommand;
import net.mcreator.stevefabric.server.PlayAnimationCommandCommand;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraft.util.Identifier;
import net.minecraft.screen.ScreenHandlerType;

import net.mcreator.stevefabric.screen.SteveGuiGui;
import net.mcreator.stevefabric.procedures.SteveTickProcedure;

import net.mcreator.stevefabric.entity.SteveEntity;
import net.mcreator.stevefabric.client.gui.screen.SteveGuiGuiWindow;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.api.ModInitializer;

import software.bernie.geckolib3.GeckoLib;

public class StevefabricMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();

	public static final ScreenHandlerType<SteveGuiGui.GuiContainerMod> SteveGuiScreenType = ScreenHandlerRegistry.registerExtended(id("steve_gui"),
			SteveGuiGui.GuiContainerMod::new);
	@Override
	public void onInitialize() {
		LOGGER.info("Initializing StevefabricMod");
		new SteveTickProcedure();
		
		SteveEntity.init();
		SteveGuiGuiWindow.screenInit();

		
		GeckoLib.initialize();
		ServerTickEvents.END_SERVER_TICK.register((server )->{
			server.execute(()->{
				ManegerCustomCode.serverManeger();
				BreakBlockCommand.serverEachTick();
			});
		});
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			MovetoCommandCommand.register(dispatcher);
			PlayAnimationCommandCommand.register(dispatcher);
			ManegerWriterCommand.register(dispatcher);
			BreakBlockCommand.register(dispatcher);
		});
	}

	public static final Identifier id(String s) {
		return new Identifier("stevefabric", s);
	}
}
