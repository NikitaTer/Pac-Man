package Pacman.Game;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    SpriteAnimation(ImageView imageView, int count, int columns, int offsetX, int offsetY, int width, int height, Duration duration) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        setCycleDuration(duration);
    }

    @Override
    protected void interpolate(double frac) {
        final int index;
        if(frac < 1/3)
            index = 0;
        else if(frac < 2/3)
            index = 1;
        else
            index = 2;
        final int x = index;
        final int y = 0;
    }
}
