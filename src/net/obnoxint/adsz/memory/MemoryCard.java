package net.obnoxint.adsz.memory;

import org.lwjgl.util.Point;

final class MemoryCard extends Box {

    private final String name;

    boolean hidden = true;
    boolean solved = false;

    MemoryCard(final String name, final Point upperLeft, final Point lowerRight) {
        super(upperLeft, lowerRight);
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final MemoryCard other = (MemoryCard) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    void draw() {
        State.drawTexturedBox(this, hidden ? Main.instance.cards.get(Main.GAME_HIDDEN_CARD_NAME) : Main.instance.cards.get(name));
    }

    String getName() {
        return name;
    }

}
