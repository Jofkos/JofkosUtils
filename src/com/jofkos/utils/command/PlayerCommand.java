package com.jofkos.utils.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableList;

public abstract class PlayerCommand<P extends JavaPlugin> extends com.jofkos.utils.command.Command<P> {
	
	public PlayerCommand(String name) {
		super(name);
	}
	
	public PlayerCommand(String name, String description, String usage, String permission, String... aliases) {
		super(name, description, usage, permission, aliases);
	}
	
	@Override
	public boolean onCommand(CommandSender cs, String label, String[] args) {
		if (cs instanceof Player) {
			return onCommand((Player) cs, label, args);
		} else {
			cs.sendMessage("§cYou must be a player to execute that command");
		}
		return true;
	}
	
	public abstract boolean onCommand(Player player, String label, String[] args);
	
	@Override
	public List<String> tabComplete(CommandSender sender, String label, String[] args) {
		if (sender instanceof Player) {
			return tabComplete((Player) sender, label, args);
		}
		return ImmutableList.of();
	}
	
	public List<String> tabComplete(Player player, String label, String[] args) {
		return super.tabComplete(player, label, args);
	}
	
}
