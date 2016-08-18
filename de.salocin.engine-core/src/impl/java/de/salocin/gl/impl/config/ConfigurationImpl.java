package de.salocin.gl.impl.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.config.Configuration;
import de.salocin.gl.config.ConfigurationType;
import de.salocin.gl.config.Property;
import de.salocin.gl.util.exception.EngineException;

public class ConfigurationImpl implements Configuration {
	
	private final Properties properties = new Properties();
	private final Set<Property<?>> propertySet = new HashSet<Property<?>>();
	
	@Override
	public ConfigurationType getConfigurationType() {
		return ConfigurationType.INI;
	}
	
	@Override
	public Set<Property<?>> getProperties() {
		return propertySet;
	}
	
	@Override
	public boolean isPropertyPresent(Property<?> property) {
		return isPropertyPresent(property.getName());
	}
	
	@Override
	public boolean isPropertyPresent(String propertyName) {
		return getProperty(propertyName) != null;
	}
	
	@Override
	public void registerProperty(Property<?> property) {
		Validate.notNull(property);
		
		if (isPropertyPresent(property.getName())) {
			throw new IllegalArgumentException("This property is already registered.");
		} else {
			propertySet.add(property);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Property<?>> T getProperty(String propertyName) {
		for (Property<?> property : propertySet) {
			if (property.getName().equalsIgnoreCase(propertyName)) {
				try {
					return (T) property;
				} catch (ClassCastException e) {
					throw new IllegalArgumentException("Could not convert property", e);
				}
			}
		}
		
		return null;
	}
	
	@Override
	public void load(InputStream input) throws IOException {
		properties.load(input);
		onLoad();
	}
	
	protected void onLoad() {
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = entry.getKey().toString();
			Object value = entry.getValue();
			
			Property<?> property = getProperty(key);
			
			if (property != null) {
				try {
					property.setObject(value);
				} catch (Exception e) {
					new EngineException(e).log();
				}
			} else {
				registerProperty(Property.newInstance(key, value));
			}
		}
	}
	
	@Override
	public void save(OutputStream output) throws IOException {
		onSave();
		properties.store(output, null);
	}
	
	protected void onSave() {
		for (Property<?> property : propertySet) {
			properties.setProperty(property.getName(), property.getObject().toString());
		}
	}
	
}
