package de.salocin.engine.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import de.salocin.engine.util.exception.EngineException;

public class PluginDescriptionFile {
	
	protected String mainClassName;
	protected Class<?> mainClass;
	protected String pluginName;
	protected String pluginVersion;
	
	protected PluginDescriptionFile() {
	}
	
	public static PluginDescriptionFile load(File file) throws IOException {
		return load(new FileInputStream(file));
	}
	
	public static PluginDescriptionFile load(InputStream in) throws IOException {
		PluginDescriptionFile pdf = new PluginDescriptionFile();
		Properties properties = new Properties();
		properties.load(in);
		
		pdf.mainClassName = properties.getProperty("main");
		
		try {
			pdf.mainClass = Class.forName(pdf.mainClassName);
		} catch (ClassNotFoundException e) {
			new EngineException(e).log();
			pdf.mainClass = null;
		}
		
		pdf.pluginName = properties.getProperty("name");
		pdf.pluginVersion = properties.getProperty("version");
		return pdf;
	}
	
	public Class<?> getMainClass() {
		return mainClass;
	}
	
	public String getMainClassName() {
		return mainClassName;
	}
	
	public String getPluginName() {
		return pluginName;
	}
	
	public String getPluginVersion() {
		return pluginVersion;
	}
	
}
