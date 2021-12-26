

@Environment(EnvType.CLIENT)
public class ClientInit implements ClientModInitializer {
	

	@Override
	public void onInitializeClient() {
	

		ClientPlayNetworking.registerReceiver(StevefabricMod.id("PlayAnimationCommend"),  (client, handler, buf, responseSender) -> {
			if(PlayAnimationCommandCommand.dataSeted){
				NbtCompound nbtCompound = buf.readNbt();
				HashMap<String , String> param = new HashMap<>();
				for (String s : nbtCompound.getKeys()) {
					param.put(s , nbtCompound.getString(s));
				}
				client.execute(()->{
					clientRunable(param);
				});
			}


		}));

		




}
