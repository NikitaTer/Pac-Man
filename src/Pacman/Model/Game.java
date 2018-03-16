package Pacman.Model;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Game extends Canvas implements Runnable {

    private boolean  isRunning = false;
    public static final int WIDTH = 700;
    public static final int HIGH = 500;
    public static final String TITLE = "Pac-Man";
    private GraphicsContext gc;
    private Thread thread;

    private Point2D pos;

    public Game() {
        setWidth(Game.WIDTH);
        setHeight(Game.HIGH);

        gc = this.getGraphicsContext2D();
        gc.setFill(Color.YELLOW);
        gc.setStroke(Color.BROWN);
        pos = new Point2D(15, 15);
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
        gc.fillRect(pos.getX(), pos.getY(), 40, 40);
    }

    @Override
    public void run() {
        long LastTime = System.nanoTime();
        long Now;
        double TargetTicks = 60;
        double Delta = 0;
        double ns = 1000000000/TargetTicks;

        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case A:
                        pos.add(pos.getX() - 1, pos.getY());
                        break;
                    case D:
                        pos.add(pos.getX() + 1, pos.getY());
                        System.out.println("worked");
                        break;
                    case W:
                        pos.add(pos.getX(), pos.getY() + 1);
                        break;
                    case S:
                        pos.add(pos.getX(), pos.getY() - 1);
                        break;
                    default:
                        System.out.println("Something wrong\n");
                        break;
                }
            }
        });


        while(isRunning) {
            Now = System.nanoTime();
            Delta+=(Now - LastTime) / ns;
            LastTime = Now;
            while(Delta >=1) {
                tick();
                render();
                Delta--;
            }
        }
    }
}
