package me.nukleo.msgplugin;

import me.nukleo.msgplugin.commands.MsgCommand;
import me.nukleo.msgplugin.commands.ReloadCommand;
import me.nukleo.msgplugin.commands.ReplyCommand;
import me.nukleo.msgplugin.listeners.OnLogoutListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MsgPlugin extends JavaPlugin {
    private FileConfiguration playerConfig;
    private File playerFile;
    @Override
    public void onEnable() {
        playerFile = new File(getDataFolder(), "playerdata.yml");
        if (!playerFile.exists()) {
            saveResource("playerdata.yml", false);
        }
        saveDefaultConfig();
        getCommand("msg").setExecutor(new MsgCommand(this, playerFile));
        getCommand("reply").setExecutor(new ReplyCommand(this, playerFile));
        getCommand("msgreload").setExecutor(new ReloadCommand(this));
        getServer().getPluginManager().registerEvents(new OnLogoutListener(this, playerFile),this);
    }

}
