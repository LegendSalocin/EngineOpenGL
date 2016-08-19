package de.salocin.engine.utils.core;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class DetailedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private final LinkedHashMap<String, Object> details = new LinkedHashMap<String, Object>();
	
	public DetailedException() {
		super();
	}
	
	public DetailedException(Throwable cause) {
		super(cause);
	}
	
	public DetailedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DetailedException addDetail(String name, Object value) {
		details.put(name, value);
		return this;
	}
	
	@Override
	public void printStackTrace(PrintStream s) {
		if (s == System.err) {
			throw new IllegalArgumentException("Use DetailedException.log() to print the exception.");
		}
		
		s.print(buildHeader());
		super.printStackTrace(s);
	}
	
	@Override
	public void printStackTrace(PrintWriter s) {
		s.print(buildHeader());
		super.printStackTrace(s);
	}
	
	private String buildHeader() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream print = new PrintStream(baos);
		
		print.println();
		print.println("Exception occured.");
		print.println();
		print.println("--------------------------------------------------------------------");
		print.println("Details:");
		print.println();
		for (Map.Entry<String, Object> entry : details.entrySet()) {
			print.println(String.format("%-15s = %-15s", entry.getKey(), entry.getValue()));
		}
		print.println("--------------------------------------------------------------------");
		print.println("Stack Trace:");
		print.println();
		
		return new String(baos.toByteArray());
	}
	
}
