package com.jofkos.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class PlayerCommand extends com.jofkos.utils.Command {
	
	public PlayerCommand(Plugin plugin, String name, String description, String usageMessage, String permission, String... aliases) {
		super(plugin, name, description, usageMessage, permission, aliases);
	}
	
	@Override
	public boolean onCommand(CommandSender cs, String alias, String[] args) {
		if (cs instanceof Player) {
			return onCommand((Player) cs, alias, args); 
		} else {
			cs.sendMessage("§cYou must be a player to execute that command");
		}
		return true;
	}
	
	public abstract boolean onCommand(Player player, String alias, String[] args);
	
}
