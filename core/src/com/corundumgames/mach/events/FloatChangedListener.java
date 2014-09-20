package com.corundumgames.mach.events;

public interface FloatChangedListener extends EventListener {
    public boolean changed(FloatChangedEvent event);

    public static class FloatChangedEvent extends Event {
        public float oldValue;
        public float newValue;
        public String name;

        public FloatChangedEvent() {}

        @Override
        public void reset() {
            this.oldValue = 0;
            this.newValue = 0;
            this.name = null;
        }

        public float getOldValue() {
            return this.oldValue;
        }

        public float getNewValue() {
            return this.newValue;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return "FloatChangedEvent[" + this.name + ", " + this.oldValue + " -> " + this.newValue + "]";
        }
    }

    public static final String MASS_LEFT = "mass-left";
}
