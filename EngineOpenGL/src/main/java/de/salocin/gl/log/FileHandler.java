package de.salocin.gl.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Formatter;
import java.util.logging.StreamHandler;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.util.exception.DetailedException;
import de.salocin.gl.util.exception.EngineException;

public class FileHandler extends StreamHandler {
	
	private File current;
	private FileOutputStream currentOutput;
	private String fileName;
	private int fileCount;
	
	public FileHandler(String fileName, Formatter formatter) throws EngineException {
		this(fileName, 10, formatter);
	}
	
	public FileHandler(String fileName, int maxFileCount, Formatter formatter) throws EngineException {
		this(new File("log"), fileName, maxFileCount, formatter);
	}
	
	public FileHandler(File dir, String fileName, int maxFileCount, Formatter formatter) throws EngineException {
		setFormatter(formatter);
		Validate.notNull(dir);
		Validate.notNull(fileName);
		
		dir = dir.getAbsoluteFile();
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		current = new File(dir, fileName + ".log");
		
		try {
			if (!current.exists()) {
				current.createNewFile();
			}
			currentOutput = new FileOutputStream(current);
			setOutputStream(currentOutput);
		} catch (IOException e) {
			throw new DetailedException(e).addDetail("File", current.getAbsolutePath());
		}
	}
	
	public File getCurrent() {
		return current;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public int getMaxFileCount() {
		return fileCount;
	}
	
	@Override
	public synchronized void flush() {
		super.flush();
		System.out.println("flush");
	}
	
}
