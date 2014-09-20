package com.corundumgames.mach.events;

import com.artemis.Entity;

public interface DeathListener extends EventListener {

    public boolean died(DeathEvent event);

    public static class DeathEvent extends EventListener.Event {
        private Entity entity;

        public DeathEvent() {}

        public Entity getEntity() {
            return this.entity;
        }

        public void setEntity(Entity e) {
            this.entity = e;
        }

        @Override
        public String toString() {
            return "DeathEvent[" + this.entity + "]";
        }

        @Override
        public void reset() {
            this.entity = null;
        }
    }
}
