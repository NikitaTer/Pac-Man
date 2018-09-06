package Pacman.Replay;

import java.io.Serializable;

public class PacManData implements Serializable {
    private long delay;
    private int way;

    public PacManData(long delay, int way) {
        this.delay = delay;
        this.way = way;
    }

    public int getWay() {
        return way;
    }

    public long getDelay() {
        return delay;
    }
}
