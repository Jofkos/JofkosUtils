package com.jofkos.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;

public abstract class JavaPluginUTF8 extends JavaPlugin {

	private FileConfiguration cfg = null;
	private File file = new File(this.getDataFolder(), "config.yml");

	@Override
	public FileConfiguration getConfig() {
		if (this.cfg == null) {
			this.reloadConfig();
		}
		return this.cfg;
	}

	@Override
	public void saveConfig() {
		try (BufferedWriter w = Files.newWriter(this.file, StandardCharsets.UTF_8)) {
			w.write(this.cfg.saveToString());
			w.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reloadConfig() {
		try {
			if (cfg == null) {
				cfg = new YamlConfiguration();
			}
			if (!file.exists()) {
				Files.createParentDirs(file);
				Files.touch(file);
			}
			cfg.loadFromString(Files.toString(file, StandardCharsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
