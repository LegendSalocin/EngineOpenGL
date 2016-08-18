package de.salocin.gl.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import de.salocin.gl.Engine;
import de.salocin.gl.util.exception.DetailedException;

public class EventManager {
	
	private static EventManager instance;
	protected Logger logger;
	private ArrayList<ListenerMethod> listenerMethods = new ArrayList<ListenerMethod>();
	
	public static void init() throws Throwable {
		if (isInitialized()) {
			throw new RuntimeException("EventManager already initialized.");
		}
		
		if (!Engine.isStarted()) {
			throw new RuntimeException("You have to call Engine.start() to start the engine first.");
		}
		
		instance = new EventManager();
		instance.logger = Logger.getLogger("EventManager");
		instance.logger.setParent(Engine.ENGINE_LOGGER);
	}
	
	public static boolean isInitialized() {
		return instance != null;
	}
	
	public static EventManager getInstance() {
		if (!isInitialized()) {
			throw new RuntimeException("EventManager is not initialized.");
		}
		
		return instance;
	}
	
	public void registerListener(Listener listener) {
		Validate.notNull(listener);
		
		for (Method method : listener.getClass().getMethods()) {
			if (method.isAnnotationPresent(EventHandler.class)) {
				if (method.getParameterCount() == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
					try {
						ListenerMethod m = new ListenerMethod();
						m.listenerInstance = listener;
						m.method = method;
						m.eventClass = (Class<?>) method.getParameterTypes()[0];
						m.ignoreCanceled = method.getAnnotation(EventHandler.class).ignoreCanceled();
						listenerMethods.add(m);
						logger.info("Method found and registered. (" + listener.getClass().getName() + "#" + method.getName() + ")");
					} catch (Exception e) {
						new DetailedException(e).log();
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param event
	 * @return If the event is canceled or not
	 */
	public boolean callEvent(Event event) {
		Validate.notNull(event);
		
		for (ListenerMethod method : listenerMethods) {
			if (method.eventClass.isAssignableFrom(event.getClass())) {
				if (!method.ignoreCanceled || !event.isCanceled()) {
					try {
						method.method.invoke(method.listenerInstance, event);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						new DetailedException(e).log();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return event.isCanceled();
	}
	
	private class ListenerMethod {
		private Object listenerInstance;
		private Method method;
		private Class<?> eventClass;
		private boolean ignoreCanceled;
	}
	
}
