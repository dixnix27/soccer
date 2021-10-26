import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SoccerField {
    public static AtomicBoolean gameActive;

    public SoccerField() {
        gameActive = new AtomicBoolean(true);
    }

    public void play() throws InterruptedException, IOException {

        if (!gameActive.get()) {
            synchronized (this) {
                wait();
            }
        }
        Thread.sleep(2000);
    }

// METODA NOUA PENTRU RESTARTAREA JOCULUI
    public void reload() throws InterruptedException, IOException {
        if (!gameActive.get()) {
            synchronized (this) {
                System.out.println("Game Reloaded");
                this.setGameActive(true);
                notifyAll();
            }
        }
        Thread.sleep(1000);
    }



    public void setGameActive(Boolean v) {
        gameActive.set(v);
    }

    public boolean getGameActive() {
        return gameActive.get();
    }
}
