

public class StevefabricMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public void onInitialize() {
		LOGGER.info("Initializing StevefabricMod");
		
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			
			PlayAnimationCommandCommand.register(dispatcher);

		});
	}

	public static final Identifier id(String s) {
		return new Identifier("stevefabric", s);
	}
}
