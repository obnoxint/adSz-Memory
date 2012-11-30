package net.obnoxint.adsz.memory;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

import java.io.IOException;

import org.lwjgl.util.Point;

class StatePlay extends State {

    private static final String RES_BUTTON_EXIT = "exit";
    private static final String RES_BUTTON_BACK = "back";
    private static final String RES_BUTTON_RESTART = "restart";

    private static final Box BUTTON_EXIT = new Box(new Point(945, 160), new Point(1009, 224));
    private static final Box BUTTON_BACK = new Box(new Point(945, 360), new Point(1009, 424));
    private static final Box BUTTON_RESTART = new Box(new Point(945, 560), new Point(1009, 624));

    private MemoryGame game = null;

    StatePlay() throws IOException {
        super(State.STATE_PLAY);
    }

    private void drawButtons() {
        drawBox(BUTTON_EXIT, RES_BUTTON_EXIT);
        drawBox(BUTTON_BACK, RES_BUTTON_BACK);
        drawBox(BUTTON_RESTART, RES_BUTTON_RESTART);
    }

    private void drawSummary() {
        // TODO Auto-generated method stub
    }

    @Override
    void draw() {
        drawButtons();
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
            if (isMouseInside(BUTTON_EXIT)) {
                if (showConfirmDialog(null, "Möchtest Du das Spiel wirklich beenden?", "Spiel beenden...", YES_NO_OPTION) == YES_OPTION) {
                    System.exit(Main.EXIT_CODE_OK);
                }
            } else if (isMouseInside(BUTTON_BACK)) {
                if (showConfirmDialog(null, "Möchtest Du das laufende Spiel beenden und zur Paarauswahl zurück kehren?", "Zur Paarauswahl zurückkehren...", YES_NO_OPTION) == YES_OPTION) {
                    game = null;
                    setActiveState(STATE_PAIRSELECTION);
                }
            } else if (isMouseInside(BUTTON_RESTART)) {
                if (showConfirmDialog(null, "Möchtest Du das laufende Spiel beenden und von vorn beginnen?", "Spiel neu starten...", YES_NO_OPTION) == YES_OPTION) {
                    init();
                }
            } else if (!game.isGameSolved()) {
                game.selectAt(mx, my);
            }
        }
    }

    void init() {
        game = new MemoryGame(((StatePairSelection) State.getState(STATE_PAIRSELECTION)).difficulty);
    }

}
