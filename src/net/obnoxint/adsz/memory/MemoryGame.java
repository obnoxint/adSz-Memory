package net.obnoxint.adsz.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.lwjgl.util.Point;
import org.newdawn.slick.opengl.Texture;

final class MemoryGame {

    static int hideDelay = 1000;

    private final Difficulty d;
    private final MemoryCard[][] field;

    private MemoryCard sel1 = null, sel2 = null;
    private boolean locked = false;
    private int solvedPairs = 0;

    long started;
    long finished;
    int mistakes = 0;

    MemoryGame(final Difficulty difficulty) {
        this.d = difficulty;
        this.field = new MemoryCard[d.hCount][d.vCount];
        init();
    }

    private void init() {
        final Map<String, Texture> c = new HashMap<>();
        final String[] n = Main.instance.cards.keySet().toArray(new String[c.size()]);
        final Random r = new Random();

        // Initialize field array
        for (int i = 0; i < d.hCount; i++) {
            for (int j = 0; j < d.vCount; j++) {
                field[i][j] = null;
            }
        }

        // randomly select cards to use in this game
        for (int i = 0; i < d.pairs(); i++) {
            final String cn = n[r.nextInt(n.length)];
            if (cn.equals(Main.GAME_HIDDEN_CARD_NAME) || c.containsKey(cn)) {
                i--;
            } else {
                c.put(cn, Main.instance.cards.get(cn));
            }
        }

        // randomly assign pairs of cards to the field array
        for (final String s : c.keySet()) {
            for (int i = 0; i < 2; i++) {
                boolean b = false;
                while (!b) {

                    final int h = r.nextInt(d.hCount);
                    final int v = r.nextInt(d.vCount);
                    if (field[h][v] == null) {
                        final Point ul = d.upperLeftOf(h, v);
                        field[h][v] = new MemoryCard(s, ul, new Point(ul.getX() + Main.GAME_CARD_SIZE, ul.getY() + Main.GAME_CARD_SIZE));
                        b = true;
                    }

                }
            }
        }

        started = System.currentTimeMillis();
    }

    private void select(final int h, final int v) {
        final MemoryCard c = field[h][v];

        if (!c.solved && !locked) {
            if (sel1 == null) {
                sel1 = c;
                c.hidden = false;
            } else if (sel2 == null && sel1 != null && !sel1.getUpperLeft().equals(c.getUpperLeft())) {
                sel2 = c;
                c.hidden = false;
            }

            if (sel1 != null && sel2 != null) {
                if (sel1.getName().equals(sel2.getName())) {
                    sel1.solved = true;
                    sel2.solved = true;
                    sel1 = null;
                    sel2 = null;
                    solvedPairs++;
                } else {
                    new Thread() {

                        @Override
                        public void run() {
                            try {
                                locked = true;
                                mistakes++;
                                sleep(hideDelay);
                            } catch (final InterruptedException e) {} finally {
                                sel1.hidden = true;
                                sel2.hidden = true;
                                sel1 = null;
                                sel2 = null;
                                locked = false;
                            }
                        }
                    }.start();
                }
            }

            if (isGameSolved()) {
                finished = System.currentTimeMillis();
            }
        }

    }

    void draw() {
        for (int i = 0; i < d.hCount; i++) {
            for (int j = 0; j < d.vCount; j++) {
                field[i][j].draw();
            }
        }
    }

    boolean isGameSolved() {
        return solvedPairs == d.pairs();
    }

    void selectAt(final int mx, final int my) {
        for (int i = 0; i < d.hCount; i++) {
            for (int j = 0; j < d.vCount; j++) {
                if (field[i][j].isInside(mx, my)) {
                    select(i, j);
                }
            }
        }
    }

}
