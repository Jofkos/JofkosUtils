package com.jofkos.utils.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
	
	private static final Pattern ph = Pattern.compile("(%[^%]+?%)");
	
	private String[] placeholders;
	protected String string, format;
	
	public Message(String string) {
		Matcher matcher = ph.matcher(string);
		List<String> placeholders = new ArrayList<String>();
		while (matcher.find()) {
			String placeholder = matcher.group(1);
			if (placeholders.indexOf(placeholder) > -1) continue;
			placeholders.add(placeholder);
		}
		this.placeholders = placeholders.toArray(new String[placeholders.size()]);
		setString(string);
	}
	
	public Message(String string, String... placeholders) {
		this.placeholders = placeholders;
		setString(string);
	}
	
	protected void setString(String string) {
		this.string = string;
		
		for (int i = 0; i < placeholders.length; i++) {
			string = string.replace(placeholders[i], "%" + (i+1) + "$s");
		}
		this.format = string;
	}
	
	public String get(Object... objs) {
		return String.format(format, Arrays.copyOf(objs, placeholders.length));
	}
	
	@Override
	public String toString() {
		return this.format;
	}
}
