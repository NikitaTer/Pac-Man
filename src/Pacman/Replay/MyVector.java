package Pacman.Replay;

import java.io.Serializable;
import java.util.Vector;

public class MyVector<E> extends Vector<E> implements Serializable {
    public void copy(Vector<E> vector) {
        if (this.size() != 0)
            this.clear();
        this.addAll(vector);
    }
}
