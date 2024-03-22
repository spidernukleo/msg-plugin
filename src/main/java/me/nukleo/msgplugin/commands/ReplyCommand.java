package me.nukleo.msgplugin.commands;

import me.nukleo.msgplugin.MsgPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ReplyCommand implements CommandExecutor {
    public String colora(String mex){
        return ChatColor.translateAlternateColorCodes('&', mex);
    }
    private final MsgPlugin plugin;
    private File playerFile;
    public ReplyCommand(MsgPlugin plugin, File playerFile){
        this.plugin=plugin;
        this.playerFile=playerFile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("reply")){
            if(sender instanceof Player){
                Player player=(Player) sender;
                if(args.length<1){
                    String message=this.plugin.getConfig().getString("messaggi.errori.reply-args");
                    player.sendMessage(colora(message));
                }
                else{
                    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
                    String uuidmandatore=player.getUniqueId().toString();
                    String convo=playerConfig.getString("players."+uuidmandatore+".current", null);
                    if(convo!=null) {
                        UUID UUIDtarget = UUID.fromString(convo);
                        Player target = Bukkit.getPlayer(UUIDtarget);
                        if (target != null) {
                            StringBuilder frase = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {
                                frase.append(args[i]);
                                frase.append(" ");
                            }
                            String message = frase.toString();
                            message = message.stripTrailing();
                            String ricevuto = this.plugin.getConfig().getString("messaggi.core.ricevuto");
                            String mandato = this.plugin.getConfig().getString("messaggi.core.mandato");
                            ricevuto = ricevuto.replace("%player%", player.getName());
                            ricevuto = ricevuto.replace("%target%", target.getName());
                            mandato = mandato.replace("%player%", player.getName());
                            mandato = mandato.replace("%target%", target.getName());
                            target.sendMessage(colora(ricevuto) + message);
                            player.sendMessage(colora(mandato) + message);
                            String uuidmandato = player.getUniqueId().toString();
                            String uuidricevuto = target.getUniqueId().toString();
                            playerConfig.set("players." + uuidmandato + ".current", uuidricevuto);
                            playerConfig.set("players." + uuidricevuto + ".current", uuidmandato);
                            try {
                                playerConfig.save(playerFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String message = this.plugin.getConfig().getString("messaggi.errori.not-online");
                            player.sendMessage(colora(message));
                        }

                    }
                    else {
                        String message = this.plugin.getConfig().getString("messaggi.errori.no-reply");
                        player.sendMessage(colora(message));
                    }
                }
            }
            else{
                System.out.println("Comando solo per giocatori");
            }
        }
        return true;
    }
}
