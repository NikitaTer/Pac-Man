package Pacman.Game.GameObjects;

public class Space {
    private boolean isPoint = true;
    private boolean isCherry = false;
    private boolean isUltimate = false;

    public Space(boolean isPoint, boolean isCherry, boolean isUltimate) {
        this.isPoint = isPoint;
        this.isCherry = isCherry;
        this.isUltimate = isUltimate;
    }

    public Space() {

    }

    public boolean isEmpty() {
        if (isPoint || isCherry || isUltimate)
            return false;
        return true;
    }

    public void eaten() {
        if (isPoint) isPoint = false;
        else if (isCherry) isCherry = false;
        else if (isUltimate) isUltimate = false;
    }

    public boolean isPoint() {
        return isPoint;
    }

    public boolean isCherry() {
        return isCherry;
    }

    public boolean isUltimate() {
        return isUltimate;
    }
}
