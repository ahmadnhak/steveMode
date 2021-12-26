
package net.mcreator.stevefabric.server;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.mcreator.stevefabric.JsonerCustomCode;
import net.mcreator.stevefabric.StevefabricMod;
import net.mcreator.stevefabric.VariablesCustomCode;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.CommandSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.entity.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.util.Identifier;

import javax.xml.crypto.Data;

public class PlayAnimationCommandCommand {

    public static final SuggestionProvider<ServerCommandSource> animename_SUGGESTER = (p_198477_0_, p_198477_1_) -> {

        JsonerCustomCode jsoner = new JsonerCustomCode();

        jsoner.location="C:/Users/Ahmad/Desktop/source/steve.json";


        try {
            jsoner.readfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> animename = new ArrayList<>();

        animename = jsoner.finderanimation();

        return suggest(animename , p_198477_1_);
    };
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		final LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = CommandManager.literal("PlayAnimation").requires((p_198491_0_) -> {
			return p_198491_0_.hasPermissionLevel(1);
		});
		String[] list ={"MovmentAnimations" , "controller1" , "controller2" , "controller3" ,"controller4"};
		String[] playState ={"playReapAnim", "playLoopAnim" , "stop" };
		for(String s : list ){
			for(String p : playState ){
			literalargumentbuilder.then(CommandManager.literal(s).executes(PlayAnimationCommandCommand::execute)
					.then(CommandManager.literal(p).executes(PlayAnimationCommandCommand::execute)
					.then(CommandManager.argument("animname&repet", StringArgumentType.greedyString()).suggests(animename_SUGGESTER).executes(PlayAnimationCommandCommand::execute)))).executes(PlayAnimationCommandCommand::execute);
			}
		}

		dispatcher.register(literalargumentbuilder);


	}

	private static int execute(CommandContext<ServerCommandSource> ctx) {
		ServerWorld world = ctx.getSource().getWorld();
		double x = ctx.getSource().getPosition().getX();
		double y = ctx.getSource().getPosition().getY();
		double z = ctx.getSource().getPosition().getZ();
		Entity entity = ctx.getSource().getEntity();
		HashMap<String, String> cmdparams = new HashMap<>();
		int[] index = {-1};
		Arrays.stream(ctx.getInput().split("\\s+")).forEach(param -> {
			if (index[0] >= 0)
				cmdparams.put(Integer.toString(index[0]), param);
			index[0]++;
		});
		PacketByteBuf packetByteBuf = PacketByteBufs.create();
		NbtCompound nbtCompound = new NbtCompound();
		for (String s:cmdparams.keySet()) {
			nbtCompound.putString(s , cmdparams.get(s));
		}
		packetByteBuf.writeNbt(nbtCompound);

		ServerPlayNetworking.send((ServerPlayerEntity) entity , StevefabricMod.id("PlayAnimationCommend"),packetByteBuf );
		dataSeted=true;

		return 0;
	}

	private static CompletableFuture<Suggestions> suggest(Iterable<String> p_197005_0_, SuggestionsBuilder builder) {
		String s = builder.getRemaining().toLowerCase(Locale.ROOT);

		for(String s1 : p_197005_0_) {
			if (s1.toLowerCase(Locale.ROOT).startsWith(s)) {
				builder.suggest(s1);
			}
		}

		return builder.buildFuture();
	}

	public static boolean dataSeted = false;



	public static void clientRunable (HashMap<String , String> cmdparams){
		if(cmdparams.get("1").equals("playReapAnim")){

			VariablesCustomCode.clientEntities.playRepaetingAnimation(cmdparams.get("0") ,cmdparams.get("2"),Integer.parseInt((String) cmdparams.get("3")));

		}
		else if(cmdparams.get("1").equals("playLoopAnim")){
			System.out.println(Thread.currentThread().getName());
			VariablesCustomCode.clientEntities.playLoopAnimation(cmdparams.get("0") ,cmdparams.get("2"));
		}
		else if(cmdparams.get("1").equals("stop")){
			VariablesCustomCode.clientEntities.stopAnimation(cmdparams.get("0"));
		}

		dataSeted=false;
	}

}
