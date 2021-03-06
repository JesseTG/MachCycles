package com.corundumgames.mach.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.corundumgames.mach.MachCycles;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = 800;
        config.height = 600;
        config.foregroundFPS = MachCycles.FPS;
        config.backgroundFPS = MachCycles.FPS / 3;
        config.samples = 2;
        new LwjglApplication(new MachCycles(), config);
    }
}
