package com.corundumgames.mach.managers;

import com.artemis.Manager;
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
