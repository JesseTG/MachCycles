package com.corundumgames.mach.events;

import com.artemis.Entity;

public interface DamageListener extends EventListener {
    public boolean damaged(DamageEvent event);

    public static class DamageEvent extends Event {
        public Entity entity;
        public int amount;

        public DamageEvent() {}

        public Entity getEntity() {
            return this.entity;
        }

        public int getDamage() {
            return this.amount;
        }

        @Override
        public String toString() {
            return "DamageEvent[" + this.entity + ", " + this.amount + "]";
        }

        @Override
        public void reset() {
            this.entity = null;
            this.amount = 0;
        }
    }
}
