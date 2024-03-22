package me.nukleo.msgplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public class OnLogoutListener implements Listener {
    private final JavaPlugin plugin;
    private File playerFile;

    public OnLogoutListener(JavaPlugin plugin, File playerFile) {
        this.plugin = plugin;
        this.playerFile=playerFile;
    }
    @EventHandler
    public void onLogout(PlayerQuitEvent event){
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        Player player = event.getPlayer();
        String uuid=player.getUniqueId().toString();
        playerConfig.set("players."+uuid+".current",null);
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
