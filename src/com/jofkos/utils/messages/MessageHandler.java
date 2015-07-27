package com.jofkos.utils.messages;

import java.io.BufferedWriter;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;
import com.jofkos.utils.reflect.Reflect;

public class MessageHandler {
	
	public static void saveAndLoad(Class<?> clazz, File file) {
		new MessageHandler(clazz, file).touchFile().saveDefaults().load();
	}
	
	private FileConfiguration config;
	private Class<?> clazz;
	private File file;
	
	private MessageHandler(Class<?> clazz, File file) {
		this.clazz = clazz;
		this.file = file;
		
		config = YamlConfiguration.loadConfiguration(file);
		
		this.touchFile();
		this.saveDefaults();
		this.load();
	}
	
	private MessageHandler touchFile() {		
		try {
			Files.createParentDirs(file);
			Files.touch(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	private MessageHandler saveDefaults() {
		
		ConfigurationSection section = config.getConfigurationSection("messages");
		if (section == null) section = config.createSection("messages");
		
		for (Field field : this.clazz.getDeclaredFields()) {
			if (!Message.class.isAssignableFrom(field.getType())) continue;
			if (section.getString(field.getName().toLowerCase()) != null) continue;
			
			section.set(field.getName().toLowerCase(), ((Message) Reflect.get(field, (Object) null)).string);
		}
		
		try (BufferedWriter writer = Files.newWriter(file, StandardCharsets.UTF_8)){
			writer.write(config.saveToString());
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	private void load() {
		try {
			ConfigurationSection section = config.getConfigurationSection("messages");
			
			for (String string : section.getKeys(false)) {
				try {
					Field field = clazz.getDeclaredField(string.toUpperCase());
					if (field == null) continue;
					Message message = Reflect.get(field, (Object) null);
					message.setString(section.getString(string));
				} catch (Exception e) {}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

