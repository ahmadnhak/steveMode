
package net.mcreator.stevefabric.screen;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;

import net.mcreator.stevefabric.StevefabricMod;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

import io.netty.buffer.Unpooled;

public class SteveGuiGui {
	public static HashMap guistate = new HashMap();
	public static class GuiContainerMod extends ScreenHandler implements Supplier<Map<Integer, Slot>> {
		public World world;
		public PlayerEntity entity;
		public int x, y, z = 0;
		private Map<Integer, Slot> customSlots = new HashMap<>();
		private boolean bound = false;
		private final Inventory inventory;
		public GuiContainerMod(int id, PlayerInventory inv, PacketByteBuf data) {
			this(id, inv, new SimpleInventory(2));
			BlockPos pos;
			if (data != null) {
				pos = data.readBlockPos();
				this.x = pos.getX();
				this.y = pos.getY();
				this.z = pos.getZ();
			}
		}

		public GuiContainerMod(int id, PlayerInventory inv, Inventory inventory) {
			super(StevefabricMod.SteveGuiScreenType, id);
			checkSize(inventory , 2);
			this.entity = inv.player;
			this.world = inv.player.world;
			this.inventory = inventory;
			this.customSlots.put(0, this.addSlot(new Slot(this.inventory, 0, 17, 37) {
			}));
			int si;
			int sj;
			this.addSlot(new Slot(inventory , 0 , 17 , 37 ));
			for (si = 0; si < 3; ++si)
				for (sj = 0; sj < 9; ++sj)
					this.addSlot(new Slot(inv, sj + (si + 1) * 9, 0 + 8 + sj * 18, 0 + 84 + si * 18));
			for (si = 0; si < 9; ++si)
				this.addSlot(new Slot(inv, si, 0 + 8 + si * 18, 0 + 142));
		}


		public Map<Integer, Slot> get() {
			return customSlots;
		}

		@Override
		public boolean canUse(PlayerEntity player) {
			return true;
		}

		@Override
		public ItemStack transferSlot(PlayerEntity player, int index) {
			ItemStack itemstack = ItemStack.EMPTY;
			Slot slot = (Slot) this.slots.get(index);
			if (slot != null && slot.hasStack()) {
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				if (index < 1) {
					if (!this.insertItem(itemstack1, 1, this.slots.size(), true)) {
						return ItemStack.EMPTY;
					}
					slot.onQuickTransfer(itemstack1, itemstack);
				} else if (!this.insertItem(itemstack1, 0, 1, false)) {
					if (index < 1 + 27) {
						if (!this.insertItem(itemstack1, 1 + 27, this.slots.size(), true)) {
							return ItemStack.EMPTY;
						}
					} else {
						if (!this.insertItem(itemstack1, 1, 1 + 27, false)) {
							return ItemStack.EMPTY;
						}
					}
					return ItemStack.EMPTY;
				}
				if (itemstack1.getCount() == 0) {
					slot.setStack(ItemStack.EMPTY);
				} else {
					slot.markDirty();
				}
				if (itemstack1.getCount() == itemstack.getCount()) {
					return ItemStack.EMPTY;
				}
				slot.onTakeItem(player, itemstack1);
			}
			PacketByteBuf packetByteBuf = PacketByteBufs.create();
			NbtCompound Compound = new NbtCompound();

			NbtList nbtList = new NbtList();

			for(Map.Entry<Integer, Slot> entry : get().entrySet()) {
				ItemStack itemStack = get().get(entry.getKey()).getStack();
				if (!itemStack.isEmpty()) {
					NbtCompound nbtCompound = new NbtCompound();
					nbtCompound.putByte("Slot", (byte)entry.getKey().byteValue());
					itemStack.writeNbt(nbtCompound);
					nbtList.add(nbtCompound);
				}
			}

			return itemstack;
		}

		@Override
		public void close(PlayerEntity playerIn) {
			super.close(playerIn);
		}

		private void slotChanged(int slotid, int ctype, int meta) {
			if (this.world != null && this.world.isClient()) {
				ClientPlayNetworking.send(StevefabricMod.id("stevegui_slot_" + slotid), new GUISlotChangedMessage(slotid, x, y, z, ctype, meta));


			}
		}
	}

	public static class ButtonPressedMessage extends PacketByteBuf {
		public ButtonPressedMessage(int buttonID, int x, int y, int z) {
			super(Unpooled.buffer());
			writeInt(buttonID);
			writeInt(x);
			writeInt(y);
			writeInt(z);
		}

		public static void apply(MinecraftServer server, ServerPlayerEntity entity, ServerPlayNetworkHandler handler, PacketByteBuf buf,
				PacketSender responseSender) {
			int buttonID = buf.readInt();
			double x = buf.readInt();
			double y = buf.readInt();
			double z = buf.readInt();
			server.execute(() -> {
				World world = entity.getServerWorld();
			});
		}
	}

	public static class GUISlotChangedMessage extends PacketByteBuf {
		public GUISlotChangedMessage(int slotID, int x, int y, int z, int changeType, int meta) {
			super(Unpooled.buffer());
			writeInt(slotID);
			writeInt(x);
			writeInt(y);
			writeInt(z);
			writeInt(changeType);
			writeInt(meta);
		}

		public static void apply(MinecraftServer server, ServerPlayerEntity entity, ServerPlayNetworkHandler handler, PacketByteBuf buf,
				PacketSender responseSender) {
			int slotID = buf.readInt();
			double x = buf.readInt();
			double y = buf.readInt();
			double z = buf.readInt();
			int changeType = buf.readInt();
			int meta = buf.readInt();
			server.execute(() -> {
				World world = entity.getServerWorld();
			});
		}
	}
}
