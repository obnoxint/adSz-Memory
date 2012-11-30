package net.obnoxint.adsz.memory;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.lwjgl.util.Point;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

abstract class State {

    private static final String FILE_BG_PREFIX = "bg_";
    private static final Box BACKGROUND = new Box(new Point(0, 0), new Point(Main.DISPLAY_WIDTH, Main.DISPLAY_HEIGHT));

    private static Map<Integer, State> states = new HashMap<>();
    private static State activeState = null;

    static final int STATE_INTRO = 0;
    static final int STATE_PAIRSELECTION = 1;
    static final int STATE_PLAY = 2;

    static final Map<String, Texture> textures = new HashMap<>();

    protected static State getState(final int id) {
        State r = null;
        if (states.containsKey(id)) {
            r = states.get(id);
        } else {
            try {
                switch (id) {
                case STATE_INTRO:
                    r = new StateIntro();
                break;
                case STATE_PAIRSELECTION:
                    r = new StatePairSelection();
                break;
                case STATE_PLAY:
                    r = new StatePlay();
                break;
                }
                if (r != null) {
                    states.put(r.id, r);
                }
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "Beim initialisieren des Spielzustands " + id + " ist ein Problem aufgetreten: " + e.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                Main.writeStackTrace(e);
                System.exit(Main.EXIT_CODE_ERROR);
            }
        }
        return r;
    }

    static void drawTexturedBox(final Box box, final Texture texture) {
        texture.bind();
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0f, 0f);
            glVertex2i(box.getUpperLeft().getX(), box.getUpperLeft().getY());
            glTexCoord2f(texture.getWidth(), 0f);
            glVertex2i(box.getUpperLeft().getX() + box.getWidth(), box.getUpperLeft().getY());
            glTexCoord2f(texture.getWidth(), texture.getHeight());
            glVertex2i(box.getUpperLeft().getX() + box.getWidth(), box.getUpperLeft().getY() + box.getHeight());
            glTexCoord2f(0f, texture.getHeight());
            glVertex2i(box.getUpperLeft().getX(), box.getUpperLeft().getY() + box.getHeight());
        }
        glEnd();
    }

    static State getActiveState() {
        return activeState;
    }

    static Texture getTexture(final String id) {
        Texture r = textures.get(id);
        if (r == null) {
            try (FileInputStream fis = new FileInputStream(new File(Main.instance.getRessourceFolder(), id + Main.FILE_EXT_PNG))) {
                r = TextureLoader.getTexture(Main.TEXTURE_TYPE_PNG, fis);
                textures.put(id, r);
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "Textur konnte nicht geladen werden: " + id, "Fehler", JOptionPane.ERROR_MESSAGE);
                Main.writeStackTrace(e);
                System.exit(Main.EXIT_CODE_ERROR);
            }
        }
        return r;
    }

    static void setActiveState(final int id) {
        activeState = getState(id);
        if (id == STATE_PLAY) {
            ((StatePlay) activeState).init();
        }
    }

    final int id;
    final Texture bg;

    protected State(final int id) throws IOException {
        this.id = id;
        try (FileInputStream fis = new FileInputStream(new File(Main.instance.getRessourceFolder(), FILE_BG_PREFIX + id + Main.FILE_EXT_PNG))) {
            this.bg = TextureLoader.getTexture(Main.TEXTURE_TYPE_PNG, fis);
        }
    }

    abstract void draw();

    final void drawBackground() {
        drawTexturedBox(BACKGROUND, bg);
    }

    abstract void handleInput();

}
