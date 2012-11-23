package net.obnoxint.adsz.memory;

import org.lwjgl.util.Point;

class Pair {

    final Box[] cards = new Box[2];
    final int id;

    boolean[] uncovered = { false, false };

    Pair(final Point first, final Point second, final int id) {
        this.cards[0] = new Box(new Point(first), new Point(first.getX() + Main.GAME_CARD_SIZE, first.getY() + Main.GAME_CARD_SIZE));
        this.cards[1] = new Box(new Point(second), new Point(second.getX() + Main.GAME_CARD_SIZE, second.getY() + Main.GAME_CARD_SIZE));
        this.id = id;
    }

    void cover() {
        if (!isBothUncovered()) {
            uncovered[0] = false;
            uncovered[1] = false;
        }
    }

    boolean isBothUncovered() {
        return uncovered[0] && uncovered[1];
    }

    boolean uncover(final int mx, final int my) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].isInside(mx, my) && !uncovered[i]) {
                uncovered[i] = true;
                return true;
            }
        }
        return false;
    }

}
