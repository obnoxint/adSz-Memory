package net.obnoxint.adsz.memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.lwjgl.util.Point;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

class StatePairSelection extends State {

    private static final String RES_BUTTON_NEXT = "next" + Main.FILE_EXT_PNG;
    private static final String RES_BUTTON_PREVIOUS = "prev" + Main.FILE_EXT_PNG;
    private static final String RES_BUTTON_PLAY = "play" + Main.FILE_EXT_PNG;
    private static final String RES_HOVER_PREFIX = "h_";

    private static final Box BUTTON_NEXT = new Box(new Point(250, 325), new Point(300, 350));
    private static final Box BUTTON_PREVIOUS = new Box(new Point(250, 425), new Point(300, 450));
    private static final Box BUTTON_PLAY = new Box(new Point(400, 375), new Point(600, 425));
    private static final Point SELECTION_POS = new Point(190, 360);

    private boolean switchCooldown = false;

    Difficulty difficulty = Difficulty.getNext(Difficulty._28);

    private final Map<String, Texture> textures = new HashMap<>();

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

    private void drawBox(final Box box, final String id, final boolean hover) {
        drawTexturedBox(box, getTexture(id, hover));

    }

    private void drawSelection() {
        Main.instance.font.drawString(SELECTION_POS.getX(), SELECTION_POS.getY(), difficulty.count() / 2 + " Paare");

    }

    private Texture getTexture(final String id, final boolean hover) {
        final String n = (hover) ? RES_HOVER_PREFIX + id : id;
        Texture r = textures.get(n);
        if (r == null) {
            try (FileInputStream fis = new FileInputStream(new File(Main.instance.getRessourceFolder(), n))) {
                r = TextureLoader.getTexture(Main.TEXTURE_TYPE_PNG, fis);
                textures.put(n, r);
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "Textur konnte nicht geladen werden: " + n, "Fehler", JOptionPane.ERROR_MESSAGE);
                Main.writeStackTrace(e);
                System.exit(Main.EXIT_CODE_ERROR);
            }
        }
        return r;
    }

    private boolean isHover(final Box box) {
        return box.isInside(Main.instance.mouse_abs_x, Main.instance.mouse_abs_y);
    }

    @Override
    void draw() {
        drawBox(BUTTON_NEXT, RES_BUTTON_NEXT, isHover(BUTTON_NEXT));
        drawBox(BUTTON_PREVIOUS, RES_BUTTON_PREVIOUS, isHover(BUTTON_PREVIOUS));
        drawBox(BUTTON_PLAY, RES_BUTTON_PLAY, isHover(BUTTON_PLAY));
        drawSelection();
    }

    @Override
    void handleInput() {
        if (Main.instance.mouse_but_l) {
            if (isHover(BUTTON_NEXT)) {
                changeDifficulty(true);
            } else if (isHover(BUTTON_PREVIOUS)) {
                changeDifficulty(false);
            } else if (isHover(BUTTON_PLAY)) {
                setActiveState(STATE_PLAY);
            }
        }
    }

}
