package net.obnoxint.adsz.memory;

import java.io.IOException;

import org.lwjgl.util.Point;

class StatePairSelection extends State {

    private static final String RES_BUTTON_NEXT = "next";
    private static final String RES_BUTTON_PREVIOUS = "prev";
    private static final String RES_BUTTON_PLAY = "play";

    private static final Box BUTTON_NEXT = new Box(new Point(250, 325), new Point(300, 350));
    private static final Box BUTTON_PREVIOUS = new Box(new Point(250, 425), new Point(300, 450));
    private static final Box BUTTON_PLAY = new Box(new Point(400, 375), new Point(600, 425));
    private static final Point SELECTION_POS = new Point(210, 365);

    private boolean switchCooldown = false;

    Difficulty difficulty = Difficulty.getNext(Difficulty._28);

    StatePairSelection() throws IOException {
        super(State.STATE_PAIRSELECTION);
    }

    private void changeDifficulty(final boolean next) {
        if (!switchCooldown) {
            difficulty = (next) ? Difficulty.getNext(difficulty) : Difficulty.getPrevious(difficulty);
            new Thread() {

                @Override
                public void run() {
                    try {
                        switchCooldown = true;
                        sleep(200);
                    } catch (final InterruptedException e) {} finally {
                        switchCooldown = false;
                    }
                }

            }.start();
        }
    }

    private void drawSelection() {
        Main.instance.font.drawString(SELECTION_POS.getX(), SELECTION_POS.getY(), difficulty.count() / 2 + " Paare");
    }

    @Override
    void draw() {
        drawBox(BUTTON_NEXT, RES_BUTTON_NEXT);
        drawBox(BUTTON_PREVIOUS, RES_BUTTON_PREVIOUS);
        drawBox(BUTTON_PLAY, RES_BUTTON_PLAY);
        drawSelection();
    }

    @Override
    void handleInput() {
        if (Main.instance.mouse_but_l) {
            if (isMouseInside(BUTTON_NEXT)) {
                changeDifficulty(true);
            } else if (isMouseInside(BUTTON_PREVIOUS)) {
                changeDifficulty(false);
            } else if (isMouseInside(BUTTON_PLAY)) {
                setActiveState(STATE_PLAY);
                try { // Try to prevent accidently selecting a card in the next state.
                    Main.instance.mouse_but_l = false;
                    Thread.sleep(200);
                } catch (final InterruptedException e) {}
            }
        }
    }

}
