package dev.imperiumcode.townydiscord.listeners;

import com.gmail.goosius.siegewar.events.SiegeWarStartEvent;
import dev.imperiumcode.townydiscord.Main;
import dev.imperiumcode.townydiscord.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class SiegeStart implements Listener {

    private Logger logger;

    public SiegeStart(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTown(SiegeWarStartEvent event) {
        if (Main.getInstance().getCustomConfig().getString("notification-siegewar-session-start").equalsIgnoreCase("true")) {
            if (event.getTargetTown().hasNation()) {
                DiscordWebhook webhook = new DiscordWebhook(Main.getInstance().getCustomConfig().getString("webhook-url"));

                webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setColor(new Color(255, 157, 0))
                        .setDescription("An army belonging to " + event.getTownOfSiegeStarter().getName() + " has attacked " + event.getTargetTown().getNationOrNull().getName() + " at " + event.getTargetTown().getName() + "! A " + event.getSiegeType() + " siege has begun!")
                );
                try {
                    webhook.execute();
                } catch (java.io.IOException e) {
                    logger.severe(Arrays.toString(e.getStackTrace()));
                }
            } else {
                DiscordWebhook webhook = new DiscordWebhook(Main.getInstance().getCustomConfig().getString("webhook-url"));

                webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setColor(new Color(255, 157, 0))
                        .setDescription("An army belonging to " + event.getTownOfSiegeStarter().getName() + " has attacked " + event.getTargetTown().getName() + "! A " + event.getSiegeType() + " siege has begun!")
                );
                try {
                    webhook.execute();
                } catch (java.io.IOException e) {
                    logger.severe(Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }
}

