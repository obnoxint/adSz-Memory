package net.obnoxint.adsz.memory;

import java.awt.Font;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

class StateIntro extends State {

    private static final int DELAY = 5000;

    private final long started = System.currentTimeMillis();

    StateIntro() throws IOException {
        super(State.STATE_INTRO);
    }

    @SuppressWarnings("unchecked")
    private void initializeFont() {
        final UnicodeFont f = new UnicodeFont(new Font("MV Boli", Font.BOLD, 36));
        f.getEffects().add(new ColorEffect(new java.awt.Color(Main.RGB_OCHER_LIGHT[0] + 128, Main.RGB_OCHER_LIGHT[1] + 128, Main.RGB_OCHER_LIGHT[2] + 128)));
        f.addAsciiGlyphs();
        try {
            f.loadGlyphs();
        } catch (final SlickException e) {
            JOptionPane.showMessageDialog(null, "Beim Laden der Schriftart ist ein Fehler aufgetreten: " + e.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            Main.writeStackTrace(e);
            System.exit(Main.EXIT_CODE_ERROR);
        }
        Main.instance.font = f;
    }

    @Override
    void draw() {
        if (Main.instance.font == null) {
            initializeFont();
        }
        if (System.currentTimeMillis() > started + DELAY) {
            State.setActiveState(STATE_PAIRSELECTION);
        } else {
            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {}
        }
    }

    @Override
    void handleInput() {}

}
