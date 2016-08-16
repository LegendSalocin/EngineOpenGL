package de.salocin.gl.impl.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.salocin.gl.config.FileConfiguration;

public class FileConfigurationImpl extends ConfigurationImpl implements FileConfiguration {
	
	private File defaultFile;
	
	@Override
	public void load(File file) throws IOException {
		this.defaultFile = file;
		
		if (file.exists()) {
			load(new FileInputStream(file));
		}
	}
	
	@Override
	public void save() throws IOException {
		if (defaultFile != null) {
			save(defaultFile);
		}
	}
	
	@Override
	public void save(File file) throws IOException {
		file.createNewFile();
		save(new FileOutputStream(file));
	}
	
}
