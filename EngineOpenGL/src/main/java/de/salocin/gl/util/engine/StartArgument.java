package de.salocin.gl.util.engine;

import java.util.ArrayList;

public class StartArgument extends AbstractConfiguration {
	
	private static final String enginePrefix = "engine:";
	private static ArrayList<StartArgument> startArguments = new ArrayList<StartArgument>();
	private static String[] raw;
	
	public static final StartArgument SCENE_WIDTH = new StartArgument(enginePrefix + "sceneWidth").setDefault(800);
	public static final StartArgument SCENE_HEIGHT = new StartArgument(enginePrefix + "sceneHeight").setDefault(600);
	public static final StartArgument SHOW_CLASS_NAMES_AND_METHODS = new StartArgument(enginePrefix + "simpleLog").setDefault(false);
	public static final StartArgument NORMAL_STD = new StartArgument(enginePrefix + "normalStd").setDefault(true);
	
	public StartArgument(String key) {
		super(key);
		startArguments.add(this);
	}
	
	public StartArgument setDefault(Object o) {
		setValue(o);
		return this;
	}
	
	public static void reinit(String[] args) {
		raw = args;
		startArguments.clear();
		
		parse(args);
	}
	
	private static void parse(String[] args) {
		if (args == null) {
			return;
		}
		
		for (String arg : args) {
			String key = arg;
			String value = String.valueOf(true);
			
			if (arg.contains("=")) {
				String[] parts = arg.split("=");
				key = parts[0];
				value = parts[1];
			}
			
			StartArgument a = null;
			
			for (StartArgument startArgument : startArguments) {
				if (startArgument.getKey().equalsIgnoreCase(key)) {
					a = startArgument;
				}
			}
			
			if (a == null) {
				a = new StartArgument(key);
			}
			
			a.setValue(value);
		}
	}
	
	public static String[] getArguments() {
		return raw;
	}
	
}
