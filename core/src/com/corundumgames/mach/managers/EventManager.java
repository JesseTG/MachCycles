package com.corundumgames.mach.managers;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.utils.Pool;
import com.corundumgames.mach.events.EventBus;

public class EventManager extends Manager {
    
    private EventBus events;
    

    public EventManager() {
        this.events = new EventBus();
    }
    
    public EventBus getEventBus() {
        return this.events;
    }
}
