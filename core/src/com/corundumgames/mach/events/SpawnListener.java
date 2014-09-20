package com.corundumgames.mach.events;

import com.artemis.Entity;

public interface SpawnListener extends EventListener {

    public boolean spawned(SpawnEvent event);

    public static class SpawnEvent extends EventListener.Event {
        private Entity entity;

        public SpawnEvent() {}

        public SpawnEvent(Entity e) {
            this.entity = e;
        }

        public Entity getEntity() {
            return this.entity;
        }

        public void setEntity(Entity e) {
            this.entity = e;
        }

        @Override
        public String toString() {
            return "SpawnEvent[" + this.entity + "]";
        }

        @Override
        public void reset() {
            this.entity = null;
        }
    }
}
