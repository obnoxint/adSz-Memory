package net.obnoxint.adsz.memory;

import java.io.IOException;

class StatePlay extends State {

    private MemoryGame game = null;

    StatePlay() throws IOException {
        super(State.STATE_PLAY);
    }

    @Override
    void draw() {
        game.draw();
    }

    @Override
    void handleInput() {
        final int mx = Main.instance.mouse_abs_x;
        final int my = Main.instance.mouse_abs_y;
        final boolean mbl = Main.instance.mouse_but_l;
        if (mbl) {
            game.selectAt(mx, my);
        }
        if (game.isGameSolved()) {
            State.setActiveState(STATE_END);
        }
    }

    void init() {
        game = new MemoryGame(((StatePairSelection) State.getState(STATE_PAIRSELECTION)).difficulty);
    }

}
