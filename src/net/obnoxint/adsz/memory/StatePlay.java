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

    private static final Point TEXT_SCORE = new Point(100, 250);

    private MemoryGame game = null;
    private int[] summary;

    StatePlay() throws IOException {
        super(State.STATE_PLAY);
    }

    private void createSummary() {
        summary = new int[2];

        final int d = ((StatePairSelection) getState(STATE_PAIRSELECTION)).difficulty.pairs() * 100;
        final int t = Math.round((game.finished - game.started) / 1000);
        final int m = game.mistakes * 10;
        int s = d - t - m;

        if (s < 0) {
            s = 0;
        }

        final int p = Math.round((100f / d) * s);

        summary[0] = s;
        summary[1] = p;
    }

    private void drawButtons() {
        drawBox(BUTTON_EXIT, RES_BUTTON_EXIT);
        drawBox(BUTTON_BACK, RES_BUTTON_BACK);
        drawBox(BUTTON_RESTART, RES_BUTTON_RESTART);
    }

    private void drawSummary() {
        if (summary == null) {
            createSummary();
        }
        final int s = summary[0];
        final int p = summary[1];

        String c;
        if (p >= 90) {
            c = "Wow!\nSehr beeindruckend!";
        } else if (p >= 80) {
            c = "Hervorragend!";
        } else if (p >= 60) {
            c = "Sehr gut!";
        } else if (p >= 40) {
            c = "Gut!\nAber das kannst Du besser!";
        } else if (p >= 20) {
            c = "Übe weiter!\nDas kannst Du besser!";
        } else {
            c = "Schade!\nGib nicht auf und versuche es nochmal!";
        }

        Main.instance.font.drawString(TEXT_SCORE.getX(), TEXT_SCORE.getY(), "Du hast " + s + " Punkte erreicht (" + p + "%).");
        Main.instance.font.drawString(TEXT_SCORE.getX() + 50, TEXT_SCORE.getY() + 100, c);
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
        summary = null;
    }

}
