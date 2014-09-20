package com.corundumgames.mach.events;

public interface IntegerChangedListener extends EventListener {
    public boolean changed(IntegerChangedEvent event);

    public static class IntegerChangedEvent extends Event {
        public int oldValue;
        public int newValue;
        public String name;

        public IntegerChangedEvent() {}

        @Override
        public void reset() {
            this.oldValue = 0;
            this.newValue = 0;
            this.name = null;
        }

        public int getOldValue() {
            return this.oldValue;
        }

        public int getNewValue() {
            return this.newValue;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return "IntegerChangedEvent[" + this.name + ", " + this.oldValue + " -> " + this.newValue + "]";
        }
    }

    public static final String BOMBS = "bombs";
    public static final String LIVES = "lives";
    public static final String SCORE = "score";
    public static final String HEALTH = "health";
    public static final String MULTIPLIER = "multiplier";
}
