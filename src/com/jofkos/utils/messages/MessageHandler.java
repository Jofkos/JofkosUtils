package com.jofkos.utils.messages;

import java.io.BufferedWriter;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;

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
			try { 
				if (!Message.class.isAssignableFrom(field.getType())) continue;
				if (section.getString(field.getName()) != null) continue;
				
				field.setAccessible(true);
				
				section.set(field.getName(), ((Message) field.get(null)).string);
			} catch (Exception e) {}
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
					Field field = clazz.getDeclaredField(string);
					if (field == null) continue;
					
					field.setAccessible(true);
					
					((Message) field.get(null)).setString(section.getString(string));
				} catch (Exception e) {}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

