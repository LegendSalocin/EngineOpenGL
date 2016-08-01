package de.salocin.gl.log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import de.salocin.gl.Engine;
import de.salocin.gl.util.DetailedException;
import de.salocin.gl.util.EngineException;
import de.salocin.gl.util.StartArgument;

public class Log {
	
	public static final Logger ENGINE_LOGGER = Logger.getLogger("Engine");
	public static final Formatter LOG_FORMATTER = new Formatter() {
		
		public final SimpleDateFormat dateFormatter = new SimpleDateFormat();
		
		@Override
		public String format(LogRecord record) {
			try {
				final Logger logger = Logger.getLogger(record.getLoggerName());
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				final PrintStream print = new PrintStream(baos);
				
				/* Date */
				print.print(dateFormatter.format(new Date(record.getMillis())));
				print.print(" ");
				
				/* Source class & method */
				if (StartArgument.SHOW_CLASS_NAMES_AND_METHODS.getBooleanValue()) {
					if (record.getSourceClassName() != null && !record.getSourceClassName().trim().isEmpty()) {
						print.print(record.getSourceClassName() + "#" + record.getSourceMethodName());
						print.print(" ");
					}
				}
				
				/* Logger Name */
				Logger parent = logger;
				String loggerNames = "";
				
				do {
					loggerNames = "[" + parent.getName() + "] " + loggerNames;
					parent = parent.getParent();
				} while (parent != null && !parent.equals(ENGINE_LOGGER.getParent()));
				
				print.print(loggerNames);
				
				/* Level */
				print.print(record.getLevel() + ": ");
				
				/* Message */
				if (record.getMessage() != null && !record.getMessage().trim().isEmpty()) {
					print.print(record.getMessage());
				}
				
				print.println();
				
				/* Exception */
				if (record.getThrown() != null) {
					record.getThrown().printStackTrace(print);
				}
				
				/* Build string */
				String string = new String(baos.toByteArray(), "UTF-8");
				String[] lines = string.split(System.lineSeparator());
				StringBuilder result = new StringBuilder(lines[0]).append(System.lineSeparator());
				
				for (int i = 1; i < lines.length; i++) {
					result.append("\t").append(lines[i]).append(System.lineSeparator());
				}
				
				return result.toString();
			} catch (Exception e) {
				new DetailedException(e).addDetail("Message", record.getMessage()).addDetail("Source Class", record.getSourceClassName()).addDetail("Source Method", record.getSourceMethodName())
						.addDetail("Thrown", record.getThrown()).log();
				return "";
			}
		}
	};
	
	private static boolean initialized;
	
	private Log() {
	}
	
	public static void init() throws Throwable {
		if (initialized) {
			throw new RuntimeException("Logger already initialized.");
		}
		
		if (!Engine.isStarted()) {
			throw new RuntimeException("You have to call Engine.start() to start the engine first.");
		}
		
		ENGINE_LOGGER.setUseParentHandlers(false);
		ENGINE_LOGGER.addHandler(new StreamHandler(System.out, LOG_FORMATTER));
		ENGINE_LOGGER.addHandler(new FileHandler("latest", LOG_FORMATTER));
		
		if (!StartArgument.NORMAL_STD.getBooleanValue()) {
			System.setOut(new PrintStream(new LogOutputStream(ENGINE_LOGGER, Level.INFO), true));
			System.setErr(new PrintStream(new LogOutputStream(ENGINE_LOGGER, Level.SEVERE), true));
		}
		
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			new EngineException(e).log();
		});
		
		initialized = true;
	}
	
}
