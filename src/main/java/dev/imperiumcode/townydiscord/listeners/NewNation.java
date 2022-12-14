package dev.imperiumcode.townydiscord.listeners;

import com.palmergames.bukkit.towny.event.NewNationEvent;
import dev.imperiumcode.townydiscord.Main;
import dev.imperiumcode.townydiscord.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class NewNation implements Listener {

    private Logger logger;

    public NewNation(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTown(NewNationEvent event) {
        if (Main.getInstance().getCustomConfig().getString("notification-nation-create").equalsIgnoreCase("true")) {
            String nationName = event.getNation().getName();
            String kingName = event.getNation().getKing().getName();
            DiscordWebhook webhook = new DiscordWebhook(Main.getInstance().getCustomConfig().getString("webhook-url"));

            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(0, 81, 255))
                    .setDescription(kingName + " has made a new nation called " + nationName + "!")
            );
            try {
                webhook.execute();
            } catch (java.io.IOException e) {
                logger.severe(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}

