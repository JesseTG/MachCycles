package com.corundumgames.mach.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.corundumgames.mach.events.EventListener.Event;

public class EventBus {
    private ObjectMap<Class<? extends Event>, Array<EventListener>> listeners;

    public EventBus() {
        this.listeners = new ObjectMap<>();
    }

    /**
     * @param e
     * @return True if the event was handled
     */
    public boolean fire(Event e) {
        if (e == null) return false;

        Gdx.app.log("Events", e + " fired");
        Array<EventListener> el = this.listeners.get(e.getClass());
        if (el == null) {
            Gdx.app.log("Events", "No listener registered for " + ClassReflection.getSimpleName(e.getClass()));
            return false;
        }
        else {
            boolean handled = false;
            for (EventListener l : new Array.ArrayIterator<>(el)) {
                handled |= l.handle(e);
            }
            return handled;
        }
    }

    public void register(EventListener listener, Class<? extends Event> type) {
        if (listener == null) throw new IllegalArgumentException("listener can't be null");
        if (type == null) throw new IllegalArgumentException("type can't be null");

        if (!this.listeners.containsKey(type)) {
            this.listeners.put(type, new Array<EventListener>());
        }

        Array<EventListener> el = this.listeners.get(type);
        el.add(listener);
    }

    public boolean unregister(EventListener listener, Class<? extends Event> type) {
        if (listener == null) throw new IllegalArgumentException("listener can't be null");
        if (type == null) throw new IllegalArgumentException("type can't be null");

        boolean removed = false;
        if (this.listeners.containsKey(type)) {
            removed |= this.listeners.get(type).removeValue(listener, true);
        }

        return removed;
    }

    public boolean unregister(EventListener listener) {
        boolean removed = false;
        if (listener != null) {
            for (Array<EventListener> el : this.listeners.values()) {
                removed |= el.removeValue(listener, true);
            }
        }
        return removed;
    }

    public void clear() {
        this.listeners.clear();
    }
}
