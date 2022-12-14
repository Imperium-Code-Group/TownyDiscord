package dev.imperiumcode.townydiscord.listeners;

import com.palmergames.bukkit.towny.event.DeleteTownEvent;
import dev.imperiumcode.townydiscord.Main;
import dev.imperiumcode.townydiscord.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class DeletedTown implements Listener {

    private Logger logger;

    public DeletedTown(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTown(DeleteTownEvent event) {
        if (Main.getInstance().getCustomConfig().getString("notification-town-delete").equalsIgnoreCase("true")) {
            String townName = event.getTownName();
            DiscordWebhook webhook = new DiscordWebhook(Main.getInstance().getCustomConfig().getString("webhook-url"));

            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(214, 99, 84))
                    .setDescription("The town of " + townName + " has been deleted!")
            );
            try {
                webhook.execute();
            } catch (java.io.IOException e) {
                logger.severe(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}

