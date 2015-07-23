package com.jofkos.utils.command;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableList;

@SuppressWarnings("unchecked")
public abstract class Command<P extends JavaPlugin> extends org.bukkit.command.Command {
	
	protected P plugin;
	
	public Command(String name) {
		this(name, name, "/<command>", "%pluginname%." + name);
	}
	
	public Command(String name, String description, String usage, String permission, String... aliases) {
		super(name, description, usage, aliases == null || aliases.length == 0 ? ImmutableList.of() : ImmutableList.copyOf(aliases));
		
		try {
			Type genericType = getClass().getGenericSuperclass();
			Validate.isTrue(genericType instanceof ParameterizedType, "The command class needs explicit generic type declaration (Command<ProvidingPlugin>)");
			
			this.plugin = JavaPlugin.getPlugin((Class<P>) ((ParameterizedType) genericType).getActualTypeArguments()[0]);
			
			if (permission != null) super.setPermission(permission.replace("%pluginname%", plugin.getName()));
			super.setPermissionMessage("§cYou don't have permission to do that.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		((CraftServer) Bukkit.getServer()).getCommandMap().register(plugin.getName(), this);
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
