package com.corundumgames.mach.components;

import com.artemis.PooledComponent;

public class BackgroundComponent extends PooledComponent {
    public float z;
    
    @Override
    protected void reset() {
        this.z = 0;
    }

}
