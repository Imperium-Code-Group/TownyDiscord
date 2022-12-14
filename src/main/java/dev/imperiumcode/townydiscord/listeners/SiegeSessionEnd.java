package dev.imperiumcode.townydiscord.listeners;

import com.gmail.goosius.siegewar.events.BattleSessionEndedEvent;
import dev.imperiumcode.townydiscord.Main;
import dev.imperiumcode.townydiscord.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class SiegeSessionEnd implements Listener {

    private Logger logger;

    public SiegeSessionEnd(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTown(BattleSessionEndedEvent event) {
        if (Main.getInstance().getCustomConfig().getString("notification-siegewar-session-end").equalsIgnoreCase("true")) {

            DiscordWebhook webhook = new DiscordWebhook(Main.getInstance().getCustomConfig().getString("webhook-url"));

            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(255, 157, 0))
                    .setDescription("The current battle session has ended!")
            );
            try {
                webhook.execute();
            } catch (java.io.IOException e) {
                logger.severe(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}

