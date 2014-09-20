package com.corundumgames.mach.events;

import com.badlogic.gdx.utils.Pool.Poolable;

public interface EventListener {

    public boolean handle(Event event);
    
    public static abstract class Event implements Poolable {
    }
}
