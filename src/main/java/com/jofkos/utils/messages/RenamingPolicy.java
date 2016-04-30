package com.jofkos.utils.messages;

public interface RenamingPolicy {
	
	/**
	 * 
	 * Naming policy that keeps the config keys and field names the same
	 */
	RenamingPolicy KEEP = new RenamingPolicy() {
		
		@Override
		public String toSaveKey(String string) {
			return string;
		};
		
		@Override
		public String toFieldName(String string) {
			return string;
		}
	};
	
	RenamingPolicy LOWERCASE_CONSTANTS = new RenamingPolicy() {

		@Override
		public String toSaveKey(String string) {
			return string.toLowerCase();
		}
		
		@Override
		public String toFieldName(String string) {
			return string.toUpperCase();
		}
		
	};
	
	/**
	 * Converts a field name to the config format
	 *  
	 * @param string The field name
	 * @return The converted string
	 */	
	public String toSaveKey(String string);
	
	/**
	 * Converts a config key back to the Field name
	 * 
	 * @param string The config key
	 * @return The field name
	 */
	public String toFieldName(String string);
	
}
