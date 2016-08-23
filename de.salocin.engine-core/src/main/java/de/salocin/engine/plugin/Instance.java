package de.salocin.engine.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a variable with that annotation which holds a plugin instance. The
 * plugin to hold can be specified with {@link #value()}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Instance {
	
	/**
	 * The plugin id of the plugin to store in the variable
	 * 
	 * @return The plugin id
	 */
	String value();
	
}
