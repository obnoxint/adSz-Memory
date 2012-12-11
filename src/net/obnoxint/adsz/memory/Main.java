package net.obnoxint.adsz.memory;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public final class Main {

    public static final int VERSION = 1;

    static final int EXIT_CODE_ERROR = -1;
    static final int EXIT_CODE_OK = 0;

    static final int DISPLAY_WIDTH = 1024;
    static final int DISPLAY_HEIGHT = 768;
    static final int DISPLAY_FPS = 60;
    static final String DISPLAY_TITLE = "adSz - Memory";

    static final String FILE_NAME_RESSOURCEFOLDER = "res";
    static final String FILE_NAME_CARDSFOLDER = "cards";
    static final String FILE_NAME_PROPERTIES = "game.properties";
    static final String FILE_EXT_PNG = ".png";

    static final String TEXTURE_TYPE_PNG = "PNG";

    static final byte[] RGB_OCHER_LIGHT = { 127, 87, -55 };
    static final byte[] RGB_LAVENDER_DARK = { -95, -85, -39 };

    static final int GAME_CARD_SIZE = 96;
    static final int GAME_BORDER = 10;
    static final int GAME_CARD_MARGIN = 10;
    static final String GAME_HIDDEN_CARD_NAME = "_hidden";

    static Main instance = null;

    public static void main(final String[] args) {
        if (instance == null) {
            try {
                instance = new Main();
                try {
                    instance.init();
                } catch (final LWJGLException e) {
                    JOptionPane.showMessageDialog(null, "Beim Starten der Anwendung ist ein Problem aufgetreten: " + e.getLocalizedMessage()
                            + "\n\nEin Fehlerbericht wird erzeugt.", "Initialisierungsfehler", JOptionPane.ERROR_MESSAGE);
                    writeStackTrace(e);
                    System.exit(EXIT_CODE_ERROR);
                }
                instance.run();
                instance.die();
                System.exit(EXIT_CODE_OK);
            } catch (final Exception e) {
                JOptionPane.showMessageDialog(null, "Bei der Ausführung der Anwendung ist ein Problem aufgetreten: " + e.getLocalizedMessage()
                        + "\n\nEin Fehlerbericht wird erzeugt.", "Unbehandelte Ausnahme", JOptionPane.ERROR_MESSAGE);
                writeStackTrace(e);
            }
        }
    }

    static void writeStackTrace(final Throwable throwable) {
        final File f = new File("error_" + System.currentTimeMillis());
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            throwable.printStackTrace(pw);
            JOptionPane.showMessageDialog(null, "Ein Fehlerbericht wurde gespeichert:\n" + f.getAbsolutePath());
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, "Ein Fehlerbericht konnte nicht gespeichert werden. Bitte überprüfen Sie, ob Sie über die notwendigen Rechte zum Schreiben der Datei verfügen:\n"
                    + f.getAbsolutePath(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private File ressourceFolder = null;
    private File cardsFolder = null;

    Map<String, Texture> cards = new HashMap<>();

    UnicodeFont font = null;

    // State variables for mouse cursor position, movement and button state
    int mouse_abs_x = 0;
    int mouse_abs_y = 0;
    int mouse_dyn_x = 0;
    int mouse_dyn_y = 0;
    boolean mouse_but_l = false;

    private Properties properties;

    private Main() {}

    private void die() {
        Display.destroy();
    }

    private void init() throws LWJGLException {

        Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        Display.setTitle(DISPLAY_TITLE);
        Display.create();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, DISPLAY_WIDTH, DISPLAY_HEIGHT, 0, 1, -1);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Difficulty.availableCards = loadCards();

        loadProperties();

    }

    private int loadCards() {
        final File f = getCardsFolder();
        final String[] l = f.list(new FilenameFilter() {

            @Override
            public boolean accept(final File dir, final String name) {
                return name.endsWith(FILE_EXT_PNG);
            }

        });
        for (final String s : l) {
            try (FileInputStream fis = new FileInputStream(new File(f, s))) {
                final Texture t = TextureLoader.getTexture(TEXTURE_TYPE_PNG, fis);
                cards.put(s.substring(0, s.length() - FILE_EXT_PNG.length()), t);
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "Die Kartentextur \"" + s + "\" konnte nicht geladen werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
                writeStackTrace(e);
                System.exit(EXIT_CODE_ERROR);
            }
        }

        if (cards.size() < 4) {
            JOptionPane.showMessageDialog(null, "Es befinden sich nicht genügend Kartentexturen im Verzeichnis \"cards\".\nDie minimale Anzahl beträgt 4 (inkl. der Textur für verdeckte Karten \""
                    + GAME_HIDDEN_CARD_NAME + "\").", "Nicht genügend Texturen gefunden.", JOptionPane.ERROR_MESSAGE);
            System.exit(EXIT_CODE_OK);
        }

        return l.length - 1;

    }

    private void loadProperties() {
        properties = new Properties();

        try (FileInputStream fis = new FileInputStream(new File(FILE_NAME_PROPERTIES))) {
            properties.load(fis);
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, "Beim laden der Eigenschaftendatei \"" + FILE_NAME_PROPERTIES + "\" ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
            writeStackTrace(e);
            System.exit(EXIT_CODE_ERROR);
        }

        String prop = properties.getProperty("pairs").trim();
        if (prop != null && !prop.isEmpty()) {
            final Set<Difficulty> set = new HashSet<>();
            final String[] split = prop.split(",");

            for (final String s : split) {
                try {
                    final int i = Integer.parseInt(s);
                    final Difficulty d = Difficulty.getByPairs(i);
                    if (d != null) {
                        set.add(d);
                    }
                } catch (final NumberFormatException e) {}
            }

            Difficulty.limitedDifficulties = set;

            try {
                Difficulty.getNext(Difficulty._28);
            } catch (final StackOverflowError e) {
                JOptionPane.showMessageDialog(null, "Aufgrund der Anzahl der verfügbaren Kartentexturen und der Einschränkung der Paarmöglichkeiten in der Datei \""
                        + FILE_NAME_PROPERTIES + "\"\nkann keine zulässige Paarauswahl zustande kommen.\n\nBitte überprüfen Sie die Einstellungen.", "Konfigurationsfehler", JOptionPane.ERROR_MESSAGE);
                System.exit(EXIT_CODE_ERROR);
            }
        }
        
        prop = properties.getProperty("hideDelay");
        if (prop != null && !prop.isEmpty()){
            try{
                int delay = Integer.parseInt(prop);
                MemoryGame.hideDelay = delay;
            }catch(NumberFormatException e){};
        }
    }

    private void pollMouse() {
        mouse_abs_x = Mouse.getX();
        mouse_abs_y = Display.getHeight() - Mouse.getY(); // Y-coordinate is based on the "bottom".
        mouse_dyn_x = Mouse.getDX();
        mouse_dyn_y = -Mouse.getDY();
        mouse_but_l = Mouse.isButtonDown(0);
    }

    private void run() {
        while (!Display.isCloseRequested()) {
            if (Display.isActive()) {
                glClear(GL_COLOR_BUFFER_BIT);
                if (State.getActiveState() == null) {
                    State.setActiveState(State.STATE_INTRO);
                } else if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    State.setActiveState(State.STATE_PAIRSELECTION);
                }
                pollMouse();
                State.getActiveState().handleInput();
                State.getActiveState().drawBackground();
                State.getActiveState().draw();
            } else {
                try {
                    Thread.sleep(50);
                } catch (final InterruptedException e) {}
            }
            Display.update();
            Display.sync(DISPLAY_FPS);
        }
    }

    File getCardsFolder() {
        if (cardsFolder == null) {
            cardsFolder = new File(FILE_NAME_CARDSFOLDER);
        }
        return cardsFolder;
    }

    File getRessourceFolder() {
        if (ressourceFolder == null) {
            ressourceFolder = new File(FILE_NAME_RESSOURCEFOLDER);
        }
        return ressourceFolder;
    }

}
