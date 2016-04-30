package com.jofkos.utils.command;

import com.google.common.collect.ImmutableList;
import com.jofkos.utils.reflect.Reflect;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class Command<P extends JavaPlugin> extends org.bukkit.command.Command {
	
	private static final CommandMap COMMAND_MAP = Reflect.getField(Reflect.getCoreMCClass("obc.CraftServer"), "commandMap").get(Bukkit.getServer());
	
	protected P plugin;
	
	public Command(String name) {
		this(name, name, "/<command>", "%pluginname%." + name);
	}
	
	public Command(String name, String description, String usage, String permission, String... aliases) {
		super(name, description, usage, aliases == null || aliases.length == 0 ? ImmutableList.<String>of() : ImmutableList.copyOf(aliases));
		
		try {
			Type genericType = getClass().getGenericSuperclass();
			Validate.isTrue(genericType instanceof ParameterizedType, "The command class needs explicit generic type declaration (Command<ProvidingPlugin>)");
			
			this.plugin = JavaPlugin.getPlugin((Class<P>) ((ParameterizedType) genericType).getActualTypeArguments()[0]);
			
			if (permission != null) super.setPermission(permission.replace("%pluginname%", plugin.getName().equalsIgnoreCase(name) ? "command" : plugin.getName()));
			super.setPermissionMessage("§cYou don't have permission to do that.");
			
			COMMAND_MAP.register(plugin.getName(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
