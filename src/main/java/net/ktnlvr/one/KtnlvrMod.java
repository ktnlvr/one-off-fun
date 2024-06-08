package net.ktnlvr.one;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.ktnlvr.one.events.LootBarrelWorldEvent;
import net.ktnlvr.one.events.WorldEvent;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class KtnlvrMod implements ModInitializer {
	public static final String MOD_ID = "ktnlvr";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int ticker = 0;
	public static int nextEventAfterTicks = 1000;

	private static final ServerBossBar eventBar = new ServerBossBar(Text.literal("Next Event Incoming..."), BossBar.Color.WHITE, BossBar.Style.PROGRESS);
	private static WorldEvent ongoingEvent = null;

	public static final GameRules.Key<GameRules.BooleanRule> DO_EVENTS =
			GameRuleRegistry.register("doEvents", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.IntRule> MAX_EVENT_WAIT_TICKS =
			GameRuleRegistry.register("maxEventWaitTicks", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(8000));

	@Override
	public void onInitialize() {
		Items.registerMod();

		ServerTickEvents.END_SERVER_TICK.register((server) -> {
			if (!server.getOverworld().getGameRules().getBoolean(DO_EVENTS)) {
				eventBar.clearPlayers();
				return;
			}

			server.getPlayerManager().getPlayerList().forEach((player) -> {
				if (!eventBar.getPlayers().contains(player))
					eventBar.addPlayer(player);
			});

			KtnlvrMod.ticker++;

			if (ongoingEvent == null) {
				eventBar.setPercent(1f - (float)KtnlvrMod.ticker / (float)nextEventAfterTicks);

				if (KtnlvrMod.ticker > nextEventAfterTicks) {
					ticker = 0;
					int timeBetweenEvents = server.getOverworld().getGameRules().getInt(MAX_EVENT_WAIT_TICKS);
					nextEventAfterTicks = (new Random()).nextInt(timeBetweenEvents / 2) + timeBetweenEvents / 2;

					ongoingEvent = new LootBarrelWorldEvent(server);
					eventBar.setName(ongoingEvent.name());
					eventBar.setColor(BossBar.Color.PURPLE);
					LOGGER.info("Starting an event: " + ongoingEvent.name());

					ongoingEvent.begin(server);
				}
			} else {
				eventBar.setPercent(1f - (float)KtnlvrMod.ticker / (float)ongoingEvent.tickDuration());

				if (KtnlvrMod.ticker > ongoingEvent.tickDuration()) {
					ticker = 0;
					eventBar.setColor(BossBar.Color.WHITE);
					eventBar.setName(Text.empty());

					ongoingEvent.end(server);
					ongoingEvent = null;

					LOGGER.info("Waiting for next event");
				}
			}
		});
	}
}