package com.corundumgames.mach.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.IntArray;
import com.corundumgames.mach.components.MachComponent;
import com.corundumgames.mach.components.PlayerInputComponent;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
    @Wire
    private ComponentMapper<PlayerInputComponent> playerInputMapper;

    @Wire
    private ComponentMapper<MachComponent> machMapper;

    private IntArray downQueue;
    private IntArray upQueue;

    public PlayerInputSystem() {
        super(Aspect.getAspectForAll(PlayerInputComponent.class, MachComponent.class));

        this.downQueue = new IntArray();
        this.upQueue = new IntArray();
    }

    @Override
    protected void process(Entity e) {
        PlayerInputComponent pic = playerInputMapper.get(e);

        if (pic.canMove) {
            MachComponent mc = machMapper.get(e);

            for (int i = 0; i < this.downQueue.size; ++i) {
                int key = this.downQueue.get(i);
                if (pic.left.contains(key)) {
                    mc.action = MachComponent.Action.TURN_LEFT;
                }
                else if (pic.right.contains(key)) {
                    mc.action = MachComponent.Action.TURN_RIGHT;
                }
            }            
        }
    }
    
    @Override
    protected void end() {
        this.downQueue.clear();
        this.upQueue.clear();
    }

    @Override
    public boolean keyDown(int keycode) {
        this.downQueue.add(keycode);

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        this.upQueue.add(keycode);

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
