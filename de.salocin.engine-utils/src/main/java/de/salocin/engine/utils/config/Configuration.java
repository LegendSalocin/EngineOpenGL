package de.salocin.engine.utils.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

/**
 * Represents a configuration with multiple properties.
 */
public interface Configuration {
	
	/**
	 * Loads the contents from the given input stream. <b>Existing properties
	 * will not be cleared!</b> You have to call
	 * {@link #clearRegisteredProperties()} by yourself.
	 * 
	 * @param input
	 *            The {@link InputStream} to load from
	 * @throws IOException
	 *             if there was an error while loading the configuration
	 */
	void load(InputStream input) throws IOException;
	
	/**
	 * Saves all registered properties to the {@link OutputStream}
	 * 
	 * @param output
	 *            The {@link OutputStream} to write to
	 * @throws IOException
	 *             if there was an error while saving the configuration
	 */
	void save(OutputStream output) throws IOException;
	
	/**
	 * Returns a {@link Set} containing all properties that are registered.
	 * 
	 * @return All properties
	 */
	Set<Property<?>> getProperties();
	
	/**
	 * Returns <code>true</code> if a property exists.
	 * 
	 * @param property
	 *            The property to check
	 * @return <code>true</code> if the property exists, otherwise
	 *         <code>false</code>
	 */
	boolean isPropertyPresent(Property<?> property);
	
	/**
	 * Returns <code>true</code> if a property exists.
	 * 
	 * @param property
	 *            The property to check
	 * @return <code>true</code> if the property exists, otherwise
	 *         <code>false</code>
	 */
	boolean isPropertyPresent(String propertyName);
	
	/**
	 * Retrieves a property in the configuration. You can optionally add a
	 * generic type, so the property will automatically converted to that value.
	 * 
	 * @param propertyName
	 *            The property's name to find
	 * @return The property if it was found, otherwise <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the given generic type does not match with the property's
	 *             value
	 */
	<T extends Property<?>> T getProperty(String propertyName);
	
	/**
	 * Registers a new property. If a property with this name already exists,
	 * its value will automatically be copied and it will be removed.
	 * 
	 * @param property
	 *            The property to register
	 * @return The same property instance for further use
	 */
	<T extends Property<?>> T registerProperty(T property);
	
	/**
	 * Deletes all registered properties, the configuration keys and values will
	 * not be affected.
	 */
	void clearRegisteredProperties();
	
	/**
	 * Creates a new empty .ini configuration.
	 * 
	 * @return An empty configuration
	 */
	public static Configuration empty() {
		return new INIConfiguration();
	}
	
	/**
	 * Creates a new empty configuration and loads it.
	 * 
	 * @param input
	 *            The {@link InputStream} to load from
	 * @return A loaded configuration
	 * @throws IOException
	 *             if there was an error while loading the configuration
	 */
	public static Configuration loadFrom(InputStream input) throws IOException {
		Configuration config = empty();
		config.load(input);
		return config;
	}
	
}
