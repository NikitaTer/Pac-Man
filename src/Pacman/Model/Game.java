package Pacman.Model;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Game extends Canvas implements Runnable {

    private boolean  isRunning = false;
    public static final int WIDTH = 700;
    public static final int HIGH = 500;
    public static final String TITLE = "Pac-Man";
    private Thread thread;

    public Game() {
        setWidth(Game.WIDTH);
        setHeight(Game.HIGH);
    }

    public synchronized void start() {
        if(isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(!isRunning) return;
        isRunning = false;
        try {
            thread.join();
        }
        catch(InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Stoped");
    }

    private void tick() {

    }

    private void render() {

    }

    @Override
    public void run() {
        int fps = 0;
        long LastTime = System.nanoTime();
        long Now;
        double TargetTicks = 60;
        double Timer = System.currentTimeMillis();
        double Delta = 0;
        double ns = 1000000000/TargetTicks;

        while(isRunning) {
            Now = System.nanoTime();
            Delta+=(Now - LastTime) / ns;
            LastTime = Now;

            while(Delta >=1) {
                tick();
                render();
                fps++;
                Delta--;
            }

            if(System.currentTimeMillis() - Timer >=  1000) {
                System.out.println(fps);
                fps = 0;
                Timer+=1000;
            }
        }
    }
}
