package com.jofkos.utils.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;

@SuppressWarnings({"unchecked"})
public class PlayerList implements Iterable<Player>, ConfigurationSerializable {
	
	static {
		ConfigurationSerialization.registerClass(PlayerList.class);
	}
	
	private List<UUID> players = new ArrayList<>();

	public PlayerList() {}

	public PlayerList(List<UUID> players) {
		this.players.addAll(players);
	}
	
	public PlayerList(UUID... players) {
		this.players.addAll(Arrays.asList(players));
	}

	public void add(Player... players) {
		Validate.notNull(players, "Players cannot be null");
		add(Arrays.stream(players));
	}

	public void add(Collection<Player> players) {
		Validate.notNull(players, "Players cannot be null");
		add(players.stream());
	}
	
	public void add(Stream<Player> players) {
		Validate.notNull(players, "Players cannot be null");
		players.map(Player::getUniqueId).forEach(this.players::add);
	}

	public void remove(Player... players) {
		Validate.notNull(players, "Players cannot be null");
		Arrays.stream(players).map(Player::getUniqueId).forEach(this.players::remove);
	}

	public boolean contains(Player player) {
		Validate.notNull(player, "Player cannot be null");
		return players.contains(player.getUniqueId());
	}
	
	public int indexOf(Player player) {
		Validate.notNull(player, "Player cannot be null");
		return players.indexOf(player.getUniqueId());
	}

	public List<UUID> getUUIDList() {
		return new ArrayList<>(players);
	}
	
	public List<String> getNameList() {
		return players.stream().map(Bukkit::getPlayer).map(Player::getName).collect(Collectors.toList());
	}

	public void shuffle() {
		Collections.shuffle(players);
	}

	public void clear() {
		players.clear();
	}

	public int size() {
		return players.size();
	}

	public Iterator<Player> iterator() {
		return new PlayerListIterator(players);
	}
	
	public Stream<Player> stream() {
		return players.stream().map(Bukkit::getPlayer);
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("players", this.players);
		return map;
	}

	public static PlayerList deserialize(Map<String, Object> map) {
		return new PlayerList((List<UUID>) map.get("players"));
	}
	
	public class PlayerListIterator implements Iterator<Player> {
		
		private final List<UUID> players, playersFinal;
		private int cursor = 0;
		
		public PlayerListIterator(List<UUID> players) {
			this.players = players;
			this.playersFinal = ImmutableList.copyOf(players);
		}
		
		public boolean hasNext() {
			return cursor < playersFinal.size();
		}
		
		public Player next() {
			return Bukkit.getPlayer(playersFinal.get(cursor++));
		}
		
		public void remove() {
			players.remove(playersFinal.get(cursor));
		}
		
	}
}
