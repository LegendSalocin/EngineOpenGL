package de.salocin.engine.utils.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.Validate;

public class INIConfiguration implements Configuration {
	
	private final Properties config = new Properties();
	private final Set<Property<?>> properties = new HashSet<Property<?>>();
	
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
	public Set<Property<?>> getProperties() {
		return properties;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPropertyPresent(Property<?> property) {
		return properties.contains(property);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPropertyPresent(String propertyName) {
		return getProperty(propertyName) != null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Property<?>> T getProperty(String propertyName) {
		Validate.notNull(propertyName);
		
		for (Property<?> property : properties) {
			if (property.getName().equalsIgnoreCase(propertyName)) {
				try {
					return (T) property;
				} catch (ClassCastException e) {
					throw new IllegalArgumentException("The given type conflicts with the property's value.");
				}
			}
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T extends Property<?>> T registerProperty(T property) {
		T old = this.<T> getProperty(property.getName());
		
		if (old != null) {
			try {
				property.setObject(old.getObject());
			} catch (Exception e) {
				throw new IllegalArgumentException("Wrong generic type", e);
			}
			properties.remove(old);
		}
		
		properties.add(property);
		
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
