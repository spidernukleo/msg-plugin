package me.nukleo.msgplugin.commands;

import me.nukleo.msgplugin.MsgPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {
    private final MsgPlugin plugin;
    public ReloadCommand(MsgPlugin plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("msgreload")){
            if(sender instanceof Player){
                Player player=(Player) sender;
                this.plugin.reloadConfig();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dPlugin msg ricaricato con successo"));
            }
            else{
                this.plugin.reloadConfig();
                System.out.println("Plugin msg ricaricato con successo");
            }


        }
        return true;
    }
}
