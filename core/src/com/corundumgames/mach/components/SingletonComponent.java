package com.corundumgames.mach.components;

import com.artemis.Entity;
import com.artemis.PackedComponent;

public abstract class SingletonComponent extends PackedComponent {
    @Override
    protected void forEntity(Entity e) {}

    @Override
    protected void reset() {}

    @Override
    protected void ensureCapacity(int id) {}
}
