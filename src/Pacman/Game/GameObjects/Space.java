package Pacman.Game.GameObjects;

public class Space {

    public boolean isPoint = true;
    public boolean isUltimate = false;
    public boolean isCherry = false;

    public Space(boolean isPoint, boolean isUltimate, boolean isCherry) {
        this.isPoint = isPoint;
        this.isUltimate = isUltimate;
        this.isCherry = isCherry;
    }

    public Space() {
        isPoint = true;
        isCherry = false;
        isUltimate = false;
    }

    public boolean isEmpty() {
        if(isPoint || isCherry || isUltimate)
            return false;
        return true;
    }

    public void Empty() {
        if (isPoint) isPoint = false;
        else if (isCherry) isCherry = false;
        else if (isUltimate) isUltimate = false;
    }
}
