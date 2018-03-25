package Pacman.Game.GameObjects;

public class Ghost implements Runnable {

    private boolean isWeak = false;
    private boolean isRunning = false;
    private Thread thread;

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
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
