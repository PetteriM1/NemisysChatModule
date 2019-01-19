package me.petterim1.nemisyschat;

import org.itxtech.nemisys.Player;
import org.itxtech.nemisys.event.EventHandler;
import org.itxtech.nemisys.event.Listener;
import org.itxtech.nemisys.event.player.PlayerChatEvent;
import org.itxtech.nemisys.plugin.PluginBase;
import org.itxtech.nemisys.utils.Config;

public class Main extends PluginBase implements Listener {

    Config config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();

        if (config.getBoolean("log_messages")) {
            getServer().getLogger().info(config.getString("chat_format").replace("%server%", p.getClient().getDescription()).replace("%player%", p.getName()).replace("%message%", e.getMessage()).replaceAll("§", "\u00A7"));
        }

        getServer().getClients().forEach((s, c) -> {
            if (!c.getDescription().equals(p.getClient().getDescription())) {
                c.getPlayers().forEach((u, pl) -> {
                    pl.sendMessage(config.getString("chat_format").replace("%server%", p.getClient().getDescription()).replace("%player%", p.getName()).replace("%message%", e.getMessage()).replaceAll("§", "\u00A7"));
                });
            }
        });
    }
}