package de.salocin.engine.utils.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.utils.property.Property;

public class INIConfiguration implements Configuration {
	
	private final Properties config = new Properties();
	private final Map<String, Property<?>> properties = new HashMap<String, Property<?>>();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load(InputStream input) throws IOException {
		config.load(input);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(OutputStream output) throws IOException {
		config.store(output, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Property<?>> getProperties() {
		return properties;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Property<?>> getPropertyValues() {
		return properties.values();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPropertyPresent(String propertyName) {
		return properties.containsKey(propertyName);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Property<?>> T getProperty(String propertyName) {
		Validate.notNull(propertyName);
		
		if (isPropertyPresent(propertyName)) {
			for (Map.Entry<String, Property<?>> entry : properties.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(propertyName)) {
					return (T) entry.getValue();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Property<?>> T registerProperty(String name, T property) {
		T old = this.<T> getProperty(name);
		
		if (old != null) {
			try {
				property.load(old.save());
			} catch (Exception e) {
				throw new IllegalArgumentException("Wrong generic type", e);
			}
			properties.remove(old);
		}
		
		properties.put(name, property);
		
		return property;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearRegisteredProperties() {
		properties.clear();
	}
	
}
