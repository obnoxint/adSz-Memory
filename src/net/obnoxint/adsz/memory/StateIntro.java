package net.obnoxint.adsz.memory;

import java.io.IOException;

class StateIntro extends State {
    
    private static final int DELAY = 5000;
    
    private final long started = System.currentTimeMillis();

    StateIntro() throws IOException {
        super(State.STATE_INTRO);
    }

    @Override
    void draw() {
        if (System.currentTimeMillis() > started + DELAY){
            State.setActiveState(STATE_PAIRSELECTION);
        }else{
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
    }

    @Override
    void handleInput() {}

}
