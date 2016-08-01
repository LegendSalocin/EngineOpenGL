package de.salocin.gl.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.plugin.Plugin;

public class ResourceLocation {
	
	private Plugin owner;
	private String path;
	private Type type;
	
	private ResourceLocation() {
	}
	
	public static ResourceLocation newInstance(Plugin owner, File file) {
		Validate.notNull(owner);
		Validate.notNull(file);
		
		ResourceLocation loc = new ResourceLocation();
		loc.type = Type.FILE_SYSTEM;
		loc.owner = owner;
		loc.path = file.getAbsolutePath();
		return loc;
	}
	
	public static ResourceLocation newInstance(Plugin owner, String packagePath) {
		Validate.notNull(owner);
		Validate.notBlank(packagePath);
		
		ResourceLocation loc = new ResourceLocation();
		loc.type = Type.PACKAGE;
		loc.owner = owner;
		loc.path = packagePath;
		return loc;
	}
	
	public Type getType() {
		return type;
	}
	
	public Plugin getOwner() {
		return owner;
	}
	
	public String getPath() {
		return path;
	}
	
	public URL toURL() throws MalformedURLException {
		switch (type) {
		case FILE_SYSTEM:
			return new URL(path);
		case PACKAGE:
			return ResourceLocation.class.getResource("/" + path.replace("/", "."));
		default:
			return null;
		}
	}
	
	public InputStream openStream() throws IOException {
		return toURL().openStream();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ResourceLocation) {
			ResourceLocation loc = (ResourceLocation) obj;
			return loc.type == type && loc.owner.equals(owner) && loc.path.equals(path);
		}
		
		return false;
	}
	
	public static enum Type {
		FILE_SYSTEM, PACKAGE;
	}
	
}
