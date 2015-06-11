package com.jofkos.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.ImmutableList;

public abstract class Command extends org.bukkit.command.Command {
	
	public Command(Plugin plugin, String name, String description, String usageMessage, String permission, String... aliases) {
		this(plugin, null, name, description, usageMessage, permission, aliases);
	}
	
	public Command(Plugin plugin, String prefix, String name, String description, String usageMessage, String permission, String[] aliases) {
		super(name, description, usageMessage, aliases == null ? ImmutableList.of() : ImmutableList.copyOf(aliases));
		
		if (permission != null) super.setPermission(permission);
		super.setPermissionMessage("§cYou don't have permission to do that.");
		
		((SimpleCommandMap) Reflect.invoke(Bukkit.getServer(), NMSUtils.getClass("obc.CraftServer"), "getCommandMap", new Class<?>[0], new Object[0])).register(prefix == null ? plugin.getName() : prefix, this);
	}
	
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!testPermission(sender)) {
			return true;
		}
		try {
			if (!this.onCommand(sender, commandLabel, args)) {
				for (String s : usageMessage.replace("<command>", commandLabel).split("\n")) {
					sender.sendMessage(s);
				}
			}
		} catch (Exception exe) {
			throw new CommandException("Unhandled exception executing command '" + commandLabel + "'", exe);
		}
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String label, String[] args) { 
		return super.tabComplete(sender, label, args);
	}
	
	public abstract boolean onCommand(CommandSender cs, String label, String[] args);
	
}
