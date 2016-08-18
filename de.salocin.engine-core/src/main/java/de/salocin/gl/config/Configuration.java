package de.salocin.gl.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import de.salocin.gl.impl.config.ConfigurationImpl;

/**
 * Represents a configuration with multiple properties.
 */
public interface Configuration {
	
	ConfigurationType getConfigurationType();
	
	void load(InputStream input) throws IOException;
	
	void save(OutputStream output) throws IOException;
	
	/**
	 * Returns a Set containing all properties that are stored in this
	 * configuration
	 * 
	 * @return All properties
	 */
	Set<Property<?>> getProperties();
	
	boolean isPropertyPresent(Property<?> property);
	
	boolean isPropertyPresent(String propertyName);
	
	void registerProperty(Property<?> property);
	
	/**
	 * 
	 * @param propertyName
	 * @return
	 * @throws IllegalArgumentException
	 */
	<T extends Property<?>> T getProperty(String propertyName);
	
	default StringProperty getStringProperty(String propertyName) {
		return this.<StringProperty> getProperty(propertyName);
	}
	
	default BooleanProperty getBooleanProperty(String propertyName) {
		return this.<BooleanProperty> getProperty(propertyName);
	}
	
	default DoubleProperty getDoubleProperty(String propertyName) {
		return this.<DoubleProperty> getProperty(propertyName);
	}
	
	default LongProperty getLongProperty(String propertyName) {
		return this.<LongProperty> getProperty(propertyName);
	}
	
	public static Configuration newInstance() {
		return new ConfigurationImpl();
	}
	
	public static Configuration loadFrom(InputStream input) throws IOException {
		Configuration config = newInstance();
		config.load(input);
		return config;
	}
	
}
