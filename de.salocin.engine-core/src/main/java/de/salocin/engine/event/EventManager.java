package de.salocin.engine.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.commons.lang3.Validate;

import de.salocin.engine.Engine;
import de.salocin.engine.util.exception.EngineException;

public class EventManager {
	
	private static EventManager instance;
	private ArrayList<ListenerMethod> listenerMethods = new ArrayList<ListenerMethod>();
	
	public static void init() throws Throwable {
		if (isInitialized()) {
			throw new RuntimeException("EventManager already initialized.");
		}
		
		if (!Engine.isStarted()) {
			throw new RuntimeException("You have to call Engine.start() to start the engine first.");
		}
		
		instance = new EventManager();
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
					} catch (Exception e) {
						new EngineException(e).log();
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
						new EngineException(e).log();
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
