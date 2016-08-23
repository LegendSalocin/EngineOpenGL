package de.salocin.engine.event;

import java.util.ArrayList;

public class CallbackHandler<E extends Event> {
	
	private ArrayList<Callback<E>> callbacks = new ArrayList<Callback<E>>();
	
	public void add(Callback<E> callback) {
		callbacks.add(callback);
	}
	
	public void call(E event) {
		for (Callback<E> callback : callbacks) {
			callback.call(event);
		}
	}
	
	public ArrayList<Callback<E>> getCallbacks() {
		return callbacks;
	}
	
}
