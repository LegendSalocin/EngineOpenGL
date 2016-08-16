package de.salocin.gl.config;

import java.io.File;
import java.io.IOException;

import de.salocin.gl.impl.config.FileConfigurationImpl;

public interface FileConfiguration extends Configuration {
	
	void load(File file) throws IOException;
	
	void save() throws IOException;
	
	void save(File file) throws IOException;
	
	public static FileConfiguration newInstance() {
		return new FileConfigurationImpl();
	}
	
	public static FileConfiguration loadFrom(File file) throws IOException {
		FileConfiguration config = newInstance();
		config.load(file);
		return config;
	}
}
