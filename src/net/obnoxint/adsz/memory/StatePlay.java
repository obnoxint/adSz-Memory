package net.obnoxint.adsz.memory;

import java.io.IOException;

class StatePlay extends State {

    private MemoryGame game = null;

    StatePlay() throws IOException {
        super(State.STATE_PLAY);
    }

    private void drawSummary() {
        // TODO Auto-generated method stub
    }

    @Override
    void draw() {
        if (game.isGameSolved()) {
            drawSummary();
        } else {
            game.draw();
        }
    }

    @Override
    void handleInput() {
        final int mx = Main.instance.mouse_abs_x;
        final int my = Main.instance.mouse_abs_y;
        final boolean mbl = Main.instance.mouse_but_l;
        if (mbl) {
            if (!game.isGameSolved()) {
                game.selectAt(mx, my);
            }
        }
    }

    void init() {
        game = new MemoryGame(((StatePairSelection) State.getState(STATE_PAIRSELECTION)).difficulty);
    }

}
