package com.jofkos.utils.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerMap<V> implements Map<Player, V> {

	private Map<UUID, V> map = new HashMap<UUID, V>();

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return key instanceof Player ? map.containsKey(((Player) key).getUniqueId()) : false;
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return key instanceof Player ? map.get(((Player) key).getUniqueId()) : null;
	}

	@Override
	public V put(Player key, V value) {
		return map.put(key.getUniqueId(), value);
	}

	@Override
	public V remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends Player, ? extends V> m) {
		for (Entry<? extends Player, ? extends V> entry : m.entrySet()) {
			map.put(entry.getKey().getUniqueId(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<Player> keySet() {
		return map.keySet().stream().map(Bukkit::getPlayer).collect(Collectors.toSet());
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<Player, V>> entrySet() {
		return map.entrySet().stream().map(PlayerEntry::new).collect(Collectors.toSet());
	}
	
	private class PlayerEntry implements Map.Entry<Player, V> {
		
		private Player key;
		private V value;
		
		private PlayerEntry(Entry<UUID, V> entry) {
			this.key = Bukkit.getPlayer(entry.getKey());
			this.value = entry.getValue();
		}
		
		@Override
		public Player getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}	
	}

}
